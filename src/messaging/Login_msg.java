package messaging;

public class Login_msg extends Message{
	private String userName;
	private String password;
	
	public Login_msg(String userName, String password) {
		super(MessageType.LOGIN);
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + userName + '|' + password;
	}
}