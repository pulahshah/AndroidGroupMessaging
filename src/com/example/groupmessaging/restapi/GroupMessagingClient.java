package com.example.groupmessaging.restapi;

import android.content.Context;

import com.firebase.client.Firebase;

public class GroupMessagingClient {
	private static Context context;
	private static Firebase fbClient;
	
	public static void initialize(Context c, Firebase fb) {
		context = c;
		fbClient = fb;
	}
}
