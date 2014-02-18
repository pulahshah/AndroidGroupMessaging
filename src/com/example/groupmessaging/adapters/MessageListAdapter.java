package com.example.groupmessaging.adapters;

import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.models.Message;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class MessageListAdapter extends FirebaseListAdapter<Message> {
	private Map<String, Contact> contacts;
	private String userID;

	public MessageListAdapter(String groupID, String userID,
			Activity activity) {
		super(GroupMessagingClient.getMessagesForGroup(groupID), Message.class, android.R.layout.simple_list_item_1, activity);
		
		this.userID = userID;
		/* Get a mapping of contact IDs to their information */
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

	}

	@Override
	protected void populateView(View v, Message message) {
		TextView tv = (TextView)v;
		
		
		String sender = message.getSender();
		String displayName = null;
		
		if (sender.equals(userID)) {
			displayName = "You";
		} else {		
			if (contacts != null) {
				Contact c = contacts.get(sender);
				if (c != null)
					displayName = c.getFirstName();
			} else {
				displayName = sender;
			}
		}
			
		tv.setText(displayName + ": " + message.getText());
	}

}
