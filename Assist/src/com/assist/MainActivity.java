package com.assist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_options);

		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);

		
	}

	public void onClickSignUpWithFB(View v) {

		Toast.makeText(this, "Navigate to Fb.", Toast.LENGTH_LONG).show();

	}

	public void onClickSignUpWithEmail(View v) {

		startActivity(new Intent(this, RegisterActivity.class));

	}

	public void onClicGoToSignIn(View v) {
		startActivity(new Intent(this, LoginActivity.class));
	}
	
	
	

}
