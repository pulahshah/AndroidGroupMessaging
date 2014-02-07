package com.example.groupmessaging.models;

import java.util.Calendar;

public class Message {
	private String sender;
	private String text;
	private long timestamp;
	
	private Message() {
		
	}
	
	public Message (String sender, String text) {
		this.sender = sender;
		this.text = text;
		this.timestamp = Calendar.getInstance().getTimeInMillis();
	}
}
