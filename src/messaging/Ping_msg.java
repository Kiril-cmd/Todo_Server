package messaging;

public class Ping_msg extends Message {
	private String token = null;
	
	public Ping_msg() {
		super(MessageType.PING);
	}
	
	public Ping_msg(String token) {
		super(MessageType.PING);
		this.token = token;
	}

	@Override
	public String toString() {
		if(token != null)
			return type.toString() + '|' + token;
		else 
			return type.toString();
	}
}

