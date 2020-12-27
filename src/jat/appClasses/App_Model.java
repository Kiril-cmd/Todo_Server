package jat.appClasses;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import jat.ServiceLocator;
import jat.abstractClasses.Model;
import jat.commonClasses.Client;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
	private ArrayList<Client> clients = new ArrayList<>();
	
    ServiceLocator serviceLocator;
    ServerSocket listener;
    private volatile boolean stop = false;
    
    public App_Model() {
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
    }
    
    public void startServer(int port) {
    	serviceLocator.getLogger().info("Start Server");
    	try {
    		listener = new ServerSocket(port, 10, null);
    		Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						Socket socket = listener.accept();
						Client client = new Client(App_Model.this, socket);
						clients.add(client);	
					} catch (IOException e) {
						serviceLocator.getLogger().info(e.toString());
					}					
				}   			
    		};
    	} catch (IOException e) {
    		serviceLocator.getLogger().info(e.toString());
    	}
    }
    
    public void stopServer() {
    	serviceLocator.getLogger().info("Stop running clients");
    	for (Client client : clients)
    		client.stop();
    	
    	serviceLocator.getLogger().info("Stop server");
    	stop = true;
    	if (listener != null) {
    		try {
    			listener.close();
    		} catch (IOException e) {
    		}
    	}
    }
 
}
