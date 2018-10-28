package server.startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.controller.ServerController;

public class Main {

    public static void main(String[] args) {
        
            try {
				new Main().startRegistry();
				Naming.rebind("SERVER", new ServerController());
			} catch (RemoteException | MalformedURLException e) {
				e.printStackTrace();
			}
            System.out.println("Server is running.");

    }
    
    private void startRegistry() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException noRegistryIsRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
    }
}
