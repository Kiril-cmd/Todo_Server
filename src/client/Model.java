package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import messaging.*;

public class Model {
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String name;
	
	public void connect(String ipAddress, int Port, String name) {
		logger.info("Connect");
		this.name = name;
		try {
			socket = new Socket(ipAddress, Port);
			
			// Create thread to read incoming messages
			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (true) {
						System.out.println("Please login");
						Message msg = (Message)receiveMessage(socket);
						
						// If the client is sending the _same_ message as before, we cannot simply
						// set the property, because this would not be a change, and the change
						// listener will not trigger. Therefore, we first erase the previous message.
						// This is a change, but empty messages are ignored by the change-listener
						newestMessage.set(""); // erase previous message
						newestMessage.set(msg.getName() + ": " + msg.getContent());
					}
				}
			};
			Thread t = new Thread(r);
			t.start();

			// Send join message to the server
			Message msg = new JoinMsg(name);
			msg.send(socket);
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

	public void sendMessage(String message) {
		logger.info("Send message");
		Message msg = new ChatMsg(name, message);
		msg.send(socket);
	}

	public String receiveMessage() {
		logger.info("Receive message");
		return newestMessage.get();
	}
		
	}
	
	
	
}
