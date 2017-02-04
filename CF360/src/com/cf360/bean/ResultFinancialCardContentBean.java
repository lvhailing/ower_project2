package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultFinancialCardContentBean implements IMouldType {
	private String result;
	private String tmpFileName;
	private String tmpFilePath;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTmpFileName() {
		return tmpFileName;
	}
	public void setTmpFileName(String tmpFileName) {
		this.tmpFileName = tmpFileName;
	}
	public String getTmpFilePath() {
		return tmpFilePath;
	}
	public void setTmpFilePath(String tmpFilePath) {
		this.tmpFilePath = tmpFilePath;
	}
	
	
}
