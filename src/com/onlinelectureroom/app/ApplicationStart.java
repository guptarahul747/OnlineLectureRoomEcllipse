package com.onlinelectureroom.app;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.onlinelectureroom.ui.TeacherHomeActivity;
import com.onlinelectureroom.ui.LoginActivity;

public class ApplicationStart extends Application {

    private int nCloseApplication = 0; 
    private int nFreeTrial = 0;
    private String sUserId;
    public static final String TAG = "OnlineLecture";

    private String TravelFrom="";
    private String TravelTo="";
    private String TravelDate="";
    
    public void startMainApplication() {
    	try {
	        Intent result = new Intent();
	        result.setClass(getBaseContext(), TeacherHomeActivity.class);
        	startActivity(result);
		} catch (Exception e) {
		}
    }
    public void setCloseApplication() {
    	nCloseApplication = 1;
    }
    public int getCloseApplication() {
    	return nCloseApplication;
    }
    public void setFreeTrial() {
    	nFreeTrial = 1;
    }
    public int getFreeTrial() {
    	return nFreeTrial;
    }

	public void setUserId(String sUserId) {
		this.sUserId = sUserId;
	}
	public String getUserId() {
		return this.sUserId;
	}
	
	public String getTravelFrom() {
		return TravelFrom;
	}
	public void setTravelFrom(String travelFrom) {
		TravelFrom = travelFrom;
	}
	public String getTravelTo() {
		return TravelTo;
	}
	public void setTravelTo(String travelTo) {
		TravelTo = travelTo;
	}
	public String getTravelDate() {
		return TravelDate;
	}
	public void setTravelDate(String travelDate) {
		TravelDate = travelDate;
	}
	@Override
	public void onCreate() {
		Log.e(ApplicationStart.TAG, "onCreate");
		try {
				Intent result = new Intent();
				result.setClass(getBaseContext(), LoginActivity.class);
				result.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(result);
		
		} catch (Exception e) {
			Log.e(ApplicationStart.TAG, "Start ex=" + e.getMessage());
		}
	}
}