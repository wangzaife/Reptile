package com.yhf.test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestXueXin {

    public static void main(String[] args) throws Exception {
        testXue();
    }

    /**
     * 测试学信
     */
    public static void testXue() throws Exception {

        String userName = "18639376973";
        String pwd = "wangzaifei_*0813";
        // 第一次请求
        Connection con = Jsoup.connect("https://account.chsi.com.cn/passport/login");// 获取连接
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
        Response rs = con.execute();// 获取响应
        Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
        List<Element> et = d1.select("#fm1");// 获取form表单，可以通过查看页面源码代码得知

        System.out.println("re: " + rs);
        
        if (et.isEmpty()) {
            System.out.println("获取form表单失败");
            return;
        }

        // 获取，cooking和表单属性，下面map存放post时的数据
        Map<String, String> datas = new HashMap<>();
        for (Element e : et.get(0).getAllElements()) {
            if (e.attr("name").equals("username")) {
                e.attr("value", userName);// 设置用户名
            }

            if (e.attr("name").equals("password")) {
                e.attr("value", pwd); // 设置用户密码
            }

            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }

        /**
         * 第二次请求，post表单数据，以及cookie信息
         */
        Connection con2 = Jsoup.connect("https://account.chsi.com.cn/passport/login");
        con2.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
//        con.followRedirects(false);
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).method(Method.GET).data(datas).cookies(rs.cookies()).execute();
        // 打印，登陆成功后的信息
//        System.out.println(login.body());
        Document document = Jsoup.parse(login.body());
//        Document document = con2.ignoreContentType(true).method(Method.GET).data(datas).cookies(login.cookies()).get();
        Elements elementById = document.getElementsByAttributeValue("class", "regrightCont");
//        System.out.println("div<regrightCont>" + elementById);

        String url = null;
        Elements links = elementById.get(0).getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.attr("title");
            if ("学信档案".equals(linkText)) {
                url = linkHref;
            }
        }


        Connection con3 = Jsoup.connect(url);
        con3.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
//        con.followRedirects(false);
        // 设置cookie和post上面的map数据
        Response submitRes = con3.ignoreContentType(true).method(Method.GET).data(datas).cookies(login.cookies()).execute();
        Document submit = con3.ignoreContentType(true).method(Method.GET).data(datas).cookies(submitRes.cookies()).get();
        System.out.println("*********submit********");
//        System.out.println(submit);

        Elements xueEle = submit.getElementsByAttributeValue("class", "content_block");
        for (Element element : xueEle) {
            Elements alinks = element.getElementsByTag("a");
            for (Element link : alinks) {
                String linkHref = link.attr("href");
                String linkText = link.text().trim();
                if ("学历信息".equals(linkText)) {
                    System.out.println("学历信息URL:" + linkHref);
                    url = linkHref;
                }
            }
        }
        Connection con4 = Jsoup.connect("http://my.chsi.com.cn/archive/xlarchive.action");
        con4.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        con.followRedirects(false);
        // 设置cookie和post上面的map数据
        Response resultRes = con4.ignoreContentType(true).method(Method.GET).data(datas).cookies(submitRes.cookies()).execute();
        Document result = con4.ignoreContentType(true).method(Method.GET).data(datas).cookies(resultRes.cookies()).get();



        // 获取内容--body
        Elements tabElements = result.getElementsByAttributeValue("id", "tabs");
        if (CollectionUtils.isEmpty(tabElements)) {
            return;
        }

        Document tabDocument = Jsoup.parseBodyFragment(tabElements.html());
        // 获取标签链接
        Elements titleElements = tabDocument.getElementsByAttributeValue("class", "tab_1");
        if (CollectionUtils.isEmpty(titleElements)) {
            return;
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


        // 获取script初始化的值(s1:毕业院校; s2:专业名称; s3:学历类别; s4:学历层次; s5:证书编号; s6:学习形式)
        List<Map> mapList = new ArrayList<>();
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
                    System.out.println(value);

                }
            }
            mapList.add(keyValue);
        }

        System.out.println();
        List<String> trans = new ArrayList<>();

        // 获取标题内容
        for (int k = 0; k < tabHrefList.size(); k++) {
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
                if (ths.size() == tds.size()) {//5
                    for (int j = 0; j < ths.size(); j++) {
                        String title1 = ths.get(j).text().replace("：", "");
                        String title1Value = tds.get(j).text();

                        if (StringUtils.isBlank(title1Value)) {
                            title1Value = mapValue.get(title1);
                        }

                        System.out.println(title1 + ":" + title1Value);
                        trans.add(title1 + title1Value);

                    }
                } else {
                    String title1 = ths.get(0).text();
                    String title1Value = tds.get(0).text();
                    String imgurl = "http://my.chsi.com.cn" + tds.get(1).select("img").attr("src");
                    Connection con5 = Jsoup.connect(imgurl);
                    Response resultResq = con5.ignoreContentType(true).method(Method.GET).data(datas).cookies(submitRes.cookies()).execute();
                    long time = System.currentTimeMillis();
                    FileOutputStream out = (new FileOutputStream(new java.io.File("E:/" + time + ".jpg")));
                    out.write(resultResq.bodyAsBytes()); 
                    out.close();

                    System.out.println(title1 + ":" + title1Value);
                    trans.add(title1 + title1Value);
                }
            }

        }
    }


    public static void login() {
        try {

            String userName = "18639376973";
            String pwd = "wangzaifei_*0813";
            // 第一次请求
            Connection con = Jsoup.connect("https://account.chsi.com.cn/passport/login");// 获取连接
            con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
            Response rs = con.execute();// 获取响应
            Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
            List<Element> et = d1.select("#fm1");// 获取form表单，可以通过查看页面源码代码得知

            if (et.isEmpty()) {
                System.out.println("获取form表单失败");
                return;
            }

            // 获取，cooking和表单属性，下面map存放post时的数据
            Map<String, String> datas = new HashMap<>();
            for (Element e : et.get(0).getAllElements()) {
                if (e.attr("name").equals("username")) {
                    e.attr("value", userName);// 设置用户名
                }

                if (e.attr("name").equals("password")) {
                    e.attr("value", pwd); // 设置用户密码
                }

                if (e.attr("name").length() > 0) {// 排除空值表单属性
                    datas.put(e.attr("name"), e.attr("value"));
                }
            }

            /**
             * 第二次请求，post表单数据，以及cookie信息
             */
            Connection con2 = Jsoup.connect("https://account.chsi.com.cn/passport/login");
            con2.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            con.followRedirects(false);
            // 设置cookie和post上面的map数据
            Response login = con2.ignoreContentType(true).method(Method.GET).data(datas).cookies(rs.cookies()).execute();
            // 打印，登陆成功后的信息
//            System.out.println(login.body());


            // 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
            Map<String, String> map = login.cookies();
            for (String s : map.keySet()) {
                System.out.println(s + "      " + map.get(s));
            }

//			 WebClient webclient = new WebClient();
//			 // 这里是配置一下不加载css和javaScript，因为httpunit对javascript兼容性不太好
//			 webclient.getOptions().setCssEnabled(false);
//			 webclient.getOptions().setJavaScriptEnabled(false);

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            Connection con3 = Jsoup.connect("https://account.chsi.com.cn/account/accountroadmap.action");
            con3.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
            Document document = con3.ignoreContentType(true).method(Method.GET).data(datas).cookies(rs.cookies()).timeout(5000).get();
            Elements select = document.select("script");
            int j = 0;
            for (Element element : select) {
                if (j == 6) {
                    System.out.println(element.html());
                }
                j++;
            }
//			 Elements elementById = document.getElementsByAttributeValue("class","regMainPannel");
            System.out.println("****************************************");
            Elements select2 = document.select("div.regMainPannel");
            System.out.println(select2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
