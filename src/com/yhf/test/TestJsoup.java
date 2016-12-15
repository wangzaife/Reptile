package com.yhf.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoup {
	
	private static final String fileName = "captcha.jpg";
	
	private static String validCode = "";
	
	public static void main(String[] args) throws IOException {
//		String validCode="0285";
		String cardid="410923199210086633";
		String password="jiao780028";
	
		//获得验证码，返回cookie
		String cookie=getSafeCode();
		
		//发送（身份证，密码，验证码）认证cookie
		authenticate(cardid,password,validCode,cookie);

		String url="http://www.bjrbj.gov.cn/csibiz/indinfo/search/ind/indPaySearchAction!!medicalcare?searchYear=2016&time=1479976178771";
		Document parse=Jsoup.connect(url).header("cookie", cookie).get();
		System.out.println(parse + "*****************");
//		Elements tabElements = parse.select("table");
//		String value = tabElements.get(0).select("td").text().trim();
//		for (int i = 0; i < value.length(); i++) {
//			if ("：".equals(String.valueOf(value.charAt(i)))) {
//				value = value.substring(0, i + 1) + value.substring(i+1).replace(" ", "");
//			}
//			if (" ".equals(String.valueOf(value.charAt(i)))) {
//				value = value.substring(0, i + 1) + value.substring(i+1).replace(" ", "\n");
//			}
//		}
//		List<String> list = new ArrayList<>();
//		String[] strings = value.split("\n");
//		for (String string : strings) {
//			if ("".equals(string)) {
//				continue;
//			}
//			list.add(string.trim());
//		}
//		
//		String unitName = list.get(0).split("：")[1];
//		String organizeCode = list.get(1).split("：")[1];
//		String socialCode = list.get(2).split("：")[1];
//		String unitAddress = list.get(3).split("：")[1];
//		
//		Elements elements = tabElements.get(1).select("tr");
//		Elements select = elements.get(0).select("td");
//		String socialType = select.get(1).text();
//		
//		Elements select2 = elements.get(1).select("td");
//		String name = select2.get(1).text();
//		String critizenNum = select2.get(3).text();
//		
//		Elements select3 = elements.get(2).select("td");
//		String sex = select3.get(1).text();
//		String birthday = select3.get(3).text();
//		
//		Elements select4 = elements.get(3).select("td");
//		String nation = select4.get(1).text();
//		String country = select4.get(3).text();
//		
//		Elements select5 = elements.get(4).select("td");
//		String identity = select5.get(1).text();
//		String workTime = select5.get(3).text();
//		
//		Elements select6 = elements.get(5).select("td");
//		String registeredDetailAddress = select6.get(1).text();
//		String registeredNature = select6.get(3).text();
//		
//		Elements select7 = elements.get(6).select("td");
//		String registeredAddress = select7.get(1).text();
//		String registeredCode = select7.get(3).text();
//		
//		Elements select8 = elements.get(7).select("td");
//		String localAddress = select8.get(1).text();
//		String localCode = select8.get(3).text();
//		
//		Elements select9 = elements.get(8).select("td");
//		String billMailAddress = select9.get(1).text();
//		String billMailCode = select9.get(3).text();
//		
//		Elements select10 = elements.get(9).select("td");
//		String extBillMode = select10.get(1).text();
//		String email = select10.get(3).text();
//		String eduLevel = select10.get(5).text();
//		
//		Elements select11 = elements.get(10).select("td");
//		String mobile = select11.get(3).text();
//		String phone = select11.get(1).text();
//		String monthIncome = select11.get(3).text();
//		
//		Elements select12 = elements.get(11).select("td");
//		String cardNum = select12.get(1).text();
//		String cardType = select12.get(3).text();
//		
//		Elements select13 = elements.get(12).select("td");
//		String principalBank = select13.get(1).text();
//		String principalBankNum = select13.get(3).text();
//		
//		Elements select14 = elements.get(13).select("td");
//		String paymentType = select14.get(1).text();
//		String medicalType = select14.get(3).text();
//		
//		Elements select15 = elements.get(14).select("td");
//		String retirementType = select15.get(1).text();
//		String retirementTime = select15.get(3).text();
//		
//		Elements select16 = elements.get(15).select("td");
//		String medicalOrganize1 = select16.get(1).text();
//		String medicalOrganize2 = select16.get(3).text();
//		
//		Elements select17 = elements.get(16).select("td");
//		String medicalOrganize3 = select17.get(1).text();
//		String medicalOrganize4 = select17.get(3).text();
//		
//		Elements select18 = elements.get(17).select("td");
//		String medicalOrganize5 = select18.get(1).text();
//		String specialDefect = select18.get(3).text();
//		
//		System.out.println("");
	}

	private static void authenticate(String cardid, String password, String safeCode, String cookie) throws IOException {
		String url="http://www.bjrbj.gov.cn/csibiz/indinfo/login_check?type=1&flag=3&j_username=410923199210086633&j_password=jiao780028&safecode="+safeCode+"&x=50&y=14";
		System.out.println(url);
		Document doc;
		try {
			doc = Jsoup.connect(url).header("cookie", cookie).post();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getSafeCode() {
		HashMap<String, String> code = CodeProcess.getCode("http://www.bjrbj.gov.cn/csibiz/indinfo/validationCodeServlet.do");
		validCode = code.get("code").trim();
		String cook = code.get("cookie");
		System.out.println(code + "---"+ cook);
		return cook;
	}

	
	 
}
