package com.yhf.domain;

import java.io.Serializable;

import com.yhf.vo.response.BlacklistVo;

/**
 * Created by wangzaifei on 2016/11/4.
 */
public class Judicial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String cardNum;

	private String caseCode;

	private String age;

	private String sex;
	
	private String areaName;

	private String courtName;

	private String duty;

	private String performance;

	private String disruptTypeName;

	private String publishDate;

	private String regDate;

	private String gistUnit;

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

	public Judicial () {
	}
	
	public Judicial(BlacklistVo blacklistVo) {
		this.age = blacklistVo.getAge();
		this.name = blacklistVo.getIname();
		this.cardNum = blacklistVo.getCardNum();
		this.caseCode = blacklistVo.getCaseCode();
		this.sex = blacklistVo.getSexy();
		this.areaName = blacklistVo.getAreaName();
		this.courtName = blacklistVo.getCourtName();
		this.duty = blacklistVo.getDuty();
		this.performance = blacklistVo.getPerformance();
		this.disruptTypeName = blacklistVo.getDisruptTypeName();
		this.publishDate = blacklistVo.getPublishDate();
		this.regDate = blacklistVo.getRegDate();
		this.gistUnit = blacklistVo.getGistUnit();
	}
}