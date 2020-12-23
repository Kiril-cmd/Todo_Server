package messaging;

public class Ping_msg extends Message {
	private String token;
	
	public Ping_msg(String token) {
		super(MessageType.PING);
		this.token = token;
	}

}
