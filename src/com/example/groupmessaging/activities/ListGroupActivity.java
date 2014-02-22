package com.example.groupmessaging.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.GroupListAdapter;
import com.example.groupmessaging.models.AccountManager;
import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.models.ContactManager;
import com.example.groupmessaging.models.ContactManager.InfoListener;
import com.example.groupmessaging.models.Group;

public class ListGroupActivity extends Activity {
	private static final String TAG = "ListGroupActivity";
	private ListView lvGroups;
	private GroupListAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_group);
		
		setTitle(AccountManager.getInstance().getCurrentUser().getEmail());
		updateTitleWithUserInfo();
		lvGroups = (ListView)findViewById(R.id.lvGroups);
		lvGroups.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ListGroupActivity.this.showGroup((Group)adapter.getItem(position));
			}
			
		});
	}

	private void updateTitleWithUserInfo() {
		ContactManager.getInstance().getMyInfo(new InfoListener() {
			
			@Override
			public void onSuccess(Contact me) {
				setTitle(me.getFirstName() + " " + me.getLastName());
				
			}
			
			@Override
			public void onFailure(String message) {
				
			}
		});
	}

	protected void showGroup(Group item) {
		Intent i = new Intent(this, MessagesActivity.class);
		i.putExtra(MessagesActivity.INTENT_PARAM_GROUPID, item.getId());
		i.putExtra(MessagesActivity.INTENT_PARAM_USERID, AccountManager.getInstance().getUserUniqueID());
		i.putExtra(MessagesActivity.INTENT_PARAM_GROUPNAME, item.getName());
		startActivity(i);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_group, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		adapter = new GroupListAdapter(R.layout.list_item_group, this);
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
