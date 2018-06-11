package com.service.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DataManager.DBManager;
import com.DataManager.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


public class EditTags extends HttpServlet
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
		String tags = req.getParameter("tags");
		Gson gson = new Gson();
		List<String> tagsList = gson.fromJson(tags, List.class);
		String _id = req.getParameter("_id");
		UserDAO userDAO = new UserDAO();
		userDAO.editTags(tagsList, _id);
	}
	
}
