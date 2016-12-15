package com.yhf.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.asprise.ocr.Ocr;

public class CodeGifUtil {

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

	// 将过滤都的黑白图片保存
	public static void handleImg(String file) {
		BufferedImage img;
		try {
			img = removeBackgroud4Tone(file);
			ImageIO.write(img, "JPG", new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 识别验证码，这里如果验证码处理不好识别率很低
	public static String identifyCode(String fileName) {
		handleImg(fileName);
		File imageFile = new File(fileName);
		String result = null;
		try {
			Ocr.setUp();
			Ocr ocr = new Ocr();
			ocr.startEngine("eng", Ocr.SPEED_FASTEST);
			result = ocr.recognize(new File[] { imageFile }, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
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
			String fileName = System.currentTimeMillis() + ".jpg";
			ImageIO.write(ImageIO.read(content), "JPG", new File(fileName));
			String code = identifyCode(fileName).trim();
			if (new File(fileName).exists()) {
				new File(fileName).delete();
			}
			map.put("cookie", cookie);
			map.put("code", code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
}
