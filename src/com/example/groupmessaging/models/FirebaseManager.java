package com.example.groupmessaging.models;

import com.firebase.client.Firebase;

public class FirebaseManager {
	private static Firebase instance = new Firebase("https://resplendent-fire-8448.firebaseio.com");
	
	public FirebaseManager() {
	}
	
	public static Firebase getInstance() {
		return instance;
	}

}
