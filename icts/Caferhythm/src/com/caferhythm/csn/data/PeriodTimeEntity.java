package com.caferhythm.csn.data;


public class PeriodTimeEntity extends TimeEntity {
	protected String status;
	protected boolean useStatus;

	public boolean isUseStatus() {
		return useStatus;
	}

	public void setUseStatus(boolean useStatus) {
		this.useStatus = useStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public PeriodTimeEntity() {
		super();
		this.status = "";
		this.useStatus = false;
	}
	
	public void removeNull() {
		super.removeNull();
		if(this.status.equals("null")){
			this.status = "";
		}
	}
}
