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
	private String token;
	private String userName;
	
	public Client(App_Model model, Socket socket) {
		this.model = model;
		this.socket = socket;
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(true) {
					Message msg = Message.receiveMessage(socket);
					if (msg instanceof CreateLogin_msg) {
						CreateLogin_msg clgMsg = (CreateLogin_msg) msg;
						model.createAccount(clgMsg.getUserName(), clgMsg.getPassword(), Client.this);
					} else if (msg instanceof Login_msg) {
						Login_msg lgMsg = (Login_msg) msg;
						userName = lgMsg.getUserName();
						model.login(userName, lgMsg.getPassword(), Client.this);
					} else if (msg instanceof ChangePassword_msg) {
						ChangePassword_msg cpMsg = (ChangePassword_msg) msg;
						model.changePassword(cpMsg.getPassword(), cpMsg.getToken(), Client.this);						
					} else if (msg instanceof CreateToDo_msg) {
						CreateToDo_msg ctdMsg = (CreateToDo_msg) msg;
						model.createToDo(ctdMsg.getTitle(), ctdMsg.getPriority(), ctdMsg.getDescription(), ctdMsg.getDueDate(), Client.this);
					} else if (msg instanceof GetToDo_msg) {
						
					} else if (msg instanceof DeleteToDo_msg) {
						
					} else if (msg instanceof ListToDos_msg) {
						
					} else if (msg instanceof Ping_msg) {
						
					} else if (msg instanceof Logout_msg) {
						Logout_msg loMsg = (Logout_msg) msg;
						model.Logout(Client.this);
					}
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	public void send(Result_msg msg) {
		msg.sendMessage(socket);
	}
	
	public void stop() {
		try {
			serviceLocator.getLogger().info("Stop client");
			this.socket.close();
		} catch (IOException e) {
			serviceLocator.getLogger().info(e.toString());
		}
	}
	
	public static String generateToken() {
		int stringSize = 20;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String token = "";
		for (int i = 0; i < stringSize; i++) {
			char letter = characters.charAt((int) (Math.random() * characters.length()));
			token = token + letter;
		}
		return token;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken() {
		if (this.token == null)
			this.token = generateToken();
		else
			this.token = null;
	}
	
	public String getUserName() {
		return userName;
	}

}
