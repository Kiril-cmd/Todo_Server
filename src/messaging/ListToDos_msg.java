package messaging;

public class ListToDos_msg extends Message {
	String token;
	
	public ListToDos_msg(String token) {
		super(MessageType.ListToDos);
		this.token = token;
	}
	
	public String toString() {
		return type.toString() + '|' + token;
	}
	
	public String getToken() {
		return token;
	}

}
