package messaging;

public class Logout_msg extends Message{
	
	public Logout_msg() {
		super(MessageType.Logout);
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
