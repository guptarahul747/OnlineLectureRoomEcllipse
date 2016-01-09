package com.onlinelectureroom.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinelectureroom.R;
import com.onlinelectureroom.app.ApplicationStart;
import com.onlinelectureroom.utils.SyUser;

public class LoginActivity extends Activity {

	private EditText mEditTextUserName = null;
	private EditText mEditTextPassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		initView();
	}

	private void initView() {
		Button login_btn = (Button)findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onLoginButtonClick();
			}
		});

		Button signup_btn = (Button)findViewById(R.id.signup_btn);
		signup_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onSignUpButtonClick();
			}
		});

		mEditTextUserName = (EditText)findViewById(R.id.edittext_username);
		mEditTextPassword = (EditText)findViewById(R.id.edittext_pass);
	}

	private void onLoginButtonClick() {	
		CallWebService callWebService = new CallWebService();
		callWebService.execute();
	}

	private void onSignUpButtonClick() {
		Intent result = new Intent();
		result.setClass(getBaseContext(), RegisterActivity.class);
		startActivity(result);
		finish();
	}



	class CallWebService extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = null;
		boolean isLoggedIn = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(LoginActivity.this, "Loading", "Please wait...", true);
			dialog.show();

		}


		@Override
		protected String doInBackground(String... arg0) {
			SyUser syUser = new SyUser();
			String stredittextemail = mEditTextUserName.getText().toString().trim();
			String stredittextpassword = mEditTextPassword.getText().toString().trim();
			
			if(syUser.AuthenticateLogin(stredittextemail, stredittextpassword))
			{
				isLoggedIn = true;
				Log.v(ApplicationStart.TAG, syUser.getsUserName() + " " + syUser.getsUserId());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			if (!isLoggedIn) {//TODO remove ! added for testing
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), TeacherHomeActivity.class);
				startActivity(intent);
				finish();	
			} else {
				Toast.makeText(getBaseContext(), "Please check username or password", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
