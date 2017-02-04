package com.cf360.bean;

import com.cf360.mould.types.IMouldType;
import com.cf360.mould.types.MouldList;

public class ResultUploadListContentBean implements IMouldType {
	private String result;
	private String tmpFileName;
	private String tmpFilePath;
	private String uploadType;
	
	public ResultUploadListContentBean(String result, String tmpFileName,
			String tmpFilePath, String uploadType) {
		setResult(result);
		setTmpFileName(tmpFileName);
		setTmpFilePath(tmpFilePath);
		setUploadType(uploadType);
	}
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
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

}