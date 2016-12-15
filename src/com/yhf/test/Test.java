package com.yhf.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yhf.dao.SocialOldDao;
import com.yhf.domain.SocialInjure;
import com.yhf.domain.SocialMaternity;
import com.yhf.domain.SocialMedicalcare;
import com.yhf.domain.SocialOld;
import com.yhf.service.ISocialInjureService;
import com.yhf.service.ISocialMaternityService;
import com.yhf.service.ISocialMedicalcareService;
import com.yhf.service.ISocialOldService;
import com.yhf.service.ISocialUnemploymentService;

//表示继承了SpringJUnit4ClassRunner类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:resources/spring-mybatis.xml"})
public class Test {

	@Autowired
	private ISocialInjureService socialInjureService;
	
	@Autowired
	private ISocialMaternityService socialMaternityService;
	
	@Autowired
	private ISocialMedicalcareService socialMedicalcareService;
	
	@Autowired
	private ISocialOldService socialOldService;
	
	@Autowired
	private ISocialUnemploymentService socialUnemploymentService;
	
	@Autowired
	private SocialOldDao socialOldDao;

	/**
	 * 工伤
	 */
	@org.junit.Test
	public void injureInsert() {
		SocialInjure socialInjure = new SocialInjure();
		socialInjure.setName("AAAA");
		socialInjure.setCardNum("88888888");
		socialInjure.setPaymentDate("20161130");
		socialInjure.setPaymentNum("8800");
		socialInjure.setUnitPayment("889");
		System.out.println(socialInjureService.insert(socialInjure));
	}
	
	/**
	 * shengyu
	 */
	@org.junit.Test
	public void maternityInsert() {
		SocialMaternity socialInjure = new SocialMaternity();
		socialInjure.setName("AAAA");
		socialInjure.setCardNum("88888888");
		socialInjure.setSettleTime("20161130");
		socialInjure.setPaymentNum("8800");
		socialInjure.setUnitPayment("889");
		System.out.println(socialMaternityService.insert(socialInjure));
	}
	
	/*** 医疗  */
	@org.junit.Test
	public void medicalcareInsert() {
		SocialMedicalcare socialInjure = new SocialMedicalcare();
		socialInjure.setName("AAAA");
		socialInjure.setCardNum("88888888");
		socialInjure.setPaymentDate("20161130");
		socialInjure.setPaymentCategory("111");
		socialInjure.setPaymentNum("8800");
		socialInjure.setUnitPayment("889");
		socialInjure.setUserPayment("700");
		socialInjure.setUnitName("AAA");
		System.out.println(socialMedicalcareService.insert(socialInjure));
	}
	
	
	/*** 养老  */
	@org.junit.Test
	public void oldInsert() {
		SocialOld socialInjure = new SocialOld();
		socialInjure.setName("AAAA");
		socialInjure.setCardNum("88888888");
		socialInjure.setPaymentDate("20161130");
		socialInjure.setPaymentCategory("111");
		socialInjure.setPaymentNum("8800");
		socialInjure.setUnitPayment("889");
		socialInjure.setUserPayment("700");
		socialInjure.setUnitName("AAA");
		System.out.println(socialOldService.insert(socialInjure));
	}
	
	/*** shiye  */
	@org.junit.Test
	public void unemployment() {
//		SocialUnemployment socialInjure = new SocialUnemployment();
//		socialInjure.setName("AAAA");
//		socialInjure.setCardNum("88888888");
//		socialInjure.setPaymentDate("20161130");
//		socialInjure.setPaymentNum("8800");
//		socialInjure.setUnitPayment("889");
//		socialInjure.setUserPayment("700");
//		System.out.println(socialUnemploymentService.insert(socialInjure));
		
		System.out.println((-7) >>> 2);
	}
	
	@org.junit.Test
	public void testSelect() {
		socialOldDao.findByGroup("410923199208136654");
	}
}
