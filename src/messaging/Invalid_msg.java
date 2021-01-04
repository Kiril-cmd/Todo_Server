package messaging;

public class Invalid_msg extends Message {
	
	public Invalid_msg() {
		super(MessageType.Invalid);
	}
	
	@Override
	public String toString() {
		return type.toString();
	}

}
