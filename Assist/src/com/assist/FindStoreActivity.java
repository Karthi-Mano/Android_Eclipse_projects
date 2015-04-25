package com.assist;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.assist.adapter.FriendAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class FindStoreActivity extends FragmentActivity {
	private GoogleMap mMap;
	private ListView listViewFriend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_find_store);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Find Store");

		listViewFriend = (ListView) findViewById(R.id.activity_find_store_listFriend);
		final ArrayList<FriendModel> listFriend = new ArrayList<FriendModel>();
		for (int i = 0; i < 5; i++) {
			final FriendModel model = new FriendModel();
			model.setName("Friend " + (i + 1));
			listFriend.add(model);

		}
		listViewFriend.setAdapter(new FriendAdapter(this, listFriend));

		listViewFriend.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				startActivity(new Intent(FindStoreActivity.this,
						FriendListActivity.class));

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		setUpMapIfNeeded();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId()==android.R.id.home)
		{
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapView)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {

				mMap.setMyLocationEnabled(true);
			}
		}
	}
}
