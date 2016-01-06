package com.onlinelectureroom.net;

import java.io.IOException;

public class UserApi {

	String weblogin = "";
	String webpassword = "";

	public String getWebLogin() {
		return weblogin;
	}
	public void setWebLogin(String weblogin) {
		this.weblogin = weblogin;
	}
	public String getWebPassword() {
		return webpassword;
	}
	public void setWebPassword(String webpassword) {
		this.webpassword = webpassword;
	}
	
	public String AuthenticateLogin() throws IOException{
	
		
		String strData="<request><user>" +
		"<email>"+weblogin+"</email>" +
		"<password>"+webpassword+"</password>" +
				"</user></request>";	
		CallWebService oCallWebService = new CallWebService();
		return oCallWebService.postAuthenticateLogin(strData);	
	}
}
