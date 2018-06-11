package com.service.Dict;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DictDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetDict extends HttpServlet
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
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/json;charset=utf-8");
		resp.setContentType("text/json");
		
		String _id = req.getParameter("_id");
		System.out.println(_id);
		System.out.println("**********************************");
		DictDAO dictDAO = new DictDAO();
		
		int size = dictDAO.getDictSize(_id);
		String dict = dictDAO.getDict(_id);	
		

		JsonObject json = new JsonObject();
		
		json.addProperty("dict", dict);
		json.addProperty("size", size);
		resp.getWriter().println(json.toString());
		
		
	}

	
}
