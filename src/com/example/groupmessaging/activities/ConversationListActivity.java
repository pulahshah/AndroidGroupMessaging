package com.example.groupmessaging.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.GroupListAdapter;
import com.example.groupmessaging.models.AccountManager;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class ConversationListActivity extends Activity {
	private static final String TAG = "ConversationListActivity";
	private ListView lvGroups;
	private int group = 0;
	private GroupListAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation_list);
		lvGroups = (ListView)findViewById(R.id.lvGroups);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation_list, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		adapter = new GroupListAdapter(android.R.layout.simple_list_item_1, this);
		lvGroups.setAdapter(adapter);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void onActionSignOut(MenuItem mi) {
		AccountManager.getInstance().logout();
		finish();
	}
	
	public void onActionNewGroup(MenuItem mi) {
		Intent i = new Intent(this, CreateGroupActivity.class);
		startActivity(i);
	}

}
