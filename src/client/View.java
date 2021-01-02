package client;

import userData.ToDo;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	Alert alertAccount = new Alert (AlertType.ERROR);
	
	// Add Todos
	Label lblMytodos = new Label ("My Todos");
	Label lblTitle = new Label ("Title");
	TextField txtTitle = new TextField();
	Label lblPriority = new Label("Priority");
	ComboBox<userData.Priority> cmbPriority = new ComboBox<>();
	Label lblDescription = new Label("Description");
	TextField txtDescription = new TextField();
	Label lblDuedate = new Label("Due date");
	DatePicker dueDate = new DatePicker();
	Button btnAddtodo = new Button("Add new todo");
	Button btnUpdate = new Button("Update");
	
	// Root
	BorderPane root = new BorderPane();
     
	// Todo list
	protected ListView<ToDo> listView;
	
	public View(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		
		//Alerts
		alertAccount.setTitle("Wrong account credentials");
		alertAccount.setHeaderText("The username or the password is wrong");
		
		
		root = loginView();
		//root = todoView();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setTitle("Client");
	}
	
	public void setTodoView() {
		todoView();
		
	}

	public void todoView() {
		Region spacer = new Region();
		Region spacer2 = new Region();
		spacer.setPrefHeight(20);
		spacer2.setPrefWidth(20);
		
		// ComboBox default selection
		cmbPriority.getItems().setAll(userData.Priority.values());
		cmbPriority.getSelectionModel().select(userData.Priority.MEDIUM);
		
		VBox title = new VBox(lblMytodos);
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		gridPane.add(lblTitle, 0, 0);
		gridPane.add(lblPriority, 1, 0);
		gridPane.add(lblDescription, 2, 0);
		gridPane.add(lblDuedate, 3, 0);
		
		gridPane.add(txtTitle, 0, 1);
		gridPane.add(cmbPriority, 1, 1);
		gridPane.add(txtDescription, 2, 1);
		gridPane.add(dueDate, 3, 1);
		gridPane.add(btnAddtodo, 4, 1);
		gridPane.add(btnUpdate, 5, 1);
		
		title.getStyleClass().add("vbox");
		gridPane.getStyleClass().add("gridpane");
		
		// Add all todos to the list
		listView = new ListView<>(model.todos);
		
		HBox todoList = new HBox(listView);
		
		BorderPane root = new BorderPane();
		root.setTop(title);
		root.setCenter(gridPane);
		root.setBottom(todoList);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setTitle("Client");
	}
	
	protected BorderPane loginView() {
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
	
	public void start(){
		stage.show();
		
		// Prevent resizing below initial size
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}
	
}
