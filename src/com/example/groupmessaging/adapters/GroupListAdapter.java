package com.example.groupmessaging.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.groupmessaging.models.Group;
import com.firebase.client.Query;

public class GroupListAdapter extends FirebaseListAdapter<Group> {
	private static final String TAG = "GroupListAdapter";
	
	public GroupListAdapter(Query ref, int layout,
			Activity activity) {
		super(ref, Group.class, layout, activity);
	}

	@Override
	protected void populateView(View v, Group model) {
		Log.d(TAG, "users " + model.getUsers().toString());
	}

}
