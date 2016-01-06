package com.onlinelectureroom.net;

import java.io.IOException;

import com.onlinelectureroom.net.RestClient.RequestMethod;
import com.onlinelectureroom.utils.SyUtil;

public class CallWebService {
	static String WSBaseUrl = "http://172.20.10.2:8000/OnlineLectureRoomServer/";
	Boolean bDebug = false;
	
	public String postData(String UrlPath, String strData) throws IOException {
		String strRetData = "";
		RestClient objRestClient = new RestClient(UrlPath);
		try {
			MyLogger.log(MyLogger.LOG_LEVEL_INFO, SyUtil.TAG, "UrlPath=" +UrlPath);
			MyLogger.log(MyLogger.LOG_LEVEL_INFO, SyUtil.TAG, "post=" +strData);
			objRestClient.AddParam("data", strData);
			objRestClient.Execute(RequestMethod.POST);
			if (objRestClient.getResponseCode() == 200) {
				strRetData = objRestClient.getResponse();
				MyLogger.log(MyLogger.LOG_LEVEL_INFO, SyUtil.TAG, "strRetData=" +strRetData);
			} else {
				MyLogger.log(MyLogger.LOG_LEVEL_ERROR,SyUtil.TAG, objRestClient.getErrorMessage());
			}

		}
		catch (IOException e) {
            throw e;
        }catch (Exception e) {
			MyLogger.log(MyLogger.LOG_LEVEL_ERROR,SyUtil.TAG, e.getMessage());
		}
		return strRetData;
	}
	
	public String postAuthenticateLogin(String strData) throws IOException {
		String UrlPath = WSBaseUrl + "LoginServlet";
		if(bDebug) {
			return "<res><status><un>yashashvi</un><id>1</id></status></res>";
		}
		return postData(UrlPath, strData);
	}
	
	public String postAuthenticateCode(String strData) throws IOException {
		String UrlPath = WSBaseUrl + "AuthenticateCode";
		if(bDebug) {
			return "<res><status>success</status></res>";
		}
		return postData(UrlPath, strData);
	}
	
	public String postcheckUserNameAvailable(String strData) throws IOException {
		String UrlPath = WSBaseUrl + "CheckUserNameAvailability";
		if(bDebug) {
			return "<res><status>success</status></res>";
		}
		return postData(UrlPath, strData);
	}
	
	public String postCreateUser(String strData) throws IOException {
		String UrlPath = WSBaseUrl + "RegisterUser";
		if(bDebug) {
			return "<res><status>success</status></res>";
		}
		return postData(UrlPath, strData);
	}
}
