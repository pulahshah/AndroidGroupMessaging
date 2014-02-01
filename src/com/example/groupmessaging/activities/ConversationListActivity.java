package com.example.groupmessaging.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.AccountManager;

public class ConversationListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation_list);
		TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
		tvUsername.setText(AccountManager.getInstance().getCurrentUser().toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation_list, menu);
		return true;
	}
	
	public void onActionSignOut(MenuItem mi) {
		AccountManager.getInstance().logout();
		finish();
	}

}
