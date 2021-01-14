package userData;

import java.util.ArrayList;
import java.util.Iterator;

public class Account {
	private String userName;
	private String password;
	private ArrayList<ToDo> toDoList;
	private int toDoIdCounter = -1;
	
	
	public Account (String userName, String password) {
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
	
	public ToDo getToDo(int id) {
		ToDo toDo = null;
		for (ToDo i : toDoList) {
			if (i.getId() == id)
				toDo = i;
		}
		return toDo;
	}
	
	public boolean deleteToDo(int id) {
		boolean removed = false;
		Iterator<ToDo> iterator = toDoList.iterator();		
		while (iterator.hasNext() && !removed) {
			if (iterator.next().getId() == id) {
				iterator.remove();
				removed = true;
			}				
		}
		return removed;
	}
	
	public String toDoListToString() {
		String toDos = null;
		Iterator<ToDo> iterator = toDoList.iterator();
		while (iterator.hasNext()) {
			ToDo todo = iterator.next();
			if (toDos == null)
				toDos = Integer.toString(todo.getId()) + '|';
			else if (toDos != null && iterator.hasNext())
				toDos += Integer.toString(todo.getId()) + '|';
			else if (!iterator.hasNext())
				toDos += todo.getId();
		}
		return toDos;
	}
	
	public static boolean validateLoginData(String userName, String password) {
		boolean valid = false;
		try {
			// splits the userName into 3 parts
			String[] userArray = userName.split("@");
			String[] nameSecond = userArray[1].split("\\."); 
			
			String nameStart = userArray[0];
			String nameMiddle = nameSecond[0];
			String nameEnd = nameSecond[1];
			
			if (nameStart.length() > 2 && nameMiddle.length() > 1 && nameEnd.length() > 1 
					&& validatePassword(password)) {
				valid = true;
			} 
		} catch (Exception e) {	
		}	
		return valid;
	}
	
	public static boolean validatePassword(String password) {
		boolean valid = false;	
		if (password.length() > 2 && password.length() < 21)
			valid = true;
		
		return valid;
	}
	
	public int generateToDoId() {
		toDoIdCounter++;
		return toDoIdCounter;
	}
}