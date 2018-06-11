package com.service.News;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.NewsDAO;

public class GetNews extends HttpServlet
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
		System.out.println("_id:" + _id + " send request");
		NewsDAO newsDAO = new NewsDAO();
		resp.getWriter().println("{\"news\":" +newsDAO.getNewsInfo(_id) + "}");
		System.out.println("succeed");
	}

}
