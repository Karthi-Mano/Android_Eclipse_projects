package com.assist;

import android.app.Activity;
import android.os.Bundle;

public class ViewMessageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("View Message");
	}

}
