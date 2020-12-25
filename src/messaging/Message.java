package messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class Message {
	private Logger logger = Logger.getLogger("");
	protected MessageType type;

	public Message(MessageType type) {
		this.type = type;
	}
	
	public Message receiveMessage(Socket socket) {
		BufferedReader in;
		Message msg = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgText = in.readLine();
			logger.info("Receiving message: " + msgText);
			
			// Parse message
			String msgParts[] = msgText.split("\\|");
			if (msgParts[0].equals(MessageType.CREATE_LOGIN.toString())) {
				msg = new CreateLogin_msg(msgParts[1], msgParts[2]);
			} else if (msgParts[0].equals(MessageType.LOGIN.toString())) {
				msg = new Login_msg(msgParts[1], msgParts[2]);
			} else if (msgParts[0].equals(MessageType.CHANGE_PASSWORD.toString())) {
				 msg = new ChatMsg(msgParts[1], msgParts[2]);
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
