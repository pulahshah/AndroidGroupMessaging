package com.example.groupmessaging.models;

import java.text.DateFormat;
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
		
		DateFormat df = DateFormat.getTimeInstance(SimpleDateFormat.SHORT);
		return df.format(new Date(lastMessage.getTimestamp()));
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (other.getClass() != getClass()) return false;

		Group otherGroup = (Group)other;
		if (getId() == otherGroup.getId()) return true;
		if (otherGroup.getId() == null) return false;
		if (getId() == null) return false;
			
		if (otherGroup.getId().equals(getId())) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
