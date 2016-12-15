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

import com.yhf.dao.SocialInjureDao;
import com.yhf.domain.SocialInjure;
import com.yhf.service.ISocialInjureService;
import com.yhf.service.helper.URLHelper;

@Service("socialInjureService")
public class SocialInjureServiceImp implements ISocialInjureService {

	private static final Logger LOGGER = Logger.getLogger(SocialInjureServiceImp.class);
	
	@Autowired
	private SocialInjureDao SocialInjureDao;

	@Override
	public Integer insert(SocialInjure socialInjure) {
		return SocialInjureDao.insert(socialInjure);
	}

	@Override
	public Integer update(SocialInjure socialInjure) {
		return SocialInjureDao.update(socialInjure);
	}

	@Override
	public SocialInjure findById(Integer id) {
		return SocialInjureDao.findById(id);
	}

	@Override
	public List<SocialInjure> findByParam(SocialInjure socialInjure) {
		return SocialInjureDao.findByParam(socialInjure);
	}

	@Override
	public List<SocialInjure> getInjureInfo(String cookie, String year) {
		List<SocialInjure> injures = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!injuries?searchYear=" + year).header("cookie", cookie).get();
			Elements tbodyTrs = document.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
			if (null == tbodyTrs || 0 == tbodyTrs.size()) {
				return injures;
			}
			String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证
			if (infos.length <= 0) {
				return injures;
			}
			// 遍历数据部分，并将其入库
			for (int i = 2; i < tbodyTrs.size() - 1; i++) {
				SocialInjure injure = new SocialInjure();
				Elements tdDatas = tbodyTrs.get(i).select("td");
				injure.setName(infos[1].trim().split(" ")[0]);
				injure.setCardNum(infos[2].trim().split(" ")[0]);
				injure.setPaymentDate(tdDatas.get(0).text());
				if ("-".equals(tdDatas.get(1).text())
						&& "-".equals(tdDatas.get(2).text())) {
					continue;
				}
				injure.setPaymentNum(tdDatas.get(1).text());
				injure.setUnitPayment(tdDatas.get(2).text());
				injures.add(injure);
			}
			
			// 将查询集合放入map中
			Map<String, SocialInjure> injureMap = new HashMap<>();
			SocialInjure socialInjure = new SocialInjure();
			socialInjure.setCardNum(injures.get(0).getCardNum());
			List<SocialInjure> findByParam = this.findByParam(socialInjure);
			for (SocialInjure injure : findByParam) {
				injureMap.put(injure.getCardNum() + "-" + injure.getPaymentDate(), injure);
			}
			
			// 保存数据（过滤存在的）
			for (SocialInjure injure : injures) {
				if (null != injureMap.get(injure.getCardNum() + "-" + injure.getPaymentDate())) {
					continue;
				}
				this.insert(injure);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return injures;
	}

	@Override
	public List<Document> getDocument(String cookie, String year) {
		List<Document> documents = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!injuries?searchYear=" + year).header("cookie", cookie).get();
			documents.add(document);
		} catch (IOException e) {
			LOGGER.info("获取html出错!--" + e.getMessage());
		}
		return documents;
	}
	
	@Override
	public List<SocialInjure> getInfoByDocument(List<Document> documents) {
		List<SocialInjure> injures = new ArrayList<>();
		try {
			for (Document document : documents) {
				Elements tbodyTrs = document.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
				if (null == tbodyTrs || 0 == tbodyTrs.size()) {
					continue;
				}
				String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证
				if (infos.length <= 0) {
					continue;
				}
				// 遍历数据部分，并将其入库
				for (int i = 2; i < tbodyTrs.size() - 1; i++) {
					SocialInjure injure = new SocialInjure();
					Elements tdDatas = tbodyTrs.get(i).select("td");
					injure.setName(infos[1].trim().split(" ")[0]);
					injure.setCardNum(infos[2].trim().split(" ")[0]);
					injure.setPaymentDate(tdDatas.get(0).text());
					if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text())) {
						continue;
					}
					injure.setPaymentNum(tdDatas.get(1).text());
					injure.setUnitPayment(tdDatas.get(2).text());
					injures.add(injure);
				}
			}
			
			// 将查询集合放入map中
			Map<String, SocialInjure> injureMap = new HashMap<>();
			SocialInjure socialInjure = new SocialInjure();
			socialInjure.setCardNum(injures.get(0).getCardNum());
			List<SocialInjure> findByParam = this.findByParam(socialInjure);
			for (SocialInjure injure : findByParam) {
				injureMap.put(injure.getCardNum() + "-" + injure.getPaymentDate(), injure);
			}
			
			// 保存数据（过滤存在的）
			for (SocialInjure injure : injures) {
				if (null != injureMap.get(injure.getCardNum() + "-" + injure.getPaymentDate())) {
					continue;
				}
				this.insert(injure);
			}
		} catch(Exception e) {
			LOGGER.error("解析页面出错!--" + e.getMessage());
		}
		return injures;
	}

}
