package messaging;

public class DeleteToDo_msg extends Message {
	int id;
	String token;
	
	public DeleteToDo_msg(String token, int id) {
		super(MessageType.DELETE_TODO);
		this.token = token;
		this.id = id;
	}
	
	public String toString() {
		return type.toString() + '|' + token + '|' + Integer.toString(id);
	}
}
