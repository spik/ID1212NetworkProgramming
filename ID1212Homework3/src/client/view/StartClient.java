package client.view;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import common.Credentials;
import common.FileConverter;
import server.model.ExtendedFile;
import common.CatalogClient;
import common.CatalogServer;


public class StartClient implements Runnable{

	private boolean isReceivingInput = false;
	private CatalogServer server;
	private CatalogClient catalogClient;
	Scanner scanner = new Scanner(System.in);
	private boolean isLoggedIn = false;
	private FileConverter converter = new FileConverter();
	private String username;
	
	public StartClient() throws RemoteException {
		catalogClient = new Printer();
	}

	public void start() {
		if (isReceivingInput) {
			return;
		}
		isReceivingInput = true;
		System.out.println("Welcome! Login or create an account to upload or download a file."
				+ "\nTo upload a file, use command 'Upload File' and specify the path to the file and the permissions."
				+ "\nPermissions can be 'private', 'read' or 'write'."
				+ "\nIf you want notifications,"
				+ "specify this after permissons by writing of example 'true', 'yes' or 'y',\n"
				+ "though any value will work. Otherwise leave this field empty.");
		new Thread(this).start();
	}


	@Override
	public void run() {
		while (isReceivingInput) {
			try {
				UserInput input = new UserInput(scanner.nextLine());	
				Credentials credentials;
				switch(input.getInputType()) {
					case QUIT:
						isReceivingInput = false;
						isLoggedIn = false;
						server.quit(username);
						boolean forceUnexport = false;
				        UnicastRemoteObject.unexportObject(catalogClient, forceUnexport);
						break;
					case LOGIN:
						credentials = enterCredentials();
						username = credentials.getUsername();
						lookupServer(input.getHostWhenLogin());
						String loginResponse = server.login(credentials, catalogClient);
						System.out.println(loginResponse);
						isLoggedIn = true;
						break;
					case LOGOUT:
						server.logout(username);
						System.out.println("You are logged out. Login again to upload or download files");
						isLoggedIn = false;
						break;
					case REGISTER:
						credentials = enterCredentials();
						username = credentials.getUsername();
						lookupServer(input.getHostWhenCreateAccount());
						String registerResponse = server.createAccount(credentials, catalogClient);
						System.out.println(registerResponse);
						isLoggedIn = true;
						break;
					case UNREGISTER:
						if(!isLoggedIn) {
							System.out.println("You must be logged in to unregister");
							break;
						}
						server.deleteAccount(username);
						isLoggedIn = false;
						System.out.println("Your account has been deleted. Quit to end session or create a new account");
						break;
					case UPLOAD:
						if(!isLoggedIn) {
							System.out.println("You must be logged in to upload a file");
							break;
						}
						byte[] bytes = converter.convertFromFileToByteArray(input.getFilePath());
						if(bytes == null) {
							break;
						}
						server.uploadFile(bytes, input.getFileNameForCatalog(), input.getFileNameForDatabase(), username, input.getPermissions(), input.getNotification());
						break;
					case DELETE:
						if(!isLoggedIn) {
							System.out.println("You must be logged in to delete a file");
							break;
						}
						server.deleteFile(input.getFileNameForCatalog(), input.getFileNameForDatabase(), username);
						break;
					case DOWNLOAD:
						if(!isLoggedIn) {
							System.out.println("You must be logged in to download a file");
							break;
						}
						byte[] byteArray = server.downloadFile(input.getFileNameForCatalog(), input.getFileNameForDatabase(), username);
						converter.convertFromByteArrayToFile(byteArray, input.getFileNameForCatalog());
						break;
					case VIEWALL:
						if(!isLoggedIn) {
							System.out.println("You must be logged in view files");
							break;
						}
						printAllFiles(server.viewAllFiles(username));
					case INVALID:
						System.out.println("ERROR: Invalid input!\nPossible actions are:\nCreate Account\nLogin\nLogout\n"
								+ "Delete Account\nUpload\nDownload\nDelete File\nView All");
						break;
				}
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
		}
	}
	
    private void printAllFiles(ArrayList<ExtendedFile> files) {
		for(ExtendedFile file : files) {
			System.out.println(file.toString() + "\n");
		}
		
	}

	private void lookupServer(String host) throws NotBoundException, MalformedURLException, RemoteException {
		server = (CatalogServer) Naming.lookup("//" + host + "/SERVER");
	}


	private Credentials enterCredentials() {
		System.out.print("Username: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		return new Credentials(username, password);
	}

	private class Printer extends UnicastRemoteObject implements CatalogClient {
		private static final long serialVersionUID = 1L;

		protected Printer() throws RemoteException {}

		@Override
		public void sendNotification(String notification) {
			System.out.println(notification);
		}
	}
}
