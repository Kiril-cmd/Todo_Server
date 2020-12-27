package userData;

import java.util.ArrayList;

public class Account {
	private String userName;
	private String password;
	private final String token;
	private ArrayList<ToDo> toDoList;
	
	
	public Account (String userName, String password){
		this.userName = userName;
		this.password = password;
		this.token = generateToken();
		this.toDoList = new ArrayList<>();
	}
	
	private static String generateToken() {
		int stringSize = 20;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String token = "";
		for (int i = 0; i < stringSize; i++) {
			char letter = characters.charAt((int) (Math.random() * characters.length()));
			token = token + letter;
		}
		return token;
	}
}