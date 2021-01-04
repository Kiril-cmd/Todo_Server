package messaging;

public class Leave_msg extends Message {

	public Leave_msg() {
		super(MessageType.Leave);
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
