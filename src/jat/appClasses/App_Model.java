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
    
    public synchronized void createAccount(String userName, String password, Client client) {
    	// check if account with the requested userName already exists
    	for (Account account : accounts) {
    		if (account.getUserName().equals(userName)) {
    			answerInvalidRequest(client);
    			return;
    		}
    	}
    	
    	// validate account data
    	if (!Account.validateLoginData(userName, password)) {
    		answerInvalidRequest(client);
    		return;
    	}
    	
    	// creates new account with the data given by the user
    	Account account = new Account(userName, password);
    	accounts.add(account);
    	answerValidRequest(client);
    }

	public synchronized void login(String userName, String password, Client client) {
		boolean exists = false;
		Iterator<Account> iterator = accounts.iterator();
		
		while (iterator.hasNext() && exists == false) {
			Account account = iterator.next();
			if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
				client.setToken();
				client.setUserName(userName);
				answerValidRequest(client, client.getToken());
				exists = true;
			}
		}
		if (!exists)
			answerInvalidRequest(client);
	}
	
	public void changePassword(String newPassword, String token, Client client) {	
		if (!client.validateToken(token) || !Account.validatePassword(newPassword)) {
			answerInvalidRequest(client);
			return;
		}
				
		synchronized (accounts) {
			for (Account account : accounts) {
				if (account.getUserName().equals(client.getUserName())) {
					account.setPassword(newPassword);
					answerValidRequest(client);
					break;
				}
			}
		}
	}
	
	public void Logout(Client client) {
		if (client.getToken() != null) {
			client.setToken();
			client.setUserName();
			answerValidRequest(client);
		} else {
			answerInvalidRequest(client);
		}
	}
	
	public void createToDo(String title, Priority priority, String description, 
			LocalDate dueDate, String token, Client client) {	
		
		if (!client.validateToken(token)) {
			answerInvalidRequest(client);
			return;
		}
		
		ToDo toDo = null;
		if (dueDate == null) {
			toDo = new ToDo(title, priority, description);
		} else if (dueDate != null && ToDo.validateDueDate(dueDate)) {
			toDo = new ToDo(title, priority, description, dueDate);
		} else {
			answerInvalidRequest(client);
			return;
		}
		
		synchronized(accounts) {
			for (Account account : accounts) {
				if (account.getUserName().equals(client.getUserName())) {
					account.addToDo(toDo);
					answerValidRequest(client, Integer.toString(toDo.getId()));
					break;
				}
			}
		}
	}
	
	public void getToDo(int id, String token, Client client) {
		if (!client.validateToken(token)) {
			answerInvalidRequest(client);
			return;
		}
		
		boolean accountFound = false;		
		ToDo toDo = null;		
		Iterator<Account> iterator = accounts.iterator();
		
		while (iterator.hasNext() && !accountFound) {
			Account account = iterator.next();
			if (account.getUserName().equals(client.getUserName())) {
				accountFound = true;
				toDo = account.getToDo(id);
			}
		}
		
		if (toDo != null)
			answerValidRequest(client, toDo.toString());
		else
			answerInvalidRequest(client);
	}
	
	public void deleteToDo(int id, String token, Client client) {
		if (!client.validateToken(token)) {
			answerInvalidRequest(client);
			return;
		}
		
		boolean deleted = false;
		for (Account account : accounts) {
			if (account.getUserName().equals(client.getUserName())) {
				deleted = account.deleteToDo(id);
				break;
			}
		}		
		if (deleted)
			answerValidRequest(client, Integer.toString(id));
		else
			answerInvalidRequest(client);
	}
	
	public void listToDos(String token, Client client) {
		if (!client.validateToken(token)) {
			answerInvalidRequest(client);
			return;
		}
		
		boolean accountFound = false;
		Iterator<Account> iterator = accounts.iterator();
		String toDos = null;
		
		while (iterator.hasNext() && !accountFound) {
			Account account = iterator.next();
			if (account.getUserName().equals(client.getUserName())) {
				accountFound = true;
				toDos = account.toDoListToString();
			}
		}
		
		if (toDos != null)
			answerValidRequest(client, toDos);
		else
			answerInvalidRequest(client);
	}
	
	public void getPing(String token, Client client) {
		if ((token == null && client.getToken() == null) 
				|| (token != null && client.validateToken(token)))
			answerValidRequest(client);
		else
			answerInvalidRequest(client);
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
	
	public void removeClient(Client client) {
		clients.remove(client);
	}
 
}
