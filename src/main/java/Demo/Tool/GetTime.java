package Demo.Tool;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
public class GetTime {
	
	public String gettime(){
		
		//��ȡʱ��(String)
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
		String shijian = dateFormat.format(now); 
		return shijian;
	}
	
	public String Gettime(){

		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
		String year = String.valueOf(c.get(Calendar.YEAR)); 
		String month = String.valueOf(c.get(Calendar.MONTH)+1); 
		String date = String.valueOf(c.get(Calendar.DATE)); 
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY)); 
		String minute = String.valueOf(c.get(Calendar.MINUTE)); 
		String second = String.valueOf(c.get(Calendar.SECOND)); 
		String shijian = year+month+date+hour+minute+second;
		return shijian;
	}
	
	public int getsinglevalue(String value){
		
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND);
		
		switch (value) {
		case "year":
			return year;
		case "month":
			return month;
		case "day":
			return date;
		case "hour":
			return hour;
		case "minute":
			return minute;
		case "second":
			return second;
		default:
			return 404;
		}	
	}

	public long pasttime(Date a){
		long time = 0;

		Date b = new Date();

		time = (b.getTime() - a.getTime())/1000;

		return time;
	}

}
