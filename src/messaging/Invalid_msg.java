package messaging;

public class Invalid_msg extends Message {
	
	public Invalid_msg() {
		super(MessageType.INVALID);
	}
	
	@Override
	public String toString() {
		return type.toString();
	}

}
