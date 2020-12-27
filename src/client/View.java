package client;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class View {
	protected Stage stage;
	private Model model;
	
	public View(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		
		Label lblHey = new Label("Hey from client");
		
		Scene scene = new Scene(lblHey);
		stage.setScene(scene);
		stage.setTitle("Client");
	}
	
	protected void start(){
		stage.show();
		
		// Prevent resizing below initial size
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}
}
