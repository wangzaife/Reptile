package com.yhf.service;

import com.yhf.domain.EduInfo;

import java.util.List;

/**
 * Created by wangzaifei on 2016/11/9.
 */
public interface IEduInfoService {

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

    /**
     * 获取学历信息
     * @param userName
     * @param password
     * @return
     */
    public String getEduInfo(String userName, String password);
    
    /**
     * 获取json学历信息
     * @param userName
     * @param password
     * @return
     */
    public String eduInfo(String userName, String password);
}
