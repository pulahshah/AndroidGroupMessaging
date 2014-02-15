package com.example.groupmessaging.restapi;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.models.Group;
import com.example.groupmessaging.models.Message;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthListener;
import com.firebase.client.Query;
import com.firebase.simplelogin.User;

public class GroupMessagingClient {
	private static Context context;
	private static Firebase fbClient;
	private static User currentUser;
	private static String uniqueId;
	private static int groupNumber = 0;
	
	public static void initialize(Context c, Firebase fb) {
		context = c;
		fbClient = fb;
	}
	
	public static void auth(User u, AuthListener listener) {
		currentUser = u;
		fbClient.auth(currentUser.getAuthToken(), listener);
	}
	
	public static String createGroup(String name, ArrayList<Contact> users, String message) {
		Firebase groupRef = fbClient.child("groups").push();
		Firebase usersRef = fbClient.child("users");
		
		String groupID = groupRef.getName();
		
		/* Add self to group */
		groupRef.child("users/" + getUniqueId(currentUser)).setValue(Integer.valueOf(1));
		
		Message createMessage = new Message(getUniqueId(currentUser), message);
		
		/* Set name and last message */
		Group newGroup = new Group();
		newGroup.setLastMessage(createMessage);
		newGroup.setName(name);

		
		/* Add created Message to group */
		sendMessage(groupID, message);
		
		for (Contact user : users) {
			/* Add others to group */
			groupRef.child("users/" + user.getId()).setValue(Integer.valueOf(1));
			
			/* Add group to other users */
			Firebase userGroupRef = usersRef.child(user.getId() + "/groups/" + groupID); 
			userGroupRef.setValue(newGroup);
		}
		
		
		/* Add group to self groups */
		Firebase userGroupRef = usersRef.child(getUniqueId(currentUser) + "/groups/" + groupID); 
		userGroupRef.setValue(newGroup);
		
		return groupID;
	}
	
	public static void sendMessage(String groupID, String message) {
		Firebase messages = fbClient.child("groups/" + groupID + "/messages");
		messages.push().setValue(new Message(getUniqueId(currentUser), message));
	}
	
	public static Query getMyGroups() {
		return fbClient.child("users/"+getUniqueId(currentUser)+"/groups").endAt();
	}
	
	public static Query getMyContacts() {
		return fbClient.child("users/"+getUniqueId(currentUser)+"/private/contacts").startAt();
	}
	
	public static void updateUserInfo(String firstName, String lastName) {
		Firebase userInfo = fbClient.child("users/" + getUniqueId(currentUser) + "/private/userInfo");
		HashMap<String, String> kv = new HashMap<String, String>();		
		kv.put("firstName", firstName != null ? firstName : "");
		kv.put("lastName", lastName != null ? lastName : "");
		userInfo.setValue(kv);
	}
	
	public static String getUniqueId(User u) {
		return "simplelogin:" + u.getUserId();
	}
}
