package client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	protected Stage stage;
	private Model model;
	
	// Login
	Label lblEmail = new Label("Enter your current email or your new email: ");
	TextField txtEmail = new TextField();
	Label lblPassword = new Label ("Enter your current password or your new password: ");
	TextField txtPassword = new TextField();
	Button btnLogin = new Button("Login");
	Button btnRegister = new Button("Register new account");
	
	// Add Todos
	Label lblMytodos = new Label ("My Todos");
	TextField txtNewtodo = new TextField();
	Button btnAddtodo = new Button("Add new todo");
	ComboBox cmbPriority = new ComboBox<>();
     

	
	// Todo list
	protected ListView<Integer> listView = new ListView<>();
	
	public View(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		
		BorderPane root = new BorderPane(); 
		root = login();
		
		root = todoView();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setTitle("Client");
	}
	
	protected BorderPane todoView() {
		Region spacer = new Region();
		Region spacer2 = new Region();
		spacer.setPrefHeight(20);
		spacer2.setPrefWidth(20);
		
		VBox title = new VBox(lblMytodos);
		HBox hbox = new HBox(txtNewtodo, spacer2, btnAddtodo);
		title.getStyleClass().add("vbox");
		
		HBox todoList = new HBox(listView);
		
		
		BorderPane root = new BorderPane();
		root.setTop(title);
		root.setCenter(hbox);
		root.setBottom(todoList);
		
		return root;
	}
	
	protected BorderPane login() {
		Region spacer = new Region();
		Region spacer2 = new Region();
		Region spacer3 = new Region();
		
		spacer.setPrefHeight(20);
		spacer2.setPrefHeight(20);
		spacer3.setPrefHeight(20);
		VBox.setVgrow(spacer, Priority.NEVER);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		VBox.setVgrow(spacer3, Priority.ALWAYS);
		
		VBox vbox = new VBox(lblEmail, txtEmail, spacer, lblPassword, txtPassword, spacer3);
		
		BorderPane root = new BorderPane();
		root.getStyleClass().add("root");
		
		HBox hbox = new HBox(btnLogin, spacer2, btnRegister);
		
		root.setBottom(hbox);
		
		root.setCenter(vbox);
		return root;
	}
	
	protected void start(){
		stage.show();
		
		// Prevent resizing below initial size
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}
}
