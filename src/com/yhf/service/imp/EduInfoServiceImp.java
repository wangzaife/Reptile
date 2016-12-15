package com.yhf.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhf.dao.EduInfoDao;
import com.yhf.domain.EduInfo;
import com.yhf.service.IEduInfoService;
import com.yhf.service.helper.URLHelper;
import com.yhf.util.DateUtil;
import com.yhf.util.GsonUtils;
import com.yhf.vo.response.EduMsgVo;
import com.yhf.vo.response.EduVo;

/**
 * Created by wangzaifei on 2016/11/9.
 */
@Service("eduInfoService")
public class EduInfoServiceImp implements IEduInfoService {

	private static final Logger LOGGER = Logger.getLogger(EduInfoServiceImp.class);
	
	@Autowired
	private EduInfoDao eduInfoDao;

	@Override
	public Integer insert(EduInfo eduInfo) {
		return eduInfoDao.insert(eduInfo);
	}

	@Override
	public Integer update(EduInfo eduInfo) {
		return eduInfoDao.update(eduInfo);
	}

	@Override
	public EduInfo findById(Integer id) {
		return eduInfoDao.findById(id);
	}

	@Override
	public List<EduInfo> findByParam(EduInfo eduInfo) {
		return eduInfoDao.findByParam(eduInfo);
	}

	@Override
	public String getEduInfo(String userName, String password) {
		EduMsgVo eduMsgVo = new EduMsgVo();
		try {
			// 获取连接
			Connection connection = conResponse(URLHelper.EDU_URL);
			Connection.Response response = connection.execute();

			// 获取，cooking和表单属性，下面map存放post时的数据
			Map<String, String> datas = postData(response, userName, password);

			// 登陆成功--获取学信档案URL链接
			Connection.Response longResponse = connection.ignoreContentType(true).method(Connection.Method.GET)
					.data(datas).cookies(response.cookies()).execute();
			Document document = connection.ignoreContentType(true).method(Connection.Method.GET).data(datas)
					.cookies(longResponse.cookies()).get();
			// 根据标签获取元素
			Elements divElements = document.getElementsByAttributeValue("class", "regrightCont");

			String url = null;
			Elements links = divElements.get(0).getElementsByTag("a");// 获取所有的a标签
			for (Element link : links) {
				if ("学信档案".equals(link.attr("title"))) {
					url = link.attr("href");
				}
			}

			// 获取学历URL
			Connection con3 = conResponse(url);
			Connection.Response submitRes = con3.ignoreContentType(true).method(Connection.Method.GET).data(datas).cookies(longResponse.cookies()).execute();
			Document submit = con3.ignoreContentType(true).method(Connection.Method.GET).data(datas).cookies(submitRes.cookies()).get();

			Elements xueEle = submit.getElementsByAttributeValue("class", "content_block");
			for (Element element : xueEle) {
				Elements xuelinks = element.getElementsByTag("a");
				for (Element link : xuelinks) {
					if (!"学历信息".equals(link.attr("title"))) {
						continue;
					}
					url = link.attr("href");
					String linkHref = link.attr("href");
					String linkText = link.text().trim();
					if ("学历信息".equals(linkText)) {
						System.out.println(linkHref);
						url = linkHref;
					}
				}
			}

			Connection con4 = Jsoup.connect("http://my.chsi.com.cn/archive/xlarchive.action");
			con4.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
			// 设置cookie和post上面的map数据
			Connection.Response resultRes = con4.ignoreContentType(true).method(Connection.Method.GET).data(datas)
					.cookies(submitRes.cookies()).execute();
			Document resultR = con4.ignoreContentType(true).method(Connection.Method.GET).data(datas)
					.cookies(resultRes.cookies()).get();

			// 解析body----获取内容
			Elements tabElements = resultR.getElementsByAttributeValue("id", "tabs");
			if (CollectionUtils.isEmpty(tabElements)) {
				return null;
			}
			Document tabDocument = Jsoup.parseBodyFragment(tabElements.html());
			// 获取标签链接
			Elements titleElements = tabDocument.getElementsByAttributeValue("class", "tab_1");
			if (CollectionUtils.isEmpty(titleElements)) {
				return null;
			}

			Map<String, String> tabHrefs = new HashMap<>();
			List<String> tabHrefList = new ArrayList<>();
			for (Element element : titleElements) {
				Elements tab_aElements = element.getElementsByTag("a");
				if (CollectionUtils.isEmpty(tab_aElements)) {
					continue;
				}
				for (Element tab_aElement : tab_aElements) {
					String tab_aLink = tab_aElement.attr("href");
					if (StringUtils.isBlank(tab_aLink) || !tab_aLink.contains("#")) {
						continue;
					}

					tab_aLink = tab_aLink.replace("#", "");
					tabHrefs.put(tab_aLink, tab_aElement.text());
					tabHrefList.add(tab_aLink);
				}
			}

			// 获取script初始化的值(s1:毕业院校; s2:专业名称; s3:学历类别; s4:学历层次; s5:证书编号;
			// s6:学习形式)
			List<Map<String, String>> mapList = getScriptValue(tabDocument);

			List<EduInfo> eduInfoList = tranValue(tabDocument, mapList, tabHrefs, tabHrefList);

			List<EduVo> eduVos = new ArrayList<>();
			List<EduInfo> eduInfos = new ArrayList<>();
			
			for (EduInfo eduInfo : eduInfoList) {
				EduVo eduVo = new EduVo(eduInfo);
				eduVos.add(eduVo);

				List<EduInfo> findByParam = this.findByParam(eduInfo);
				if (CollectionUtils.isEmpty(findByParam)) {
					eduInfos.add(eduInfo);
				}
			}

			for (EduInfo eduInfo : eduInfos) {
				this.insert(eduInfo);
			}
			eduMsgVo.setEduVoList(eduVos);
		} catch (Exception e) {
			eduMsgVo.setCode("1111");
			eduMsgVo.setMsg("FAIL");
			LOGGER.info("学信获取结果异常!--" + e.getMessage());
		}
		return GsonUtils.createJsonString(eduMsgVo);
	}

	/**
	 * 根据URL获取响应
	 * 
	 * @param url
	 * @return
	 */
	private Connection conResponse(String url) throws IOException {
		// 获取连接
		Connection connect = Jsoup.connect(url);
		connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		return connect;
	}

	/**
	 * 获取，cooking和表单属性，下面map存放post时的数据
	 * 
	 * @param response
	 * @param userName
	 * @param password
	 * @return
	 */
	private Map<String, String> postData(Connection.Response response, String userName, String password) {
		Document document = Jsoup.parse(response.body());
		// 获取form表单，可以通过查看页面源码代码得知
		List<Element> elementList = document.select("#fm1");

		Map<String, String> postData = new HashMap<>();

		for (Element element : elementList.get(0).getAllElements()) {
			// 设置用户名、用户密码
			if (element.attr("name").equals("password")) {
				element.attr("value", password);
			}

			if (element.attr("name").equals("username")) {
				element.attr("value", userName);
			}

			// 排除空值表单属性
			if (element.attr("name").length() > 0) {
				postData.put(element.attr("name"), element.attr("value"));
			}
		}
		return postData;
	}

	/**
	 * 获取script初始化数据
	 * 
	 * @param tabDocument
	 * @return
	 */
	private List<Map<String, String>> getScriptValue(Document tabDocument) {
		List<Map<String, String>> mapList = new ArrayList<>();
		Elements scriptElements = tabDocument.getElementsByTag("script");
		for (Element scriptElement : scriptElements) {
			Map<String, String> keyValue = new HashMap<>();

			List<DataNode> nodes = scriptElement.dataNodes();

			for (DataNode dataNode : nodes) {

				Attributes attri = dataNode.attributes();
				for (Attribute attribute : attri.asList()) {
					String[] value = attribute.getValue().split(";");
					for (String str : value) {
						if (StringUtils.isBlank(str)) {
							continue;
						}
						str = str.replace("\"+\"", "").replace("initDataInfo(", "").replace(")", "").replace("\"", "");
						String[] val = str.split(",");
						if (val.length > 0) {
							if ("s1".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("毕业院校", val[1]);
							} else if ("s2".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("专业名称", val[1]);
							} else if ("s3".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("学历类别", val[1]);
							} else if ("s4".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("学历层次", val[1]);
							} else if ("s5".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("证书编号", val[1]);
							} else if ("s6".equals(val[0].substring(val[0].length() - 2))) {
								keyValue.put("学习形式", val[1]);
							}
						}
					}
				}
			}
			mapList.add(keyValue);
		}
		return mapList;
	}

	private List<EduInfo> tranValue(Document tabDocument, List<Map<String, String>> mapList,
			Map<String, String> tabHrefs, List<String> tabHrefList) {
		List<EduInfo> eduInfoList = new ArrayList<>();
		for (int k = 0; k < tabHrefList.size(); k++) {
			List<String> trans = new ArrayList<>();
			String str = tabHrefList.get(k);
			String title = tabHrefs.get(str);// 标题
			Elements conElements = tabDocument.getElementsByAttributeValue("id", str);
			Document divDocument = Jsoup.parse(conElements.html());
			Map<String, String> mapValue = mapList.get(k);
			// 获取表格中的tr()
			Elements trs = divDocument.select("table").select("tr");
			for (int i = 0; i < trs.size(); i++) {
				Elements ths = trs.get(i).select("th");
				Elements tds = trs.get(i).select("td");
				if (ths.size() == tds.size()) {// 5
					for (int j = 0; j < ths.size(); j++) {
						String title1 = ths.get(j).text().replace("：", "");
						String title1Value = tds.get(j).text();

						if (StringUtils.isBlank(title1Value)) {
							title1Value = mapValue.get(title1);
						}
						trans.add(title1Value);
					}
				} else {
					// String title1 = ths.get(0).text().replace("：", "");
					String title1Value = tds.get(0).text();
					trans.add(title1Value);
				}
			}

			EduInfo eduInfo = new EduInfo();
			eduInfo.setEduTitle(title);
			eduInfo.setName(trans.get(0));
			eduInfo.setSex(trans.get(1));
			Date birthday = trans.get(2) == null ? null : DateUtil.getFormatDate(trans.get(2), "yyyy年mm月dd日");
			eduInfo.setBirthday(birthday);
			Date enrollmentTime = trans.get(3) == null ? null : DateUtil.getFormatDate(trans.get(3), "yyyy年mm月dd日");
			eduInfo.setEnrollmentTime(enrollmentTime);
			Date graduationTime = trans.get(4) == null ? null : DateUtil.getFormatDate(trans.get(4), "yyyy年mm月dd日");
			eduInfo.setGraduationTime(graduationTime);
			eduInfo.setEduCategory(trans.get(5));
			eduInfo.setEduLevel(trans.get(6));
			eduInfo.setGraduateFrom(trans.get(7));
			eduInfo.setGraduateAddress(trans.get(8));
			eduInfo.setProfessionName(trans.get(9));
			eduInfo.setStudyType(trans.get(10));
			eduInfo.setCertificationNo(trans.get(11));
			eduInfo.setGraduationResult(trans.get(12));

			eduInfoList.add(eduInfo);
		}
		return eduInfoList;
	}

	@Override
	public String eduInfo(String userName, String password) {
		EduMsgVo eduMsgVo = new EduMsgVo();
		List<EduVo> eduVos = new ArrayList<>();

		List<EduInfo> eduInfos = new ArrayList<>();

		List<EduInfo> eduInfoList = null;
//		this.getEduInfo(userName, password);
		for (EduInfo eduInfo : eduInfoList) {
			EduVo eduVo = new EduVo(eduInfo);
			eduVos.add(eduVo);

			List<EduInfo> findByParam = this.findByParam(eduInfo);
			if (CollectionUtils.isEmpty(findByParam)) {
				eduInfos.add(eduInfo);
			}
		}

		for (EduInfo eduInfo : eduInfos) {
			this.insert(eduInfo);
		}
		eduMsgVo.setEduVoList(eduVos);
		return GsonUtils.createJsonString(eduMsgVo);
	}
}
