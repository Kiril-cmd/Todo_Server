package messaging;

public class ChangePassword_msg extends Message{
	private String password;
	private String token;
	
	public ChangePassword_msg(String token, String password) {
		super(MessageType.ChangePassword);
		this.token = token;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + token + '|' + password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getToken() {
		return token;
	}
}
