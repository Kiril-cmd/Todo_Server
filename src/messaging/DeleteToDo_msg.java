package messaging;

public class DeleteToDo_msg extends Message {
	int id;
	String token;
	
	public DeleteToDo_msg(String token, String id) {
		super(MessageType.DeleteToDo);
		this.token = token;
		this.id = Integer.parseInt(id);
	}
	
	public String toString() {
		return type.toString() + '|' + token + '|' + Integer.toString(id);
	}
	
	public String getToken() {
		return token;
	}
	
	public int getId() {
		return id;
	}
	
}
