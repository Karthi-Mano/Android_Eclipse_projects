package com.android.assignment4;

import com.android.assignment4.database.DatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySqlite extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);

	}

	public void onClickSave(View v) {

		final EditText edtBlog = (EditText) findViewById(R.id.activity_sqlite_edtBlog);

		if (edtBlog.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(this, "Please enter the blog message.",
					Toast.LENGTH_SHORT).show();
		} else {

			final DatabaseHelper dbHelper = new DatabaseHelper(this);
			dbHelper.insertBlog(edtBlog.getText().toString().trim());
			Toast.makeText(this, "Data saved successfully in Sqlite Storage.",
					Toast.LENGTH_SHORT).show();
			edtBlog.setText("");

		}

	}

	public void onClickCancel(View v) {
		finish();
	}
}
