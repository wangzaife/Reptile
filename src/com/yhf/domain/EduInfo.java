package com.yhf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangzaifei on 2016/11/9.
 * 学历信息
 */
public class EduInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 入学时间
     */
    private Date enrollmentTime;

    /**
     * 毕业时间
     */
    private Date graduationTime;

    /**
     * 学历类别
     */
    private String eduCategory;

    /**
     * 学历层次
     */
    private String eduLevel;

    /**
     * 毕业院校
     */
    private String graduateFrom;

    /**
     * 毕业所在地
     */
    private String graduateAddress;

    /**
     * 专业名称
     */
    private String professionName;

    /**
     * 学习形式
     */
    private String studyType;

    /**
     * 证书编号
     */
    private String certificationNo;

    /**
     * 毕业结业结论
     */
    private String graduationResult;

    /**
     * 学历标题
     */
    private String eduTitle;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(Date enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public Date getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(Date graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getGraduateFrom() {
        return graduateFrom;
    }

    public void setGraduateFrom(String graduateFrom) {
        this.graduateFrom = graduateFrom;
    }

    public String getGraduateAddress() {
        return graduateAddress;
    }

    public void setGraduateAddress(String graduateAddress) {
        this.graduateAddress = graduateAddress;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getCertificationNo() {
        return certificationNo;
    }

    public void setCertificationNo(String certificationNo) {
        this.certificationNo = certificationNo;
    }

    public String getGraduationResult() {
        return graduationResult;
    }

    public void setGraduationResult(String graduationResult) {
        this.graduationResult = graduationResult;
    }

    public String getEduTitle() {
        return eduTitle;
    }

    public void setEduTitle(String eduTitle) {
        this.eduTitle = eduTitle;
    }

    public String getEduCategory() {
        return eduCategory;
    }

    public void setEduCategory(String eduCategory) {
        this.eduCategory = eduCategory;
    }
}
