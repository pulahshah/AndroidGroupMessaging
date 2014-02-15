package com.example.groupmessaging.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.AccountManager;
import com.example.groupmessaging.models.AccountManager.SignUpCallback;
import com.example.groupmessaging.models.AccountManager.SignUpError;
import com.firebase.simplelogin.User;

public class SignUpActivity extends Activity {
	private EditText etUsername;
	private EditText etPassword;
	private EditText etFirstAndLastName;
	
	public static final String RESPONSE_KEY_USERNAME = "u";
	public static final String RESPONSE_KEY_PASSWORD = "p";
	public static final String RESPONSE_KEY_FIRSTNAME = "fn";
	public static final String RESPONSE_KEY_LASTNAME = "ln";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		setupViews();
	}

	protected void setupViews() {
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etFirstAndLastName = (EditText) findViewById(R.id.etFirstAndLastName);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	
	public void onSignUp(View v) {
		final String username = etUsername.getText().toString();
		final String password = etPassword.getText().toString();
		
		final String firstAndLastName = etFirstAndLastName.getText().toString();
		
		final String[] names = firstAndLastName.split(" ", 2);
		
		AccountManager.getInstance().signUp(username, password, new SignUpCallback() {
			
			@Override
			public void onSignUpSuccess(User user) {
				
				Intent i = new Intent();
				i.putExtra(RESPONSE_KEY_USERNAME, username);
				i.putExtra(RESPONSE_KEY_PASSWORD, password);
				if (names.length > 0) {
					i.putExtra(RESPONSE_KEY_FIRSTNAME, names[0]);
				}
				
				if (names.length > 1) {
					i.putExtra(RESPONSE_KEY_LASTNAME, names[1]);
				}
				
				setResult(RESULT_OK, i);
				finish();
			}
			
			@Override
			public void onSignUpFailure(SignUpError error) {
				Toast.makeText(SignUpActivity.this, error.message(), Toast.LENGTH_LONG).show();
				
			}
		});
	}

}
