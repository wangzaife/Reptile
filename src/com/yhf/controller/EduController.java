package com.yhf.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhf.service.IEduInfoService;

/**
 * Created by wangzaifei on 2016/11/25.
 */
@Controller
@RequestMapping("/edu")
public class EduController {

    @Autowired
    private IEduInfoService eduInfoService;

    /**
     * 获取学历信息
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/eduInfo", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    public @ResponseBody String judicialInfo(String userName, String password) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return null;
        }
        String result = eduInfoService.getEduInfo(userName, password);
        return result;
    }
}
