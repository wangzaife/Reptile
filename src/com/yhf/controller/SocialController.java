package com.yhf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhf.service.ISocialUserService;

@Controller
@RequestMapping("/social")
public class SocialController {

	
	@Autowired
	private ISocialUserService socialUserService;
	
	@RequestMapping(value = "/socialInfo", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	public @ResponseBody String getSocialInfo(String userName, String password) {
		String socialInfo = socialUserService.getSocialInfo(userName, password);
		return socialInfo;
	}
}
