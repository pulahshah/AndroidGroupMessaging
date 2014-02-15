package com.example.groupmessaging.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.groupmessaging.models.Message;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class MessageListAdapter extends FirebaseListAdapter<Message> {

	public MessageListAdapter(String groupID,
			Activity activity) {
		super(GroupMessagingClient.getMessagesForGroup(groupID), Message.class, android.R.layout.simple_list_item_1, activity);
	}

	@Override
	protected void populateView(View v, Message model) {
		TextView tv = (TextView)v;
		tv.setText(model.getSender() + ": " + model.getText());
	}

}
