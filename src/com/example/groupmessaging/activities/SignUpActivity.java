package com.example.groupmessaging.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupmessaging.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
	private EditText etUsername;
	private EditText etPassword;
	private EditText etConfirmPassowrd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		setupViews();
	}

	protected void setupViews() {
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfirmPassowrd = (EditText) findViewById(R.id.etConfirmPassword);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}
	
	public void onSignUp(View v) {
		ParseUser user = new ParseUser();
		user.setUsername(etUsername.getText().toString());
		user.setPassword(etPassword.getText().toString());
		
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e != null) {
					Toast.makeText(SignUpActivity.this, "Unable to signup - " + e.toString(), Toast.LENGTH_LONG).show();
					return;
				}
				
				setResult(RESULT_OK);
				finish();
			}
		});
	}

}
