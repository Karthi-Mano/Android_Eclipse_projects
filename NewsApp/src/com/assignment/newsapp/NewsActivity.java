package com.assignment.newsapp;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.assignment.newsapp.fragment.BusinessStorySectionFragment;
import com.assignment.newsapp.fragment.PoliticsStorySectionFragment;
import com.assignment.newsapp.fragment.TopStorySectionFragment;
import com.assignment.newsapp.fragment.WorldStorySectionFragment;

public class NewsActivity extends FragmentActivity implements TabListener {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	/**
	 * 
	 * 
	 * Method is used for check network connection
	 * 
	 * @param mContext
	 * @return true if network connection is available otherwise false
	 */
	public static Boolean checknetwork(Context mContext) {

		NetworkInfo info = ((ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// disable internet while roaming, just return false
			return true;
		}

		return true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_news, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!checknetwork(this)) {
			Toast.makeText(
					this,
					"There is no internet connection available.Please enable your internet connection to get latest news.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
		private String[] titles = new String[] { "Top Stories", "World",
				"Business", "Politics" };

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new TopStorySectionFragment();
			case 1:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new WorldStorySectionFragment();
			case 2:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new BusinessStorySectionFragment();
			case 3:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new PoliticsStorySectionFragment();

			}

			return null;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}

}
