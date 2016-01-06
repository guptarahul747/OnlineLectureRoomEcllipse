package com.onlinelectureroom.ui;

import com.example.onlinelectureroom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends Activity {

	public static final String TAG_ACCOUNT_TYPE = "account_type";
	public static final String TAG_ACCOUNT_TYPE_TEACHER = "Teacher";

	private String accountType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		accountType = getIntent().getStringExtra(TAG_ACCOUNT_TYPE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (accountType != null
				&& accountType.equalsIgnoreCase(TAG_ACCOUNT_TYPE_TEACHER)) {

			getMenuInflater().inflate(R.menu.menu_add_event, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Intent intent = new Intent(this, CreateEventActivity.class);
		startActivity(intent);

		return super.onOptionsItemSelected(item);
	}
}
