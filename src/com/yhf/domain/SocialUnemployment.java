package com.yhf.domain;

import java.io.Serializable;

/**
 * Created by wangzaifei on 2016/11/9.
 * 失业信息
 */
public class SocialUnemployment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private String name;

    private String cardNum;

    /**
     * 缴费年月
     */
    private String paymentDate;

    /**
     * 缴费基数
     */
    private String paymentNum;

    /**
     * 单位缴费
     */
    private String unitPayment;

    /**
     * 个人缴费
     */
    private String userPayment;

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

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}

	public String getUnitPayment() {
		return unitPayment;
	}

	public void setUnitPayment(String unitPayment) {
		this.unitPayment = unitPayment;
	}

	public String getUserPayment() {
		return userPayment;
	}

	public void setUserPayment(String userPayment) {
		this.userPayment = userPayment;
	}
    
}
