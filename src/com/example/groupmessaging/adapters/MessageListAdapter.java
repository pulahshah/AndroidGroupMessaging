package com.example.groupmessaging.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.groupmessaging.R;
import com.example.groupmessaging.models.Contact;
import com.example.groupmessaging.models.ContactManager;
import com.example.groupmessaging.models.Message;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

public class MessageListAdapter extends FirebaseListAdapter<Message> {
	private Map<String, Contact> contacts;
	private String userID;
	private TextView tvMessageBody;
	private TextView tvMessageSender;
	private TextView tvMessageTimestamp;
	private SmartImageView ivImage;



	public MessageListAdapter(String groupID, String userID,
			Activity activity) {
		super(GroupMessagingClient.getMessagesForGroup(groupID), Message.class, android.R.layout.simple_list_item_1, activity);

		this.userID = userID;
		/* Get a mapping of contact IDs to their information */
		Query ref = GroupMessagingClient.getMyContacts();

		ref.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				GenericTypeIndicator<Map<String, Contact>> t = new GenericTypeIndicator<Map<String, Contact>>() {
				};

				contacts = snapshot.getValue(t);

			}

			@Override
			public void onCancelled(FirebaseError error) {

			}
		});

	}

	@Override
	protected View getViewFromLayout(int layout, View view, ViewGroup viewGroup, Message message) {
		String sender = message.getSender();

		//TODO: do not enforce null 
		view = null;

		if(view == null){
			if(sender.equals(userID)){
				if(message.getType() == Integer.valueOf(1)){	// location
					view = inflater.inflate(R.layout.message_me_location, viewGroup, false);
				}
				else{											// text
					view = inflater.inflate(R.layout.message_me, viewGroup, false);
				}
			}
			else{
				if(message.getType() == Integer.valueOf(1)){	// location
					Log.d("DEBUG", "received location: " + message.getText());
					view = inflater.inflate(R.layout.message_other_location, viewGroup, false);
				}
				else{											// text
					view = inflater.inflate(R.layout.message_others, viewGroup, false);
				}
			}
		}

		/*
		if (view == null) {
			//check if message from me or other
			if(sender.equals(userID)){
				view = inflater.inflate(R.layout.message_me, viewGroup, false);
				view.setTag(Integer.valueOf(0));

			}
			else{
				view = inflater.inflate(R.layout.message_others, viewGroup, false);
				view.setTag(Integer.valueOf(1));
			}
		}
		else{
			if(view.getTag().equals(Integer.valueOf(0)) && !sender.equals(userID)){
				view = inflater.inflate(R.layout.message_others, viewGroup, false);
				view.setTag(Integer.valueOf(1));
			}

			if(view.getTag().equals(Integer.valueOf(1)) && sender.equals(userID)){
				view = inflater.inflate(R.layout.message_me, viewGroup, false);
				view.setTag(Integer.valueOf(0));
			}
		}
		 */
		return view;
	}


	@Override
	protected void populateView(View v, Message message) {
		if(!message.getSender().equals(userID)){
			tvMessageSender = (TextView) v.findViewById(R.id.tvMessageSender);
			tvMessageSender.setText(ContactManager.getInstance().getDisplayName(message.getSender().toString()));
		}

		if(message.getType() == Integer.valueOf(1)){
			ivImage = (SmartImageView) v.findViewById(R.id.ivLocationMessage);
			ivImage.setImageUrl(getMapUrl(message));
		}
		else{
			tvMessageBody = (TextView) v.findViewById(R.id.tvMessageBody);
			tvMessageBody.setText(message.getText().toString());
		}

		tvMessageTimestamp = (TextView) v.findViewById(R.id.tvMessageTimestamp);
		tvMessageTimestamp.setText(getFormattedTimeStamp(message.getTimestamp()));
	}

	private String getMapUrl(Message message) {
		String latlon = message.getText();
		String delims = ",";
		String[] tokens = latlon.split(delims);
		String tempUrl = "http://maps.googleapis.com/maps/api/staticmap?format=png32&center=" +
						tokens[0]+","+tokens[1]+"&zoom=19&size=600x600&markers=color:red:S%7C"+
						tokens[0]+","+tokens[1]+"&sensor=false";
		return tempUrl;
	}

	private String getFormattedTimeStamp(long timestamp) {
		DateFormat df = DateFormat.getTimeInstance(SimpleDateFormat.SHORT);
		return df.format(new Date(timestamp));
	}

}
