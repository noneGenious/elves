package com.service.Dict;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DictDAO;
import com.google.gson.JsonObject;

public class AddToDict extends HttpServlet
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
		String user_id = req.getParameter("user_id");
		String word_id = req.getParameter("word_id");
		PrintWriter out = resp.getWriter();

		DictDAO dictDAO = new DictDAO();
		boolean result = dictDAO.addWord(word_id, user_id);

		JsonObject json = new JsonObject();
		json.addProperty("result", result);
		out.println(json.toString());
		out.flush();
	}

}
