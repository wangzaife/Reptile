package com.yhf.service;

import java.util.List;

import com.yhf.domain.Judicial;

/**
 * Created by wangzaifei on 2016/11/4.
 */
public interface IJudicialService {

    /**
     * 创建对象
     * @param judicial
     * @return
     */
    public Integer insert(Judicial judicial);

    /**
     * 修改对象
     * @param judicial
     * @return
     */
    public Integer update(Judicial judicial);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public Judicial findById(Integer id);

    /**
     * 根据对象获取集合
     * @param judicial
     * @return
     */
    public List<Judicial> findByParam(Judicial judicial);
    
    /**
     * 根据用户名、身份证号获取司法信息
     * @param name
     * @param idCard
     * @return
     */
    public String getJudicialInfo(String name, String idCard);
}
