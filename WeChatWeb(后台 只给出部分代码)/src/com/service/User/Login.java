package com.service.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import sun.nio.cs.ext.ISCII91;

import com.DataManager.DictDAO;
import com.DataManager.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Login extends HttpServlet
{
	/*
		换成你的appid和screat 

	 */
	private final String appid = ".........";
	private final String secret = "................";
	
	
	
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
		UserDAO userDAO = new UserDAO();
		String js_code = req.getParameter("js_code");
		System.out.println(js_code);
		String _id = req.getParameter("_id");
		PrintWriter out = resp.getWriter();
		JsonObject json = new JsonObject();
		
		if(_id == null || _id.isEmpty())
		{
			json = this.register(js_code);
		}
		else 
		{
			Document userDoc = userDAO.findUser(new ObjectId(_id));
			if(userDoc == null)
			{
				json = this.register(js_code);				
			}
		}

		out.println(json.toString());
		out.flush();
		
	}
	
	/*
		调用微信提供的API获取用户的openID等信息，返回请求结果
	 */
	public JsonObject register(String js_code) throws IOException
	{
		
		
		
		return null;
	}

}
