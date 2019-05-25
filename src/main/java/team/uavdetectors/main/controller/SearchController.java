package team.uavdetectors.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import team.uavdetectors.pojo.RugularBackupData;
import team.uavdetectors.snapshot.LatestCD;

@RestController
@RequestMapping(value="/uav/search")

public class SearchController {
	@RequestMapping(value="/getLatestCoordinateData",method = RequestMethod.GET)
	public RugularBackupData getLatestCD() {
		return LatestCD.getRBD();
	}
}