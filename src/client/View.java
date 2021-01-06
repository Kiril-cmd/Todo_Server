package client;

import userData.ToDo;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
	Label lblEmail = new Label("Enter your current email or your new email (***@**.**): ");
	TextField txtEmail = new TextField();
	Label lblPassword = new Label ("Enter your current password or your new password (min. 6 characters): ");
	TextField txtPassword = new TextField();
	Button btnLogin = new Button("Login");
	Button btnRegister = new Button("Register new account");
	Alert alertAccount = new Alert (AlertType.ERROR);
	Label lblIpAddress = new Label ("IP Address");
	TextField txtIpAddress = new TextField("127.0.0.1");
	Label lblPort = new Label ("Port");
	TextField txtPort = new TextField("50002");
	Button btnConnect = new Button("Connect");
	Button btnChangePassword = new Button("Change Password");
	Alert alertConnection = new Alert(AlertType.ERROR);
	
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
	Button btnDelete = new Button("Delete");
	Alert alertItem = new Alert(AlertType.INFORMATION);
	Button btnLogout = new Button("Logout");
	Label lblServer = new Label("Reply from server: ");
	TextInputDialog changePassword = new TextInputDialog();
	Label lblNewPassword = new Label("Enter your new password");
	TextField txtNewPassword = new TextField();
	Button btnOk = new Button("OK");
	
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
		
		alertConnection.setTitle("Connection has failed");
		alertConnection.setHeaderText("Please make sure the server is running. The port and the ip address must match with the server.");
		
		alertItem.setHeaderText("Please select an item");
		
		//Change Password
		changePassword.setTitle("Set new Password");
		changePassword.setHeaderText("Enter your new password:");
		changePassword.setContentText("New password:");
		
		// set loinView at the start
		loginView();
	}
	
	public void setTodoView() {
		todoView();
		
	}

	public void todoView() {
		Region spacer = new Region();
		Region spacer2 = new Region();
		Region spacer3 = new Region();
		Region spacer4 = new Region();
		Region spacer5 = new Region();
		Region spacer6 = new Region();
		spacer.setPrefHeight(20);
		spacer2.setPrefWidth(20);
		spacer3.setPrefWidth(20);
		
		HBox.setHgrow(spacer4, Priority.ALWAYS);
		HBox.setHgrow(spacer5, Priority.ALWAYS);
		HBox.setHgrow(spacer6, Priority.ALWAYS);
		
		// ComboBox default selection
		cmbPriority.getItems().setAll(userData.Priority.values());
		cmbPriority.getSelectionModel().select(userData.Priority.Medium);
		
		HBox title = new HBox(lblMytodos, spacer6, btnChangePassword, spacer4, btnLogout);
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
		
		title.getStyleClass().add("vbox");
		gridPane.getStyleClass().add("gridpane");
		
		// Add all todos to the list
		listView = new ListView<>(model.todos);
		// Clear everything before loading
		model.todos.clear();
		HBox todoList = new HBox(listView, spacer3, btnDelete, spacer5, lblServer);
		
		BorderPane root = new BorderPane();
		root.setTop(title);
		root.setCenter(gridPane);
		root.setBottom(todoList);
				
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setTitle("Client");
	}
	
	protected void loginView() {
		Region spacer = new Region();
		Region spacer2 = new Region();
		Region spacer3 = new Region();
		Region spacer4 = new Region();
		Region spacer5 = new Region();
		Region spacer6 = new Region();
		
		spacer.setPrefHeight(20);
		spacer2.setPrefHeight(20);
		spacer3.setPrefHeight(20);
		spacer4.setPrefHeight(20);
		spacer5.setPrefHeight(20);
		spacer6.setPrefHeight(20);
		VBox.setVgrow(spacer, Priority.NEVER);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		VBox.setVgrow(spacer3, Priority.ALWAYS);
		
		VBox vbox = new VBox(lblEmail, txtEmail, spacer, lblPassword, txtPassword, spacer3);
		
		BorderPane root = new BorderPane();
		root.getStyleClass().add("root");
		
		HBox hbox = new HBox(btnLogin, spacer2, btnRegister);
		
		GridPane topPane = new GridPane();
		topPane.add(lblIpAddress, 0, 0);
		topPane.add(txtIpAddress, 1, 0);
		topPane.add(spacer4, 0, 1);
		topPane.add(lblPort, 0, 2);
		topPane.add(txtPort, 1, 2);
		topPane.add(spacer5, 0, 3);
		topPane.add(btnConnect, 0, 4);
		topPane.add(spacer6, 1, 4);
		topPane.add(lblServer, 2, 4);
		topPane.getStyleClass().add("gridpane");
		
		root.setTop(topPane);
		root.setCenter(vbox);
		root.setBottom(hbox);
	
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setTitle("Client");
	}
	
	
	public void start(){
		stage.show();
		
		// Prevent resizing below initial size
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}
	
}
