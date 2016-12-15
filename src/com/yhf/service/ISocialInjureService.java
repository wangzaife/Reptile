package com.yhf.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.yhf.domain.SocialInjure;

public interface ISocialInjureService {

	/**
     * 创建对象
     * @param socialInjure
     * @return
     */
    public Integer insert(SocialInjure socialInjure);

    /**
     * 修改对象
     * @param socialInjure
     * @return
     */
    public Integer update(SocialInjure socialInjure);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialInjure findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialInjure
     * @return
     */
    public List<SocialInjure> findByParam(SocialInjure socialInjure);
    
    /**
     * 
     * @param cookie
     * @param year
     * @return
     */
    public List<SocialInjure> getInjureInfo(String cookie, String year);
    
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
    public List<SocialInjure> getInfoByDocument(List<Document> documents);
}
