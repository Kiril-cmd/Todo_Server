package messaging;

public class GetToDo_msg extends Message {
	private int id;
	
	public GetToDo_msg(int id) {
		super(MessageType.GET_TODO);
		this.id = id;
	}

}
