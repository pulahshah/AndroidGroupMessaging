package com.example.groupmessaging.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.ContactListAdapter;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.example.groupmessaging.view.ContactsCompletionView;

public class CreateGroupActivity extends Activity {
	private ContactsCompletionView mactvRecipients;
	private ContactListAdapter adapter;
	private EditText etGroupName;
	private EditText etMessage;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation_compose);
		mactvRecipients = (ContactsCompletionView)findViewById(R.id.mactvRecipients);
		etGroupName = (EditText)findViewById(R.id.etGroupName);
		etMessage = (EditText)findViewById(R.id.etMessage);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().hide();
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
	
	public void onCreate(View sender) {
		GroupMessagingClient.createGroup(etGroupName.getText().toString(), mactvRecipients.getContacts(),
				etMessage.getText().toString());
		finish();
	}
}
