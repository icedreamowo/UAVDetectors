package team.uavdetectors.pojo;

import org.bson.Document;
import com.google.gson.Gson;

import team.uavdetectors.factory.DateMethod;
import team.uavdetectors.factory.Factory;

public class CoordinateData {
	private String targetName;
	private double xCoordinate;
	private double yCoordinate;
	private double zCoordinate;
	private long timestamp;
	private int stationLabeltype;						//基站标签 类型
	private int dimensionType;							//坐标数据类型 即2D或是3D
	
	private transient Gson gson = null;
	
	public static final int TwoD = 2;					//标记为2D坐标
	public static final int ThreeD = 3;					//标记为3D坐标
	
	public static final int Relay = 1;					//中继基站
	public static final int Convergence = 2;			//汇聚基站
	public static final int Label = 3;					//标签
	
	public CoordinateData() {set(DataType.EMPTY);}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public double getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public double getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public double getzCoordinate() {
		return zCoordinate;
	}
	public void setzCoordinate(double zCoordinate) {
		this.zCoordinate = zCoordinate;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getStationLabeltype() {
		return stationLabeltype;
	}
	public void setStationLabeltype(int stationLabeltype) {
		this.stationLabeltype = stationLabeltype;
	}
	public int getDimensionType() {
		return dimensionType;
	}
	public void setDimensionType(int dimensionType) {
		this.dimensionType = dimensionType;
	}
	
	public CoordinateData set(String targetName,
							  double xCoordinate,
							  double yCoordinate,
							  double zCoordinate,
							  long timestamp,
							  int stationLabelType,
							  int dimensionType) {
		setTargetName(targetName);
		setxCoordinate(xCoordinate);
		setyCoordinate(yCoordinate);
		setzCoordinate(zCoordinate);
		setTimestamp(timestamp);
		setStationLabeltype(stationLabelType);
		setDimensionType(dimensionType);
		return this;
	}
	public CoordinateData set(String serializationString) {
		if(gson == null) {
			gson = Factory.getNewGson();
		}
		CoordinateData newCD = gson.fromJson(serializationString, CoordinateData.class);
		set(newCD.getTargetName(),
			newCD.getxCoordinate(),
			newCD.getyCoordinate(),
			newCD.getzCoordinate(),
			newCD.getTimestamp(),
			newCD.getStationLabeltype(),
			newCD.getDimensionType());
		newCD = null;
		return this;
	}
	public CoordinateData set(int dataType) {
		switch (dataType) {
		case DataType.EMPTY:
			set("",0,0,0,0,0,0);
			break;
		case DataType.DEBUG:
			set("GENERATE4DEBUG",
				DateMethod.rD(),DateMethod.rD(),DateMethod.rD(),
				System.currentTimeMillis(),
				Relay,ThreeD);
			break;
		default:
			break;
		}
		return this;
	}
	public Document toDocument() {
		Document document = new Document("targetName",getTargetName()).
								append("xCoordinate", getxCoordinate()).
								append("yCoordinate", getyCoordinate()).
								append("zCoordinate", getzCoordinate()).
								append("timestamp", getTimestamp()).
								append("stationLabelType", getStationLabeltype()).
								append("dimensionType", getDimensionType());
		
		return document;
	}
	public CoordinateData copy() {
		CoordinateData newCD = Factory.getNewCD().set(getTargetName(), 
													  getxCoordinate(), 
													  getyCoordinate(), 
													  getzCoordinate(),
													  getTimestamp(), 
													  getStationLabeltype(),
													  getDimensionType());
		return newCD;
	}
	
	public String toString() {
		if(gson == null) {
			gson = Factory.getNewGson();
		}
		return gson.toJson(this);
	}
}
