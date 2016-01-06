package com.onlinelectureroom.net;

import java.io.IOException;

public class CheckUserNameAvailabilityApi {
	private String mUserName = "";

	public String getUserName() {
		return mUserName;
	}
	public void setUserName(String userName) {
		this.mUserName = userName;
	}

	public String checkUserNameAvailable() throws IOException{
		String strData="<request><user>" +
				"<username>"+mUserName+"</username>"+
				"</user></request>";	
		CallWebService oCallWebService = new CallWebService();
		return oCallWebService.postAuthenticateLogin(strData);	
	}
}
