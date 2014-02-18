package com.example.groupmessaging.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupmessaging.R;
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
		ImageView ivGroupImage = (ImageView)v.findViewById(R.id.ivGroupImage);
		TextView tvGroupMembers = (TextView)v.findViewById(R.id.tvGroupMembers);
		TextView tvMessageSnippet = (TextView)v.findViewById(R.id.tvMessageSnippet);
		TextView tvLastMessageTimestamp = (TextView)v.findViewById(R.id.tvLastMessageTimestamp);
		
		if (model != null) {
			if (model.getLastMessage() != null) {
				tvMessageSnippet.setText(model.getName() + ":" + model.getLastMessage().getText());
				tvLastMessageTimestamp.setText(model.formattedTimestamp());
			}
			tvGroupMembers.setText(model.getName());
		}
	}

}
