package com.yhf.dao;

import java.util.List;

import com.yhf.domain.SocialMaternity;

public interface SocialMaternityDao {

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
}
