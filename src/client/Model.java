package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messaging.*;
import userData.Priority;
import userData.ToDo;

public class Model {
	
	protected SimpleStringProperty newestMessage = new SimpleStringProperty();
	
	private Logger logger = Logger.getLogger("");
	private Socket socket;
	private String name;
	private String command;
	
	private final ObservableList<ToDo> elements = FXCollections.observableArrayList();

	public void addNewElement(String title, Priority priority, String description, LocalDate dueDate) {
		elements.add(new ToDo(title, priority, description, dueDate));
	}

	// getters and setters
	public ObservableList<ToDo> getElements() {
		return elements;
	}
	
	
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
					
					String msgParts[] = command.split("\\|");
					
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	// Commands
	public void CreateLogin(String email, String password) {
		CreateLogin_msg msg = new CreateLogin_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void Login(String email, String password) {
		Login_msg msg = new Login_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void ChangePassword(String email, String password) {
		ChangePassword_msg msg = new ChangePassword_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void Logout() {
		Logout_msg msg = new Logout_msg();
		msg.sendMessage(socket);
	}
	
	public void CreateTodo(String title, String token, String priority, String description) {
		CreateToDo_msg msg = new CreateToDo_msg (title, token, priority, description);
		msg.sendMessage(socket);
	}
	
	public void CreateTodo(String title, String token, String priority, String description, String dueDate) {
		CreateToDo_msg msg = new CreateToDo_msg (title, token, priority, description, dueDate);
		msg.sendMessage(socket);
	}
	
	public void GetTodo(String token, String id) {
		GetToDo_msg msg = new GetToDo_msg (token, id);
		msg.sendMessage(socket);
	}
	
	public void DeleteTodo(String token, String id) {
		DeleteToDo_msg msg = new DeleteToDo_msg(token, id);
		msg.sendMessage(socket);
	}
	
	public void ListTodos(String token) {
		ListToDos_msg msg = new ListToDos_msg(token);
		msg.sendMessage(socket);
	}
	
	public void Ping(String token) {
		Ping_msg msg = new Ping_msg(token);
		msg.sendMessage(socket);
	}
	
	public void Result(String result, String data) {
		Result_msg msg = new Result_msg(result, data);
		msg.sendMessage(socket);
	}
	
	
}
	
