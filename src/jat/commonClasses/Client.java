package jat.commonClasses;
import java.io.IOException;
import java.net.Socket;

import jat.ServiceLocator;
import jat.appClasses.App_Model;
import messaging.Message;
import messaging.Message;
import userData.Account;

public class Client {
	private ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private App_Model model;
	private Socket socket;
	private Account account;
	
	public Client(App_Model model, Socket socket) {
		this.model = model;
		this.socket = socket;
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while(true) {
		
				}
			}
			
		};
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
