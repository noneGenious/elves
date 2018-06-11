package com.service.News;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DBManager;
import com.DataManager.NewsDAO;
import com.google.gson.Gson;


public class NewsInfo extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String _id = req.getParameter("_id");
		
		NewsDAO newsDAO = new NewsDAO();
		resp.getOutputStream().println(newsDAO.getNewsInfo(_id));
		
		
		
	}

}
