package com.example.groupmessaging.app;

import android.content.Context;

import com.example.groupmessaging.models.AccountManager;
import com.example.groupmessaging.restapi.GroupMessagingClient;
import com.firebase.client.Firebase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GroupMessagingApp extends com.activeandroid.app.Application {
	private static Context context;
	private Firebase fbContext;
    @Override
    public void onCreate() {
        super.onCreate();
        GroupMessagingApp.context = this;
        fbContext = new Firebase("https://resplendent-fire-8448.firebaseio.com");
        AccountManager.initialize(this, fbContext);
        GroupMessagingClient.initialize(this, fbContext);
        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
    }

}
