package messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class Message {
	private static Logger logger = Logger.getLogger("");
	protected MessageType type;

	public Message(MessageType type) {
		this.type = type;
	}
	
	public static Message receiveMessage(Socket socket) {
		BufferedReader in;
		Message msg = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgText = in.readLine();
			logger.info("Receiving message: " + msgText);
			
			try {
				// Parse message
				String msgParts[] = msgText.split("\\|");
				if (msgParts[0].equals(MessageType.CREATE_LOGIN.toString())) {
					msg = new CreateLogin_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.LOGIN.toString())) {
					msg = new Login_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.CHANGE_PASSWORD.toString())) {
					msg = new ChangePassword_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.LOGOUT.toString())) {
					msg = new Logout_msg();
				} else if (msgParts[0].equals(MessageType.CREATE_TODO.toString())) {
					msg = new CreateToDo_msg(msgParts[1], msgParts[2], msgParts[3], msgParts[4]);
				} else if (msgParts[0].equals(MessageType.GET_TODO.toString())) {
					msg = new GetToDo_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.DELETE_TODO.toString())) {
					msg = new DeleteToDo_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.LIST_TODOS.toString())) {
					msg = new ListToDos_msg(msgParts[1]);
				} else if (msgParts[0].equals(MessageType.PING.toString())) {
					if (msgParts.length > 1)
						msg = new Ping_msg(msgParts[1]);
					else
						msg = new Ping_msg();
				} else if (msgParts[0].equals(MessageType.RESULT.toString())) {
					if (msgParts.length > 2)
						msg = new Result_msg(msgParts[1], msgParts[2]);
					else
						msg = new Result_msg(msgParts[1]);
				} else {
					msg = new Invalid_msg();
				}
			} catch (Exception e) {
				msg = new Invalid_msg();
			}			
		
		} catch(IOException e) {
			logger.warning(e.toString());
		}
		return msg;
	}
	
	public void sendMessage(Socket socket) {
		OutputStreamWriter out;
		try {
			out = new OutputStreamWriter(socket.getOutputStream());
			logger.info("Sending message: " + this.toString());
			out.write(this.toString() + "\n");
			out.flush();
		} catch (IOException e) {
			logger.warning(e.toString());
		}		
	}
	
	public MessageType getType() {
		return type;
	}
	
	

}
