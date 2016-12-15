package com.yhf.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhf.dao.SocialUserDao;
import com.yhf.domain.SocialUser;
import com.yhf.service.ISocialInjureService;
import com.yhf.service.ISocialMaternityService;
import com.yhf.service.ISocialMedicalcareService;
import com.yhf.service.ISocialOldService;
import com.yhf.service.ISocialUnemploymentService;
import com.yhf.service.ISocialUserService;
import com.yhf.service.helper.URLHelper;
import com.yhf.util.CodeGifUtil;
import com.yhf.util.GsonUtils;
import com.yhf.vo.response.MedicalcareInfoVo;
import com.yhf.vo.response.OldInfoVo;
import com.yhf.vo.response.SocialInfoVo;
import com.yhf.vo.response.SocialMsgVo;

@Service("socialService")
public class SocialUserServiceImp implements ISocialUserService {

	@Autowired
	private SocialUserDao socialUserDao;
	
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
	
	@Override
	public Integer insert(SocialUser socialUser) {
		return socialUserDao.insert(socialUser);
	}

	@Override
	public Integer update(SocialUser socialUser) {
		return socialUserDao.update(socialUser);
	}

	@Override
	public SocialUser findById(Integer id) {
		return socialUserDao.findById(id);
	}

	@Override
	public List<SocialUser> findByParam(SocialUser socialUser) {
		return socialUserDao.findByParam(socialUser);
	}
	
	
	private Boolean postLogin(String uri, String username, String password, String code, String cookie) {
		Boolean flag = false;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpClientContext context = HttpClientContext.create();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("j_username", username));
			nvps.add(new BasicNameValuePair("j_password", password));
			nvps.add(new BasicNameValuePair("safecode", code));
			nvps.add(new BasicNameValuePair("type", "1"));
			nvps.add(new BasicNameValuePair("flag", "3"));
			httpPost.setHeader("Cookie", cookie);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			CloseableHttpResponse response = httpclient.execute(httpPost, context);
//			int statusCode = response.getStatusLine().getStatusCode();
			String locUrl = response.getFirstHeader("location").getValue();
			if (URLHelper.SOCIAL_REDIRECT.equals(locUrl)) {
				flag = true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String getSocialInfo(String name, String password) {
		long time = System.currentTimeMillis();
		SocialMsgVo msgVo = new SocialMsgVo();
		try {
			HashMap<String,String> codeMap = null;
			String code;
			do {
				codeMap = CodeGifUtil.getCode(URLHelper.SOCIAL_CODE + "?d=" + time);
				code = codeMap.get("code").trim();
				System.out.println(code + "---");
			} while(StringUtils.isBlank(code) || code.length() < 4);
			String cookie = codeMap.get("cookie");
			Boolean postLogin = postLogin(URLHelper.SOCIAL_LOGIN, name, password, code, cookie);
			if (!postLogin) {
				msgVo.setCode("1111");
				msgVo.setMsg("登录失败!");
			} else {
				SocialUser socialUser = getPersonInfo(cookie);
				int year = Integer.valueOf(socialUser.getWorkTime().substring(0, 4));
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				
				List<Document> injureDocs = new ArrayList<>();
				List<Document> maternityDocs = new ArrayList<>();
				List<Document> medicalDocs = new ArrayList<>();
				List<Document> oldDocs = new ArrayList<>();
				List<Document> unemploymentDocs = new ArrayList<>();
				for (int i = year; i <= currentYear; i++) {
					List<Document> injureDocuments = socialInjureService.getDocument(cookie, i + "");
					
					List<Document> maternityDocument = socialMaternityService.getDocument(cookie, i + "");
					
					List<Document> medicalDocument = socialMedicalcareService.getDocument(cookie, i + "");
					
					List<Document> oldDocument = socialOldService.getDocument(cookie, i + "");
					
					List<Document> unemploymentDocument = socialUnemploymentService.getDocument(cookie, i + "");
					
					injureDocs.addAll(injureDocuments);
					maternityDocs.addAll(maternityDocument);
					medicalDocs.addAll(medicalDocument);
					oldDocs.addAll(oldDocument);
					unemploymentDocs.addAll(unemploymentDocument);
				}
				socialInjureService.getInfoByDocument(injureDocs);
				socialMaternityService.getInfoByDocument(maternityDocs);
				socialMedicalcareService.getInfoByDocument(medicalDocs, socialUser.getCitizenNum());
				socialOldService.getInfoByDocument(oldDocs, socialUser.getCitizenNum());
				socialUnemploymentService.getInfoByDocument(unemploymentDocs, socialUser.getCitizenNum());
				
				//
				SocialInfoVo infoVo = new SocialInfoVo();
				infoVo.setName(socialUser.getName());
				infoVo.setCardNum(socialUser.getCitizenNum());
				
				List<MedicalcareInfoVo> medicalcareInfo = socialMedicalcareService.getMedicalcareInfo(socialUser.getCitizenNum());
				List<OldInfoVo> oldInfo = socialOldService.getOldInfo(socialUser.getCitizenNum());
				infoVo.setMedicalcareInfoVos(medicalcareInfo);
				infoVo.setOldInfoVos(oldInfo);
				
				msgVo.setInfoVo(infoVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GsonUtils.createJsonString(msgVo);
	}
	
	private SocialUser getPersonInfo(String cookie) throws Exception {
		Document document = Jsoup.connect(URLHelper.SOCIAL_PERSONAL).header("cookie", cookie).get();
		SocialUser socialUser = socialUserInfo(document);
		SocialUser findByCitizenNum = socialUserDao.findByCitizenNum(socialUser.getCitizenNum());
		if (null == findByCitizenNum) {
			this.insert(socialUser);
		}
		return socialUser;
	}
	
	
	private SocialUser socialUserInfo(Document document) {
		SocialUser socialUser = new SocialUser();
		
		Elements tabElements = document.select("table");
		String value = tabElements.get(0).select("td").text().trim();
		for (int i = 0; i < value.length(); i++) {
			if ("：".equals(String.valueOf(value.charAt(i)))) {
				value = value.substring(0, i + 1) + value.substring(i+1).replace(" ", "");
			}
			if (" ".equals(String.valueOf(value.charAt(i)))) {
				value = value.substring(0, i + 1) + value.substring(i+1).replace(" ", "\n");
			}
		}
		List<String> list = new ArrayList<>();
		String[] strings = value.split("\n");
		for (String string : strings) {
			if ("".equals(string)) {
				continue;
			}
			list.add(string.trim());
		}
		
		
		socialUser.setUnitName(list.get(0).split("：")[1]);
		socialUser.setOrganizeCode(list.get(1).split("：")[1]);
		socialUser.setSocialCode(list.get(2).split("：")[1]);
		socialUser.setUnitAddress(list.get(3).split("：")[1]);
		
		Elements elements = tabElements.get(1).select("tr");
		Elements select = elements.get(0).select("td");
		socialUser.setSocialType(select.get(1).text().replaceAll(" ", ""));
		
		Elements select2 = elements.get(1).select("td");
		socialUser.setName(select2.get(1).text());
		socialUser.setCitizenNum(select2.get(3).text());
		
		Elements select3 = elements.get(2).select("td");
		socialUser.setSex(select3.get(1).text());
		socialUser.setBirthday(select3.get(3).text());
		
		Elements select4 = elements.get(3).select("td");
		socialUser.setNation(select4.get(1).text());
		socialUser.setCountry(select4.get(3).text());
		
		Elements select5 = elements.get(4).select("td");
		socialUser.setIdentity(select5.get(1).text());
		socialUser.setWorkTime(select5.get(3).text());
		
		Elements select6 = elements.get(5).select("td");
		socialUser.setRegisteredDetailAddress(select6.get(1).text());
		socialUser.setRegisteredNature(select6.get(3).text());
		
		Elements select7 = elements.get(6).select("td");
		socialUser.setRegisteredAddress(select7.get(1).text());
		socialUser.setRegisteredCode(select7.get(3).text());
		
		Elements select8 = elements.get(7).select("td");
		socialUser.setLocalAddress(select8.get(1).text());
		socialUser.setLocalCode(select8.get(3).text());
		
		Elements select9 = elements.get(8).select("td");
		socialUser.setBillMailAddress(select9.get(1).text());
		socialUser.setBillMailCode(select9.get(3).text());
		
		Elements select10 = elements.get(9).select("td");
		socialUser.setExtBillMode(select10.get(1).text());
		socialUser.setEmail(select10.get(3).text());
		socialUser.setEduLevel(select10.get(5).text());
		
		Elements select11 = elements.get(10).select("td");
		socialUser.setMobile(select11.get(3).text());
		socialUser.setPhone(select11.get(1).text());
		socialUser.setMonthIncome(select11.get(3).text());
		
		Elements select12 = elements.get(11).select("td");
		socialUser.setCardNum(select12.get(1).text());
		socialUser.setCardType(select12.get(3).text());
		
		Elements select13 = elements.get(12).select("td");
		socialUser.setPrincipalBank(select13.get(1).text());
		socialUser.setPrincipalBankNum(select13.get(3).text());
		
		Elements select14 = elements.get(13).select("td");
		socialUser.setPaymentType(select14.get(1).text());
		socialUser.setMedicalType(select14.get(3).text());
		
		Elements select15 = elements.get(14).select("td");
		socialUser.setRetirementType(select15.get(1).text());
		socialUser.setRetirementTime(select15.get(3).text());
		
		Elements select16 = elements.get(15).select("td");
		socialUser.setMedicalOrganize1(select16.get(1).text());
		socialUser.setMedicalOrganize2(select16.get(3).text());
		
		Elements select17 = elements.get(16).select("td");
		socialUser.setMedicalOrganize3(select17.get(1).text());
		socialUser.setMedicalOrganize4(select17.get(3).text());
		
		Elements select18 = elements.get(17).select("td");
		socialUser.setMedicalOrganize5(select18.get(1).text());
		socialUser.setSpecialDefect(select18.get(3).text());
		
		return socialUser;
	}
	
}
