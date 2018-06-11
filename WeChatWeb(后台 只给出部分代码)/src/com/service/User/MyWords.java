package com.service.User;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.Word;
import com.DataManager.DBManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class MyWords extends HttpServlet
{

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
		resp.setContentType("text/json");
//		String openid = req.getParameter("openid");
//		ArrayList<Word> words = DBManager.words(openid);
//		
		Gson gson = new Gson();
//		resp.getWriter().print(gson.toJson(words));
		Word word = new Word("we", "succeed");
		System.out.println(gson.toJson(word));
		
//		JsonObject json = new JsonObject();
//		json.addProperty("test", false);
//		json.addProperty("ewr", "sdfa");
//		System.out.println(json.toString());
		String string = "{\"wrod\":\"we\",\"translate\":\"succeed\"}";
		resp.getWriter().print("{\"wrod\": \"654654\"}");
	}
	
}
