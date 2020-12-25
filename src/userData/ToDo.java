package userData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDo {
	private int id;
	private static int counter = -1;
	private String title;
	private Priority priority;
	private String description;
	private LocalDate dueDate = null;
		
	public ToDo (String title, Priority priority, String description, LocalDate dueDate) {
		this.id = ++counter;
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	@Override
	public String toString() {
		if(dueDate != null)
			return id + '|' + title + '|' + priority + '|' + description + '|' + dueDate.format(DateTimeFormatter.ISO_DATE);
		else
			return id + '|' + title + '|' + priority + '|' + description;
	}
}
