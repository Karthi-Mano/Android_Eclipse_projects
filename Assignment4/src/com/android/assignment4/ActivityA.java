package com.android.assignment4;

import com.android.assignment4.database.DatabaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityA extends Activity {
	private TextView txtSavedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		txtSavedData = (TextView) findViewById(R.id.activity_main_txtData);

	}

	@Override
	protected void onResume() {
		super.onResume();
		final SharedPreferences prefs = getSharedPreferences(
				getString(R.string.app_name), Context.MODE_PRIVATE);
		if (prefs.getInt(getString(R.string.prefs_count), 0) > 0) {

			final int count = prefs.getInt(getString(R.string.prefs_count), 0);
			StringBuilder sb = new StringBuilder();
			sb.append("Shared Prefernce Storage Data:\n");
			for (int i = 0; i < count; i++) {
				sb.append(prefs.getString(getString(R.string.prefs_book_name)
						+ (i + 1), ""));
				sb.append(" , ");
				sb.append(prefs.getString(getString(R.string.prefs_book_author)
						+ (i + 1), ""));
				sb.append( " , ");
				sb.append(prefs.getString(getString(R.string.prefs_description)
						+ (i + 1), ""));

				sb.append(" , ");
				sb.append(prefs.getString(getString(R.string.prefs_timestamp)
						+ (i + 1), ""));
				sb.append("\n");

			}
			
			sb.append("\n");
			sb.append("\n");
			txtSavedData.setText(sb.toString());
		}

		final String[][] dbData = new DatabaseHelper(this).getBlog();

		if (dbData != null) {
			final int count = dbData.length;
			StringBuilder sb = new StringBuilder();
			sb.append("Sqlite Storage Data:\n");
			for (int i = 0; i < count; i++) {
				sb.append(dbData[i][0]);
				sb.append(" , ");
				sb.append(dbData[i][1]);

				sb.append("\n");

			}

			txtSavedData.setText(txtSavedData.getText().toString()
					+ sb.toString());
		}
	}

	public void onClickSqlite(View v) {
		startActivity(new Intent(this, ActivitySqlite.class));

	}

	public void onClickClose(View v) {
		finish();

	}

	public void onClickPref(View v) {
		startActivity(new Intent(this, ActivityPrefs.class));
	}

}
