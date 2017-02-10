package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultHotProductContentBean implements IMouldType {

    private String ID;            //产品编号
    private String NAME;            //产品名称
    private String SALETYPE;    //销售方式（包销/分销）
    private String SELLINGSTATUS;//销售状态(包销，热销,推荐)
    private String PHOTOSATTACHMENTS;    //图片地址
    private String CATEGORY;        //产品类型   信托、资管、ygsm、smgq、1、2、3、4、5、6、7、8
    private String CREDITLEVEL;    //信用等级
    private String NAME3;        //认购金额，保险公司
    private String NAME3KEY;        //认购金额(保险公司)对应的值
    private String NAME4;        //产品期限，累计净值，投资期限，保险期间
    private String NAME4KEY;    //产品期限，累计净值，投资期限，保险期间对应的值
    private String NAME5;        //前段返佣，后端返佣，返佣比例
    private String NAME5KEY;    //前端返佣，后端返佣，返佣比例对应的值
    private String RECRUITMENTPROCESS;    //进度条数据
    private String ISSHOW;        //进度条书名true/false
    private String RECOMMENDSTATUS;  //推荐   0  表示不显示，1 显示
    private String auditStatus;  //是否认证

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRECOMMENDSTATUS() {
        return RECOMMENDSTATUS;
    }

    public void setRECOMMENDSTATUS(String rECOMMENDSTATUS) {
        RECOMMENDSTATUS = rECOMMENDSTATUS;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getSALETYPE() {
        return SALETYPE;
    }

    public void setSALETYPE(String sALETYPE) {
        SALETYPE = sALETYPE;
    }

    public String getSELLINGSTATUS() {
        return SELLINGSTATUS;
    }

    public void setSELLINGSTATUS(String sELLINGSTATUS) {
        SELLINGSTATUS = sELLINGSTATUS;
    }

    public String getPHOTOSATTACHMENTS() {
        return PHOTOSATTACHMENTS;
    }

    public void setPHOTOSATTACHMENTS(String pHOTOSATTACHMENTS) {
        PHOTOSATTACHMENTS = pHOTOSATTACHMENTS;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        CATEGORY = cATEGORY;
    }

    public String getCREDITLEVEL() {
        return CREDITLEVEL;
    }

    public void setCREDITLEVEL(String cREDITLEVEL) {
        CREDITLEVEL = cREDITLEVEL;
    }

    public String getNAME3() {
        return NAME3;
    }

    public void setNAME3(String nAME3) {
        NAME3 = nAME3;
    }

    public String getNAME3KEY() {
        return NAME3KEY;
    }

    public void setNAME3KEY(String nAME3KEY) {
        NAME3KEY = nAME3KEY;
    }

    public String getNAME4() {
        return NAME4;
    }

    public void setNAME4(String nAME4) {
        NAME4 = nAME4;
    }

    public String getNAME4KEY() {
        return NAME4KEY;
    }

    public void setNAME4KEY(String nAME4KEY) {
        NAME4KEY = nAME4KEY;
    }

    public String getNAME5() {
        return NAME5;
    }

    public void setNAME5(String nAME5) {
        NAME5 = nAME5;
    }

    public String getNAME5KEY() {
        return NAME5KEY;
    }

    public void setNAME5KEY(String nAME5KEY) {
        NAME5KEY = nAME5KEY;
    }

    public String getRECRUITMENTPROCESS() {
        return RECRUITMENTPROCESS;
    }

    public void setRECRUITMENTPROCESS(String rECRUITMENTPROCESS) {
        RECRUITMENTPROCESS = rECRUITMENTPROCESS;
    }

    public String getISSHOW() {
        return ISSHOW;
    }

    public void setISSHOW(String iSSHOW) {
        ISSHOW = iSSHOW;
    }


}
