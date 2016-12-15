package com.yhf.service.helper;

public enum URLHelper {

	INSTANCE;
	
	/**
	 * 司法URL
	 */
	public static final String JUDICIAL_URL = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA%E5%90%8D%E5%8D%95&areaName=&ie=utf-8&oe=utf-8&format=json&t=1477554019190&cb=jQuery110208296680131089085_1477553998614&_=1477553998625";

	/**
	 * 学信URL
	 */
	public static final String EDU_URL = "https://account.chsi.com.cn/passport/login";
	
	/**
	 * BJ社保codeURL
	 */
	public static final String SOCIAL_CODE = "http://www.bjrbj.gov.cn/csibiz/indinfo/validationCodeServlet.do";
	
	/**
	 * BJ社保登录URL
	 */
	public static final String SOCIAL_LOGIN = "http://www.bjrbj.gov.cn/csibiz/indinfo/login_check";
	
	/**
	 * BJ社保登录成功重定向
	 */
	public static final String SOCIAL_REDIRECT = "http://www.bjrbj.gov.cn/csibiz/indinfo/index2.jsp";
	
	/**
	 * BJ社保个人基本信息URL
	 */
	public static final String SOCIAL_PERSONAL = "http://www.bjrbj.gov.cn/csibiz/indinfo/search/ind/indNewInfoSearchAction";
	
	/**
	 * BJ社保缴费URL
	 * 养老：!oldage?searchYear=2016&time=1479976059929
	 * 失业：!unemployment?searchYear=2016&time=1479976108432
	 * 工伤：!injuries?searchYear=2016&time=1479976125541
	 * 生育：!maternity?searchYear=2016&time=1479976140819
	 * 医疗：!medicalcare?searchYear=2016&time=1479976178771
	 */
	public static final String SOCIAL_PAY = "http://www.bjrbj.gov.cn/csibiz/indinfo/search/ind/indPaySearchAction";
	
	
			
				
				
					
}
