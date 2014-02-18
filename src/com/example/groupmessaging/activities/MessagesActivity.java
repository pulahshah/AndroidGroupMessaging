package com.example.groupmessaging.activities;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.MessageListAdapter;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class MessagesActivity extends Activity {
	private ListView lvMessages;
	private MessageListAdapter adapter;
	private EditText etNewMessage;
	
	public static final String INTENT_PARAM_GROUPID = "gid";
	public static final String INTENT_PARAM_USERID = "uid";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		lvMessages = (ListView)findViewById(R.id.lvMessages);
		etNewMessage = (EditText)findViewById(R.id.etNewMessage);
		
		final String groupID = getIntent().getStringExtra(INTENT_PARAM_GROUPID);
		String userID = getIntent().getStringExtra(INTENT_PARAM_USERID);
		adapter = new MessageListAdapter(groupID, userID, this);
		
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				lvMessages.setSelection(adapter.getCount() - 1);
			}
		});
		
		lvMessages.setAdapter(adapter);
		
		etNewMessage.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
					String message = v.getText().toString();
					if (message != null && message.length() > 0) {
						GroupMessagingClient.sendMessage(groupID, message);
						v.setText("");
					}
					return true;
				}
				
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
		return true;
	}

}
