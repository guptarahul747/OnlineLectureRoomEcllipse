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

import com.onlinelectureroom.net.AuthenticateCodeApi;
import com.onlinelectureroom.net.CheckUserNameAvailabilityApi;

public class CheckUserNameAvailable {
	private String mUserName;

	public void setsUserName(String userName) {
		this.mUserName = userName;
	}

	public Boolean checkUserNameAvailable() {

		CheckUserNameAvailabilityApi oCheckUserNameAvailabilityApi = new CheckUserNameAvailabilityApi();
		oCheckUserNameAvailabilityApi.setUserName(mUserName);
		String strXmlRet;
		try {
			strXmlRet = oCheckUserNameAvailabilityApi.checkUserNameAvailable();
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
