package messaging;

public class DeleteToDo_msg extends Message {
	int id;
	
	public DeleteToDo_msg(int id) {
		super(MessageType.DELETE_TODO);
		this.id = id;
	}
}
