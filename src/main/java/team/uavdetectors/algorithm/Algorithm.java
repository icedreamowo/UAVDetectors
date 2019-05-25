package team.uavdetectors.algorithm;

import team.uavdetectors.pojo.BaseStationData;
import team.uavdetectors.pojo.CoordinateData;

public interface Algorithm {
	public CoordinateData calculate(BaseStationData baseStationData);
}
