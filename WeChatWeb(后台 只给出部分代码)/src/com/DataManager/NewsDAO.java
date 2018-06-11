package com.DataManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSON;
import org.bson.Document;

import com.Bean.News;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import  org.bson.types.ObjectId; 

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.BsonField.*;


public class NewsDAO
{
	private MongoDatabase db = DBManager.init();
	private MongoCollection<Document> newsCollection = db.getCollection("news");
	
	public ArrayList<String> getNews(List<String> tags, int skip)
	{

		//简单的查询操作
		//由于个人原因这部分代码不贴
		//
		return newsList;
	}

	public ArrayList<String> getNews(int skip)
	{
		//简单的查询操作
		//由于个人原因这部分代码不贴
		//
		return newsList;
	}

	public String getNewsInfo(String _id)
	{
		Document newsDoc = newsCollection.find(eq("_id", new ObjectId(_id))).first();
		return newsDoc.toJson();
	}
	
	public String findNews(String key)
	{
		return "";
	}
}
