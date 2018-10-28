package server.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import common.CatalogClient;
import common.Credentials;

public class UserHandler {
	
	User user;
	 private final Map<String, User> users = Collections.synchronizedMap(new HashMap<>());

	public void createAccount(Credentials credentials, CatalogClient remoteObject) {
		User newUser = new User(credentials, remoteObject);
		users.put(credentials.getUsername(), newUser);
	}
	
	public void login(Credentials credentials, CatalogClient remoteObject) {
		User newUser = new User(credentials, remoteObject);
		users.put(credentials.getUsername(), newUser);
	}
	
	/**
	 * Sends a notification message to the user with the specified username. This is the owner of the file that has been accessed.
	 * @param username The username of the user who is to receive the notification
	 * @param fileName The name of the file that has been accessed 
	 */
	public void notifyUser(String username, String msg) {
		if (users.get(username) == null) {
			return;
		}
		users.get(username).send(msg);
	}
    
    public void removeUser(String username) {
        users.remove(username);
    }
}
