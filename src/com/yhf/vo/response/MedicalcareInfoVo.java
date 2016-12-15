package com.yhf.vo.response;

public class MedicalcareInfoVo {

	/**
	 * 缴费年月
	 */
	private String paymentTime;
	
	/**
	 * 缴费基数
	 */
	private String paymentNum;
	
	/**
	 * 缴费单位
	 */
	private String unitName;

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}
