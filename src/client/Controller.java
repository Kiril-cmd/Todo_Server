package client;

import java.time.LocalDate;

import com.sun.net.httpserver.Authenticator.Result;

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
		
		
		
		view.btnLogin.setDisable(true);
		view.btnRegister.setDisable(true);
		
		
		
		view.btnConnect.setOnAction(event -> {
			String ip = view.txtIpAddress.getText();
			int port = Integer.parseInt(view.txtPort.getText());
			try {
				model.connect(ip, port);
			} catch (Exception e){
				System.out.println(e.toString());
			}
			if(model.connectionfailed == true) {
				view.alertConnection.showAndWait();
				model.connectionfailed = false;
			} else {
				model.Ping();
			}
		});
				
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
		
		view.btnDelete.setOnAction(event -> {
			if(view.listView.getSelectionModel().getSelectedItem() != null) {
				String selectedId = String.valueOf(view.listView.getSelectionModel().getSelectedItem().getId());
				model.DeleteTodo(model.token, selectedId);
			} else {
				view.alertItem.showAndWait();
			}
		});
		
		model.newestMessage.addListener((observable, oldValue, newValue) -> {
			
			//newestMessage = newValue;
			
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
					model.ListTodos(model.token);
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
					model.GetTodo(model.token, id);
				} else {
					System.out.println("Could not create a new Todo for some reason");
				}
			} else if (model.lastSentMessage.equals("GetTodo")) {
				String msgParts[] = model.data.split("\\|");
				int id = Integer.parseInt(msgParts[0]);
				String title = msgParts[1];
				Priority priority = Priority.valueOf(msgParts[2]);
				String description = msgParts[3];
				LocalDate dueDate;
				
				if(model.result && msgParts.length >= 5) {
					dueDate = LocalDate.parse(msgParts[4]);
					Platform.runLater(() -> {
						model.addNewTodo(id, title, priority, description, dueDate);
					});
				} else if (model.result && msgParts.length < 5) {
					dueDate = null;
					Platform.runLater(() -> {
						model.addNewTodo(id, title, priority, description, dueDate);
					});
				} else {
					System.out.println("The server didn't send any todo");
					model.ListTodos(model.token);
				}
			} else if (model.lastSentMessage.equals("DeleteTodo")) {
				if(model.result) {
					Platform.runLater(() -> {
						model.todos.clear();
					});
					System.out.println("Todo was deleted");
					model.ListTodos(model.token);
				} else {
					System.out.println("Todo could not be deleted");
				}
				
			} else if (model.lastSentMessage.equals("ListTodos")) {
				if(model.result) {
					model.everyId = model.data;
					System.out.println(model.everyId);
					String msgParts[] = model.everyId.split("\\|");
					for(int i = 0; i < msgParts.length; i++) {
						model.GetTodo(model.token, msgParts[i]);
					}
				} else {
					System.out.println("No, todos were found");
				}
			} else if (model.lastSentMessage.equals("Ping")) {
				if(model.result) {
					view.btnLogin.setDisable(false);
					view.btnRegister.setDisable(false);
				} else {
					System.out.println("Connection could not be established with the server");
				}
			} else if (model.lastSentMessage.equals("Result")) {
				
			}
		});
		
		
		
		
		
		view.stage.setOnCloseRequest(event -> model.disconnect());
		
	}

	
}
