package com.yhf.dao;

import java.util.List;

import com.yhf.domain.SocialUnemployment;

public interface SocialUnemploymentDao {

	 /**
     * 创建对象
     * @param socialUnemployment
     * @return
     */
    public Integer insert(SocialUnemployment socialUnemployment);

    /**
     * 修改对象
     * @param socialUnemployment
     * @return
     */
    public Integer update(SocialUnemployment socialUnemployment);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialUnemployment findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialUnemployment
     * @return
     */
    public List<SocialUnemployment> findByParam(SocialUnemployment socialUnemployment);
}
