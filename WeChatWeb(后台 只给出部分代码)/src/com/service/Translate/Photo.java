package com.service.Translate;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
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

public class Photo extends HttpServlet
{
			/*
		换成你的appkey和screat 

	 */
	private static final String appKey = ".......";
	private static final String screat = "......";

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
		factory.setSizeThreshold(1024 * 1024 * 50);
		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(1024 * 1024 * 40);

		try
		{
			List<FileItem> formItems = upload.parseRequest(req);
			if (formItems != null && formItems.size() > 0)
			{
				double height = 0;
				double width = 0;
				String from = "";
				String to = "";
				//from-data
				for(FileItem item : formItems)
				{
					if (item.isFormField())
					{
						String name = item.getFieldName();
						String value = item.getString();
						System.out.println(name + " " + value);
						if(name.equals("height"))
						{
							height = Integer.parseInt(value);
						}
						else if(name.equals("width"))
						{
							width = Integer.parseInt(value);
						}
						else if(name.equals("_from"))
						{
							from = value;
						}
						else if(name.equals("to"))
						{
							to = value;
						}
					}
				}
				
				for (FileItem item : formItems)
				{
					if (!item.isFormField())
					{
						
						
						// 压缩图片，由于个人原因这部分代码就不给出来了，要是图片够小不压缩也可以
						/*
							压缩图片代码
							.......
							.......
							.......
							.......

						 */
					    
					    //获取信息
					    if(from == null || from.length() <=0)
					    	from = "auto";
					    if(to == null || to.length() <=0)
					    	to = "en";
					    
						byte[] data = null;

//						InputStream itemIn = item.getInputStream();
						// item.write(new File("D://2.jpg"));
						data = new byte[itemIn.available()];
						itemIn.read(data);
						String httpurl = makeURL(Base64.encode(data), from, to);
						System.out.println(httpurl);
						// String img = req.getParameter("img");
						// String httpurl =
						// makeURL(Base64.encode(img.getBytes()));
						URL url = new URL(httpurl);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("POST");
						conn.connect();
						StringBuilder res = new StringBuilder();
						System.out.println(conn.getResponseCode());
						if (conn.getResponseCode() == 200)
						{
							InputStream resIn = conn.getInputStream();
							BufferedReader br = new BufferedReader(
									new InputStreamReader(resIn, "utf-8"));
							String bufString = "";
							while ((bufString = br.readLine()) != null
									&& !bufString.isEmpty())
							{
								res.append(bufString);
							}
						}
						Gson gson = new Gson();
						System.out.println(gson.toJson(res.toString()));
						System.out.println(res);
						resp.getWriter().println(res);
						System.out.println(bfImage.getHeight()+ " " + bfImage.getWidth());
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

	public static String makeURL(String imgBase64, String from, String to)
			throws UnsupportedEncodingException
	{
//		Random random = new Random();
//		random.nextInt(9999999);
		Date date = new Date();
		String salt = new Long(date.getTime()).toString() ;

		StringBuilder httpurl = new StringBuilder(
				"https://openapi.youdao.com/ocrtransapi?");
		httpurl.append("q=" + URLEncoder.encode(imgBase64, "UTF-8"));
		httpurl.append("&type=1");
		httpurl.append("&from=" + from);
		httpurl.append("&to=" + to);
		httpurl.append("&appKey=" + appKey);
		httpurl.append("&salt=" + salt);
		httpurl.append("&sign=" + md5(appKey + imgBase64 + salt + screat));
		httpurl.append("&docType=json");
		return httpurl.toString();
	}

}
