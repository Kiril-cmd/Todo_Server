package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application{
	private View view;
	private Controller controller;
	private Model model;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		model = new Model();
		view = new View(primaryStage, model);
		controller = new Controller(model, view);
		view.start();
	}
}
