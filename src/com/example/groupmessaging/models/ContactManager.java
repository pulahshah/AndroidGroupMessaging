package com.example.groupmessaging.models;

import java.util.Map;

import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.example.groupmessaging.models.Contact;

public class ContactManager {
	private static ContactManager instance;
	private Map<String, Contact> contacts;
	private Contact me;
		
	public static ContactManager getInstance() {
		if (instance == null) {
			instance = new ContactManager();
		}		
		return instance;
	}
	
	public ContactManager() {
		Query ref = GroupMessagingClient.getMyContacts();
		
		ref.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				GenericTypeIndicator<Map<String, Contact>> t = new GenericTypeIndicator<Map<String, Contact>>() {
				};
				
				contacts = snapshot.getValue(t);
			}
			
			@Override
			public void onCancelled(FirebaseError error) {
				
			}
		});
		
		getMyInfo(new InfoListener() {
			
			@Override
			public void onSuccess(Contact me) {
				ContactManager.this.me = me;
				
			}
			
			@Override
			public void onFailure(String message) {			
			}
		});
	}
	
	public String getDisplayName(String userId) {
		if (contacts == null && me == null)
			return userId;
		
		if (me != null && me.getId().equals(userId))
			return "You";
		
		Contact c = contacts.get(userId);
		
		if (c == null)
			return userId;
		
		return c.getFirstName();
	}

	public static interface InfoListener {
		public void onSuccess(Contact me);
		public void onFailure(String message);
	}
	
	public void getMyInfo(final InfoListener listener) {
		Query ref1 = GroupMessagingClient.getMyUserInfo();
		ref1.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Contact me = snapshot.getValue(Contact.class);
				listener.onSuccess(me);
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				
			}
		});		
	}
}
