package messaging;

public class GetToDo_msg extends Message {
	private int id;
	private String token;
	
	public GetToDo_msg(String token, int id) {
		super(MessageType.GET_TODO);
		this.token = token;
		this.id = id;
	}

}
