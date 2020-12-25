package messaging;

import userData.ToDo;

public class Result_msg extends Message {
	private boolean result;
	private ToDo todo;
	
	public Result_msg(boolean result, ToDo todo) {
		super(MessageType.RESULT);
		this.result = result;
		this.todo = todo;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + result + '|' + todo.toString();
	}
}
