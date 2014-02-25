package com.example.groupmessaging.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.ContactManager;
import com.example.groupmessaging.models.Group;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class GroupListAdapter extends FirebaseListAdapter<Group> {
	@SuppressWarnings("unused")
	private static final String TAG = "GroupListAdapter";
    
	public GroupListAdapter(int layout,
			Activity activity) {
		super(GroupMessagingClient.getMyGroups(), Group.class, layout, activity);
	}

	@Override
	public Object getItem(int i) {
		return super.getItem(getCount() - i - 1);
	}
	
	@Override
	protected void populateView(View v, Group model) {
		ImageView ivGroupImage = (ImageView)v.findViewById(R.id.ivGroupImage);
		TextView tvGroupMembers = (TextView)v.findViewById(R.id.tvGroupMembers);
		TextView tvMessageSnippet = (TextView)v.findViewById(R.id.tvMessageSnippet);
		TextView tvLastMessageTimestamp = (TextView)v.findViewById(R.id.tvLastMessageTimestamp);
		
		if (model != null) {
			if (model.getLastMessage() != null) {
				String senderId = model.getLastMessage().getSender();
				tvMessageSnippet.setText(ContactManager.getInstance().getDisplayName(senderId) + ": " + model.getLastMessage().getText());
				tvLastMessageTimestamp.setText(model.formattedTimestamp());
			}
			
			String groupName = model.getName();
			tvGroupMembers.setText(groupName);
			
			if(groupName.equals("Awesome UI")){
				ivGroupImage.setBackgroundResource(R.drawable.profile);
			}
			else if(groupName.equals("Another Class Group")){
				ivGroupImage.setBackgroundResource(R.drawable.profile1);
			}
			else if(groupName.equals("Group")){
				ivGroupImage.setBackgroundResource(R.drawable.profile2);
			}
			else{
				ivGroupImage.setBackgroundResource(R.drawable.batman);
			}
		}
	}

}
