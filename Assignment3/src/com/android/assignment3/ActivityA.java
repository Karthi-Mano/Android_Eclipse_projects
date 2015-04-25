package com.android.assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.assignment3.model.DataModel;

public class ActivityA extends Activity {
	private ArrayList<DataModel> listData = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);

		if (checknetwork(this)) {
			new GetData().execute();
		} else {
			Toast.makeText(
					this,
					"Not able to fetch data as your internet connection is not available.",
					Toast.LENGTH_LONG).show();
		}

	}

	public JSONObject makeHttpRequest(String url, List<NameValuePair> params) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {

			// check for request method

			// request method is GET
			DefaultHttpClient httpClient = new DefaultHttpClient();

			String paramString = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("api_key", "SEM33ADF5370C391CD2D027057F98D768E94");

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();

			FileOutputStream ous = new FileOutputStream(new File(
					Environment.getExternalStorageDirectory(), "test"));
			ous.write(json.getBytes());
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	private Boolean checknetwork(Context mContext) {

		NetworkInfo info = ((ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option we can change it if we want to
			// disable internet while roaming, just return false
			return true;
		}

		return true;

	}

	private class GetData extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			dialog = ProgressDialog
					.show(ActivityA.this, getString(R.string.app_name),
							"Please wait...", true, false);
		}

		@Override
		protected Void doInBackground(Void... param) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = null;
			try {
				json = makeHttpRequest(
						"https://api.semantics3.com/test/v1/products?q="
								+ URLEncoder.encode("{\"search\""
										+ ":\"Samsung Galax*\"}", "UTF-8"),
						params);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Check your log cat for JSON reponse

			if (json != null) {
				Log.e("json", "json data" + json.toString());

				try {
					final JSONArray resultArray = json.getJSONArray("results");
					for (int i = 0; i < resultArray.length(); i++) {
						final DataModel model = new DataModel();
						model.setBrand(resultArray.getJSONObject(i).getString(
								"brand"));
						model.setModel(resultArray.getJSONObject(i).getString(
								"model"));
						model.setPrice(resultArray.getJSONObject(i).getString(
								"price"));
						model.setUpc(resultArray.getJSONObject(i).getString(
								"upc"));
						try {
							model.setSeller(resultArray.getJSONObject(i)
									.getJSONArray("sitedetails")
									.getJSONObject(0)
									.getJSONArray("latestoffers")
									.getJSONObject(0).getString("seller"));
						} catch (Exception e) {
							model.setSeller("WalMart");
						}
						listData.add(model);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();

				if (listData != null && listData.size() > 0) {
					((ListView) findViewById(R.id.activity_a_listView))
							.setAdapter(new TariffAdapter(ActivityA.this,
									listData));
				}

			}

		}
	}

	public class TariffAdapter extends BaseAdapter {
		private LayoutInflater linf;

		private Context mcontext;
		private ArrayList<com.android.assignment3.model.DataModel> listData;

		public TariffAdapter(Context m1, ArrayList<DataModel> listData) {
			mcontext = m1;
			linf = LayoutInflater.from(mcontext);
			this.listData = listData;

		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = new ViewHolder();
			final int position = pos;
			if (convertView == null) {
				view = linf.inflate(R.layout.row_list_item, null);

				holder.txtBrand = (TextView) view
						.findViewById(R.id.row_list_item_txtBrand);
				holder.txtName = (TextView) view
						.findViewById(R.id.row_list_item_txtName);
				holder.txtPrice = (TextView) view
						.findViewById(R.id.row_list_item_txtPrice);
				holder.txtSeller = (TextView) view
						.findViewById(R.id.row_list_item_txtSeller);
				holder.txtUpc = (TextView) view
						.findViewById(R.id.row_list_item_txtUpc);

				holder.imgProduct = (ImageView) view
						.findViewById(R.id.row_list_item_imgProduct);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.txtBrand.setText("BRAND:"
					+ listData.get(position).getBrand());
			holder.txtName.setText("NAME:" + listData.get(position).getModel());
			holder.txtPrice.setText("PRICE:"
					+ listData.get(position).getPrice());

			holder.txtSeller.setText("SELLER:"
					+ listData.get(position).getSeller());
			holder.txtUpc.setText("UPC:" + listData.get(position).getUpc());

			switch (position) {
			case 0:
				holder.imgProduct.setImageResource(R.drawable.smasung_s4);
				break;
			case 7:
				holder.imgProduct.setImageResource(R.drawable.smasung_s4);
				break;
			case 1:
				holder.imgProduct.setImageResource(R.drawable.smasungi827);
				break;
			case 2:
				holder.imgProduct.setImageResource(R.drawable.samsung_s3);
				break;
			case 3:
				holder.imgProduct.setImageResource(R.drawable.samsung_centura);
				break;
			case 4:
				holder.imgProduct.setImageResource(R.drawable.samsungnote2);
				break;
			case 5:
				holder.imgProduct.setImageResource(R.drawable.samsung_ed);
				break;

			case 6:
				holder.imgProduct.setImageResource(R.drawable.samsung_et);
				break;
			case 8:
				holder.imgProduct.setImageResource(R.drawable.samsung_rugby);
				break;
			case 9:
				holder.imgProduct.setImageResource(R.drawable.samsungnote2);
				break;
			default:
				break;
			}

			return view;
		}

	}

	static class ViewHolder {
		ImageView imgProduct;
		TextView txtBrand, txtPrice, txtName, txtSeller, txtUpc;
	}
}
