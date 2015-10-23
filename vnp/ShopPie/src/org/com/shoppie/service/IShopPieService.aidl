package org.com.shoppie.service;
import org.com.shoppie.service.Rect;

interface IShopPieService{
	/*
	*Example
	*/
	Rect example();
	/**
	*
	*
	*/
	 /** Request the process ID of this service, to do evil things with it. */
    int getPid();

    /** Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,double aDouble, String aString);
            
	/**
	* start sip service
	*/
	void startWithAccount(String account, String statusAccount, String passwrod);
	
	/**
	*	logout account
	*/
	void logout();
	
	/**
	* make call account
	*/
	void makeCall(String calle);

	/**
	*make key
	*/		
	void makeKey(int idCall, String key);
	
	
	/**
	*	forword a call
	*/
	void forward(int callId, String forwardNumber);
	
	/**
	*	transfer number
	*/
	void transferNumber(int callId, String number);
	
}