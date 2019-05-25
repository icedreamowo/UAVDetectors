package team.uavdetectors.factory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateMethod {
	public static String getTime(){
		return getCTMToDateTime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
	}
	public static String getDate() {
		return getCTMToDateTime(System.currentTimeMillis(),"yyyy-MM-dd");
	}
	public static String getCTMToDateTime(long currentTime,String formatString){
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		Date date = new Date(currentTime);
		return formatter.format(date);
	}
	
	public static double rD() {	//马上删
		Random r = new Random();
		return r.nextDouble()*100;
	}
}
