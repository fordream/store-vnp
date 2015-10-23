package com.caferhythm.csn.data;

import java.io.Serializable;

public class Fluctuation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8490067488623248155L;
	private int pt_01;
	private int pt_02;
	private int pt_03;
	private int pt_04;
	private String message01;
	private String message02;
	private String message03;
	private String message04;
	private String title1;
	private String title2;
	private String title3;
	private String title4;
	private ErrorEntity error;
	public Fluctuation() {
		super();
		this.pt_01 = -1;
		this.pt_02 = -1;
		this.pt_03 = -1;
		this.pt_04 = -1;
		this.message01 = "";
		this.message02 = "";
		this.message03 = "";
		this.message04 = "";
		this.title1 = "お肌";
		this.title2 = "体調";
		this.title3 = "PMS";
		this.title4 = "ダイエット";
		
	}

	public String getMessage01() {
		return message01;
	}

	public void setMessage01(String message01) {
		this.message01 = message01;
	}

	public String getMessage02() {
		return message02;
	}

	public void setMessage02(String message02) {
		this.message02 = message02;
	}

	public String getMessage03() {
		return message03;
	}

	public void setMessage03(String message03) {
		this.message03 = message03;
	}

	public String getMessage04() {
		return message04;
	}

	public void setMessage04(String message04) {
		this.message04 = message04;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	public String getTitle4() {
		return title4;
	}

	public void setTitle4(String title4) {
		this.title4 = title4;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPt_01() {
		return pt_01;
	}

	public ErrorEntity getError() {
		return error;
	}

	public void setError(ErrorEntity error) {
		this.error = error;
	}

	public void setPt_01(int pt_01) {
		this.pt_01 = pt_01;
	}

	public int getPt_02() {
		return pt_02;
	}

	public void setPt_02(int pt_02) {
		this.pt_02 = pt_02;
	}

	public int getPt_03() {
		return pt_03;
	}

	public void setPt_03(int pt_03) {
		this.pt_03 = pt_03;
	}

	public int getPt_04() {
		return pt_04;
	}

	public void setPt_04(int pt_04) {
		this.pt_04 = pt_04;
	}

	public String getMessage_01() {
		return message01;
	}

	public void setMessage_01(String message_01) {
		this.message01 = message_01;
	}

	public String getMessage_02() {
		return message02;
	}

	public void setMessage_02(String message_02) {
		this.message02 = message_02;
	}

	public String getMessage_03() {
		return message03;
	}

	public void setMessage_03(String message_03) {
		this.message03 = message_03;
	}

	public String getMessage_04() {
		return message04;
	}

	public void setMessage_04(String message_04) {
		this.message04 = message_04;
	}
}
