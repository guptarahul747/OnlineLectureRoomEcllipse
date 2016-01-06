package com.onlinelectureroom.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class SyUtil {
    public static final String TAG = "OnlineLecturesApp";
	public static String GetNodeValue(Node ParentNode, String TagName) {
		Element ParentElement = (Element) ParentNode;
		return GetElementNode(ParentElement, TagName);
	}

	public static String GetElementNode(Element fstElmnt, String strNode) {
		NodeList lstNode = null;
		String strNodeValue = null;
		try {
			lstNode = fstElmnt.getElementsByTagName(strNode);
			Element eleCarId = (Element) lstNode.item(0);
			lstNode = eleCarId.getChildNodes();
			if (lstNode != null) {
				strNodeValue = lstNode.item(0).getNodeValue();
			
			} else {
				strNodeValue = null;
			}

		} catch (Exception e) {
		}
		return strNodeValue;
	}
	
	
	public static String getGenderName(int gender) {
		if(gender==1) return "Male";
		return "Female";
	}
	public static String getSmokingName(int smoking) {
		if(smoking ==1) return "Smoking";
		return "No Smoking";
	}
	public static String getFlexName(int flex) {
		return Integer.toString(flex);
	}
}
