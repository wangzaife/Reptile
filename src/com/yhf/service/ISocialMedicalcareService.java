package com.yhf.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.yhf.domain.SocialMedicalcare;
import com.yhf.vo.response.MedicalcareInfoVo;

public interface ISocialMedicalcareService {

	/**
     * 创建对象
     * @param socialMedicalcare
     * @return
     */
    public Integer insert(SocialMedicalcare socialMedicalcare);

    /**
     * 修改对象
     * @param socialMedicalcare
     * @return
     */
    public Integer update(SocialMedicalcare socialMedicalcare);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialMedicalcare findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialMedicalcare
     * @return
     */
    public List<SocialMedicalcare> findByParam(SocialMedicalcare socialMedicalcare);

    /**
     * 
     * @param cookie
     * @param year
     * @return
     */
    public List<SocialMedicalcare> getSocialMedicalcare(String cookie, String year);
    
    /**
     * 
     * @param cardNum
     * @param cookie
     * @param year
     * @return
     */
    public List<MedicalcareInfoVo> getMedicalcareInfo(String cardNum);
    
    /**
     * 获取html集合
     * @param cookie
     * @param year
     * @return
     */
    public List<Document> getDocument(String cookie, String year);
    
    /**
     * 解析html集合
     * @param documents
     * @return
     */
    public List<SocialMedicalcare> getInfoByDocument(List<Document> documents, String cardNum);
}
