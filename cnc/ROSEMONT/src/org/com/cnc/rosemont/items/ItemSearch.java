package org.com.cnc.rosemont.items;

import android.widget.TextView;

public class ItemSearch {
	private boolean isAnphabe = false;
	private String txtHeader;
	private String txtStrength;
	private String txtContent;
	private String id;
	private String idTable;
	private boolean isChecked = false;

	public String getIdTable() {
		return idTable;
	}

	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}

	public boolean isAnphabe() {
		return isAnphabe;
	}

	public void setAnphabe(boolean isAnphabe) {
		this.isAnphabe = isAnphabe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTxtHeader() {
		return txtHeader;
	}

	public void setTxtHeader(String txtHeader) {
		this.txtHeader = txtHeader;
	}

	public String getTxtContent() {
		return txtContent;
	}

	public void setTxtContent(String txtContent) {
		this.txtContent = txtContent;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	private boolean isSpec = false;

	public void setSpec(boolean equals) {
		isSpec = equals;
	}

	public boolean isSpec() {
		return isSpec;
	}
	String strength ;
	
	public void setStrength(String value) {
		strength = value;
		
	}
	
	public String getStrength(){
			return strength;
		
	}

	public String getTxtStrength() {
		return txtStrength;
	}

	public void setTxtStrength(String txtStrength) {
		this.txtStrength = txtStrength;
	}

}
