package com.example.groupmessaging.adapters;

import java.util.ArrayList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class ContactListAdapter extends ArrayAdapter<Contact> {
	private static final String TAG = "ContactListAdapter";
    private Query ref;

	@SuppressLint("NewApi")
	public ContactListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1, new ArrayList<Contact>());
		ref = GroupMessagingClient.getMyContacts();
		
		ref.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				GenericTypeIndicator<Map<String, Contact>> t = new GenericTypeIndicator<Map<String, Contact>>() {
				};
				
				Map<String, Contact> contacts = snapshot.getValue(t);
				
				for (Contact c: contacts.values()) {
					add(c);
				}
			}
			
			@Override
			public void onCancelled(FirebaseError error) {
				
			}
		});
	}
}
