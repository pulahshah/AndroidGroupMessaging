package com.example.groupmessaging.activities;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.example.groupmessaging.adapters.MessageListAdapter;
import com.example.groupmessaging.restapi.GroupMessagingClient;

public class MessagesActivity extends Activity {
	private ListView lvMessages;
	private MessageListAdapter adapter;
	private EditText etNewMessage;
	
	public static final String INTENT_PARAM_GROUPID = "gid";
	public static final String INTENT_PARAM_USERID = "uid";
	public static final String INTENT_PARAM_GROUPNAME = "gname";
	
	String groupID = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		lvMessages = (ListView)findViewById(R.id.lvMessages);
		etNewMessage = (EditText)findViewById(R.id.etNewMessage);
		
		setTitle(getIntent().getStringExtra(INTENT_PARAM_GROUPNAME));
		groupID = getIntent().getStringExtra(INTENT_PARAM_GROUPID);
		String userID = getIntent().getStringExtra(INTENT_PARAM_USERID);
		adapter = new MessageListAdapter(groupID, userID, this);
		
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				lvMessages.setSelection(adapter.getCount() - 1);
				scrollMyListViewToBottom();
			}
		});
		
		lvMessages.setAdapter(adapter);
	}
	
	public void sendMessage(View v){
		String message = etNewMessage.getText().toString();
		if (message != null && message.length() > 0) {
			if(groupID != null){
				GroupMessagingClient.sendMessage(groupID, message);
				scrollMyListViewToBottom();
				etNewMessage.setText("");
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void scrollMyListViewToBottom() {
	    lvMessages.post(new Runnable() {
	        @Override
	        public void run() {
	            lvMessages.setSelection(adapter.getCount() - 1);
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
