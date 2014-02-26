package com.example.groupmessaging.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.Contact;
import com.tokenautocomplete.TokenCompleteTextView;

public class ContactsCompletionView extends TokenCompleteTextView {

	public ContactsCompletionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected Contact defaultObject(String arg0) {
		return null;
	}

	@Override
	protected View getViewForObject(Object obj) {
		Contact c = (Contact)obj;
		
		LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.contact_token_view, (ViewGroup)ContactsCompletionView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(c.toString());
        
		return view;
	}
	
	public ArrayList<Contact> getContacts() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		for (Object o: super.getObjects()) {
			contacts.add((Contact)o);
		}
		
		return contacts;
	}

}
