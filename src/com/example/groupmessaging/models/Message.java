package com.example.groupmessaging.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Message {
	private String sender;
	private String text;
	private long timestamp;
	private int type;
	
	public static final int MESSAGE=0;
	public static final int LOCATION=1;
	
	private Message() {
		
	}
	
	public Message (String sender, String text) {
		this.sender = sender;
		this.text = text;
		this.timestamp = Calendar.getInstance().getTimeInMillis();
		this.type = MESSAGE;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String formattedTimestamp() {
		DateFormat df = DateFormat.getTimeInstance(SimpleDateFormat.SHORT);
		return df.format(new Date(timestamp));
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
