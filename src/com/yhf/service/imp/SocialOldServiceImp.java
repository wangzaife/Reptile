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

import com.yhf.dao.SocialOldDao;
import com.yhf.domain.SocialOld;
import com.yhf.service.ISocialOldService;
import com.yhf.service.helper.URLHelper;
import com.yhf.vo.request.SocialOldVo;
import com.yhf.vo.response.OldInfoVo;

@Service("socialOldService")
public class SocialOldServiceImp implements ISocialOldService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = Logger.getLogger(SocialOldServiceImp.class);
	
	@Autowired
	private SocialOldDao socialOldDao;

	@Override
	public Integer insert(SocialOld socialOld) {
		return socialOldDao.insert(socialOld);
	}

	@Override
	public Integer update(SocialOld socialOld) {
		return socialOldDao.update(socialOld);
	}

	@Override
	public SocialOld findById(Integer id) {
		return socialOldDao.findById(id);
	}

	@Override
	public List<SocialOld> findByParam(SocialOld socialOld) {
		return socialOldDao.findByParam(socialOld);
	}

	@Transactional
	@Override
	public List<SocialOld> getSocialOld(String cookie, String year) {
		List<SocialOld> olds = new ArrayList<>();
		try {
			Document docFile = Jsoup.connect(URLHelper.SOCIAL_PAY + "!oldage?searchYear=" + year).header("cookie", cookie).get();
			Elements tbodyTrs = docFile.select("table").select("tbody").select("tr"); // 得到所有tbody中的tr标签
			if (null == tbodyTrs || 0 == tbodyTrs.size()) {
				return olds;
			}
			String[] infos = tbodyTrs.get(0).text().replace(Jsoup.parse("&nbsp;").text(), " ").split("："); // 截取第一行tr的信息，姓名，身份证，单位

			// 遍历数据部分，并将其入库
			for (int i = 2; i < tbodyTrs.size() - 1; i++) {
				SocialOld old = new SocialOld();
				Elements tdDatas = tbodyTrs.get(i).select("td");
				old.setName(infos[1].trim().split(" ")[0]);
				old.setCardNum(infos[2].trim().split(" ")[0]);
				old.setPaymentDate(tdDatas.get(0).text());
				if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && 
						"-".equals(tdDatas.get(3).text()) && "-".equals(tdDatas.get(4).text())) {
					continue;
				}
				old.setPaymentCategory(tdDatas.get(1).text());
				old.setPaymentNum(tdDatas.get(2).text());
				old.setUnitPayment(tdDatas.get(3).text());
				old.setUserPayment(tdDatas.get(4).text());
				old.setUnitName(tdDatas.get(5).text());
				olds.add(old);
			}

			// 将查询集合放入map中
			Map<String, SocialOld> oldMap = new HashMap<>();
			SocialOld socialOld = new SocialOld();
			socialOld.setCardNum(olds.get(0).getCardNum());
			List<SocialOld> findByParam = this.findByParam(socialOld);
			for (SocialOld socialOld2 : findByParam) {
				oldMap.put(socialOld2.getCardNum() + "-" + socialOld2.getPaymentDate(), socialOld2);
			}
			
			// 保存数据
			for (SocialOld old : olds) {
				if (null != oldMap.get(old.getCardNum() + "-" + old.getPaymentDate())) {
					continue;
				}
				this.insert(old);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return olds;
	}

	@Override
	public List<OldInfoVo> getOldInfo(String cardNum) {
		List<OldInfoVo> infoVos = new ArrayList<>();
		List<SocialOldVo> findByGroup = socialOldDao.findByGroup(cardNum);
		for (SocialOldVo socialOld : findByGroup) {
			OldInfoVo oldInfoVo = new OldInfoVo();
			oldInfoVo.setPaymentNum(socialOld.getPaymentNum());
			oldInfoVo.setPaymentTime(socialOld.getPaymentDate());
			oldInfoVo.setUnitName(socialOld.getUnitName());
			infoVos.add(oldInfoVo);
		}
		return infoVos;
	}

	@Override
	public List<Document> getDocument(String cookie, String year) {
		List<Document> documents = new ArrayList<>();
		try {
			Document document = Jsoup.connect(URLHelper.SOCIAL_PAY + "!oldage?searchYear=" + year).header("cookie", cookie).get();
			documents.add(document);
		} catch (IOException e) {
			LOGGER.info("获取old-html出错! -- " + e.getMessage());
		}
		return documents;
	}

	@Override
	public List<SocialOld> getInfoByDocument(List<Document> documents, String cardNum) {
		List<SocialOld> olds = new ArrayList<>();
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
					SocialOld old = new SocialOld();
					Elements tdDatas = tbodyTrs.get(i).select("td");
					old.setName(infos[1].trim().split(" ")[0]);
					old.setCardNum(infos[2].trim().split(" ")[0]);
					old.setPaymentDate(tdDatas.get(0).text());
					if ("-".equals(tdDatas.get(1).text()) && "-".equals(tdDatas.get(2).text()) && "-".equals(tdDatas.get(3).text()) && "-".equals(tdDatas.get(4).text())) {
						continue;
					}
					old.setPaymentCategory(tdDatas.get(1).text());
					old.setPaymentNum(tdDatas.get(2).text());
					old.setUnitPayment(tdDatas.get(3).text());
					old.setUserPayment(tdDatas.get(4).text());
					old.setUnitName(tdDatas.get(5).text());
					olds.add(old);
				}
			}
			
			// 将查询集合放入map中
			Map<String, SocialOld> oldMap = new HashMap<>();
			SocialOld socialOld = new SocialOld();
			socialOld.setCardNum(cardNum);
			List<SocialOld> findByParam = this.findByParam(socialOld);
			for (SocialOld socialOld2 : findByParam) {
				oldMap.put(socialOld2.getCardNum() + "-" + socialOld2.getPaymentDate(), socialOld2);
			}
			
			// 保存数据
			for (SocialOld old : olds) {
				if (null != oldMap.get(old.getCardNum() + "-" + old.getPaymentDate())) {
					continue;
				}
				this.insert(old);
			}
		} catch (Exception e) {
			LOGGER.error("解析old异常!--" + e.getMessage());
		}
		return olds;
	}

}
