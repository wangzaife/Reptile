package com.yhf.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhf.service.IJudicialService;

@Controller
@RequestMapping("/judicial")
public class JudicialController {

	@Autowired
	private IJudicialService judicialService;
	
	/**
	 * 获取司法信息
	 * @param idCard
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/judicialInfo", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	public @ResponseBody String judicialInfo(String idCard, String name) {
		if (StringUtils.isBlank(idCard) || StringUtils.isBlank(name) || 18 != idCard.length()) {
			return null;
		}
		String result = judicialService.getJudicialInfo(name, idCard);
		return result;
	}
}
