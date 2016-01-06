package com.onlinelectureroom.ui;

import java.util.Calendar;
import java.util.Date;

import com.example.onlinelectureroom.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventActivity extends Activity implements OnClickListener {

	private EditText editEventName;
	private Button btnEventDate;
	private Spinner spnDepartment;
	private Button btnSubmit;
	private Button btnCancel;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private String date = null, time = null;
	private String department = null;

	/*
	 * Event name Time date Department Department is drop down list Mech comp
	 * electrical
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_event_activity);

		initViews();

	}

	private void initViews() {

		editEventName = (EditText) findViewById(R.id.editEventName);

		btnEventDate = (Button) findViewById(R.id.btnEventDate);

		spnDepartment = (Spinner) findViewById(R.id.spnDepartment);

		btnEventDate = (Button) findViewById(R.id.btnEventDate);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnCancel.setOnClickListener(this);

		btnSubmit.setOnClickListener(this);

		btnEventDate.setOnClickListener(this);

		String[] departmentArr = getResources().getStringArray(
				R.array.dept_array);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, departmentArr);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spnDepartment.setAdapter(dataAdapter);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnCancel:

			finish();
			break;
		case R.id.btnSubmit:

			createEvent();
			break;
		case R.id.btnEventDate:

			showDatePicker();
			break;
		default:
			break;
		}
	}

	private void showDatePicker() {

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						date = dayOfMonth + "/" + (monthOfYear + 1) + "/"
								+ year;
						showTimePicker();

					}
				}, mYear, mMonth, mDay);
		dpd.show();
	}

	private void showTimePicker() {

		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						time = hourOfDay + ":" + minute;
						btnEventDate.setText(date + " " + time);
					}
				}, mHour, mMinute, false);
		tpd.show();
	}

	private void createEvent() {

		String eventName = editEventName.getText().toString().trim();

		String department = null;

		if (eventName.length() > 0
				&& spnDepartment.getSelectedItemPosition() > 0) {

			department = (String) spnDepartment.getSelectedItem();
			if (date == null || time == null) {

				Toast.makeText(this, "Select date and time", Toast.LENGTH_SHORT)
						.show();
			} else {

				callCreateEventWebservice(eventName, department, date + " "
						+ time);
			}

		} else {

			Toast.makeText(this, "All Fields are mandatory", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void callCreateEventWebservice(String eventName, String depart,
			String dateTime) {

	}
}
