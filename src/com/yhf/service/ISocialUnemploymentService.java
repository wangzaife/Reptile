package com.yhf.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.yhf.domain.SocialUnemployment;

public interface ISocialUnemploymentService {

	public Integer insert(SocialUnemployment socialUnemployment);

    public Integer update(SocialUnemployment socialUnemployment);

    public SocialUnemployment findById(Integer id);

    public List<SocialUnemployment> findByParam(SocialUnemployment socialUnemployment);
    
    /**
     * 
     * @param cookie
     * @param year： XXXX
     * @return
     */
    public List<SocialUnemployment> getEntity(String cookie, String year);
    
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
    public List<SocialUnemployment> getInfoByDocument(List<Document> documents, String cardNum);
}
