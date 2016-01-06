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

import com.onlinelectureroom.net.UserApi;

public class SyUser {
	private String sUserName;
	private String sUserId;

	public String getsUserName() {
		return sUserName;
	}

	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}

	public String getsUserId() {
		return sUserId;
	}

	public void setsUserId(String sUserId) {
		this.sUserId = sUserId;
	}

	public Boolean AuthenticateLogin(String strEmail, String strPassword) {

		UserApi oOwnerApi = new UserApi();
		oOwnerApi.setWebLogin(strEmail);
		oOwnerApi.setWebPassword(strPassword);
		String strXmlRet;
		try {
			strXmlRet = oOwnerApi.AuthenticateLogin();
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
