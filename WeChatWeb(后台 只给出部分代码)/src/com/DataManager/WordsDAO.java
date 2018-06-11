package com.DataManager;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class WordsDAO
{
	private MongoDatabase db = DBManager.init();
	private MongoCollection<Document> wordsCollection = db.getCollection("words");
	
	public void addWord(String wordInfo)
	{
		//TODO
		Gson gson = new Gson();
		Document doc = gson.fromJson(wordInfo, Document.class);
//		System.out.println(doc.toJson());
		if(doc != null)
			wordsCollection.insertOne(doc);
	}
	
	public String findWord_B(String word)
	{
		Document doc = wordsCollection.find(eq("trans_result.src", word)).first();
		if(doc == null)
			return null;
		return doc.toJson();
	}
	
	public String findWord_Y(String word)
	{
		//数据库中查找单词
		return doc.toJson();
	}
}
