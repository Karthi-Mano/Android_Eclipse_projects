package com.assignment.newsapp.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assignment.newsapp.R;
import com.assignment.newsapp.adpater.NewsAdapter;
import com.assignment.newsapp.database.DatabaseHelper;
import com.assignment.newsapp.model.NewsModel;
import com.assignment.newsapp.xmlparser.XMLHandler;

/**
 * A fragment that launches other parts of the demo application.
 */
public class BusinessStorySectionFragment extends Fragment {
	private ProgressBar progressBar;
	private ListView listViewStory;
	private ArrayList<NewsModel> listData = new ArrayList<NewsModel>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_top_story,
				container, false);
		progressBar = (ProgressBar) rootView
				.findViewById(R.id.fragment_top_story_progressBar);
		listViewStory = (ListView) rootView
				.findViewById(R.id.fragment_to_story_listStories);

		loadNewsData();
		return rootView;

	}

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.news:
			if (listViewStory.getVisibility() == View.VISIBLE) {
				if (!checknetwork(getActivity())) {
					Toast.makeText(
							getActivity(),
							"There is no internet connection available.Please enable your internet connection to get latest news.",
							Toast.LENGTH_LONG).show();
				} else {
					loadNewsData();
				}
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void loadNewsData() {
		listViewStory.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		new GetDataFromFeed().execute();
	}

	private class GetDataFromFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				final String feedUrl = "http://feeds.bbci.co.uk/news/business/rss.xml";

				/*
				 * uses HttpURLConnection to make Http request from Android to
				 * download the XML file
				 */
				StringBuffer output = new StringBuffer("");
				InputStream stream = null;
				URL url = new URL(feedUrl);
				URLConnection connection = url.openConnection();

				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(stream));
					String s = "";
					while ((s = buffer.readLine()) != null)
						output.append(s);
				}

				Log.e("output", "" + output.toString());
				InputStream is = new ByteArrayInputStream(output.toString()
						.getBytes());
				// create a XMLReader from SAXParser
				XMLReader xmlReader = SAXParserFactory.newInstance()
						.newSAXParser().getXMLReader();
				// create a SAXXMLHandler
				XMLHandler saxHandler = new XMLHandler(
						DatabaseHelper.NEWS_TYPE_BUSINESS);
				// store handler in XMLReader
				xmlReader.setContentHandler(saxHandler);
				// the process starts
				xmlReader.parse(new InputSource(is));
				// get the `Laptop list`
				listData = saxHandler.getNews();

				if (listData != null) {
					Collections.reverse(listData);
					final DatabaseHelper dbHelper = new DatabaseHelper(
							getActivity());
					dbHelper.addNewsItem(listData);
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			progressBar.setVisibility(View.GONE);

			final DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

			listViewStory.setAdapter(new NewsAdapter(getActivity(), dbHelper
					.getNewsItems(DatabaseHelper.NEWS_TYPE_BUSINESS)));

			listViewStory.setVisibility(View.VISIBLE);
		}

	}

}
