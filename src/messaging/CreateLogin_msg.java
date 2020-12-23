package messaging;

public class CreateLogin_msg extends Message {
	private String name;
		
	public CreateLogin_msg(String name) {
		super(MessageType.CREATE_LOGIN);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + name;
	}
}
