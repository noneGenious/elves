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

		// �����ݿ��в���
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
		// MD5����
		String str = appid + URLDecoder.decode(q, "utf-8") + salt + screat;
		System.out.println(str);
		String sign = md5(str);

		// �е�API����
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
		//��ӵ����ݿ�
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
//            // �õ�һ��MD5ת�����������ҪSHA1�������ɡ�SHA1����
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            // ������ַ���ת�����ֽ�����
//            byte[] inputByteArray = input.getBytes("utf-8");
//            // inputByteArray�������ַ���ת���õ����ֽ�����
//            messageDigest.update(inputByteArray);
//            // ת�������ؽ����Ҳ���ֽ����飬����16��Ԫ��
//            byte[] resultByteArray = messageDigest.digest();
//            // �ַ�����ת�����ַ�������
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
//        // newһ���ַ����飬�������������ɽ���ַ����ģ�����һ�£�һ��byte�ǰ�λ�����ƣ�Ҳ����2λʮ�������ַ���2��8�η�����16��2�η�����
//        char[] resultCharArray = new char[byteArray.length * 2];
//        // �����ֽ����飬ͨ��λ���㣨λ����Ч�ʸߣ���ת�����ַ��ŵ��ַ�������ȥ
//        int index = 0;
//        for (byte b : byteArray) {
//            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
//            resultCharArray[index++] = hexDigits[b & 0xf];
//        }
//
//        // �ַ�������ϳ��ַ�������
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
			/** ���MD5ժҪ�㷨�� MessageDigest ���� */
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			/** ʹ��ָ�����ֽڸ���ժҪ */
			mdInst.update(btInput);
			/** ������� */
			byte[] md = mdInst.digest();
			/** ������ת����ʮ�����Ƶ��ַ�����ʽ */
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
