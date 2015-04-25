package com.assist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class SendMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_send_message);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Send Message");
	}

	public void onClickSend(View v) {
		startActivity(new Intent(this, ViewMessageActivity.class));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==android.R.id.home)
		{
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

}
