package team.uavdetectors.algorithm;


import team.uavdetectors.factory.Factory;
import team.uavdetectors.pojo.BaseStationData;
import team.uavdetectors.pojo.CoordinateData;
import team.uavdetectors.pojo.DataType;

public class TOALocationAlgorithm implements Algorithm{
	public TOALocationAlgorithm() {}
	public CoordinateData calculate(BaseStationData baseStationData) {
		return Factory.getNewCD().set(DataType.DEBUG);
	}
}
