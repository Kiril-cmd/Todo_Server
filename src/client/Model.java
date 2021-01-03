package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;

import javafx.application.Platform;
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
	
	protected final ObservableList<ToDo> todos = FXCollections.observableArrayList();
	
	protected boolean connectionfailed = false;
	protected String lastSentMessage = null;
	protected boolean result;
	protected String data;
	protected String token;
	protected String everyId;
	
	
	
	public void addNewTodo(int id, String title, Priority priority, String description, LocalDate dueDate) {
		todos.add(new ToDo(id, title, priority, description, dueDate));
	}

	// getters and setters
	public ObservableList<ToDo> getTodos() {
		return todos;
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
						
						result = msg.getResult();
						data = msg.getData();
						
						System.out.println(msg);
						// Do it always at the end
						newestMessage.set(msg.toString());
					}
				}
			};
			Thread t = new Thread(r);
			t.start();

			} catch (Exception e) {
			logger.warning(e.toString());
			System.out.println("Connection failed");
			connectionfailed = true;
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

//	public void sendMessage() {
//		// Create thread to send messages
//		Runnable r = new Runnable() {
//			@Override
//			public void run() {
//			logger.info("Send message");
//			
//				while(true) {
//					Message msg = null;
//					
//					String msgParts[] = command.split("\\|");
//				}
//			}
//		};
//		Thread t = new Thread(r);
//		t.start();
//	}
	
	// Commands
	public void CreateLogin(String email, String password) {
		lastSentMessage = "CreateLogin";
		CreateLogin_msg msg = new CreateLogin_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void Login(String email, String password) {
		lastSentMessage = "Login";
		Login_msg msg = new Login_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void ChangePassword(String email, String password) {
		lastSentMessage = "ChangePassword";
		ChangePassword_msg msg = new ChangePassword_msg(email, password);
		msg.sendMessage(socket);
	}
	
	public void Logout() {
		lastSentMessage = "Logout";
		Logout_msg msg = new Logout_msg();
		msg.sendMessage(socket);
	}
	
	public void CreateTodo(String token, String title, String priority, String description, String dueDate) {
		lastSentMessage = "CreateTodo";
		CreateToDo_msg msg;
		if(dueDate != null)	msg = new CreateToDo_msg (token, title, priority, description, dueDate);
		else msg = new CreateToDo_msg(token, title, priority, description);
		msg.sendMessage(socket);
	}
	
	public void GetTodo(String token, String id) {
		lastSentMessage = "GetTodo";
		GetToDo_msg msg = new GetToDo_msg (token, id);
		msg.sendMessage(socket);
	}
	
	public void DeleteTodo(String token, String id) {
		lastSentMessage = "DeleteTodo";
		DeleteToDo_msg msg = new DeleteToDo_msg(token, id);
		msg.sendMessage(socket);
	}
	
	public void ListTodos(String token) {
		lastSentMessage = "ListTodos";
		ListToDos_msg msg = new ListToDos_msg(token);
		msg.sendMessage(socket);
	}
	
	public void Ping() {
		lastSentMessage = "Ping";
		Ping_msg msg = new Ping_msg();
		msg.sendMessage(socket);
	}
	
	public void Ping(String token) {
		lastSentMessage = "Ping";
		Ping_msg msg = new Ping_msg(token);
		msg.sendMessage(socket);
	}
	
	public void Result(String result, String data) {
		lastSentMessage = "Result";
		Result_msg msg = new Result_msg(result, data);
		msg.sendMessage(socket);
	}
	
}
	
