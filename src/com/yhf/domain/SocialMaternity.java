package com.yhf.domain;

import java.io.Serializable;

/**
 * Created by wangzaifei on 2016/11/30.
 * 生育信息
 */
public class SocialMaternity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String cardNum;

    /**
     * 结算日期
     */
    private String settleTime;

    /**
     * 缴费基数
     */
    private String paymentNum;

    /**
     * 单位缴费
     */
    private String unitPayment;

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

	public String getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
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

}