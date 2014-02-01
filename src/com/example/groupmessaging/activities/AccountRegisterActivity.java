package com.example.groupmessaging.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class AccountRegisterActivity extends Activity
{
	private EditText etUsername;
	private EditText etPassword;
	
	private static final int REQUEST_SIGNUP = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if (ParseUser.getCurrentUser() != null) {
    		showConversations();
    	}
    }
    
    public void onSignUp(View v) {
    	Intent i = new Intent(this, SignUpActivity.class);
    	startActivityForResult(i, REQUEST_SIGNUP);
	}
    
    public void onSignIn(View v) {
		ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e != null) {
					Toast.makeText(AccountRegisterActivity.this, "Unable to sign-in" + e.toString(), Toast.LENGTH_LONG).show();
					return;
				}
				
				showConversations();
			}
		});
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_SIGNUP) {
    		if (resultCode == RESULT_OK)
    			showConversations();
    		return;
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected void showConversations() {
    	Intent i = new Intent(this, ConversationListActivity.class);
    	startActivity(i);
		
	}
}
