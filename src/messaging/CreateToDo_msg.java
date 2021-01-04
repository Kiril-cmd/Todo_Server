package messaging;

import java.time.LocalDate;
import userData.Priority;

public class CreateToDo_msg extends Message {
	private String title;
	private String token;
	private Priority priority;
	private String description;
	private LocalDate dueDate;
	
	public CreateToDo_msg(String token, String title, String priority, String description) {
		super(MessageType.CreateToDo);
		this.token = token;
		this.title = title;
		this.priority = Priority.valueOf(priority);
		this.description = description;
	}

	public CreateToDo_msg(String token, String title, String priority, String description, String dueDate) {
		super(MessageType.CreateToDo);
		this.token = token;
		this.title = title;
		this.priority = Priority.valueOf(priority);
		this.description = description;
		this.dueDate = LocalDate.parse(dueDate);
	}
	
	@Override
	public String toString() {
		if(dueDate != null) {
			return type.toString() + '|' + token + '|' + title + '|' 
					+ priority + '|' + description + '|' + dueDate; 
		} else {
			return type.toString() + '|' + token + '|' + title + '|' 
					+ priority + '|' + description;
		}
	}
	
	public String getToken() {
		return token;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public String getDescription() {
		return description;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
}
