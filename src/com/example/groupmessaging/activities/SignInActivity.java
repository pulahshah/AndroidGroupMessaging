package com.example.groupmessaging.activities;

import android.R.bool;
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

public class SignInActivity extends Activity
{
	private static final String TAG = "SignInActivity";
	
	private EditText etUsername;
	private EditText etPassword;
	private String firstName, lastName;
	private boolean updateUserInfo;
	
	private static final int REQUEST_SIGNUP = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        updateUserInfo = false;
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	AccountManager.getInstance().start(new StartCallback() {
			
			@Override
			public void onLoggedIn(User user) {
				onUserSignedIn(user);
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
				onUserSignedIn(user);
			}
			
			@Override
			public void onLoginFailure(LoginError error) {
				Toast.makeText(SignInActivity.this, error.message(), Toast.LENGTH_LONG).show();
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_SIGNUP) {
    		if (resultCode == RESULT_OK) {
    			firstName = data.getStringExtra(SignUpActivity.RESPONSE_KEY_FIRSTNAME);
    			lastName = data.getStringExtra(SignUpActivity.RESPONSE_KEY_LASTNAME);
    			if (firstName != null && firstName.length() > 0)
    				updateUserInfo = true;
    			
    			signIn(data.getStringExtra(SignUpActivity.RESPONSE_KEY_USERNAME),
    					data.getStringExtra(SignUpActivity.RESPONSE_KEY_PASSWORD));
    			return;
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    protected void showConversations() {
    	Intent i = new Intent(this, ListGroupActivity.class);
    	startActivity(i);
	}
    
    protected void updateUserInfo() {
		GroupMessagingClient.updateUserInfo(firstName, lastName);
		updateUserInfo = false;
	}
    
    protected void onUserSignedIn(User user) {
    	Log.d(TAG, "GMUser info: " + user.getUserId() + " " + user.getEmail());
		GroupMessagingClient.auth(user, new AuthListener() {
			
			@Override
			public void onAuthSuccess(Object arg0) {
				if (updateUserInfo)
					updateUserInfo();
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
}
