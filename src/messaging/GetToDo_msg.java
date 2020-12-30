package messaging;

public class GetToDo_msg extends Message {
	private int id;
	private String token;
	
	public GetToDo_msg(String token, String id) {
		super(MessageType.GET_TODO);
		this.token = token;
		this.id = Integer.parseInt(id);
	}
	
	public String toString() {
		return type.toString() + '|' + token + '|' + Integer.toString(id);
	}
	
	public int getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}

}
