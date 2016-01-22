package crun.v3;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;

public class Test{
	
	public static void main(String[] args){
		
		//String time = Entry.getMaxTime(topicId);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse("2014-09-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar c = Calendar.getInstance();
        c.setTime(d);
       // System.out.println("当前时间:" + f.format(c.getTime()));
        c.add(Calendar.DAY_OF_MONTH, 1);
        
        
        System.out.println("半年后:" + f.format(c.getTime()));
		
	}
}