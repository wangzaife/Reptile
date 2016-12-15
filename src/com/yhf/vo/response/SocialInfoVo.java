package com.yhf.vo.response;

import java.util.List;

public class SocialInfoVo {

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 身份证号
	 */
	private String cardNum;
	
	/**
	 * yanglao集合
	 */
	private List<OldInfoVo> oldInfoVos;

	/**
	 * yiliao集合
	 */
	private List<MedicalcareInfoVo> medicalcareInfoVos;
	
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

	public List<OldInfoVo> getOldInfoVos() {
		return oldInfoVos;
	}

	public void setOldInfoVos(List<OldInfoVo> oldInfoVos) {
		this.oldInfoVos = oldInfoVos;
	}

	public List<MedicalcareInfoVo> getMedicalcareInfoVos() {
		return medicalcareInfoVos;
	}

	public void setMedicalcareInfoVos(List<MedicalcareInfoVo> medicalcareInfoVos) {
		this.medicalcareInfoVos = medicalcareInfoVos;
	}
}
