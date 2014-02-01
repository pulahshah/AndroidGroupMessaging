package com.example.groupmessaging.restapi;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

public class GroupMessagingClient extends AsyncHttpClient {
	private static GroupMessagingClient instance;
	private Context context;

	public GroupMessagingClient(Context c) {
		this.context = c;
	}
	
	public static GroupMessagingClient getInstance(Context c) {
		if (instance == null) {
			instance = new GroupMessagingClient(c);
		}
		
		return instance;
	}
}
