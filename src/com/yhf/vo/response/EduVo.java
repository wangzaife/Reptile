package com.yhf.vo.response;

import com.yhf.domain.EduInfo;
import com.yhf.util.DateUtil;

import java.util.Date;

/**
 * Created by wangzaifei on 2016/11/25.
 */
public class EduVo {


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
    private String birthday;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 入学时间
     */
    private String enrollmentTime;

    /**
     * 毕业时间
     */
    private String graduationTime;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(String enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
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

    public EduVo() {
    }

    public EduVo(EduInfo eduInfo) {
        this.name = eduInfo.getName();
        this.sex = eduInfo.getSex();
        this.birthday = eduInfo.getBirthday() != null ? DateUtil.getFormattedString(eduInfo.getBirthday(), "yyyy-MM-dd") : "";
        this.headImg = eduInfo.getHeadImg();
        this.enrollmentTime = eduInfo.getEnrollmentTime() != null ? DateUtil.getFormattedString(eduInfo.getEnrollmentTime(), "yyyy-MM-dd") : "";
        this.graduationTime = eduInfo.getGraduationTime() != null ? DateUtil.getFormattedString(eduInfo.getGraduationTime(), "yyyy-MM-dd") : "";
        this.eduCategory = eduInfo.getEduCategory();
        this.eduLevel = eduInfo.getEduLevel();
        this.graduateFrom = eduInfo.getGraduateFrom();
        this.graduateAddress = eduInfo.getGraduateAddress();
        this.professionName = eduInfo.getProfessionName();
        this.studyType = eduInfo.getStudyType();
        this.certificationNo = eduInfo.getCertificationNo();
        this.graduationResult = eduInfo.getGraduationResult();
        this.eduTitle = eduInfo.getEduTitle();
    }
}
