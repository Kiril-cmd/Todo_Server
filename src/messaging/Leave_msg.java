package messaging;

public class Leave_msg extends Message {

	public Leave_msg() {
		super(MessageType.LEAVE);
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
