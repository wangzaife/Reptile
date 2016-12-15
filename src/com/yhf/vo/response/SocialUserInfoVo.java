package com.yhf.vo.response;

public class SocialUserInfoVo {

	/**
     * 单位名称
     */
    private String unitName;

    /**
     * 头像
     */
    private String headImg;
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 月缴费基数
     */
    private String monthIncome;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    
}
