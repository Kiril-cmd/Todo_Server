package jat.appClasses;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import jat.ServiceLocator;
import jat.abstractClasses.Model;
import jat.commonClasses.Client;
import messaging.Result_msg;
import userData.Account;
import userData.Priority;
import userData.ToDo;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
	private ArrayList<Client> clients = new ArrayList<>();
	private ArrayList<Account> accounts = new ArrayList<>();
	
    ServiceLocator serviceLocator;
    ServerSocket listener;
    private volatile boolean stop = false;
//    private Iterator<Account> acIterator = accounts.iterator();
    
    public App_Model() {
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
    }
    
    public void startServer(int port) {
    	serviceLocator.getLogger().info("Start Server");
    	try {
    		listener = new ServerSocket(port, 10, null);
    		Runnable runnable = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = listener.accept();
							Client client = new Client(App_Model.this, socket);
							synchronized(clients) {
								clients.add(client);	
							}
						} catch (IOException e) {
							serviceLocator.getLogger().info(e.toString());
						}
					}					
				}   			
    		};
    		Thread thread = new Thread(runnable);
    		thread.start();
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
    
    public synchronized void createAccount(String username, String password, Client client) {
    	// check if account with the requested userName already exists
    	for (Account account : accounts) {
    		if (account.getUserName().equals(username)) {
    			answerInvalidRequest(client);
    			return;
    		}
    	}
    	
    	// creates new account with the data given by the user
    	Account account = new Account(username, password);
    	accounts.add(account);
    	answerValidRequest(client);
    }

	public void login(String username, String password, Client client) {
		boolean exists = false;
		Result_msg msg = null;
		Iterator<Account> iterator = accounts.iterator();
		
		while (iterator.hasNext() && exists == false) {
			Account account = iterator.next();
			if (account.getUserName().equals(username) && account.getPassword().equals(password)) {
				client.setToken();
				msg = new Result_msg("true", client.getToken());
				exists = true;
			} else if (!iterator.hasNext() && !exists) {
				msg = new Result_msg("false");
			}
		}
		client.send(msg);	
	}
	
	public void changePassword(String newPassword, String token, Client client) {
		Result_msg msg = null;		
		if (client.getToken().equals(token)) {
			Iterator<Account> iterator = accounts.iterator();
			while (iterator.hasNext()) {
				Account account = iterator.next();
				if (account.getUserName().equals(client.getUserName())) {
					iterator.next().setPassword(newPassword);
					msg = new Result_msg("true");
					break;
				}
			}
		} else {
			msg = new Result_msg("false");
		}
		client.send(msg);
	}
	
	public void Logout(Client client) {
		Result_msg msg;
		if (client.getToken() != null)
			msg = new Result_msg("true");
		else
			msg = new Result_msg("false");
		
		client.setToken();
		client.send(msg);
	}
	
	public void createToDo(String title, Priority priority, String description, 
			LocalDate dueDate, String token, Client client) {	
		Result_msg msg = null;
		
		if (client.getToken().equals(token)) {
			ToDo toDo = new ToDo(title, priority, description, dueDate);
			Iterator<Account> iterator = accounts.iterator();
			
			while (iterator.hasNext()) {
				Account account = iterator.next();
				if (account.getUserName().equals(client.getUserName())) {
					iterator.next().addToDo(toDo);
					msg = new Result_msg("true", Integer.toString(toDo.getId()));
					break;
				}
			}
		} else {
			msg = new Result_msg("false");
		}
		client.send(msg);
	}
	
	public void getToDo(int id, String token, Client client) {
		Result_msg msg = null;
		ToDo toDo = null;
		if (client.getToken().equals(token)) {
			Iterator<Account> iterator = accounts.iterator();
			while (iterator.hasNext()) {
				Account account = iterator.next();
				if (account.getUserName().equals(client.getUserName()))
					toDo = account.getToDo(id);			
			}
			if (toDo != null)
				msg = new Result_msg("true", toDo.toString());
		} else {
			msg = new Result_msg("false");
		}
		client.send(msg);
	}
	
	public void deleteToDo(int id, String token, Client client) {
		Result_msg msg = new Result_msg("false");;
		if (!validateToken(token, msg, client))
			return;
		
		Iterator<Account> iterator = accounts.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();
			if (account.getUserName().equals(client.getUserName())) {
				iterator.next().deleteToDo(id);
				msg = new Result_msg("true", Integer.toString(id));
			}
		}
		client.send(msg);		
	}
	
	public void listToDos(String token, Client client) {
		Result_msg msg = new Result_msg("false");
		if (!validateToken(token, msg, client)) {
			return;
		}
		
		Iterator<Account> iterator = accounts.iterator();
		while (iterator.hasNext()) {
			Account account = iterator.next();
			if (account.getUserName().equals(client.getUserName()))
				msg = new Result_msg("true", account.toDoListToString());
		}
		client.send(msg);
	}
	
	public void getPing(String token, Client client) {
		Result_msg msg;
		
		if (token == null || client.getToken().equals(token))
			msg = new Result_msg("true");
		else
			msg = new Result_msg("false");
		
		client.send(msg);			
	}
	
	public boolean validateToken(String token, Result_msg msg, Client client) {
		boolean valid = true;
		if (!client.getToken().equals(token)) {
			client.send(msg);
			valid = false;
		}
		return valid;
	}
	
	// answers client if only the result must be send
	public void answerValidRequest(Client client) {
		Result_msg msg = new Result_msg("true");
		client.send(msg);
	}
	
	// answers client if the result and data are required
	public void answerValidRequest(Client client, String data) {
		Result_msg msg = new Result_msg("true", data);
		client.send(msg);
	}
	
	// answers the client if the request is invalid
	public void answerInvalidRequest(Client client) {
		Result_msg msg = new Result_msg("false");
		client.send(msg);
	}
 
}
