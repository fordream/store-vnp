package org.com.cnc.common.adnroid;

public class Test {
public static void main(String[] args) {
	String search = "abcss esd";
	int first = search.indexOf(" ");
	if(first >=0){
		String s = search.substring(0, first);
		String end = search.substring(first);
		search = end + " " + s;
	}
	
	System.out.println(search);
}
}
