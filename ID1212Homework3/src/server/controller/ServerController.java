package server.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Handler;

import common.CatalogClient;
import common.CatalogServer;
import common.Credentials;
import common.FileConverter;
import common.UnauthorizedAccessException;
import common.WrongCredentialsException;
import server.integration.DatabaseAccess;
import server.model.ExtendedFile;
import server.model.FileHandler;
import server.model.User;
import server.model.UserHandler;

@SuppressWarnings("serial")
public class ServerController extends UnicastRemoteObject implements CatalogServer{
	
	private DatabaseAccess database = new DatabaseAccess();
	private FileHandler fileHandler = new FileHandler();
	private UserHandler userHandler = new UserHandler();
	private User user;

	public ServerController() throws RemoteException {}

	@Override
	public void quit(String username) throws RemoteException {
		userHandler.removeUser(username);
		
	}

	@Override
	public String login(Credentials credentials, CatalogClient remoteObject) throws RemoteException {
		try {
			database.login(credentials);
			userHandler.login(credentials, remoteObject);
			return "You have been successfully logged in";
		} catch (SQLException e) {
			e.printStackTrace();
			return "An unexpected error has occurred, you are not logged in";
		}
		catch(WrongCredentialsException e) {
			return "Wrong username or password, try again";
		}
		
	}

	@Override
	public void logout(String username) throws RemoteException {
		userHandler.removeUser(username);
		
	}

	@Override
	public String createAccount(Credentials credentials, CatalogClient remoteObject) throws RemoteException {
		try {
			database.createAccount(credentials);
			userHandler.createAccount(credentials, remoteObject);
			return "Account successfully created";
		} catch (SQLException e) {
			e.printStackTrace();
			return "An unexpected error has occurred, account has not been created";
		} catch (WrongCredentialsException e) {
			return "The username is unavailable, choose another one";
		}
	}

	@Override
	public void deleteAccount(String username) throws RemoteException {
		try {
			database.deleteAccount(username);
			userHandler.removeUser(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void uploadFile(byte[] byteArray, String fileName, String fileNameForDatabase, String username, String permissions, boolean notification) throws RemoteException {
		String fileAndUser = null;
		try {
			ExtendedFile extendedFile = fileHandler.upload(byteArray, fileName, fileNameForDatabase, username, permissions, notification);
			fileAndUser = database.insertFile(extendedFile, username);
			userHandler.notifyUser(username, "File successfully Uploaded");
		} catch (SQLException e) {
			userHandler.notifyUser(username, "An unexpected error has occorred, has not been uploaded correctly");
			e.printStackTrace();
		}catch (UnauthorizedAccessException e) {
			userHandler.notifyUser(username, "You don't have access to this resource");
		}
		if(fileAndUser != null) {
			String[] splittedString = fileAndUser.split(" ");
			userHandler.notifyUser(splittedString[0], "Another user has edited your file " + splittedString[1]);
		}
		
	}

	@Override
	public void deleteFile(String fileName, String fileNameForDatabase, String username) throws RemoteException {
		String fileAndUser = null;
		try {
			fileAndUser = database.deleteFileFromDatabase(username, fileNameForDatabase);
			fileHandler.deleteFileFromCatalog(fileName);
			userHandler.notifyUser(username, "File successfully deleted");
		} catch (SQLException e) {
			userHandler.notifyUser(username, "An unexpected error has occorred, has not been deleted correctly");
			e.printStackTrace();
		} catch(UnauthorizedAccessException e) {
			userHandler.notifyUser(username,"You don't have access to this resource");
		}
		if(fileAndUser != null) {
			String[] splittedString = fileAndUser.split(" ");
			userHandler.notifyUser(splittedString[0], "Another user has deleted your file " + splittedString[1]);
		}
		
	}

	@Override
	public byte[] downloadFile(String fileName, String fileNameForDatabase, String username) throws RemoteException {
		
		String fileAndUser = null;
		try {
			fileAndUser = database.getSpecificFile(fileNameForDatabase, username);
			userHandler.notifyUser(username, "File downloaded successfully");
		} catch (SQLException e) {
			userHandler.notifyUser(username, "An unexpected error has occorred, has not been downloaded correctly");
			e.printStackTrace();
		} catch(UnauthorizedAccessException e) {
			userHandler.notifyUser(username, "You don't have access to this resource");
		}
		if(fileAndUser != null) {
			String[] splittedString = fileAndUser.split(" ");
			userHandler.notifyUser(splittedString[0], "Another user has downloaded your file " + splittedString[1]);
		}
		return fileHandler.downloadFile(fileName);
		
	}

	@Override
	public ArrayList<ExtendedFile> viewAllFiles(String username) throws RemoteException{
		ArrayList<ExtendedFile> files = null;
		try {
			files = database.getAllPossibleFiles(username);
		} catch (SQLException e) {
			userHandler.notifyUser(username, "You do not have access to any files");
		}
		return files;
	}

}
