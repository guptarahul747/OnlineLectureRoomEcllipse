package com.onlinelectureroom.net;

import java.io.IOException;

public class AuthenticateCodeApi {
	private String authenticateCode = "";

	public String getAuthenticateCode() {
		return authenticateCode;
	}
	public void setAuthenticateCode(String code) {
		this.authenticateCode = code;
	}

	public String AuthenticateCode() throws IOException{
		String strData="<request><user>" +
				"<code>"+authenticateCode+"</code>"+
				"</user></request>";	
		CallWebService oCallWebService = new CallWebService();
		return oCallWebService.postAuthenticateLogin(strData);	
	}
}
