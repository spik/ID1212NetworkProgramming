package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Handles response from server.
 */
public interface CatalogClient extends Remote{
	 public void sendNotification(String response) throws RemoteException;
}
