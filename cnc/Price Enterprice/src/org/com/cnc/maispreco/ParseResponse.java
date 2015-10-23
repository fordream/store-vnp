package org.com.cnc.maispreco;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseResponse extends DefaultHandler { 
	private boolean in_id=false; 
    private boolean in_response = false; 
    private boolean in_voucherCheckResponse = false ;
    private ArrayList<Response> ListResponse=new ArrayList<Response>(); 
    private Response aResponse; 
    public ArrayList<Response> getResponse(){ 
        return this.ListResponse ; 
    } 
    @Override  
    public void startDocument() throws SAXException {      
    }  
    @Override  
    public void endDocument() throws SAXException {  
    }  
    @Override  
    public void startElement(String n, String l,String q, Attributes a) throws SAXException { 
        if(l.equals("VoucherCheckResponse")){ 
            this.in_voucherCheckResponse = true ;
            this.aResponse=new Response(); 
        } else 
        	if(l.equals("responseId"))
        		this.in_id=true ;
        	else 
        		if(l.equals("response"))
        			this.in_response = true ;
    } 
    @Override  
    public void endElement(String n, String l,String q) throws SAXException  {         
    	if(l.equals("VoucherCheckResponse")) { 
            this.ListResponse.add(0,aResponse); 
            this.in_voucherCheckResponse=false; 
        }else 
        	if(l.equals("responseId"))
        		this.in_id = false ;
        	else 
        		if(l.equals("response")) 
        			this.in_response=false; 
    } 
    @Override  
    public void characters(char ch[], int start, int length){ 
        if(this.in_id==true && this.in_voucherCheckResponse==true) 
            this.aResponse.setId(new String(ch,start,length)); 
        else 
        	if(this.in_response && this.in_voucherCheckResponse==true)
        		this.aResponse.setResponse(new String(ch,start,length)) ;
    } 
}  