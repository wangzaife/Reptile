package com.yhf.vo.response;

import java.util.List;

public class BlacklistDataVo {

	private List<BlacklistVo> result;

	private String otherinfo;

	private Integer resNum;

	private Integer dispNum;

	private Integer listNum;

	private Integer normdisp;

	private Integer term_demand;

	private String url;

	private String TitleSuffix;

	private Integer template_pattern_demand_index;

	private String showlamp;

	private String ExtendedLocation;

	private String OriginQuery;

	private String tplt;

	private String resourceid;

	private String fetchkey;

	private String appinfo;

	public List<BlacklistVo> getResult() {
		return result;
	}

	public void setResult(List<BlacklistVo> result) {
		this.result = result;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Integer getResNum() {
		return resNum;
	}

	public void setResNum(Integer resNum) {
		this.resNum = resNum;
	}

	public Integer getDispNum() {
		return dispNum;
	}

	public void setDispNum(Integer dispNum) {
		this.dispNum = dispNum;
	}

	public Integer getListNum() {
		return listNum;
	}

	public void setListNum(Integer listNum) {
		this.listNum = listNum;
	}

	public Integer getNormdisp() {
		return normdisp;
	}

	public void setNormdisp(Integer normdisp) {
		this.normdisp = normdisp;
	}

	public Integer getTerm_demand() {
		return term_demand;
	}

	public void setTerm_demand(Integer term_demand) {
		this.term_demand = term_demand;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitleSuffix() {
		return TitleSuffix;
	}

	public void setTitleSuffix(String titleSuffix) {
		TitleSuffix = titleSuffix;
	}

	public Integer getTemplate_pattern_demand_index() {
		return template_pattern_demand_index;
	}

	public void setTemplate_pattern_demand_index(Integer template_pattern_demand_index) {
		this.template_pattern_demand_index = template_pattern_demand_index;
	}

	public String getShowlamp() {
		return showlamp;
	}

	public void setShowlamp(String showlamp) {
		this.showlamp = showlamp;
	}

	public String getExtendedLocation() {
		return ExtendedLocation;
	}

	public void setExtendedLocation(String extendedLocation) {
		ExtendedLocation = extendedLocation;
	}

	public String getOriginQuery() {
		return OriginQuery;
	}

	public void setOriginQuery(String originQuery) {
		OriginQuery = originQuery;
	}

	public String getTplt() {
		return tplt;
	}

	public void setTplt(String tplt) {
		this.tplt = tplt;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getFetchkey() {
		return fetchkey;
	}

	public void setFetchkey(String fetchkey) {
		this.fetchkey = fetchkey;
	}

	public String getAppinfo() {
		return appinfo;
	}

	public void setAppinfo(String appinfo) {
		this.appinfo = appinfo;
	}

}