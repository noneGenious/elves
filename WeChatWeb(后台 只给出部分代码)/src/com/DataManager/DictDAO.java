package com.DataManager;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.print.Doc;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.org.apache.bcel.internal.generic.NEW;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;



public class DictDAO
{
	private MongoDatabase db = DBManager.init();
	private MongoCollection<Document> dictCollection = db.getCollection("dict");
	private MongoCollection<Document> wordsCollection = db
			.getCollection("words");


	public String getDict(String user_id)
	{
		//获取单词本
		//简单的查询操作
		//由于个人原因这部分代码不贴
		//

		return dict.toString();
	}

	public void addDict(String user_id)
	{
		//向单词本中添加单词
	}

	public boolean addWord(String word, String user_id)
	{
		//获取单词本
		//简单的添加操作
		//由于个人原因这部分代码不贴
		//
		return true;
	}

	public void delWord(String word_id, String user_id)
	{
		//从单词本中移除单词
	}

	public int getDictSize(String user_id)
	{
		//获取单词本容量

		return size;
	}

}
