package com.example.Batch.model;

public class FristJobModel {
	
	private String parmKey;
	private String parmvalue;
	public String getParmKey() {
		return parmKey;
	}
	public void setParmKey(String parmKey) {
		this.parmKey = parmKey;
	}
	public String getParmvalue() {
		return parmvalue;
	}
	public void setParmvalue(String parmvalue) {
		this.parmvalue = parmvalue;
	}
	public FristJobModel(String parmKey, String parmvalue) {
		this.parmKey = parmKey;
		this.parmvalue = parmvalue;
	}
	public FristJobModel() {
		
	}
}
