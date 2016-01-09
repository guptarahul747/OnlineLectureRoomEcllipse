package com.onlinelectureroom.ui;

import com.example.onlinelectureroom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TeacherHomeActivity extends Activity implements OnClickListener {

	public static final String TAG_ACCOUNT_TYPE = "account_type";
	public static final String TAG_ACCOUNT_TYPE_TEACHER = "Teacher";

	private String accountType;
	private Button btnCreateEvent;
	private Button btnShowEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		accountType = getIntent().getStringExtra(TAG_ACCOUNT_TYPE);

		initViews();
	}

	private void initViews() {

		btnCreateEvent = (Button) findViewById(R.id.btnCreateEvent);
		btnShowEvent = (Button) findViewById(R.id.btnShowEvent);

		btnCreateEvent.setOnClickListener(this);

		btnShowEvent.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;

		switch (v.getId()) {
		case R.id.btnCreateEvent:

			intent = new Intent(this, CreateEventActivity.class);
			startActivity(intent);
			break;

		case R.id.btnShowEvent:
			intent = new Intent(this, ShowEventActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}
}
