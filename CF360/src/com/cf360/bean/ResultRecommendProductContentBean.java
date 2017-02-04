package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultRecommendProductContentBean implements IMouldType {

    private String NAME;    //产品名称
    private String ID;    //产品编号
    private String AMOUNT;    //	认购金额
    private String ANNUALRATE;    //预期收益率
    private String COMMISSION;    //返佣比例
    private String RECRUITMENTPROCESS;    //募集进度
    private String ISSHOW;            //进度条显示:true/false
    private String SELLINGSTATUS;    //销售状态
    private String CREDITLEVEL;        //信用等级
    private String SALETYPE;        //销售方式（包销/分销）
    private String TIMELIMIT;        //保险期间  产品期限
    private String TIMELIMITUNIT;        //TIMELIMIT单位 （年/月）
    private String RECOMMENDSTATUS;        //推荐状态
    private String CATEGORY;        //产品类型
    private String EDITTIME;        //产品编辑时间

    private String NAME5;    //累计净值(阳光私募) 投资期限(私募股权)
    private String COMPANYNAME;        //公司名称

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String aMOUNT) {
        AMOUNT = aMOUNT;
    }

    public String getANNUALRATE() {
        return ANNUALRATE;
    }

    public void setANNUALRATE(String aNNUALRATE) {
        ANNUALRATE = aNNUALRATE;
    }

    public String getCOMMISSION() {
        return COMMISSION;
    }

    public void setCOMMISSION(String cOMMISSION) {
        COMMISSION = cOMMISSION;
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

    public String getSELLINGSTATUS() {
        return SELLINGSTATUS;
    }

    public void setSELLINGSTATUS(String sELLINGSTATUS) {
        SELLINGSTATUS = sELLINGSTATUS;
    }

    public String getCREDITLEVEL() {
        return CREDITLEVEL;
    }

    public void setCREDITLEVEL(String cREDITLEVEL) {
        CREDITLEVEL = cREDITLEVEL;
    }

    public String getSALETYPE() {
        return SALETYPE;
    }

    public void setSALETYPE(String sALETYPE) {
        SALETYPE = sALETYPE;
    }

    public String getTIMELIMIT() {
        return TIMELIMIT;
    }

    public void setTIMELIMIT(String tIMELIMIT) {
        TIMELIMIT = tIMELIMIT;
    }

    public String getTIMELIMITUNIT() {
        return TIMELIMITUNIT;
    }

    public void setTIMELIMITUNIT(String tIMELIMITUNIT) {
        TIMELIMITUNIT = tIMELIMITUNIT;
    }

    public String getRECOMMENDSTATUS() {
        return RECOMMENDSTATUS;
    }

    public void setRECOMMENDSTATUS(String rECOMMENDSTATUS) {
        RECOMMENDSTATUS = rECOMMENDSTATUS;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        CATEGORY = cATEGORY;
    }

    public String getEDITTIME() {
        return EDITTIME;
    }

    public void setEDITTIME(String eDITTIME) {
        EDITTIME = eDITTIME;
    }

    public String getNAME5() {
        return NAME5;
    }

    public void setNAME5(String nAME5) {
        NAME5 = nAME5;
    }

    public String getCOMPANYNAME() {
        return COMPANYNAME;
    }

    public void setCOMPANYNAME(String cOMPANYNAME) {
        COMPANYNAME = cOMPANYNAME;
    }

}
