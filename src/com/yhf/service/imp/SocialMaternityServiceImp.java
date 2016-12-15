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

import com.yhf.dao.SocialMaternityDao;
import com.yhf.domain.SocialMaternity;
import com.yhf.service.ISocialMaternityService;
import com.yhf.service.helper.URLHelper;

@Service("socialMaternityService")
public class SocialMaternityServiceImp implements ISocialMaternityService {

	private static final Logger LOGGER = Logger.getLogger(SocialMaternityServiceImp.class);
	
	@Autowired
	private SocialMaternityDao socialMaternityDao;

	@Override
	public Integer insert(SocialMaternity socialMaternity) {
		return socialMaternityDao.insert(socialMaternity);
	}

	@Override
	public Integer update(SocialMaternity socialMaternity) {
		return socialMaternityDao.update(socialMaternity);
	}

	@Override
	public SocialMaternity findById(Integer id) {
		return socialMaternityDao.findById(id);
	}

	@Override
	public List<SocialMaternity> findByParam(SocialMaternity socialMaternity) {
		return socialMaternityDao.findByParam(socialMaternity);
	}

	@Override
	public List<SocialMaternity> getMaternityInfo(String cookie, String year) {
		List<SocialMaternity> maternities = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!maternity?searchYear=" + year).header("cookie", cookie).get();
			Elements tbodyTrs = document.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
			if (null == tbodyTrs || 0 == tbodyTrs.size()) {
				return maternities;
			}
			String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证，单位

			// 遍历数据部分，并将其入库
			for (int i = 2; i < tbodyTrs.size() - 1; i++) {
				SocialMaternity maternity = new SocialMaternity();
				Elements tdDatas = tbodyTrs.get(i).select("td");
				maternity.setName(infos[1].trim().split(" ")[0]);
				maternity.setCardNum(infos[2].trim().split(" ")[0]);
				if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text())) {
					continue;
				}
				maternity.setSettleTime(tdDatas.get(0).text());
				maternity.setPaymentNum(tdDatas.get(1).text());
				maternity.setUnitPayment(tdDatas.get(2).text());

				maternities.add(maternity);
			}
			
			// 将查询集合放入map中
			Map<String, SocialMaternity> maternityMap = new HashMap<>();
			SocialMaternity socialMaternity = new SocialMaternity();
			socialMaternity.setCardNum(maternities.get(0).getCardNum());
			List<SocialMaternity> findByParam = this.findByParam(socialMaternity);
			for (SocialMaternity maternity : findByParam) {
				maternityMap.put(maternity.getCardNum() + "-" + maternity.getSettleTime(), maternity);
			}
			
			// 保存数据
			for (SocialMaternity maternity : maternities) {
				if (null != maternityMap.get(maternity.getCardNum() + "-" + maternity.getSettleTime())) {
					continue;
				}
				this.insert(maternity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maternities;
	}

	@Override
	public List<Document> getDocument(String cookie, String year) {
		List<Document> documents = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!maternity?searchYear=" + year).header("cookie", cookie).get();
			documents.add(document);
		} catch (IOException e) {
			LOGGER.info("获取maternity-html出错!--" + e.getMessage());
		}
		return documents;
	}

	@Override
	public List<SocialMaternity> getInfoByDocument(List<Document> documents) {
		List<SocialMaternity> maternities = new ArrayList<>();
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
					SocialMaternity maternity = new SocialMaternity();
					Elements tdDatas = tbodyTrs.get(i).select("td");
					maternity.setName(infos[1].trim().split(" ")[0]);
					maternity.setCardNum(infos[2].trim().split(" ")[0]);
					if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text())) {
						continue;
					}
					maternity.setSettleTime(tdDatas.get(0).text());
					maternity.setPaymentNum(tdDatas.get(1).text());
					maternity.setUnitPayment(tdDatas.get(2).text());
					maternities.add(maternity);
				}
			}
			
			// 将查询集合放入map中
			Map<String, SocialMaternity> maternityMap = new HashMap<>();
			SocialMaternity socialMaternity = new SocialMaternity();
			socialMaternity.setCardNum(maternities.get(0).getCardNum());
			List<SocialMaternity> findByParam = this.findByParam(socialMaternity);
			for (SocialMaternity maternity : findByParam) {
				maternityMap.put(maternity.getCardNum() + "-" + maternity.getSettleTime(), maternity);
			}
			
			// 保存数据
			for (SocialMaternity maternity : maternities) {
				if (null != maternityMap.get(maternity.getCardNum() + "-" + maternity.getSettleTime())) {
					continue;
				}
				this.insert(maternity);
			}
		} catch (Exception e) {
			LOGGER.error("解析maternity异常!---" + e.getMessage());
		}
		return maternities;
	}

}
