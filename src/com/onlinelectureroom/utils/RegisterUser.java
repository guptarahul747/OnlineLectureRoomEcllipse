package com.onlinelectureroom.utils;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

import com.onlinelectureroom.net.RegisterUserApi;

public class RegisterUser {
	private String mFirstName;
	private String mLastName;
	private String mUserName;
	private String mpassword;
	private String mEmailId;
	private String mAccountType;
	private String mDeviceId;
	private String mDepartment;
	public String getmFirstName() {
		return mFirstName;
	}
	public void setmFirstName(String mFirstName) {
		this.mFirstName = mFirstName;
	}
	public String getmLastName() {
		return mLastName;
	}
	public void setmLastName(String mLastName) {
		this.mLastName = mLastName;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String getmEmailId() {
		return mEmailId;
	}
	public void setmEmailId(String mEmailId) {
		this.mEmailId = mEmailId;
	}
	public String getmAccountType() {
		return mAccountType;
	}
	public void setmAccountType(String mAccountType) {
		this.mAccountType = mAccountType;
	}
	public String getmDeviceId() {
		return mDeviceId;
	}
	public void setmDeviceId(String mDeviceId) {
		this.mDeviceId = mDeviceId;
	}
	public String getmDepartment() {
		return mDepartment;
	}
	public void setmDepartment(String mDepartment) {
		this.mDepartment = mDepartment;
	}


	public Boolean createAccount() {

		RegisterUserApi registerUser = new RegisterUserApi();
		registerUser.setmFirstName(mFirstName);
		registerUser.setmLastName(mLastName);
		registerUser.setmUserName(mUserName);
		registerUser.setMpassword(mpassword);
		registerUser.setmDeviceId(mDeviceId);
		registerUser.setmDepartment(mDepartment);
		registerUser.setmAccountType(mAccountType);
		registerUser.setmEmailId(mEmailId);
		
		String strXmlRet;
		try {
			strXmlRet = registerUser.createUser();
			Log.e("test", ""+strXmlRet);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db
					.parse(new InputSource(new StringReader(strXmlRet)));

			NodeList nodeList = doc.getElementsByTagName("res");

			Log.e("Node list",nodeList.toString());
			if (nodeList.getLength() > 0) {
				for (int iNode = 0; iNode < nodeList.getLength(); iNode++) {
					Node node = nodeList.item(iNode);
					Element fstElmnt = (Element) node;

					if (SyUtil.GetElementNode(fstElmnt, "status") != null) {
						if (SyUtil.GetElementNode(fstElmnt, "status").equalsIgnoreCase("success")) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		} catch (IOException e) {
			return false;
		}
		catch (Exception e) {
			return false;
		}

		return false;
	}

}
