package messaging;

public class ChangePassword_msg extends Message{
	private String userName;
	private String password;
	
	public ChangePassword_msg(String name, String password) {
		super(MessageType.CHANGE_PASSWORD);
		this.password = password;
	}
}
