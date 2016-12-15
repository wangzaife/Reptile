package com.yhf.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.yhf.domain.SocialMaternity;

public interface ISocialMaternityService {

	/**
     * 创建对象
     * @param socialMaternity
     * @return
     */
    public Integer insert(SocialMaternity socialMaternity);

    /**
     * 修改对象
     * @param socialMaternity
     * @return
     */
    public Integer update(SocialMaternity socialMaternity);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialMaternity findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialMaternity
     * @return
     */
    public List<SocialMaternity> findByParam(SocialMaternity socialMaternity);
    
    /**
     * 获取生育信息
     * @param cookie
     * @param year
     */
    public List<SocialMaternity> getMaternityInfo(String cookie, String year);
    
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
    public List<SocialMaternity> getInfoByDocument(List<Document> documents);
}
