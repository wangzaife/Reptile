package com.yhf.vo.response;

import com.yhf.domain.Judicial;

public class JudicialInfoVo {

	/**
	 * 被执行人姓名/名称
	 */
	private String name;

	/**
	 * 身份证号码/组织机构代码
	 */
	private String cardNum;

	/**
	 * 执行依据文号
	 */
	private String caseCode;

	/**
	 * 年龄
	 */
	private String age;

	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 省份
	 */
	private String areaName;

	/**
	 * 执行法院
	 */
	private String courtName;

	/**
	 * 义务
	 */
	private String duty;

	/**
	 * 被执行人的履行情况
	 */
	private String performance;

	/**
	 * 失信被执行人行为具体情形
	 */
	private String disruptTypeName;

	/**
	 * 发布时间
	 */
	private String publishDate;

	/**
	 * 立案时间
	 */
	private String regDate;

	/**
	 * 做出执行依据单位
	 */
	private String gistUnit;

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

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public String getDisruptTypeName() {
		return disruptTypeName;
	}

	public void setDisruptTypeName(String disruptTypeName) {
		this.disruptTypeName = disruptTypeName;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getGistUnit() {
		return gistUnit;
	}

	public void setGistUnit(String gistUnit) {
		this.gistUnit = gistUnit;
	}

	public JudicialInfoVo() {
		
	}
	
	public JudicialInfoVo(Judicial judicial) {
		this.age = judicial.getAge();
		this.areaName = judicial.getAreaName();
		this.cardNum = judicial.getCardNum();
		this.caseCode = judicial.getCaseCode();
		this.courtName = judicial.getCourtName();
		this.disruptTypeName = judicial.getDisruptTypeName();
		this.duty = judicial.getDuty();
		this.gistUnit = judicial.getGistUnit();
		this.name = judicial.getName();
		this.performance = judicial.getPerformance();
		this.publishDate = judicial.getPublishDate();
		this.regDate = judicial.getRegDate();
		this.sex = judicial.getSex();
	}
}
