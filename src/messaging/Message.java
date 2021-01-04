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
				if (msgParts[0].equals(MessageType.CreateLogin.toString())) {
					msg = new CreateLogin_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.Login.toString())) {
					msg = new Login_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.ChangePassword.toString())) {
					msg = new ChangePassword_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.Logout.toString())) {
					msg = new Logout_msg();
				} else if (msgParts[0].equals(MessageType.CreateToDo.toString())) {
					if (msgParts.length < 6)
						msg = new CreateToDo_msg(msgParts[1], msgParts[2], msgParts[3], msgParts[4]);
					else
						msg = new CreateToDo_msg(msgParts[1], msgParts[2], msgParts[3], msgParts[4], msgParts[5]);
				} else if (msgParts[0].equals(MessageType.GetToDo.toString())) {
					msg = new GetToDo_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.DeleteToDo.toString())) {
					msg = new DeleteToDo_msg(msgParts[1], msgParts[2]);
				} else if (msgParts[0].equals(MessageType.ListToDos.toString())) {
					msg = new ListToDos_msg(msgParts[1]);
				} else if (msgParts[0].equals(MessageType.Ping.toString())) {
					if (msgParts.length > 1)
						msg = new Ping_msg(msgParts[1]);
					else
						msg = new Ping_msg();
				} else if (msgParts[0].equals(MessageType.Result.toString())) {
					if (msgParts.length > 2) {
						String data = "";
						for (int i = 2; i < msgParts.length; i++) {
							data += msgParts[i];
							if(i != msgParts.length-1) data += '|';
						}
						msg = new Result_msg(msgParts[1], data);
					} else {
						msg = new Result_msg(msgParts[1]);
					}
				} else {
					msg = new Invalid_msg();
				}
			} catch (Exception e) {
				msg = new Invalid_msg();
			}			
		
		} catch(IOException e) {
			logger.info(e.toString());
			msg = new Leave_msg();
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
