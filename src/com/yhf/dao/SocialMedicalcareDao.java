package com.yhf.dao;

import java.util.List;

import com.yhf.domain.SocialMedicalcare;
import com.yhf.vo.request.SocialMedicalcareVo;

public interface SocialMedicalcareDao {

	/**
     * 创建对象
     * @param socialMedicalcare
     * @return
     */
    public Integer insert(SocialMedicalcare socialMedicalcare);

    /**
     * 修改对象
     * @param socialMedicalcare
     * @return
     */
    public Integer update(SocialMedicalcare socialMedicalcare);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialMedicalcare findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialMedicalcare
     * @return
     */
    public List<SocialMedicalcare> findByParam(SocialMedicalcare socialMedicalcare);
    
    /**
     * 
     * @param cardNum
     * @return
     */
    public List<SocialMedicalcareVo> findByGroup(String cardNum);
}
