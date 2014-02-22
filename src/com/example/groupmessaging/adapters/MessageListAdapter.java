package com.example.groupmessaging.adapters;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.models.ContactManager;
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
	private TextView tvMessageBody;
	private TextView tvMessageSender;
	private TextView tvMessageTimestamp;
	

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
	protected View getViewFromLayout(int layout, View view, ViewGroup viewGroup, Message message) {
		// TODO Auto-generated method stub
		String sender = message.getSender();
		
		if (view == null) {
			//check if message from me or other
			if(sender.equals(userID)){
				view = inflater.inflate(R.layout.message_me, viewGroup, false);
				view.setTag(Integer.valueOf(0));
			}
			else{
				view = inflater.inflate(R.layout.message_others, viewGroup, false);
				view.setTag(Integer.valueOf(1));
			}
		}
		else{
			if(view.getTag().equals(Integer.valueOf(0)) && !sender.equals(userID)){
				view = inflater.inflate(R.layout.message_others, viewGroup, false);
				view.setTag(Integer.valueOf(1));
			}
			
			if(view.getTag().equals(Integer.valueOf(1)) && sender.equals(userID)){
				view = inflater.inflate(R.layout.message_me, viewGroup, false);
				view.setTag(Integer.valueOf(0));
			}
		}
		return view;
	}
	
	@Override
	protected void populateView(View v, Message message) {
		tvMessageSender = (TextView) v.findViewById(R.id.tvMessageSender);
		tvMessageBody = (TextView) v.findViewById(R.id.tvMessageBody);
		tvMessageTimestamp = (TextView) v.findViewById(R.id.tvMessageTimestamp);
		
		tvMessageSender.setText(ContactManager.getInstance().getDisplayName(message.getSender().toString()));
		tvMessageBody.setText(message.getText().toString());
		
		Date date = new Date(message.getTimestamp());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateFormatted = formatter.format(date);
		tvMessageTimestamp.setText(dateFormatted);
	}

}
