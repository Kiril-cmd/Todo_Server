package jat.commonClasses;
import java.io.IOException;
import java.net.Socket;

import jat.ServiceLocator;
import jat.appClasses.App_Model;
import messaging.*;
import userData.Account;

public class Client {
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private App_Model model;
	private Socket socket;
	private Account account;
	
	public Client(App_Model model, Socket socket) {
		this.model = model;
		this.socket = socket;
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(true) {
					Message msg = Message.receiveMessage(socket);
					if (msg instanceof CreateLogin_msg) {
						CreateLogin_msg lgMsg = (CreateLogin_msg) msg;
						account = model.createAccount(lgMsg.getUserName(), lgMsg.getPassword());
					} else if (msg instanceof Login_msg) {
						
					} else if (msg instanceof ChangePassword_msg) {
						
					} else if (msg instanceof CreateToDo_msg) {
						
					} else if (msg instanceof GetToDo_msg) {
						
					} else if (msg instanceof DeleteToDo_msg) {
						
					} else if (msg instanceof ListToDos_msg) {
						
					} else if (msg instanceof Ping_msg) {
						
					}			
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void stop() {
		try {
			serviceLocator.getLogger().info("Stop client");
			this.socket.close();
		} catch (IOException e) {
			serviceLocator.getLogger().info(e.toString());
		}
	}

}
