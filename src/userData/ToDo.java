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
		
	public ToDo(String title, Priority priority, String description, LocalDate dueDate) {
		this.id = ++counter;
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
	}
	
	public ToDo(String title, Priority priority, String description) {
		this.id = ++counter;
		this.title = title;
		this.priority = priority;
		this.description = description;
	}
	
	@Override
	public String toString() {
		String toDoString = null;
		
		if(dueDate != null)
			toDoString = Integer.toString(id) + '|' + title + '|' + priority + '|' + description + '|' + dueDate.format(DateTimeFormatter.ISO_DATE);
		else
			toDoString = Integer.toString(id) + '|' + title + '|' + priority + '|' + description;
		
		return toDoString;
	}
	
	public int getId() {
		return id;
	}
	
	public static boolean validateDueDate(LocalDate dueDate) {
		boolean valid = false;
		LocalDate toDay = LocalDate.now();
		if (dueDate.isAfter(toDay) || dueDate.isEqual(toDay))
			valid = true;
		
		return valid;
	}
}
