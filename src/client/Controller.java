package client;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		String ip = "127.0.0.1";
		int port = 50001; 
		
		model.connect(ip, port);
		model.sendMessage();
	}
}
