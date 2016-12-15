package com.yhf.dao;

import com.yhf.domain.EduInfo;

import java.util.List;

/**
 * Created by wangzaifei on 2016/11/9.
 */
public interface EduInfoDao {

    /**
     * 创建对象
     *
     * @param eduInfo
     * @return
     */
    public Integer insert(EduInfo eduInfo);

    /**
     * 修改对象
     *
     * @param eduInfo
     * @return
     */
    public Integer update(EduInfo eduInfo);

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    public EduInfo findById(Integer id);

    /**
     * 根据对象获取集合
     *
     * @param eduInfo
     * @return
     */
    public List<EduInfo> findByParam(EduInfo eduInfo);
}
