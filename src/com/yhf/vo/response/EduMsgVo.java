package com.yhf.vo.response;

import java.util.List;

/**
 * Created by wangzaifei on 2016/11/25.
 */
public class EduMsgVo extends MessageVo {

    private List<EduVo> eduVoList;


    public List<EduVo> getEduVoList() {
        return eduVoList;
    }

    public void setEduVoList(List<EduVo> eduVoList) {
        this.eduVoList = eduVoList;
    }
}
