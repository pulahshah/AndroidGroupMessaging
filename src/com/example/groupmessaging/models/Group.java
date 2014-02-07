package com.example.groupmessaging.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Group {
	private ArrayList<Message> messages;
	private HashMap<String, Integer> users;
	
	public Group() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public HashMap<String, Integer> getUsers() {
		return users;
	}
	
	public void setUsers(HashMap<String, Integer> users) {
		this.users = users;
	}
}
