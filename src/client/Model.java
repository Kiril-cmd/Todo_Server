package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import messaging.*;

public class Model {
	
	protected SimpleStringProperty newestMessage = new SimpleStringProperty();
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String name;
	private String command;
	
	
	public void connect(String ipAddress, int Port) {
		logger.info("Connect");
		try {
			socket = new Socket(ipAddress, Port);
			
			// Create thread to read incoming messages
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (true) {
						Result_msg msg = (Result_msg) Message.receiveMessage(socket);
						System.out.println(msg);
					}
				}
			};
			Thread t = new Thread(r);
			t.start();

			} catch (Exception e) {
			logger.warning(e.toString());
		}
	}

	public void disconnect() {
		logger.info("Disconnect");
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				// Uninteresting
			}
	}

	public void sendMessage() {
		// Create thread to send messages
		Runnable r = new Runnable() {
			@Override
			public void run() {
			logger.info("Send message");
			Scanner in = new Scanner(System.in);
				while(true) {
					Message msg = null;
					command = in.nextLine();
					
					String msgParts[] = command.split("\\|");
					if (msgParts[0].equals(MessageType.CREATE_LOGIN.toString())) {
						msg = new CreateLogin_msg(msgParts[1], msgParts[2]);
					} else if (msgParts[0].equals(MessageType.LOGIN.toString())) {
						msg = new Login_msg(msgParts[1], msgParts[2]);
					} else if (msgParts[0].equals(MessageType.CHANGE_PASSWORD.toString())) {
						msg = new ChangePassword_msg(msgParts[1], msgParts[2]);
					} else if (msgParts[0].equals(MessageType.LOGOUT.toString())) {
						msg = new Logout_msg();
					} else if (msgParts[0].equals(MessageType.CREATE_TODO.toString())) {
						msg = new CreateToDo_msg(msgParts[1], msgParts[2], msgParts[3], msgParts[4], msgParts[5]);
					} else if (msgParts[0].equals(MessageType.GET_TODO.toString())) {
						msg = new GetToDo_msg(msgParts[1], msgParts[2]);
					} else if (msgParts[0].equals(MessageType.DELETE_TODO.toString())) {
						msg = new DeleteToDo_msg(msgParts[1], msgParts[2]);
					} else if (msgParts[0].equals(MessageType.LIST_TODOS.toString())) {
						msg = new ListToDos_msg(msgParts[1]);
					} else if (msgParts[0].equals(MessageType.PING.toString())) {
						msg = new Ping_msg(msgParts[1]);
					} else if (msgParts[0].equals(MessageType.RESULT.toString())) {
						msg = new Result_msg(msgParts[1], msgParts[2]);
					} 
					msg.sendMessage(socket);
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
		// Message msg = new Result(name, message);
		// msg.sendMessage(socket);
	}

}
	
