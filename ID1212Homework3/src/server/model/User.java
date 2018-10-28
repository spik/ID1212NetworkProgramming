package server.model;

import java.rmi.RemoteException;
import java.util.Random;

import common.CatalogClient;
import common.Credentials;

public class User {
	
	private String username;
	private String password;
	private long id;
	private CatalogClient client;
	
	public User(Credentials credentials, CatalogClient client) {
		Random random = new Random();
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
		this.id = random.nextLong();
		this.client = client;
	}
	
	public String getUsername() {
		return username;
	}
	
	public long getId() {
		return id;
	}
	
	public void send(String notification) {
		try {
			client.sendNotification(notification);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
