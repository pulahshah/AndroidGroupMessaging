package com.example.groupmessaging.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.groupmessaging.models.Group;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class GroupListAdapter extends FirebaseListAdapter<Group> {
	private static final String TAG = "GroupListAdapter";
	
	public GroupListAdapter(int layout,
			Activity activity) {
		super(GroupMessagingClient.getMyGroups(), Group.class, layout, activity);
	}

	@Override
	protected void populateView(View v, Group model) {
		TextView tv = (TextView)v;
		if (model != null) {
			if (model.getLastMessage() != null) {
				tv.setText(model.getName() + ":" + model.getLastMessage().getText());
			} else {
				tv.setText(model.getName());
			}
		}
	}

}
