package com.example.groupmessaging.restapi;

import java.util.ArrayList;

import android.content.Context;

import com.example.groupmessaging.models.Group;
import com.example.groupmessaging.models.Message;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.AuthListener;
import com.firebase.client.Query;
import com.firebase.simplelogin.User;

public class GroupMessagingClient {
	private static Context context;
	private static Firebase fbClient;
	private static User user;
	private static int groupNumber = 0;
	
	public static void initialize(Context c, Firebase fb) {
		context = c;
		fbClient = fb;
	}
	
	public static void auth(User u, AuthListener listener) {
		user = u;
		fbClient.auth(user.getAuthToken(), listener);
	}
	
	public static String createGroup(ArrayList<String> users) {
		Firebase groupRef = fbClient.child("groups").push();
		Firebase usersRef = fbClient.child("users");
		
		String groupID = groupRef.getName();
		
		/* Add self to group */
		groupRef.child("users/" + user.getUserId()).setValue(Integer.valueOf(1));
		
		Message createMessage = new Message(user.getUserId(), "Group created");
		
		/* Add created Message to group */
		sendMessage(groupID, "Group created");
		
		groupNumber += 1;
		
		for (String user : users) {
			/* Add others to group */
			groupRef.child("users/" + user).setValue(Integer.valueOf(1));
			
			/* Add group to other users */
			Firebase userGroupRef = usersRef.child(user + "/groups/" + groupID); 
			userGroupRef.setValue("1");
			
			/* Set name and last message */
			userGroupRef.child("name").setValue("MyGroup" + String.valueOf(groupNumber));
			userGroupRef.child("lastMessage").setValue(createMessage);
		}
		
		
		/* Add group to self groups */
		Firebase userGroupRef = usersRef.child(user.getUserId() + "/groups/" + groupID); 
		
		/* Set name and last message */
		Group newGroup = new Group();
		newGroup.setLastMessage(createMessage);
		newGroup.setName("MyGroup" + String.valueOf(groupNumber));
		userGroupRef.setValue(newGroup);
		
		return groupID;
	}
	
	public static void sendMessage(String groupID, String message) {
		Firebase messages = fbClient.child("groups/" + groupID + "/messages");
		messages.push().setValue(new Message(user.getUserId(), message));
	}
	
	public static Query getMyGroups() {
		return fbClient.child("users/"+user.getUserId()+"/groups").startAt();
	}
}
