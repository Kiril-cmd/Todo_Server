package messaging;

public class ChangePassword_msg extends Message{
	private String userName;
	private String password;
	private String token;
	
	public ChangePassword_msg(String userName, String password) {
		super(MessageType.CHANGE_PASSWORD);
		this.password = password;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + userName + '|' + password;
	}
}
