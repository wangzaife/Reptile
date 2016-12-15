package com.yhf.dao;

import java.util.List;

import com.yhf.domain.SocialInjure;

public interface SocialInjureDao {

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
}
