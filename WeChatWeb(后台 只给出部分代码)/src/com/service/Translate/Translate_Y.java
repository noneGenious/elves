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

public class Translate_Y extends HttpServlet
{
		/*
		换成你的appkey和screat 

	 */
	private static final String appKey = "...........";
	private static final String screat = "..........";

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
		}
		q =URLDecoder.decode(q, "utf-8").trim();
		
		
		// 在数据库中查找
		WordsDAO wordsDAO = new WordsDAO();
//		boolean addToDB = false;
//		if((from.equals("auto") || from.equals("en") || from.equals("EN")) && 
//				(to.equals("auto") || to.equals("zh") || to.equals("en") ))
//		{

		String json = wordsDAO.findWord_Y(q);
		if (!json.equals(""))
		{
			resp.getWriter().println(json);
			return;
		}

			
		// MD5加密
		String str = appKey + q + salt + screat;
		String sign = md5(str);

		// 有道API翻译
		StringBuffer httpurl = new StringBuffer("https://openapi.youdao.com/api?");
//		System.out.println(q);
		httpurl.append("q=" + URLEncoder.encode(q, "utf-8"));
		httpurl.append("&from=" + from);
		httpurl.append("&to=" + to);
		httpurl.append("&salt=" + salt);
		httpurl.append("&appKey=" + appKey);
		httpurl.append("&sign=" + sign);
		System.out.println(httpurl.toString());
		URL url = new URL(httpurl.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		StringBuilder res = new StringBuilder();
		System.out.println(conn.getResponseCode());
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
		System.out.println(res.toString());
		Gson gson = new Gson();
		
		//添加到数据库
		if(res.toString().contains("\"errorCode\":\"0\""))
		{
			if(!q.contains(" "))
				wordsDAO.addWord(res.toString());
		}
			
		resp.getWriter().println(res.toString());
	}

	public static String md5(String string)
	{
		if (string == null)
		{
			return null;
		}
		char hexDigits[] =
		{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };

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
