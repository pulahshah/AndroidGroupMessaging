package com.example.groupmessaging.activities;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.ContactListAdapter;
import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.example.groupmessaging.view.ContactsCompletionView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CreateGroupActivity extends Activity {
	private ContactsCompletionView mactvRecipients;
	private ContactListAdapter adapter;
	private EditText etGroupName;
	private EditText etMessage;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		mactvRecipients = (ContactsCompletionView)findViewById(R.id.mactvRecipients);
		etGroupName = (EditText)findViewById(R.id.etGroupName);
		etMessage = (EditText)findViewById(R.id.etMessage);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("New Group");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_create_group:
	            onGroupCreate();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		adapter = new ContactListAdapter(this);
		mactvRecipients.setAdapter(adapter);
		mactvRecipients.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
	
	public void onCancel(View sender) {
		finish();
	}
	
	public void onGroupCreate() {
		String groupName = etGroupName.getText().toString();
		String inviteMessage = etMessage.getText().toString(); 
		ArrayList<Contact> selectedContacts = mactvRecipients.getContacts();
		
		if(isEmpty(etGroupName)){
			groupName = "Group";
		}
		
		if(selectedContacts.size() == 0){
			Toast.makeText(getApplicationContext(), "Select group members", Toast.LENGTH_SHORT).show();
		}
		else{
			GroupMessagingClient.createGroup(groupName, selectedContacts,
					inviteMessage);
			finish();	
		}
	}
	
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
