package com.example.groupmessaging.models;


public class Group {
	private String name;
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
}
