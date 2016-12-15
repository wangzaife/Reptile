package com.yhf.vo.response;

import java.util.List;

public class ShixinVo {

	private String status;

	private String t;

	private String set_cache_time;

	private List<BlacklistDataVo> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getSet_cache_time() {
		return set_cache_time;
	}

	public void setSet_cache_time(String set_cache_time) {
		this.set_cache_time = set_cache_time;
	}

	public List<BlacklistDataVo> getData() {
		return data;
	}

	public void setData(List<BlacklistDataVo> data) {
		this.data = data;
	}

}