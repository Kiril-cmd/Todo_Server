package client;

import javafx.application.Platform;

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
		
		model.newestMessage.addListener((observable, oldValue, newValue) -> {
			newestMessage = newValue;
			if (model.lastSentMessage.equals("CreateLogin")) {
				if(newestMessage.equals("RESULT|true|null")) {
					Platform.runLater(() -> {
						view.setTodoView();
				    });
				} else {
					System.out.println("Wrong data");
				}
			} else if (model.lastSentMessage.equals("Login")) {
				
			} else if (model.lastSentMessage.equals("ChangePassword")) {
				
			} else if (model.lastSentMessage.equals("Logout")) {
				
			} else if (model.lastSentMessage.equals("CreateTodo")) {
				
			} else if (model.lastSentMessage.equals("GetTodo")) {
				
			} else if (model.lastSentMessage.equals("DeleteTodo")) {
				
			} else if (model.lastSentMessage.equals("ListTodos")) {
				
			} else if (model.lastSentMessage.equals("Ping")) {
				
			} else if (model.lastSentMessage.equals("Result")) {
			
			}
		});
		
		view.btnLogin.setOnAction(event -> {
			String email = view.txtEmail.getText();
			String password = view.txtPassword.getText();
			model.Login(email, password);
			if (model.newestMessage.get().contentEquals("RESULT|true|null")) {
				view.setTodoView();
			}
		});
		
		
		
		view.stage.setOnCloseRequest(event -> model.disconnect());
		
	}

	
}
