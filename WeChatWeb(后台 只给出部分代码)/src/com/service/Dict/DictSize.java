package com.service.Dict;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DictDAO;
import com.google.gson.JsonObject;

public class DictSize extends HttpServlet
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
		String _id = req.getParameter("_id");
		DictDAO dictDAO = new DictDAO();
		int size = dictDAO.getDictSize(_id);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("size", size);
		
		resp.getWriter().println(jsonObject.toString());
		System.out.println(jsonObject.toString());
		
	}

}
