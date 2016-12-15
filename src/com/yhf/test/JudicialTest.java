package com.yhf.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhf.util.GsonUtils;
import com.yhf.util.PageFetcher;
import com.yhf.vo.response.EduMsgVo;
import com.yhf.vo.response.EduVo;

/**
 * Created by wangzaifei on 2016/11/15.
 */
public class JudicialTest {

    public static void main(String[] args) throws Exception{
    	long value = System.currentTimeMillis();
//    	judicial();
    	edu();
//    	social();
    	System.out.println((System.currentTimeMillis() - value)/1000);

//    	String name = "王丽丽";
//    	String url = URLHelper.JUDICIAL_URL + "&cardNum=" + "&iname=" + name;
//    	Response response = Jsoup.connect(url).ignoreContentType(true).execute();
//    	InputStream in = new ByteArrayInputStream(response.bodyAsBytes());
////    	OutputStream out = new FileOutputStream(new File("C:/Users/dell/Desktop/json.txt"));
//    	byte[] b = new byte[in.available()];
//    	int count = 0, temp = 0;
//    	while ((temp = in.read()) != (-1)) {
//			b[count++] = (byte)temp;
//		}
//    	in.close();
////    	out.close();
//    	System.out.println(new String(b));
    }
    
    public static void judicial() {
    	String url = "http://100.0.1.250:8080/Reptile/judicial/judicialInfo";
        Map<String, String> params = new HashMap<>();
        String name = "王丽丽";
        params.put("idCard", "3207211974****5041");
        params.put("name", name);
        PageFetcher pf = new PageFetcher();
        System.out.println(pf.dopost(url, params));
    }
    
    public static void edu() {
    	String url = "http://localhost:8080/Reptile/edu/eduInfo";
        Map<String, String> params = new HashMap<>();
        params.put("userName", "18639376973");
        params.put("password", "wangzaifei_*0813");
        PageFetcher pf = new PageFetcher();
        System.out.println(pf.dopost(url, params));
    }
    
    public static void social() {
    	String url = "http://localhost:8080/Reptile/social/socialInfo";
        Map<String, String> params = new HashMap<>();
        params.put("userName", "410923199208136654");
        params.put("password", "jiao780028");
        PageFetcher pf = new PageFetcher();
        System.out.println(pf.dopost(url, params));
    }
}
