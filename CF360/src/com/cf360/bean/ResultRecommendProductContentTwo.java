package com.cf360.bean;

import com.cf360.mould.types.*;


public class ResultRecommendProductContentTwo implements IMouldType {
    private String auditStatus;
    private MouldList<MouldList<ResultRecommendProductContentBean>> recommendProduct;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public MouldList<MouldList<ResultRecommendProductContentBean>> getRecommendProduct() {
        return recommendProduct;
    }

    public void setRecommendProduct(MouldList<MouldList<ResultRecommendProductContentBean>> recommendProduct) {
        this.recommendProduct = recommendProduct;
    }


}
