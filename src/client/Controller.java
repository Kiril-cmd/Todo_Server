package client;


public class Controller {
	private Model model;
	private View view;
	
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
			System.out.println(model.newestMessage.getValue());
			if (model.newestMessage.get().contentEquals("RESULT|true|null")) {
				view.setTodoView();
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
