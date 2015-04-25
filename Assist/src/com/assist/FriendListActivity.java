package com.assist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.assist.adapter.FriendAdapter;

public class FriendListActivity extends Activity {
	private ListView listViewFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_friend_list);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Friend List");

		listViewFriend = (ListView) findViewById(R.id.activity_friend_list_listFriend);
		final ArrayList<FriendModel> listFriend = new ArrayList<FriendModel>();
		for (int i = 0; i < 5; i++) {
			final FriendModel model = new FriendModel();
			model.setName("Friend " + (i + 1));
			listFriend.add(model);

		}
		listViewFriend.setAdapter(new FriendAdapter(this, listFriend));

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==android.R.id.home)
		{
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}
	public void onClickBroadCast(View v) {

		startActivity(new Intent(this, SendMessageActivity.class));
	}
}
