package com.yhf.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.asprise.ocr.Ocr;
import com.yhf.service.helper.URLHelper;
import com.yhf.util.CodeGifUtil;

public class CodeProcess {

	private static final String fileName = "captcha.jpg";

	// 根据实际验证码的色彩来判断哪里要变成白色
	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 300) { // 根据实际情况修改这里的300
			return 1;
		}
		return 0;
	}

	// 根据实际验证码的色彩来判断哪里要变成黑色
	public static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) { // 根据实际情况修改这里的300
			return 1;
		}
		return 0;
	}

	// 扫描验证码所有的像素颜色过滤掉不要的颜色
	public static BufferedImage removeBackgroud4Tone(String picFile) throws Exception {
		BufferedImage img = ImageIO.read(new File(picFile));
		int width = img.getWidth();
		int height = img.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	/*
	 * public static BufferedImage removeBackgroud4MaxColor(String picFile, int
	 * count) { BufferedImage img = null; try { img = ImageIO.read(new
	 * File(picFile)); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } img = img.getSubimage(1, 1, img.getWidth() -
	 * 2, img.getHeight() - 2); int width = img.getWidth(); int height =
	 * img.getHeight(); double subWidth = (double) width / count; for (int i =
	 * 0; i < count; i++) { Map<Integer, Integer> map = new HashMap<Integer,
	 * Integer>(); for (int x = (int) (1 + i * subWidth); x < (i + 1) * subWidth
	 * && x < width - 1; ++x) { for (int y = 0; y < height; ++y) { if
	 * (isWhite(img.getRGB(x, y)) == 1) continue; if
	 * (map.containsKey(img.getRGB(x, y))) { map.put(img.getRGB(x, y),
	 * map.get(img.getRGB(x, y)) + 1); } else { map.put(img.getRGB(x, y), 1); }
	 * } } int max = 0; int colorMax = 0; for (Integer color : map.keySet()) {
	 * if (max < map.get(color)) { max = map.get(color); colorMax = color; } }
	 * for (int x = (int) (1 + i * subWidth); x < (i + 1) * subWidth && x <
	 * width - 1; ++x) { for (int y = 0; y < height; ++y) { if (img.getRGB(x, y)
	 * != colorMax) { img.setRGB(x, y, Color.WHITE.getRGB()); } else {
	 * img.setRGB(x, y, Color.BLACK.getRGB()); } } } } return img; }
	 */

	// 将过滤都的黑白图片保存
	public static void handleImg(String file) {
		BufferedImage img;
		try {
			img = removeBackgroud4Tone(file);

			ImageIO.write(img, "JPG", new File(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * public static void handleImg(String file, int count) { BufferedImage img;
	 * try { img = removeBackgroud4MaxColor(file, count);
	 * 
	 * ImageIO.write(img, "JPG", new File(fileName)); } catch (Exception e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	// 识别验证码，这里如果验证码处理不好识别率很低
	public static String identifyCode() {
		handleImg(fileName);
		// Tesseract1 instance = new Tesseract1();
		File imageFile = new File(fileName); // instance.setLanguage("chi_sim");
		String result = null;
		try {
			Ocr.setUp(); // one time setup
			Ocr ocr = new Ocr(); // create a new OCR engine
			ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
			result = ocr.recognize(new File[] { imageFile }, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
			// result = instance.doOCR(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 获得网站的验证码及COOKIE
	public static HashMap<String, String> getCode(String uri) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream content = httpResponse.getEntity().getContent();
			String cookie = "";
			Header[] headers = httpResponse.getHeaders("set-cookie");
			for (Header header : headers) {
				int index = header.getValue().indexOf(";");
				cookie += header.getValue().substring(0, index);
				cookie += ";";
			}
			if (-1 != cookie.lastIndexOf(";")) {
				cookie = cookie.substring(0, cookie.length() - 1);
			}
			ImageIO.write(ImageIO.read(content), "JPG", new File(fileName));
			String code = identifyCode();
			map.put("cookie", cookie); // cookie=JSESSIONID=16yjdmlj4l1g81jqe39c41nooc;
			map.put("code", code);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	// 模拟登录
	public static void postForm(String uri, String username, String password, String code, String cookie) {
		String result = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			con.setRequestProperty("Cookie", cookie);
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
								// 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http
								// 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"username\" \r\n\r\n"); // 这里是参数名，参数名和值之间要用两次
			sb.append(username + "\r\n"); // 参数的值

			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"password\" \r\n\r\n");
			sb.append(password + "\r\n");

			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"code\" \r\n\r\n");
			sb.append(code + "\r\n");

			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	// 模拟登录方式2
	public static void postForm2(String uri, String username, String password, String code, String cookie) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();

			HttpClientContext context = HttpClientContext.create();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("j_username", username));
			nvps.add(new BasicNameValuePair("j_password", password));
			nvps.add(new BasicNameValuePair("safecode", code));
			nvps.add(new BasicNameValuePair("type", "1"));
			nvps.add(new BasicNameValuePair("flag", "3"));
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.bjrbj.gov.cn");
			httpPost.setHeader("Origin", "http://www.bjrbj.gov.cn");
			httpPost.setHeader("Referer", "http://www.bjrbj.gov.cn/csibiz/indinfo/login.jsp");
			httpPost.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			CloseableHttpResponse response = httpclient.execute(httpPost, context);
			String result = "";
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
			System.out.println(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 根绝uri和cookie 下载整个页面
	public static void getPage(String uri, String cookie) {
		// HashMap<String, String> map = new HashMap<String, String>();
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
			con.setRequestProperty("Cookie", cookie);
			String result = "";
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		HashMap<String, String> codeMap = null;// cookie=JSESSIONID=16yjdmlj4l1g81jqe39c41nooc;
		String code = "";
		do {
			codeMap = CodeProcess.getCode("http://www.bjrbj.gov.cn/csibiz/indinfo/validationCodeServlet.do?d=" + time);
			code = codeMap.get("code").trim();
			System.out.println(code + "----" + code);
		} while(StringUtils.isBlank(code) || code.length() < 4);
		String cookie = codeMap.get("cookie");
		System.out.println("验证码：" + code + ", cookie：" + cookie);
		// // 将上面的cookie 传入下面方面中即可
		// // CodeProcess.postForm("登录地址","admin","123123","ppbuc",
		// "JSESSIONID=ot1hycqeqc8x3hbner0fehsr");
		CodeProcess.postForm2("http://www.bjrbj.gov.cn/csibiz/indinfo/login_check", "410923199208136654", "jiao780028", code, cookie);
		// // CodeProcess.getPage("下载页面的地址",
		// "JSESSIONID=ot1hycqeqc8x3hbner0fehsr");

		String url = "http://www.bjrbj.gov.cn/csibiz/indinfo/personalTelAction!checkTel";
//		String cookie = "JSESSIONID=1488DF4273F59D6BE20235183EA1F8BC; _gscu_2065735475=79888362xxwxl019; mjrzMBJgZO=MDAwM2IyYWYyZjQwMDAwMDAwMDgwExwYEWgxNDc5OTgwNjk3";
		Document doc = Jsoup.connect(url).header("cookie", cookie).get();
		System.out.println(doc);
	}

}
