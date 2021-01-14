package userData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ToDo {
	private int id;
	private String title;
	private Priority priority;
	private String description;
	private LocalDate dueDate = null;
	
	public ToDo(int id, String title, Priority priority, String description) {
		this.id = id;
		this.title = title;
		this.priority = priority;
		this.description = description;
	}
	
	public ToDo(int id, String title, Priority priority, String description, LocalDate dueDate) {
		this.id = id;
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.dueDate = dueDate;
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
	
	public static boolean validateToDoData(String title, String description) {
		boolean valid = false;
		if (validateTitle(title) && validateDescription(description))
			valid = true;
		
		return valid;
	}
	
	public static boolean validateToDoData(String title, String description, LocalDate dueDate) {
		boolean valid = false;
		if (validateTitle(title) && validateDescription(description)
				&& validateDueDate(dueDate))
			valid = true;
		
		return valid;
	}
	
	public static boolean validateDueDate(LocalDate dueDate) {
		boolean valid = false;
		LocalDate toDay = LocalDate.now();
		if (dueDate.isAfter(toDay) || dueDate.isEqual(toDay))
			valid = true;
		
		return valid;
	}
	
	public static boolean validateTitle(String title) {
		boolean valid = false;
		if (title.length() > 2 && title.length() < 21)
			valid = true;
		
		return valid;
	}
	
	public static boolean validateDescription(String description) {
		boolean valid = false;
		if (description.length() > -1 && description.length() < 255)
			valid = true;
		return valid;
	}
}
