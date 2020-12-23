package messaging;

import java.util.logging.Logger;

public abstract class Message {
	private Logger logger = Logger.getLogger("");
	private MessageType type;

	public Message(MessageType type) {
		this.type = type;
	}
	
	public Message receiveMessage() {
		Message msg = null;
		return msg;
	}
	
	public void sendMessage() {
		
	}
	
	public MessageType getType() {
		return type;
	}

}
