package com.example.groupmessaging.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.AccountManager;
import com.example.groupmessaging.models.AccountManager.LoginCallback;
import com.example.groupmessaging.models.AccountManager.LoginError;
import com.example.groupmessaging.models.AccountManager.StartCallback;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.Firebase.AuthListener;
import com.firebase.client.FirebaseError;
import com.firebase.simplelogin.User;

public class AccountRegisterActivity extends Activity
{
	private static final String TAG = "AccountRegisterActivity";
	
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
    	AccountManager.getInstance().start(new StartCallback() {
			
			@Override
			public void onLoggedIn(User user) {
				Log.d(TAG, "GMUser info: " + user.getUserId() + " " + user.getEmail());
				GroupMessagingClient.auth(user, new AuthListener() {
					
					@Override
					public void onAuthSuccess(Object arg0) {
						showConversations();
					}
					
					@Override
					public void onAuthRevoked(FirebaseError arg0) {
						Log.e(TAG, "Auth revoked: "+ arg0.toString());
					}
					
					@Override
					public void onAuthError(FirebaseError arg0) {
						Log.e(TAG, "Auth error: "+ arg0.toString());
					}
				});
				
			}
			
			@Override
			public void onLoggedOut() {
				
			}
		});
    }
    
    public void onSignUp(View v) {
    	Intent i = new Intent(this, SignUpActivity.class);
    	startActivityForResult(i, REQUEST_SIGNUP);
	}
    
    public void onSignIn(View v) {
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		signIn(username, password);
	}
    
    public void signIn(String username, String password) {
    	AccountManager.getInstance().login(username, password, new LoginCallback() {
			
			@Override
			public void onLoginSuccess(User user) {
				showConversations();
				
			}
			
			@Override
			public void onLoginFailure(LoginError error) {
				Toast.makeText(AccountRegisterActivity.this, error.message(), Toast.LENGTH_LONG).show();
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_SIGNUP) {
    		if (resultCode == RESULT_OK) {
    			signIn(data.getStringExtra(SignUpActivity.RESPONSE_KEY_USERNAME),
    					data.getStringExtra(SignUpActivity.RESPONSE_KEY_PASSWORD));
    			return;
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected void showConversations() {
    	Intent i = new Intent(this, ConversationListActivity.class);
    	startActivity(i);
	}
}
