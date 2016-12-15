package com.yhf.dao;

import com.yhf.domain.SocialUser;

import java.util.List;

/**
 * Created by wangzaifei on 2016/11/25.
 */
public interface SocialUserDao {

    /**
     * 创建对象
     * @param socialUser
     * @return
     */
    public Integer insert(SocialUser socialUser);

    /**
     * 修改对象
     * @param socialUser
     * @return
     */
    public Integer update(SocialUser socialUser);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public SocialUser findById(Integer id);

    /**
     * 根据对象获取集合
     * @param socialUser
     * @return
     */
    public List<SocialUser> findByParam(SocialUser socialUser);
    
    /**
     * 
     * @param citizenNum
     * @return
     */
    public SocialUser findByCitizenNum(String citizenNum);
}
