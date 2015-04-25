package com.android.assignment2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class ActivityA extends Activity {
	private Handler handler = new Handler();
	private static final long TIME_DELAY = 20000L;
	private int count = -1;
	private TextView txtThreadCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		txtThreadCounter = (TextView) findViewById(R.id.activity_main_txtCounter);
		handler.post(updateTextRunnable);

	}

	Runnable updateTextRunnable = new Runnable() {
		public void run() {
			count++;
			txtThreadCounter.setText(getString(R.string.thread_counter, count));
			handler.postDelayed(this, TIME_DELAY);
		}
	};

	public void onClickStartB(View v) {

		startActivity(new Intent(this, ActivityB.class));

	}

	public void onClickCloseApp(View v) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getString(R.string.app_name));
		dialog.setMessage(getString(R.string.confirm_exit));
		dialog.setPositiveButton(getString(R.string.confirm_yes),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

		dialog.setNegativeButton(getString(R.string.confirm_no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});

		dialog.create().show();

	}

	// open the simple dialog
	public void onClickOpenDialog(View v) {

		final Dialog dialog = new Dialog(this);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_custom);

		dialog.findViewById(R.id.dialog_custom_btnClose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		dialog.show();

	}
}
