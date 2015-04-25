package com.assignment.newsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.assignment.newsapp.database.DatabaseHelper;

public class NewsDetailActivity extends Activity {
	private ProgressBar progressBar;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		progressBar = (ProgressBar) findViewById(R.id.activity_news_detail_preogressBar);
		webView = (WebView) findViewById(R.id.activity_news_detail_webView);
		webView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);

		new GetDataFromFeed().execute();
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

	private String getData(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet, localContext);
			String result = "";

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}

			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	/**
	 * Created By: Sergio D’az
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

	private class GetDataFromFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				final String html = getData(getIntent().getStringExtra(
						"EXTRA_URL"));
				if (html != null && !html.equalsIgnoreCase("")) {
					final DatabaseHelper dbHelper = new DatabaseHelper(
							NewsDetailActivity.this);
					dbHelper.addWebPageData(html,
							getIntent().getIntExtra("EXTRA_ID", -1));
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
			final DatabaseHelper dbHelper = new DatabaseHelper(
					NewsDetailActivity.this);
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return true;
				}
			});
			webView.loadDataWithBaseURL(
					"",
					dbHelper.getWebPageData(getIntent().getIntExtra("EXTRA_ID",
							-1)), "text/html", "UTF-8", "");
			Log.e("",
					"wepagedata"
							+ dbHelper.getWebPageData(getIntent().getIntExtra(
									"EXTRA_ID", -1)));
			webView.setVisibility(View.VISIBLE);
		}
	}

}
