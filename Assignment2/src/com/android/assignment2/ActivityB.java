package com.android.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ActivityB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);

	}

	public void onClickFinishB(View v) {
		finish();

	}

}
