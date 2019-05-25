package team.uavdetectors.pojo;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import team.uavdetectors.factory.Factory;

public class BaseStationData {
	private String sourceAddress;		//16bits
	private String sourceType;			//2bits
	private String destinitionAddress;	//16bit
	private String destinitionType;		//2bits
	private long timestamp;				//32bits 4Bytes
	private double distance;			//16bits 2Bytes
	
	private transient Gson gson = null;			//Gson类 用于序列化输出及反序列化设置
	
	public BaseStationData(){set(DataType.EMPTY);}
	
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getDestinitionAddress() {
		return destinitionAddress;
	}
	public void setDestinitionAddress(String destinitionAddress) {
		this.destinitionAddress = destinitionAddress;
	}
	public String getDestinitionType() {
		return destinitionType;
	}
	public void setDestinitionType(String destinitionType) {
		this.destinitionType = destinitionType;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public BaseStationData set(String SourceAddress,
					String SourceType,
					String DestinitionAddress,
					String DestinitionType,
					long Timestamp,
					double Distance) {
		setSourceAddress(SourceAddress);
		setSourceType(SourceType);
		setDestinitionAddress(DestinitionAddress);
		setDestinitionType(DestinitionType);
		setTimestamp(Timestamp);
		setDistance(Distance);
		return this;
	}
	public BaseStationData set(String serializationString) {
		if(gson == null) {
			gson = Factory.getNewGson();
		}
		BaseStationData newBSD = gson.fromJson(serializationString, BaseStationData.class);
		set(newBSD.getSourceAddress(),
			newBSD.getSourceType(),
			newBSD.getDestinitionAddress(),
			newBSD.getDestinitionType(),
			newBSD.getTimestamp(),
			newBSD.getDistance());
		newBSD = null;
		return this;
	}
	public BaseStationData set(int dataType) {
		switch (dataType) {
		case DataType.EMPTY:
			set("","","","",0,0);
			break;
		case DataType.DEBUG:
			set("GENERATE4DEBUG1",
				"GENERATE4DEBUG2",
				"GENERATE4DEBUG3",
				"GENERATE4DEBUG4",
				System.currentTimeMillis(),
				0);
			break;
		default:
			break;
		}
		return this;
	}
	public Document toDocument() {
		Document document = new Document("sourceAddress",getSourceAddress()).
								append("sourceType", getSourceType()).
								append("destinitionAdress", getDestinitionAddress()).
								append("destinitionType", getDestinitionType()).
								append("timestamp", getTimestamp()).
								append("distance", getDistance());
		
		return document;
	}
	public BaseStationData setFromUDPServer(String hardwareData) {				//硬件数据->BaseStationData
		//在硬件端更改传输规则后再实现
		set(DataType.DEBUG);
		return this;
	}
	public BaseStationData copy() {					//不直接使用set(String serializationString)  省去创建新的gson时间
		BaseStationData newBSD = Factory.getNewBSD().set(getSourceAddress(), 
														 getSourceType(), 
														 getDestinitionAddress(), 
														 getDestinitionType(), 
														 getTimestamp(), 
														 getDistance());
		return newBSD;
	}
	public String toString() {
		if(gson == null) {
			gson = Factory.getNewGson();
		}
		return gson.toJson(this);
	}
	
}
