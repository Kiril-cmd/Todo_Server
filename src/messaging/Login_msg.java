package messaging;

public class Login_msg extends Message{
	private String userName;
	private String password;
	
	public Login_msg(String userName, String password) {
		super(MessageType.LOGIN);
		
	}
		
	public String getUserName() {
		return userName;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + userName + '|' + password;
	}
}
