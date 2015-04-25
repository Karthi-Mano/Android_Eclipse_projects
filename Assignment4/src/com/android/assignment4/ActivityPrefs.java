package com.android.assignment4;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityPrefs extends Activity {
	private EditText edtAuthor, edtName, edtDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storage);
		edtAuthor = (EditText) findViewById(R.id.activity_storage_edtBookAuthor);
		edtName = (EditText) findViewById(R.id.activity_storage_edtBookName);
		edtDesc = (EditText) findViewById(R.id.activity_storage_edtDesc);

	}

	public void onClickSave(View v) {

		if (edtAuthor.getText().toString().equalsIgnoreCase("")
				|| edtName.getText().toString().equalsIgnoreCase("")
				|| edtDesc.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(this, "Please fill all the fields.",
					Toast.LENGTH_SHORT).show();
		} else {

			final SharedPreferences prefs = this.getSharedPreferences(
					getString(R.string.app_name), Context.MODE_PRIVATE);

			final int count = prefs.getInt(getString(R.string.prefs_count), 0);

			final Editor editor = prefs.edit();
			editor.putString(getString(R.string.prefs_book_author)
					+ (count + 1), edtAuthor.getText().toString().trim());
			editor.putString(getString(R.string.prefs_description)
					+ (count + 1), edtDesc.getText().toString().trim());
			editor.putString(getString(R.string.prefs_book_name) + (count + 1),
					edtName.getText().toString().trim());
			editor.putInt(getString(R.string.prefs_count), count + 1);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat(
					"MM/dd/yyyy-hh:mm a");
			// Ouput "Wed Sep 26 14:23:28 EST 2012"

			String formatted = format1.format(cal.getTime());
			editor.putString(getString(R.string.prefs_timestamp) + (count + 1),
					formatted);
			editor.commit();
			edtAuthor.setText("");
			edtDesc.setText("");
			edtName.setText("");
			Toast.makeText(this,
					"Data saved successfully in Shared Preference Storage.",
					Toast.LENGTH_SHORT).show();

		}

	}

	public void onClickCancel(View v) {
		finish();

	}
}
