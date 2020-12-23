package messaging;

import java.util.ArrayList;

public class ListToDos_msg extends Message {
	ArrayList<Integer> idList;
	
	public ListToDos_msg(ArrayList<Integer> idList) {
		super(MessageType.LIST_TODOS);
		this.idList = idList;
	}

}
