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

import com.yhf.dao.SocialUnemploymentDao;
import com.yhf.domain.SocialUnemployment;
import com.yhf.service.ISocialUnemploymentService;
import com.yhf.service.helper.URLHelper;

@Service("socialUnemploymentService")
public class SocialUnemploymentServiceImp implements ISocialUnemploymentService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = Logger.getLogger(SocialUnemploymentServiceImp.class);
	
	@Autowired
	private SocialUnemploymentDao socialUnemploymentDao;
	
	
	@Override
	public Integer insert(SocialUnemployment socialUnemployment) {
		return socialUnemploymentDao.insert(socialUnemployment);
	}

	@Override
	public Integer update(SocialUnemployment socialUnemployment) {
		return socialUnemploymentDao.update(socialUnemployment);
	}

	@Override
	public SocialUnemployment findById(Integer id) {
		return socialUnemploymentDao.findById(id);
	}

	@Override
	public List<SocialUnemployment> findByParam(SocialUnemployment socialUnemployment) {
		return socialUnemploymentDao.findByParam(socialUnemployment);
	}

	@Transactional
	@Override
	public List<SocialUnemployment> getEntity(String cookie, String year) {
		List<SocialUnemployment> socialUnemployments = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!unemployment?searchYear=" + year).header("cookie", cookie).get();
			Elements tbodyTrs = document.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
			if (null == tbodyTrs || 0 == tbodyTrs.size()) {
				return socialUnemployments;
			}
			String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证，单位
			// 遍历数据部分，并将其入库
			for (int i = 2; i < tbodyTrs.size() - 1; i++) {
				SocialUnemployment unemployment = new SocialUnemployment();
				Elements tdDatas = tbodyTrs.get(i).select("td");
				unemployment.setName(infos[1].trim().split(" ")[0]);
				unemployment.setCardNum(infos[2].trim().split(" ")[0]);
				unemployment.setPaymentDate(tdDatas.get(0).text());
				if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && "-".equals(tdDatas.get(3).text())) {
					continue;
				}
				unemployment.setPaymentNum(tdDatas.get(1).text());
				unemployment.setUnitPayment(tdDatas.get(2).text());
				unemployment.setUserPayment(tdDatas.get(3).text());
				socialUnemployments.add(unemployment);
			}
			
			// 将查询集合放入map中
			Map<String, SocialUnemployment> unemploymentMap = new HashMap<>();
			SocialUnemployment socialUnemployment = new SocialUnemployment();
			socialUnemployment.setCardNum(socialUnemployments.get(0).getCardNum());
			List<SocialUnemployment> findByParam = this.findByParam(socialUnemployment);
			for (SocialUnemployment unemployment : findByParam) {
				unemploymentMap.put(unemployment.getCardNum() + "-" + unemployment.getPaymentDate(), unemployment);
			}
			
			// 保存数据
			for (SocialUnemployment unemployment : socialUnemployments) {
				if (null != unemploymentMap.get(unemployment.getCardNum() + "-" + unemployment.getPaymentDate())) {
					continue;
				}
				this.insert(unemployment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socialUnemployments;
	}

	@Override
	public List<Document> getDocument(String cookie, String year) {
		List<Document> documents = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!unemployment?searchYear=" + year).header("cookie", cookie).get();
			documents.add(document);
		} catch (IOException e) {
			LOGGER.info("获取unemployment-html出错! -- " + e.getMessage());
		}
		return documents;
	}

	@Override
	public List<SocialUnemployment> getInfoByDocument(List<Document> documents, String cardNum) {
		List<SocialUnemployment> socialUnemployments = new ArrayList<>();
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
					SocialUnemployment unemployment = new SocialUnemployment();
					Elements tdDatas = tbodyTrs.get(i).select("td");
					unemployment.setName(infos[1].trim().split(" ")[0]);
					unemployment.setCardNum(infos[2].trim().split(" ")[0]);
					unemployment.setPaymentDate(tdDatas.get(0).text());
					if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && "-".equals(tdDatas.get(3).text())) {
						continue;
					}
					unemployment.setPaymentNum(tdDatas.get(1).text());
					unemployment.setUnitPayment(tdDatas.get(2).text());
					unemployment.setUserPayment(tdDatas.get(3).text());
					socialUnemployments.add(unemployment);
				}
			}
			
			// 将查询集合放入map中
			Map<String, SocialUnemployment> unemploymentMap = new HashMap<>();
			SocialUnemployment socialUnemployment = new SocialUnemployment();
			socialUnemployment.setCardNum(cardNum);
			List<SocialUnemployment> findByParam = this.findByParam(socialUnemployment);
			for (SocialUnemployment unemployment : findByParam) {
				unemploymentMap.put(unemployment.getCardNum() + "-" + unemployment.getPaymentDate(), unemployment);
			}
			
			// 保存数据
			for (SocialUnemployment unemployment : socialUnemployments) {
				if (null != unemploymentMap.get(unemployment.getCardNum() + "-" + unemployment.getPaymentDate())) {
					continue;
				}
				this.insert(unemployment);
			}
		} catch (Exception e) {
			LOGGER.error("解析unemployment异常!--" + e.getMessage());
		}
		return socialUnemployments;
	}
}
