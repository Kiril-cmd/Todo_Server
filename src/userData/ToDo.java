package userData;

import java.time.LocalDate;

public class ToDo {
	private int id;
	private static int counter = -1;
	private String title;
	private Priority priority;
	private String description;
	private LocalDate dueDate;
	
	
	public ToDo (String title, Priority priority, String description, LocalDate dueDate) {
		this.id = ++counter;
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
	}

}
