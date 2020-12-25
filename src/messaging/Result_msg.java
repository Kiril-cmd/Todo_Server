package messaging;

import userData.ToDo;

public class Result_msg extends Message {
	private boolean result;
	private ToDo todo;
	private String todoString;
	
	public Result_msg(String result, String todo) {
		super(MessageType.RESULT);
		this.result = Boolean.parseBoolean(result);
		this.todoString = todo;
	}

	@Override
	public String toString() {
		return type.toString() + '|' + result + '|' + todoString;
	}
}
