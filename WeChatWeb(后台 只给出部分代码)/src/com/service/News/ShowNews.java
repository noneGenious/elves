package com.service.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.News;
import com.DataManager.DBManager;
import com.DataManager.NewsDAO;
import com.DataManager.UserDAO;
import com.google.gson.Gson;

public class ShowNews extends HttpServlet
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
		resp.setCharacterEncoding("utf-8");
		UserDAO userDAO = new UserDAO();
		NewsDAO newsDAO = new NewsDAO();

		String _id = req.getParameter("_id");
		int skip = Integer.parseInt(req.getParameter("skip"));
		System.out.println("_id:" + _id + " send request");
		List<String> tags = userDAO.getTags(_id);
		ArrayList<String> newsList = null;

		System.out.println(tags);
		 if(tags.size() <= 0)
		 {
			 newsList = newsDAO.getNews(skip);
		 }
		 else
		 {
			 newsList = newsDAO.getNews(tags, skip);
		 }
		// JSON
		System.out.println(newsList);
		resp.getWriter().println("{\"news\":" + newsList.toString() + "}");
		resp.getWriter().flush();
		System.out.println("succeed");
	}

}
