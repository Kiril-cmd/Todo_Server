package userData;

public class Account {
	private String userName;
	private String password;
	private final String token;
	
	
	Account (String userName, String password){
		this.userName = userName;
		this.password = password;
		
		this.token = generateToken();
	}
	
	private String generateToken() {
		return token;
	}
}
