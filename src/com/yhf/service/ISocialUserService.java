package com.yhf.service;

import com.yhf.domain.SocialUser;

import java.util.List;

public interface ISocialUserService {

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
	 * 获取社保信息
	 * @param name
	 * @param password
     * @return
     */
	public String getSocialInfo(String name, String password);
}
