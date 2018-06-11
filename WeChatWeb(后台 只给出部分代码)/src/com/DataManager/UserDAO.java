package com.DataManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.org.apache.bcel.internal.generic.NEW;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

public class UserDAO
{
	private MongoDatabase db = DBManager.init();
	private MongoCollection<Document> userCollection = db.getCollection("user");

	public List<String> getTags(String _id)
	{
		List<String> tags = new ArrayList<String>();
		Document userDoc = userCollection.find(eq("_id", new ObjectId(_id))).projection(
				include("tags")).first();
		if (userDoc != null && !userDoc.isEmpty())
		{
			tags = userDoc.get("tags", List.class);
		}
		return tags;
	}
	
	public void editTags(List<String> tags, String _id)
	{
		userCollection.updateOne(eq("_id", new ObjectId(_id)), new Document("$set", 
				new Document("tags", tags)));
	}
	
	public void addUser(String openid, String session_key, List<String> tags)
	{
		//TODO
		userCollection.insertOne(new Document("openid", openid)
		.append("session_key", session_key)
		.append("tags", tags));
	}
	
	public Document findUser(ObjectId _id)
	{
		Document userDoc = userCollection.find(eq("_id", _id)).first();
		if(userDoc.isEmpty() || userDoc == null)
			return null;
		return userDoc;
	}
	
	public Document findUser(String openid)
	{
		Document userDoc = userCollection.find(eq("openid", openid)).first();
		if(userDoc == null || userDoc.isEmpty())
			return null;
		return userDoc;
	}
	
	public String getId(String openid)
	{
		Document userDoc = this.findUser(openid);
		if(userDoc != null)
			return userDoc.get("_id", ObjectId.class).toString();
		return "";
	}

	public void updateSessionKey(String session_key, String openid)
	{
		userCollection.updateOne(eq("openid", openid), 
				new Document("$set", new Document("session_key", session_key)));
		
	}
}
