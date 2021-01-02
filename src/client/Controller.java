package client;

import java.time.LocalDate;

import javafx.application.Platform;
import userData.Priority;

public class Controller {
	private Model model;
	private View view;
	
	String newestMessage;
	String id;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		String ip = "127.0.0.1";
		int port = 50002;
		
		
		
		model.connect(ip, port);
		
				
		view.btnRegister.setOnAction(event -> {
			String email = view.txtEmail.getText();
			String password = view.txtPassword.getText();
			model.CreateLogin(email, password);

		});
		
		view.btnLogin.setOnAction(event -> {
			String email = view.txtEmail.getText();
			String password = view.txtPassword.getText();
			model.Login(email, password);
		});
		
		view.btnAddtodo.setOnAction(event -> {
			String title = view.txtTitle.getText();
			String priority = view.cmbPriority.getValue().toString();
			String description = view.txtDescription.getText();
			String dueDate = null;
			//String dueDate = view.dueDate.getValue().toString();
			model.CreateTodo(model.token, title, priority, description, dueDate);

		});
		
		view.btnUpdate.setOnAction(event -> {
			model.GetTodo(model.token, id);
		});
		
		model.newestMessage.addListener((observable, oldValue, newValue) -> {
			
			newestMessage = newValue;
			
			if (model.lastSentMessage.equals("CreateLogin")) {
				if(model.result) {
					System.out.println("Account successfully created, please login");
				} else {
					System.out.println("Wrong data");
				}
			} else if (model.lastSentMessage.equals("Login")) {
				if(model.result) {
					model.token = model.data;
					Platform.runLater(() -> {
						view.setTodoView();
				    });
				} else {
					Platform.runLater(() -> {
						view.alertAccount.showAndWait();
					});
				}
			} else if (model.lastSentMessage.equals("ChangePassword")) {
				
			} else if (model.lastSentMessage.equals("Logout")) {
				
			} else if (model.lastSentMessage.equals("CreateTodo")) {
				if(model.result) {
					System.out.println("Todo was successfully created");
					id = model.data;
					
					
					
				} else {
					System.out.println("Could not create a new Todo for some reason");
				}
			} else if (model.lastSentMessage.equals("GetTodo")) {
				String msgParts[] = model.data.split("\\|");
				int id = Integer.parseInt(msgParts[0]);
				String title = msgParts[1];
				Priority priority = Priority.valueOf(msgParts[2]);
				String description = msgParts[3];
				LocalDate dueDate = LocalDate.parse(msgParts[4]);
				
				if(model.result) {
					model.addNewTodo(id, title, priority, description, dueDate);
				} else {
					System.out.println("The server didn't send any todo");
				}
			} else if (model.lastSentMessage.equals("DeleteTodo")) {
				
			} else if (model.lastSentMessage.equals("ListTodos")) {
				
			} else if (model.lastSentMessage.equals("Ping")) {
				
			} else if (model.lastSentMessage.equals("Result")) {
				
			}
		});
		
		
		
		
		
		view.stage.setOnCloseRequest(event -> model.disconnect());
		
	}

	
}
