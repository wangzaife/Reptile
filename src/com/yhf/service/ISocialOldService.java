package com.yhf.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.yhf.domain.SocialOld;
import com.yhf.vo.response.OldInfoVo;

public interface ISocialOldService {

	/**
     * 创建对象
     * @param socialOld
     * @return
     */
    public Integer insert(SocialOld socialOld);

    /**
     * 修改对象
     * @param socialOld
     * @return
     */
    public Integer update(SocialOld socialOld);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialOld findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialOld
     * @return
     */
    public List<SocialOld> findByParam(SocialOld socialOld);
    
    /**
     * 
     * @param cookie
     * @param year
     * @return
     */
    public List<SocialOld> getSocialOld(String cookie, String year);
    
    /**
     * 
     * @param cookie
     * @param year
     * @return
     */
    public List<OldInfoVo> getOldInfo(String cardNum);
    
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
    public List<SocialOld> getInfoByDocument(List<Document> documents, String cardNum);
}
