package userData;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public ToDo getToDo(int id) {
		ToDo toDo = null;
		for (ToDo i : toDoList) {
			if (i.getId() == id)
				toDo = i;
		}
		return toDo;
	}
	
	public boolean deleteToDo(int id) {
		Iterator<ToDo> iterator = toDoList.iterator();		
		while (iterator.hasNext()) {
			if (iterator.next().getId() == id) {
				iterator.remove();
				return true;
			}				
		}
		return false;
	}
	
	public String toDoListToString() {
		String toDos = null;
		Iterator<ToDo> iterator = toDoList.iterator();
		while (iterator.hasNext()) {
			if (iterator.hasNext() != false)
				toDos += iterator.next().getId() + '|';
			else
				toDos += iterator.next().getId();
		}
		return toDos;
	}
}