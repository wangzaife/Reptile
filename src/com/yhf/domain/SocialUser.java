package com.yhf.domain;

import java.io.Serializable;

/**
 * Created by wangzaifei on 2016/11/24.
 * 个人社保基本信息
 */
public class SocialUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 组织机构代码
     */
    private String organizeCode;

    /**
     * 社会保险登记编号
     */
    private String socialCode;

    /**
     * 所属区县
     */
    private String unitAddress;

    /**
     * 参加险种
     */
    private String socialType;

    /**
     * 头像
     */
    private String headImg;
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 公民身份号码（社会保障号码）
     */
    private String citizenNum;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生年月日
     */
    private String birthday;

    /**
     * 民 族
     */
    private String nation;

    /**
     * 国家/地区
     */
    private String country;

    /**
     * 个人身份
     */
    private String identity;

    /**
     * 参加工作日期
     */
    private String workTime;

    /**
     * 户口所在区县街乡
     */
    private String registeredDetailAddress;

    /**
     * 户口性质
     */
    private String registeredNature;

    /**
     * 户口所在地地址
     */
    private String registeredAddress;

    /**
     * 户口所在地邮政编码
     */
    private String registeredCode;

    /**
     * 居住地(联系)地址
     */
    private String localAddress;

    /**
     * 居住地（联系）邮政编码
     */
    private String localCode;

    /**
     * 选择邮寄社会保险对账单地址
     */
    private String billMailAddress;

    /**
     * 对账单邮政编码
     */
    private String billMailCode;

    /**
     * 获取对账单方式
     */
    private String extBillMode;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String eduLevel;

    /**
     *
     */
    private String mobile;

    /**
     *
     */
    private String phone;

    /**
     *
     */
    private String monthIncome;

    /**
     *
     */
    private String cardNum;

    /**
     *
     */
    private String cardType;

    /**
     *
     */
    private String principalBank;

    /**
     *
     */
    private String principalBankNum;

    /**
     *
     */
    private String paymentType;

    /**
     *
     */
    private String medicalType;

    /**
     * 离退休类别
     */
    private String retirementType;

    /**
     * 离退休日期
     */
    private String retirementTime;

    /**
     * 定点医疗机构1
     */
    private String medicalOrganize1;

    /**
     * 定点医疗机构2
     */
    private String medicalOrganize2;

    /**
     * 定点医疗机构3
     */
    private String medicalOrganize3;

    /**
     * 定点医疗机构4
     */
    private String medicalOrganize4;

    /**
     * 定点医疗机构5
     */
    private String medicalOrganize5;

    /**
     * 是否患有特殊病
     */
    private String specialDefect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitizenNum() {
        return citizenNum;
    }

    public void setCitizenNum(String citizenNum) {
        this.citizenNum = citizenNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRegisteredDetailAddress() {
        return registeredDetailAddress;
    }

    public void setRegisteredDetailAddress(String registeredDetailAddress) {
        this.registeredDetailAddress = registeredDetailAddress;
    }

    public String getRegisteredNature() {
        return registeredNature;
    }

    public void setRegisteredNature(String registeredNature) {
        this.registeredNature = registeredNature;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getRegisteredCode() {
        return registeredCode;
    }

    public void setRegisteredCode(String registeredCode) {
        this.registeredCode = registeredCode;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getBillMailAddress() {
        return billMailAddress;
    }

    public void setBillMailAddress(String billMailAddress) {
        this.billMailAddress = billMailAddress;
    }

    public String getBillMailCode() {
        return billMailCode;
    }

    public void setBillMailCode(String billMailCode) {
        this.billMailCode = billMailCode;
    }

    public String getExtBillMode() {
        return extBillMode;
    }

    public void setExtBillMode(String extBillMode) {
        this.extBillMode = extBillMode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPrincipalBank() {
        return principalBank;
    }

    public void setPrincipalBank(String principalBank) {
        this.principalBank = principalBank;
    }

    public String getPrincipalBankNum() {
        return principalBankNum;
    }

    public void setPrincipalBankNum(String principalBankNum) {
        this.principalBankNum = principalBankNum;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(String medicalType) {
        this.medicalType = medicalType;
    }

    public String getRetirementType() {
        return retirementType;
    }

    public void setRetirementType(String retirementType) {
        this.retirementType = retirementType;
    }

    public String getRetirementTime() {
        return retirementTime;
    }

    public void setRetirementTime(String retirementTime) {
        this.retirementTime = retirementTime;
    }

    public String getSpecialDefect() {
        return specialDefect;
    }

    public void setSpecialDefect(String specialDefect) {
        this.specialDefect = specialDefect;
    }

    public String getMedicalOrganize1() {
        return medicalOrganize1;
    }

    public void setMedicalOrganize1(String medicalOrganize1) {
        this.medicalOrganize1 = medicalOrganize1;
    }

    public String getMedicalOrganize2() {
        return medicalOrganize2;
    }

    public void setMedicalOrganize2(String medicalOrganize2) {
        this.medicalOrganize2 = medicalOrganize2;
    }

    public String getMedicalOrganize3() {
        return medicalOrganize3;
    }

    public void setMedicalOrganize3(String medicalOrganize3) {
        this.medicalOrganize3 = medicalOrganize3;
    }

    public String getMedicalOrganize4() {
        return medicalOrganize4;
    }

    public void setMedicalOrganize4(String medicalOrganize4) {
        this.medicalOrganize4 = medicalOrganize4;
    }

    public String getMedicalOrganize5() {
        return medicalOrganize5;
    }

    public void setMedicalOrganize5(String medicalOrganize5) {
        this.medicalOrganize5 = medicalOrganize5;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOrganizeCode() {
        return organizeCode;
    }

    public void setOrganizeCode(String organizeCode) {
        this.organizeCode = organizeCode;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
