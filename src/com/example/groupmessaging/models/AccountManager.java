package com.example.groupmessaging.models;

import android.content.Context;

import com.firebase.client.Firebase;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;
import com.firebase.simplelogin.enums.Error;

public class AccountManager {
	private static AccountManager instance;
	private SimpleLogin authClient;
	private User currentUser;
	
	public static void initialize(Context context, Firebase fb) {
		instance = new AccountManager(new SimpleLogin(fb, context));
	}
	
	public static AccountManager getInstance() {		
		return instance;
	}
	
	private AccountManager(SimpleLogin authClient) {
		this.authClient = authClient;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public static interface StartCallback {
		public void onLoggedIn(User user);
		public void onLoggedOut();
	}
	
	public void start(final StartCallback listener) {
		authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
			
			@Override
			public void authenticated(Error error, User user) {
				if (error != null || user == null) {
					listener.onLoggedOut();
					return;
				}
				currentUser = user;
				listener.onLoggedIn(user);
			}
		});
	}
	
	public static enum LoginError {
		CREDENTIALS_MISSING ("Missing Username/Password"),
		USERNAME_OR_PASSWORD_INVALID ("Invalid Username/Password");
		
		private String message;
		
		LoginError(String message) {
			this.message = message;
		}
		
		public String message() {
			return this.message;
		}
	}
	
	public static interface LoginCallback {
		public void onLoginSuccess(User user);
		public void onLoginFailure(LoginError error);
	}
	
	
	public void login(String username, String password, final LoginCallback listener) {
		if (username == null || username.length() == 0 || password == null || password.length() == 0) {
			listener.onLoginFailure(LoginError.CREDENTIALS_MISSING);
			return;
		}
		
		authClient.loginWithEmail(username, password, new SimpleLoginAuthenticatedHandler() {
			  public void authenticated(Error error, User user) {
			    if(error != null) {
			      // There was an error logging into this account
			    	listener.onLoginFailure(LoginError.USERNAME_OR_PASSWORD_INVALID);
			    	return;
			    }
			    
		        // We are now logged in
			    currentUser = user;
			    listener.onLoginSuccess(user);
			  }
			});
	}
	
	public static enum SignUpError {
		CREDENTIALS_MISSING("Missing Credentials"),
		USERNAME_UNAVAILABLE("Username already taken");
		
		private String message;
		
		private SignUpError(String message) {
			this.message = message;
		}
		
		public String message() {
			return this.message;
		}
	}
	
	public static interface SignUpCallback {
		public void onSignUpSuccess(User user);
		public void onSignUpFailure(SignUpError error);
	}
	
	public void signUp(String username, String password, final SignUpCallback listener) {
		if (username == null || username.length() == 0 || password == null || password.length() == 0) {
			listener.onSignUpFailure(SignUpError.CREDENTIALS_MISSING);
			return;
		}
		
		
		authClient.createUser(username, password, new SimpleLoginAuthenticatedHandler() {
			  public void authenticated(Error error, User user) {
			    if(error != null) {
			    	//Need to check for codes
			     listener.onSignUpFailure(SignUpError.USERNAME_UNAVAILABLE);
			      return;
			    }
			 
			     // User Created
			    listener.onSignUpSuccess(user);
			  }
			});
	}
	
	public void logout() {
		authClient.logout();
	}
}
