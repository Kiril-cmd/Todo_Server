package messaging;

public class ChangePassword_msg extends Message{
	private String password;
	private String token;
	
	public ChangePassword_msg(String password, String token) {
		super(MessageType.CHANGE_PASSWORD);
		this.password = password;
		this.token = token;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + password + '|' + token;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getToken() {
		return token;
	}
}
