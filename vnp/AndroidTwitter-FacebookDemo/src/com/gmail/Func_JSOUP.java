package com.gmail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Func_JSOUP {
	private static final String _AUTHPARAMS = "GoogleLogin auth=";
	private static final String _GOOGLE_LOGIN_URL = "https://www.google.com/accounts/ClientLogin";
	private static final String _READER_BASE_URL = "http://www.google.com/reader/";
	private static final String _API_URL = _READER_BASE_URL + "api/0/";
	private static final String _TOKEN_URL = _API_URL + "token";
	private static final String _USER_INFO_URL = _API_URL + "user-info";
	private static final String _USER_LABEL = "user/-/label/";
	private static final String _TAG_LIST_URL = _API_URL + "tag/list";
	private static final String _EDIT_TAG_URL = _API_URL + "tag/edit";
	private static final String _RENAME_TAG_URL = _API_URL + "rename-tag";
	private static final String _DISABLE_TAG_URL = _API_URL + "disable-tag";
	private static final String _SUBSCRIPTION_URL = _API_URL
			+ "subscription/edit";
	private static final String _SUBSCRIPTION_LIST_URL = _API_URL
			+ "subscription/list";


	
	public static void main(String[] args) throws IOException {
		// Get Auth Key
//		System.out.println(Func_JSOUP.getGoogleAuthKey(_USERNAME, _PASSWORD));
//		 
//		// Get Token
//		System.out.println(Func_JSOUP.getGoogleToken(_USERNAME, _PASSWORD));
//		 
//		// Get UserID
//		System.out.println(Func_JSOUP.getGoogleUserID(_USERNAME, _PASSWORD));
//		 
//		// Get Reader TAG TITLES
//		String[] _TAG_TITLES = Func_JSOUP.getTagList(_USERNAME, _PASSWORD);
//		for(int i = 0; i < _TAG_TITLES.length; i++){
//		System.out.println(_TAG_TITLES[i]);
//		}
//		 
//		// Get Reader SUBSCRIPTION TITLES
//		String[] _SUB_TITLES = Func_JSOUP.getSubList(_USERNAME, _PASSWORD);
//		for(int i = 0; i < _SUB_TITLES.length; i++){
//		System.out.println(_SUB_TITLES[i]);
//		}
		}
}
