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
						CreateLogin_msg specMsg = (CreateLogin_msg) msg;
						model.createAccount(specMsg.getUserName(), specMsg.getPassword(), Client.this);
					} else if (msg instanceof Login_msg) {
						Login_msg specMsg = (Login_msg) msg;
						userName = specMsg.getUserName();
						model.login(userName, specMsg.getPassword(), Client.this);
					} else if (msg instanceof ChangePassword_msg) {
						ChangePassword_msg specMsg = (ChangePassword_msg) msg;
						model.changePassword(specMsg.getPassword(), specMsg.getToken(), Client.this);						
					} else if (msg instanceof CreateToDo_msg) {
						CreateToDo_msg specMsg = (CreateToDo_msg) msg;
						model.createToDo(specMsg.getTitle(), specMsg.getPriority(), specMsg.getDescription(), specMsg.getDueDate(), specMsg.getToken(), Client.this);
					} else if (msg instanceof GetToDo_msg) {
						GetToDo_msg specMsg = (GetToDo_msg) msg;
						model.getToDo(specMsg.getId(), specMsg.getToken(), Client.this);
					} else if (msg instanceof DeleteToDo_msg) {
						DeleteToDo_msg specMsg = (DeleteToDo_msg) msg;
						model.deleteToDo(specMsg.getId(), specMsg.getToken(), Client.this);
					} else if (msg instanceof ListToDos_msg) {
						ListToDos_msg specMsg = (ListToDos_msg) msg;
						model.listToDos(specMsg.getToken(), Client.this);
					} else if (msg instanceof Ping_msg) {
						Ping_msg specMsg = (Ping_msg) msg;
						model.getPing(specMsg.getToken(), Client.this);
					} else if (msg instanceof Logout_msg) {
						model.Logout(Client.this);
					} else if (msg instanceof Invalid_msg) {
						model.answerInvalidRequest(Client.this);
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
	
	public boolean validateToken(String receivedToken) {
		boolean valid = false;
		if (this.getToken() != null || this.getToken().equals(receivedToken)) {
			valid = true;
		}
		return valid;
	}
	
	public String getUserName() {
		return userName;
	}

}
