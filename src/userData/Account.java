package userData;

import java.util.ArrayList;

public class Account {
	private String userName;
	private String password;
	private ArrayList<ToDo> toDoList;
	
	
	public Account (String userName, String password){
		this.userName = userName;
		this.password = password;
		this.toDoList = new ArrayList<>();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addToDo(ToDo newToDo) {
		toDoList.add(newToDo);
	}
}