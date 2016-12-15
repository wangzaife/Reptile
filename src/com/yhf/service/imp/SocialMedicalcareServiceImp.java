package com.yhf.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhf.dao.SocialMedicalcareDao;
import com.yhf.domain.SocialMedicalcare;
import com.yhf.service.ISocialMedicalcareService;
import com.yhf.service.helper.URLHelper;
import com.yhf.vo.request.SocialMedicalcareVo;
import com.yhf.vo.response.MedicalcareInfoVo;

@Service("socialMedicalcareService")
public class SocialMedicalcareServiceImp implements ISocialMedicalcareService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = Logger.getLogger(SocialMaternityServiceImp.class);
	
	@Autowired
	private SocialMedicalcareDao socialMedicalcareDao;

	@Override
	public Integer insert(SocialMedicalcare socialMedicalcare) {
		return socialMedicalcareDao.insert(socialMedicalcare);
	}

	@Override
	public Integer update(SocialMedicalcare socialMedicalcare) {
		return socialMedicalcareDao.update(socialMedicalcare);
	}

	@Override
	public SocialMedicalcare findById(Integer id) {
		return socialMedicalcareDao.findById(id);
	}

	@Override
	public List<SocialMedicalcare> findByParam(SocialMedicalcare socialMedicalcare) {
		return socialMedicalcareDao.findByParam(socialMedicalcare);
	}

	@Transactional
	@Override
	public List<SocialMedicalcare> getSocialMedicalcare(String cookie, String year) {
		List<SocialMedicalcare> medicalcares = new ArrayList<>();
		try {
			Document docFile = Jsoup.connect(URLHelper.SOCIAL_PAY + "!medicalcare?searchYear=" + year).header("cookie", cookie).get();
			Elements tbodyTrs = docFile.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
			if (null == tbodyTrs || 0 == tbodyTrs.size()) {
				return medicalcares;
			}
			String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证，单位

			String cardNum = "";
			// 遍历数据部分，并将其入库
			for (int i = 2; i < tbodyTrs.size() - 1; i++) {
				SocialMedicalcare medicalcare = new SocialMedicalcare();
				Elements tdDatas = tbodyTrs.get(i).select("td");
				medicalcare.setName(infos[1].trim().split(" ")[0]);
				cardNum = infos[2].trim().split(" ")[0];
				medicalcare.setCardNum(cardNum);
				medicalcare.setPaymentDate(tdDatas.get(0).text());
				if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && "-".equals(tdDatas.get(3).text())) {
					continue;
				}
				medicalcare.setPaymentCategory(tdDatas.get(1).text());
				medicalcare.setPaymentNum(tdDatas.get(2).text());
				medicalcare.setUnitPayment(tdDatas.get(3).text());
				medicalcare.setUserPayment(tdDatas.get(4).text());
				medicalcare.setUnitName(tdDatas.get(5).text());
				medicalcares.add(medicalcare);
			}
			
			// 将查询集合放入map中
			Map<String, SocialMedicalcare> medicalMap = new HashMap<>();
			SocialMedicalcare medicalcare = new SocialMedicalcare();
			medicalcare.setCardNum(cardNum);
			List<SocialMedicalcare> findByParam = this.findByParam(medicalcare);
			for (SocialMedicalcare medicalParm : findByParam) {
				medicalMap.put(medicalParm.getCardNum() + "-" + medicalParm.getPaymentDate(), medicalParm);
			}
			
			// 保存数据（过滤存在的）
			for (SocialMedicalcare socialMedicalcare : medicalcares) {
				if (null != medicalMap.get(socialMedicalcare.getCardNum() + "-" + socialMedicalcare.getPaymentDate())) {
					continue;
				}
				this.insert(socialMedicalcare);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicalcares;
	}

	@Override
	public List<MedicalcareInfoVo> getMedicalcareInfo(String cardNum) {
		List<MedicalcareInfoVo> infoVos = new ArrayList<>();
		List<SocialMedicalcareVo> findByGroup = socialMedicalcareDao.findByGroup(cardNum);
		for (SocialMedicalcareVo socialMedicalcare : findByGroup) {
			MedicalcareInfoVo infoVo = new MedicalcareInfoVo();
			infoVo.setPaymentNum(socialMedicalcare.getPaymentNum());
			infoVo.setPaymentTime(socialMedicalcare.getPaymentDate());
			infoVo.setUnitName(socialMedicalcare.getUnitName());
			infoVos.add(infoVo);
		}
		return infoVos;
	}

	@Override
	public List<Document> getDocument(String cookie, String year) {
		List<Document> documents = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!medicalcare?searchYear=" + year).header("cookie", cookie).get();
			documents.add(document);
		} catch (IOException e) {
			LOGGER.info("获取html出错!--" + e.getMessage());
		}
		return documents;
	}

	@Override
	public List<SocialMedicalcare> getInfoByDocument(List<Document> documents, String cardNum) {
		List<SocialMedicalcare> medicalcares = new ArrayList<>();
		try {
			for (Document document : documents) {
				Elements tbodyTrs = document.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
				if (null == tbodyTrs || 0 == tbodyTrs.size()) {
					continue;
				}
				String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证，单位
				if (infos.length <= 0) {
					continue;
				}
				
				// 遍历数据部分，并将其入库
				for (int i = 2; i < tbodyTrs.size() - 1; i++) {
					SocialMedicalcare medicalcare = new SocialMedicalcare();
					Elements tdDatas = tbodyTrs.get(i).select("td");
					medicalcare.setName(infos[1].trim().split(" ")[0]);
					cardNum = infos[2].trim().split(" ")[0];
					medicalcare.setCardNum(cardNum);
					medicalcare.setPaymentDate(tdDatas.get(0).text());
					if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && "-".equals(tdDatas.get(3).text())) {
						continue;
					}
					medicalcare.setPaymentCategory(tdDatas.get(1).text());
					medicalcare.setPaymentNum(tdDatas.get(2).text());
					medicalcare.setUnitPayment(tdDatas.get(3).text());
					medicalcare.setUserPayment(tdDatas.get(4).text());
					medicalcare.setUnitName(tdDatas.get(5).text());
					medicalcares.add(medicalcare);
				}
			}
			
			// 将查询集合放入map中
			Map<String, SocialMedicalcare> medicalMap = new HashMap<>();
			SocialMedicalcare medicalcare = new SocialMedicalcare();
			medicalcare.setCardNum(cardNum);
			List<SocialMedicalcare> findByParam = this.findByParam(medicalcare);
			for (SocialMedicalcare medicalParm : findByParam) {
				medicalMap.put(medicalParm.getCardNum() + "-" + medicalParm.getPaymentDate(), medicalParm);
			}
			
			// 保存数据（过滤存在的）
			for (SocialMedicalcare socialMedicalcare : medicalcares) {
				if (null != medicalMap.get(socialMedicalcare.getCardNum() + "-" + socialMedicalcare.getPaymentDate())) {
					continue;
				}
				this.insert(socialMedicalcare);
			}
		} catch (Exception e) {
			LOGGER.error("解析medical出错! -- " + e.getMessage());
		}
		return medicalcares;
	}

}
