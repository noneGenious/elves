package com.service.Translate;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.bson.io.OutputBuffer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class Audio extends HttpServlet
{
	/*
		换成你的appkey和screat 

	 */
	private static final String appKey = "your appKey";
	private static final String screat = "your screat";
	
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
		resp.setContentType("text/json");
		if (!ServletFileUpload.isMultipartContent(req))
		{
			HashMap<String, String> hmap = new HashMap<String, String>();
			hmap.put("error", "Not File");
			Gson gson = new Gson();
			resp.getWriter().println(gson.toJson(hmap));
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 4);
		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(1024 * 1024 * 40);

		
		try
		{
			List<FileItem> formItems = upload.parseRequest(req);
			if (formItems != null && formItems.size() > 0)
			{
				for (FileItem item : formItems)
				{
					if (!item.isFormField())
					{
						String _id = formItems.get(0).getString();
						String from = formItems.get(1).getString();
						String to = formItems.get(2).getString();
						System.out.println(_id);
						System.out.println(from);
						System.out.println(to);
						String uploadPath = "D:"+ File.separator + _id;
						File path = new File(uploadPath);
						if(!path.exists())
						{
							path.mkdir();
						}
						

						InputStream itemIn = item.getInputStream();
						byte[] mp3Data = new byte[itemIn.available()];
						itemIn.read(mp3Data);
						File src2mp3 = new File(uploadPath + File.separator +  "src2mp3." + item.getFieldName());
						File src2wav = new File(uploadPath + File.separator +  "src2wav.wav");
						FileOutputStream src2mp3Out = new FileOutputStream(src2mp3);
						mp3Data = Base64.encode(mp3Data).getBytes();
						src2mp3Out.write(mp3Data);
						src2mp3Out.flush();
						src2mp3Out.close();
						
						//mp3 2 wav
				        AudioAttributes audio = new AudioAttributes();  
				        audio.setBitRate(128000);
				        audio.setChannels(1);  
//				        audio.setCodec("libmp3lame");
				        audio.setSamplingRate(16000);  
				        EncodingAttributes attrs = new EncodingAttributes();  
				        attrs.setFormat("wav");  
				        attrs.setAudioAttributes(audio);  
				        Encoder encoder = new Encoder();  
				        encoder.encode(src2mp3, src2wav, attrs); 
				        
				        //调用API
				        FileInputStream fin = new FileInputStream(src2wav);
				        byte[] data = new byte[fin.available()];
				        fin.read(data);
				        						
						String httpurl = makeURL(Base64.encode(data), from, to);
						System.out.println(httpurl);
//						String img = req.getParameter("img");
//						String httpurl = makeURL(Base64.encode(img.getBytes()));
						URL url = new URL(httpurl);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.connect();
						StringBuilder res = new StringBuilder();
						System.out.println(conn.getResponseCode());
						if (conn.getResponseCode() == 200)
						{
							InputStream resIn = conn.getInputStream();
							BufferedReader br = new BufferedReader(new InputStreamReader(resIn, "utf-8"));
							String bufString = "";
							while ((bufString = br.readLine()) != null && !bufString.isEmpty())
							{
								res.append(bufString);
							}
						}
						Gson gson = new Gson();
						System.out.println(gson.toJson(res.toString()));
						System.out.println(res);
						resp.getWriter().println(res);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println();
		}
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

	public static String makeURL(String q, String from, String to) throws UnsupportedEncodingException 
	{
		Random random = new Random();
		random.nextInt(9999999);
		String salt = new Integer(random.nextInt(9999999)).toString();
		
		StringBuilder httpurl = new StringBuilder("https://openapi.youdao.com/ocrapi?");
		httpurl.append("q=" + URLEncoder.encode(q, "UTF-8"));
		httpurl.append("from=" + from);
		httpurl.append("to=" + to);
		
		httpurl.append("&appKey=" + appKey);
		httpurl.append("&salt=" + salt);
		httpurl.append("&sign=" + md5(appKey + q + salt + screat));
		httpurl.append("format=wav");
		httpurl.append("rate=" + 16000);
		httpurl.append("channel=" + 1);
		httpurl.append("type=" + 1);

		
		return httpurl.toString();
	}

}
