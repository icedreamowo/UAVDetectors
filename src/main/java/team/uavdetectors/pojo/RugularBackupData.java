package team.uavdetectors.pojo;

import java.util.ArrayList;
import org.bson.Document;

import com.google.gson.Gson;

import team.uavdetectors.factory.Factory;

public class RugularBackupData {
	private String projectName;
	private ArrayList<Document> relayList;
	private ArrayList<Document> convergenceList;
	private ArrayList<Document> labelList;
	private long timestamp;
	
	private transient Gson gson = null;
	private transient Document document = null;
	
	public RugularBackupData(){
		relayList = new ArrayList<Document>();
		convergenceList = new ArrayList<Document>();
		labelList = new ArrayList<Document>();
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public ArrayList<Document> getRelayList() {
		return relayList;
	}
	public ArrayList<Document> getConvergenceList() {
		return convergenceList;
	}
	public ArrayList<Document> getLabelList() {
		return labelList;
	}

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void addRelayList(CoordinateData relayCoordinateData) {
		relayList.add(relayCoordinateData.toDocument());
	}
	public void addConvergenceList(CoordinateData convergenceCoordinateData) {
		convergenceList.add(convergenceCoordinateData.toDocument());
	}
	public void addLabelList(CoordinateData labelCoordinateData) {
		labelList.add(labelCoordinateData.toDocument());
	}
	
	public Document toDocument() {
		document = new Document();
		document.append("projectName", projectName)
				.append("relayList", relayList)
				.append("convergenceList", convergenceList)
				.append("labelList", labelList)
				.append("timestamp", timestamp);
		return document;
	}
	
	public void clear() {
		projectName = null;
		relayList.clear();
		convergenceList.clear();
		labelList.clear();
		timestamp = 0;
	}
	
	public String toString() {
		if(gson == null) {
			gson = Factory.getNewGson();
		}
		return gson.toJson(this);
	}
}
