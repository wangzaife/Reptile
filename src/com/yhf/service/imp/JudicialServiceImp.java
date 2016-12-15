package com.yhf.service.imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhf.dao.JudicialDao;
import com.yhf.domain.Judicial;
import com.yhf.service.IJudicialService;
import com.yhf.service.helper.URLHelper;
import com.yhf.util.GsonUtils;
import com.yhf.vo.response.BlacklistDataVo;
import com.yhf.vo.response.BlacklistVo;
import com.yhf.vo.response.JudicialInfoVo;
import com.yhf.vo.response.JudicialMsgVo;
import com.yhf.vo.response.ShixinVo;

/**
 * Created by wangzaifei on 2016/11/4.
 */
@Service("judicialService")
public class JudicialServiceImp implements IJudicialService {

	@Autowired
	private JudicialDao judicialDao;

	private static final Logger LOGGER = Logger.getLogger(JudicialServiceImp.class);
	
	@Override
	public Integer insert(Judicial judicial) {
		return judicialDao.insert(judicial);
	}

	@Override
	public Integer update(Judicial judicial) {
		return judicialDao.update(judicial);
	}

	@Override
	public Judicial findById(Integer id) {
		return judicialDao.findById(id);
	}

	@Override
	public List<Judicial> findByParam(Judicial judicial) {
		return judicialDao.findByParam(judicial);
	}

	@Override
	public String getJudicialInfo(String name, String idCard) {
		JudicialMsgVo msg = new JudicialMsgVo();
		try {
			List<Judicial> judicials = new ArrayList<>();
			Judicial judici = new Judicial();
			judici.setName(name);
			judici.setCardNum(idCard);
			judicials = findByParam(judici);
			if (CollectionUtils.isEmpty(judicials)) {
				String url = URLHelper.JUDICIAL_URL + "&cardNum=" + idCard + "&iname=" + name;
				if (-1 != idCard.indexOf("*")) {
					url = URLHelper.JUDICIAL_URL + "&cardNum=" + "&iname=" + name;
				}
				judicials = getJudicials(url, idCard);
			}
			
			List<JudicialInfoVo> infoVos = new ArrayList<>();
			for (Judicial judicial : judicials) {
				JudicialInfoVo judicialInfoVo = new JudicialInfoVo(judicial);
				infoVos.add(judicialInfoVo);
			}
			msg.setJudicialInfoVos(infoVos);
			LOGGER.info("获取司法结果成功!");
		} catch (Exception e) {
			msg.setCode("1111");
			msg.setMsg("FAIL");
			LOGGER.error("获取司法信息fail!", e);
		}
		return GsonUtils.createJsonString(msg);
	}
	
	private List<Judicial> getJudicials(String url, String idCard) throws Exception{
		List<Judicial> judicials = new ArrayList<>();
		Response response = Jsoup.connect(url).ignoreContentType(true).execute();
		InputStream in = new ByteArrayInputStream(response.bodyAsBytes());
		byte[] b = new byte[in.available()];
    	int count = 0, temp = 0;
    	while ((temp = in.read()) != (-1)) {
			b[count++] = (byte)temp;
		}
    	in.close();
    	String json = new String(b);
		int startP = json.indexOf("{");
		int endP = json.lastIndexOf("}");
		
		json = json.substring(startP, endP + 1).toString();
//		Document document = Jsoup.connect(url).ignoreContentType(true).get();
//		Element element = document.body();
//		int startP = element.text().indexOf("{");
//		int endP = element.text().lastIndexOf("}");
//		
//		String json = element.text().substring(startP, endP + 1).toString();
		if (StringUtils.isBlank(json)) {
			return judicials;
		}
		
		ShixinVo shixinVo = GsonUtils.getPerson(json, ShixinVo.class);
		if (null == shixinVo || CollectionUtils.isEmpty(shixinVo.getData())) {
			return judicials;
		}
		
		List<BlacklistDataVo> data = shixinVo.getData();
		for (BlacklistDataVo blacklistDataVo : data) {
			List<BlacklistVo> result = blacklistDataVo.getResult();
			for (BlacklistVo blacklistVo : result) {
				String cardNum = blacklistVo.getCardNum();
				if (StringUtils.isBlank(cardNum) || 19 != cardNum.length()) {
					continue;
				}
				// 判断idcard是否包含*
				if (-1 != idCard.lastIndexOf("*")) {// 包含*
					cardNum = cardNum.substring(0, 10) + "****" + cardNum.substring(15);
					if (idCard.equals(cardNum)) {
						blacklistVo.setCardNum(cardNum);
						Judicial jdJudicial = new Judicial(blacklistVo);
						judicials.add(jdJudicial);
					}
				} else {
					blacklistVo.setCardNum(idCard);
					//截取前10位 + **** + 截取后4位
					idCard = idCard.substring(0, 11) + "****" + idCard.substring(15);
					cardNum = cardNum.substring(0, 11) + "****" + cardNum.substring(16);
					if (idCard.equals(cardNum)) {
						Judicial jdJudicial = new Judicial(blacklistVo);
						judicials.add(jdJudicial);
					}
				}
			}
		}

		for (Judicial judicial : judicials) {
			this.insert(judicial);
		}
		
		return judicials;
	}
}
