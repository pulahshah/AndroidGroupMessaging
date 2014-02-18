package com.example.groupmessaging.models;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Group {
	private String name;
	private String id;
	private Message lastMessage;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Message getLastMessage() {
		return lastMessage;
	}
	
	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String formattedTimestamp() {
		if (lastMessage == null)
			return "";
		
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
		return df.format(new Date(lastMessage.getTimestamp()));
	}
}
