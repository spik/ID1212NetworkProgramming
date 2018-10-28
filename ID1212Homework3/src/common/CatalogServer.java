package common;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import server.model.ExtendedFile;

public interface CatalogServer extends Remote{

	void quit(String username) throws RemoteException;

	String login(Credentials credentials, CatalogClient remoteObject) throws RemoteException;

	void logout(String username) throws RemoteException;

	String createAccount(Credentials credentials, CatalogClient remoteObject) throws RemoteException;

	void deleteAccount(String username) throws RemoteException;

	void uploadFile(byte[] byteArray, String fileName, String fileNameForDatabase, String username, String permissions, boolean notificaiton) throws RemoteException;

	void deleteFile(String fileName, String fileNameForDatabase, String username) throws RemoteException;

	byte[] downloadFile(String fileName, String fileNameForDatabase, String username) throws RemoteException;

	ArrayList<ExtendedFile> viewAllFiles(String username) throws RemoteException;

}
