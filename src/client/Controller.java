package client;

import java.time.LocalDate;

import javafx.application.Platform;
import userData.Priority;

public class Controller {
	private Model model;
	private View view;
	
	String newestMessage;
	
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
			model.CreateTodo(title, model.token, priority, description, dueDate);
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
					String title = view.txtTitle.getText();
					Priority priority = view.cmbPriority.getValue();
					String description = view.txtDescription.getText();
					LocalDate dueDate = view.dueDate.getValue();
					model.addNewTodo(title, priority, description, dueDate);
				} else {
					System.out.println("Could not create a new Todo for some reason");
				}
			} else if (model.lastSentMessage.equals("GetTodo")) {
				
			} else if (model.lastSentMessage.equals("DeleteTodo")) {
				
			} else if (model.lastSentMessage.equals("ListTodos")) {
				
			} else if (model.lastSentMessage.equals("Ping")) {
				
			} else if (model.lastSentMessage.equals("Result")) {
				
			}
		});
		
		
		
		
		
		view.stage.setOnCloseRequest(event -> model.disconnect());
		
	}

	
}
