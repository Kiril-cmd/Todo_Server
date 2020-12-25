package messaging;

import java.time.LocalDate;
import userData.Priority;

public class CreateToDo_msg extends Message {
	private String title;
	private String token;
	private Priority priority;
	private String description;
	private LocalDate dueDate;

	public CreateToDo_msg(String title, String token, Priority priority, String description, LocalDate dueDate) {
		super(MessageType.CREATE_TODO);
		this.token = token;
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + token + '|' + title + '|' 
				+ priority + '|' + description + '|' + dueDate; 
	}
	
}
