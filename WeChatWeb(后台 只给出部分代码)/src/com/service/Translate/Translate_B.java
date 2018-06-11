package com.service.Translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;


import sun.misc.BASE64Encoder;

import com.DataManager.WordsDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Translate_B extends HttpServlet
{
	private static final String appid = "20180429000151759";
	private static final String screat = "ZpYeiBwEM2_Jppzl2nFx";
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/json;charset=utf-8");
		
		String q = req.getParameter("q");
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		String salt = req.getParameter("salt");

		if (q == null || q.isEmpty())
			q = "";
		if (from == null || from.isEmpty())
			from = "auto";
		if (to == null || to.isEmpty())
			to = "auto";
		if (salt == null || salt.isEmpty())
		{
			Random random = new Random();
			random.nextInt(9999999);
			salt = new Integer(random.nextInt(9999999)).toString();
			//System.out.println(salt);
		}

		// 在数据库中查找
		WordsDAO wordsDAO = new WordsDAO();
//		boolean addToDB = false;
//		if((from.equals("auto") || from.equals("en")) && 
//				(to.equals("auto") || to.equals("zh")))
//		{			
//			String json = wordsDAO.findWord_B(q);
//			if (json != null)
//			{
//				resp.getWriter().println(json);
//				return;
//			}
//			addToDB = true;
//		}

		System.out.println(URLDecoder.decode(q, "utf-8"));
		// MD5加密
		String str = appid + URLDecoder.decode(q, "utf-8") + salt + screat;
		System.out.println(str);
		String sign = md5(str);

		// 有道API翻译
		StringBuffer httpurl = new StringBuffer("https://fanyi-api.baidu.com/api/trans/vip/translate?");
		httpurl.append("q=" + URLEncoder.encode(q, "utf-8"));
		httpurl.append("&from=" + from);
		httpurl.append("&to=" + to);
		httpurl.append("&salt=" + salt);
		httpurl.append("&appid=" + appid);
		httpurl.append("&sign=" + sign);
		System.out.println(httpurl.toString());
		URL url = new URL(httpurl.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		StringBuilder res = new StringBuilder();
		if (conn.getResponseCode() == 200)
		{
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String bufString = "";
			while ((bufString = br.readLine()) != null && !bufString.isEmpty())
			{
				res.append(bufString);
			}
		}
		Gson gson = new Gson();
		//添加到数据库
//		if(!res.toString().contains("error_code") && addToDB)
//			wordsDAO.addWord(res.toString());
		System.out.println(res.toString());
		resp.getWriter().println(gson.fromJson(res.toString(), JsonObject.class).toString());
		httpurl.setLength(0);
	}



//    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
//        'e', 'f' };
//
//    public static String md5(String input) {
//        if (input == null)
//            return null;
//
//        try {
//            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            // 输入的字符串转换成字节数组
//            byte[] inputByteArray = input.getBytes("utf-8");
//            // inputByteArray是输入字符串转换得到的字节数组
//            messageDigest.update(inputByteArray);
//            // 转换并返回结果，也是字节数组，包含16个元素
//            byte[] resultByteArray = messageDigest.digest();
//            // 字符数组转换成字符串返回
//            return byteArrayToHex(resultByteArray);
//        } catch (NoSuchAlgorithmException e) {
//            return null;
//        } catch (UnsupportedEncodingException e)
//		{
//        	e.printStackTrace();
//        	return null;			
//		}
//    }
//
//    private static String byteArrayToHex(byte[] byteArray) {
//        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
//        char[] resultCharArray = new char[byteArray.length * 2];
//        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
//        int index = 0;
//        for (byte b : byteArray) {
//            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
//            resultCharArray[index++] = hexDigits[b & 0xf];
//        }
//
//        // 字符数组组合成字符串返回
//        return new String(resultCharArray);
//
//    }
    
    
    public static String md5(String string)
	{
		if (string == null)
		{
			return null;
		}
		char hexDigits[] =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };

		try
		{
			byte[] btInput = string.getBytes("utf-8");
			/** 获得MD5摘要算法的 MessageDigest 对象 */
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			/** 使用指定的字节更新摘要 */
			mdInst.update(btInput);
			/** 获得密文 */
			byte[] md = mdInst.digest();
			/** 把密文转换成十六进制的字符串形式 */
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md)
			{
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e)
		{
			return null;
		}
	}
}
