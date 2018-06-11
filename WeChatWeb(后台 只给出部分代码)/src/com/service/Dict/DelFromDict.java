package com.service.Dict;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DictDAO;

public class DelFromDict extends HttpServlet
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
		String word_id = req.getParameter("word_id");
		String user_id = req.getParameter("user_id");
		
		DictDAO dictDAO = new DictDAO();
		dictDAO.delWord(word_id, user_id);
	}

}
