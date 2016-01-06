package com.onlinelectureroom.net;

import java.io.IOException;

public class RegisterUserApi {
	private String mFirstName;
	private String mLastName;
	private String mUserName;
	private String mpassword;
	private String mEmailId;
	private String mAccountType;
	private String mDeviceId;
	private String mDepartment;
	public void setmFirstName(String mFirstName) {
		this.mFirstName = mFirstName;
	}
	public void setmLastName(String mLastName) {
		this.mLastName = mLastName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public void setmEmailId(String mEmailId) {
		this.mEmailId = mEmailId;
	}
	public void setmAccountType(String mAccountType) {
		this.mAccountType = mAccountType;
	}
	public void setmDeviceId(String mDeviceId) {
		this.mDeviceId = mDeviceId;
	}
	public void setmDepartment(String mDepartment) {
		this.mDepartment = mDepartment;
	}

	public String createUser() throws IOException{
		String strData="<request><user>" +
				"<firstName>"+mFirstName+"</firstName>" +
				"<LastName>"+mLastName+"</LastName>" +
				"<password>"+mpassword+"</password>" +
				"<UserName>"+mUserName+"</UserName>" +
				"<Department>"+mDepartment+"</Department>" +
				"<DeviceId>"+mDeviceId+"</DeviceId>" +
				"<EmailId>"+mEmailId+"</EmailId>" +
				"<AccountType>"+mAccountType+"</AccountType>" +
				"</user></request>";	
		CallWebService oCallWebService = new CallWebService();
		return oCallWebService.postAuthenticateLogin(strData);	
	}
}
