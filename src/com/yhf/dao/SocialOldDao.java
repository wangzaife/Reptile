package com.yhf.dao;

import java.util.List;

import com.yhf.domain.SocialOld;
import com.yhf.vo.request.SocialOldVo;

/**
 * Created by wangzaifei on 2016/11/30.
 */
public interface SocialOldDao {

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
     * @param cardNum
     * @return
     */
    public List<SocialOldVo> findByGroup(String cardNum);
}
