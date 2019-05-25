package team.uavdetectors.dao;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import team.uavdetectors.factory.DateMethod;
import team.uavdetectors.pojo.RugularBackupData;

public class MongoDBDriver {
	private Logger log = Logger.getLogger(MongoDBDriver.class);
	
	private String date = null;
	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;
	private MongoCollection<Document> collection = null;
	
	private String dataBaseURL = "localhost";
	private int dataBasePort = 27017;
	private String dataBaseName = "UAVDetectorsDB";
	private String collectionPrefix = "UAV";
	
	public MongoDBDriver() {
		mongoClient = new MongoClient(dataBaseURL , dataBasePort);
		mongoDatabase = mongoClient.getDatabase(dataBaseName);
		date = DateMethod.getDate();
		collection = mongoDatabase.getCollection(collectionPrefix + date);
	}
	
	public void insertRBD(RugularBackupData RBD) {
		checkDate();
		collection.insertOne(RBD.toDocument());
		log.debug(RBD);
	}
	public void select(long startTime,long endTime) {
		//TODO 完成时间区间查询
	}
	public void selectAll() {
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while(mongoCursor.hasNext()){
			System.out.println(mongoCursor.next());
			//TODO mongoCursor -> json中数组形式并return
		}
	}
	public void delete(String projectName) {
		collection.deleteMany(Filters.eq("projectName",projectName));
	}
	public void close() {
		mongoClient.close();
		mongoDatabase = null;
		collection = null;
	}
	
	private void checkDate() {
		String nowDate = DateMethod.getDate();
		if(date != nowDate) {
			date = nowDate;
			collection = mongoDatabase.getCollection(collectionPrefix + date);
		}
	}
}
