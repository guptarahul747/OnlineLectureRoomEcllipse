package com.onlinelectureroom.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.onlinelectureroom.R;
import com.onlinelectureroom.app.ApplicationStart;
import com.onlinelectureroom.utils.AuthenticateCode;
import com.onlinelectureroom.utils.CheckUserNameAvailable;
import com.onlinelectureroom.utils.Methods;
import com.onlinelectureroom.utils.RegisterUser;

public class RegisterActivity extends Activity {

	private EditText mEditTextFirstName = null;
	private EditText mEditTextLastName = null;
	private EditText mEditTextPassword = null;
	private EditText mEditTextConfirmPassword = null;
	private EditText mEditTextEmail = null;
	private EditText mEditTextUserName = null;
	private EditText edittext_authentication_code = null;

	private LinearLayout linear_layout_teacher_student = null;
	private LinearLayout linear_layout_validate_teacher = null;
	private LinearLayout linear_layout_username_availability = null;

	private String mDepartmentName = "";
	private String accountType = "";
	private int iCallWebservice = -1;
	private View registration_fields_layout;
	private ArrayAdapter<String> departmentDataAdapter;
	private Spinner spinner_dept;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		initViews();
	}

	private void initViews() {

		linear_layout_teacher_student = (LinearLayout) findViewById(R.id.linear_layout_teacher_student);
		linear_layout_validate_teacher = (LinearLayout) findViewById(R.id.linear_layout_validate_teacher);
		linear_layout_username_availability = (LinearLayout) findViewById(R.id.linear_layout_username_availability);
		registration_fields_layout = (View) findViewById(R.id.registration_fields_layout);

		mEditTextFirstName = (EditText) findViewById(R.id.edittext_firstname);
		mEditTextLastName = (EditText) findViewById(R.id.edittext_lastname);
		mEditTextPassword = (EditText) findViewById(R.id.edittext_password);
		mEditTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);

		mEditTextEmail = (EditText) findViewById(R.id.edittext_email_id);
		mEditTextUserName = (EditText) findViewById(R.id.edittext_username);
		edittext_authentication_code = (EditText) findViewById(R.id.edittext_authentication_code);
		spinner_dept = (Spinner) findViewById(R.id.spinner_dept);

		// Spinner Drop down elements

		// attaching data adapter to spinner
		List<String> categories = new ArrayList<String>();
		categories.add("Select your branch");
		categories.add("Comp");
		categories.add("Mecanical");
		categories.add("Electrical");

		// Creating adapter for spinner
		departmentDataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		departmentDataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(departmentDataAdapter);

		Button signup_btn = (Button) findViewById(R.id.signup_btn);
		signup_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSignUoButtonClicked();
			}
		});

		// Spinner element
		Spinner spinner_student_or_teacher = (Spinner) findViewById(R.id.spinner_student_or_teacher);

		// Spinner Drop down elements
		List<String> studentCategories = new ArrayList<String>();
		studentCategories.add("Identifiy your self ");
		studentCategories.add("Teacher");
		studentCategories.add("Student");

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, studentCategories);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner_student_or_teacher.setAdapter(dataAdapter);
		spinner_student_or_teacher
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						linear_layout_validate_teacher.setVisibility(View.GONE);
						linear_layout_username_availability
								.setVisibility(View.GONE);
						registration_fields_layout.setVisibility(View.GONE);

						switch (position) {
						case 1:

							accountType = "Teacher";
							authenticateTeacher();
							break;
						case 2:

							accountType = "Student";
							checkUserNameAvailability();
							break;
						default:
							break;
						}
						/*
						 * String account = parent.getSelectedItem().toString();
						 * if (account.equals("Teacher")) { accountType =
						 * "Teacher"; authenticateTeacher(); } else if
						 * (account.equals("Student")) { accountType =
						 * "Student"; checkUserNameAvailability(); }
						 */
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}

	private void authenticateTeacher() {
		
		linear_layout_validate_teacher.setVisibility(View.VISIBLE);

		Button btn_authenticate_code = (Button) findViewById(R.id.btn_authenticate_code);
		btn_authenticate_code.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iCallWebservice = 1;
				callWebservice();
			}
		});
	}

	private void checkUserNameAvailability() {
		linear_layout_username_availability.setVisibility(View.VISIBLE);

		Button btn_check_availability = (Button) findViewById(R.id.btn_check_availability);
		btn_check_availability.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iCallWebservice = 2;
				callWebservice();
			}
		});
	}

	private void showRegistrationForm() {
		linear_layout_validate_teacher.setVisibility(View.GONE);
		linear_layout_username_availability.setVisibility(View.GONE);
		registration_fields_layout.setVisibility(View.VISIBLE);

		Button signup_btn = (Button) findViewById(R.id.signup_btn);
		signup_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSignUoButtonClicked();
			}
		});
	}

	private void onSignUoButtonClicked() {
		try {
			String stredittextfirst = mEditTextFirstName.getText().toString()
					.trim();
			String stredittextlastname = mEditTextLastName.getText().toString()
					.trim();
			String stredittextpassword = mEditTextPassword.getText().toString()
					.trim();
			String stredittextconfirmPassword = mEditTextConfirmPassword
					.getText().toString().trim();
			String stredittextemail = mEditTextEmail.getText().toString()
					.trim();

			mDepartmentName = spinner_dept.getSelectedItem().toString();
			String mUserName = mEditTextUserName.getText().toString().trim();

			if ((stredittextfirst.length() > 0 && stredittextlastname.length() > 0)
					&& stredittextpassword.length() > 0
					&& stredittextemail.length() > 0
					&& mDepartmentName.length() > 0 && mUserName.length() > 0) {
				if (!stredittextconfirmPassword
							.equalsIgnoreCase(stredittextpassword) ) {
					
					Toast.makeText(getBaseContext(), "Password Missmatch",
							Toast.LENGTH_SHORT).show();
				}else if (Methods.isValidEmail(mEditTextEmail, "Invalid Email Id")) {

					iCallWebservice = 3;
					callWebservice();
				}

			} else {
				Toast.makeText(getBaseContext(), "All fields are manditory",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.v(ApplicationStart.TAG,
					"Login button clicked e=" + e.getMessage());
		}
	}

	private void callWebservice() {
		CallWebService webservice = new CallWebService();
		webservice.execute();
	}

	class CallWebService extends AsyncTask<String, Void, String> {
		ProgressDialog dialog = null;
		boolean isAuthenticateCodeValid = false;
		boolean isuserNameAvailable = false;
		boolean isAccountCreated = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(RegisterActivity.this, "Loading",
					"Please wait...", true);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			if (iCallWebservice == 1) {
				AuthenticateCode oAuthenticateCode = new AuthenticateCode();
				oAuthenticateCode
						.setsAuthenticateCode(edittext_authentication_code
								.getText().toString().trim());
				isAuthenticateCodeValid = oAuthenticateCode.AuthenticateCode();
			} else if (iCallWebservice == 2) {
				String userName = mEditTextUserName.getText().toString().trim();
				CheckUserNameAvailable oCheckUserNameAvailable = new CheckUserNameAvailable();
				oCheckUserNameAvailable.setsUserName(userName);
				isuserNameAvailable = oCheckUserNameAvailable
						.checkUserNameAvailable();
			} else if (iCallWebservice == 3) {

				String m_androidId = Secure.getString(getContentResolver(),
						Secure.ANDROID_ID);
				String stredittextfirst = mEditTextFirstName.getText()
						.toString().trim();
				String stredittextlastname = mEditTextLastName.getText()
						.toString().trim();
				String stredittextpassword = mEditTextPassword.getText()
						.toString().trim();
				String stredittextemail = mEditTextEmail.getText().toString()
						.trim();
				Spinner spinner_dept = (Spinner) findViewById(R.id.spinner_dept);
				mDepartmentName = spinner_dept.getSelectedItem().toString();
				String mUserName = mEditTextUserName.getText().toString()
						.trim();

				RegisterUser registerUser = new RegisterUser();
				registerUser.setmFirstName(stredittextfirst);
				registerUser.setmLastName(stredittextlastname);
				registerUser.setmUserName(mUserName);
				registerUser.setMpassword(stredittextpassword);
				registerUser.setmDeviceId(m_androidId);
				registerUser.setmDepartment(mDepartmentName);
				registerUser.setmAccountType(accountType);
				registerUser.setmEmailId(stredittextemail);

				isAccountCreated = registerUser.createAccount();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			if (iCallWebservice == 1) {
				if (!isAuthenticateCodeValid) {//TODO remove ! added for testing
					checkUserNameAvailability();
				} else {
					Toast.makeText(getBaseContext(), "Not valid code",
							Toast.LENGTH_SHORT).show();
				}

			} else if (iCallWebservice == 2) {
				if (!isuserNameAvailable) {//TODO remove ! added for testing
					showRegistrationForm();
				} else {
					Toast.makeText(getBaseContext(), "Username not available",
							Toast.LENGTH_SHORT).show();
				}

			} else if (iCallWebservice == 3) {
				if (!isAccountCreated) {//TODO remove ! added for testing

					Toast.makeText(getBaseContext(), "Account created",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(RegisterActivity.this,
							TeacherHomeActivity.class);
					intent.putExtra(TeacherHomeActivity.TAG_ACCOUNT_TYPE, accountType);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getBaseContext(), "something went wrong",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
