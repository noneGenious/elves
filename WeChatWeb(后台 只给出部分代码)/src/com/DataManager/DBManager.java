package com.DataManager;

import java.util.ArrayList;



import com.Bean.News;
import com.Bean.Word;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBManager
{
	public static MongoDatabase init()
	{
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDB = mongoClient.getDatabase("CDNews");
		return mongoDB;
	}



	
	
	

}
