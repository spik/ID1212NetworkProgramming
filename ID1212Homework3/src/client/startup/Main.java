package client.startup;

import java.rmi.RemoteException;

import client.view.StartClient;

public class Main {

	public static void main(String[] args) {
		try {
			new StartClient().start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
