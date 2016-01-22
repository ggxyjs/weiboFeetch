package fetch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ClassDao{
	
	public static String getHdId(String newName) {
	
		String regEx1="开业|开张|新店|驻|开幕|升级";
		String regEx2="元|特卖|岁|庆|狂欢|秒杀|折|甩|抢购|献|谢|价|内购会|促销|优惠|打折|折扣|减价|抵|免费|试|体验|送|低价";
		String regEx3="赛|赛事|竞赛|大赛";
		String regEx5="庆典|周年|庆祝";
		String regEx6="新品|最新|上市|面市";
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//b为需要匹配的微博

	    int e=0;
		boolean bb=m.find();
		if(bb){
			e = 3548;
			}else {
			p=Pattern.compile(regEx2); 
			m=p.matcher(newName);
			bb=m.find();
			if(bb){
				e = 3546;
			}else {
				p=Pattern.compile(regEx3); 
				m=p.matcher(newName);
				bb=m.find();
				if(bb){
					e = 3545;
				}else {
	        			p=Pattern.compile(regEx5); 
	        			m=p.matcher(newName);
	        			bb=m.find();
	        			if(bb){
	        				e = 3549;
	        			}else {
		        			p=Pattern.compile(regEx6); 
		        			m=p.matcher(newName);
		        			bb=m.find();
		        			if(bb){
		        				e = 3550;
		        			}
		        	
		        	}}}}
		return e+"";
	}
	
	public static String getXwId(String newName) {


		
		String regEx1="落户|开业|开张|新店|驻|开幕|升级|登录|登场|试营业|开市|会员";
		String regEx2="答谢|回馈|感恩|VIP|促|惠|打折|折扣|送|减|抵|免费|试|体验|送|低价|庆典|礼|赛|赛事|竞赛|大赛|新品|最新|上市|面市";
		String regEx3="概念|携手|上榜|走秀|主题|推广|推出|庆典|庆祝";
		//String regEx4="活动|亲子|节日|情人节|父亲节|七夕|母亲节|儿童节";
		String regEx4="总监|董事长|总经理|文化|参展|称号|创建|获|布局|趋势|订货|博览会|任命";
		String regEx5="启动|签约|协议|成立|视察|专访|挺进|见面会|对话|并购|策略|股权|考察|领导|开幕|启幕|面市|亮相";
		String regEx6="策略|案例|点评|研究";
		String regEx7="系列|首发|品鉴|上柜|发布|上架|上市|上线|新款|新品|趋势|鉴赏|搭配|周年|系列";
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//b为需要匹配的微博
	    
	    int e=0;
		boolean bb=m.find();
		if(bb){
			e = 3446;
			}else {
			p=Pattern.compile(regEx2); 
			m=p.matcher(newName);
			bb=m.find();
			if(bb){
				e = 5340;
			}else {
				p=Pattern.compile(regEx3); 
				m=p.matcher(newName);
				bb=m.find();
				if(bb){
					e = 6003;
				}else {
	        			p=Pattern.compile(regEx4); 
	        			m=p.matcher(newName);
	        			bb=m.find();
	        			if(bb){
	        				e = 6001;
	        			}else {
		        			p=Pattern.compile(regEx5); 
		        			m=p.matcher(newName);
		        			bb=m.find();
		        			if(bb){
		        				e = 6000;
		        			}else{
		        				p=Pattern.compile(regEx6); 
			        			m=p.matcher(newName);
			        			bb=m.find();
			        			if(bb){
			        				e = 6004;
			        			}
		        			else{
		        				p=Pattern.compile(regEx7); 
			        			m=p.matcher(newName);
			        			bb=m.find();
			        			if(bb){
			        				e = 6005;
			        			}
		        			}
		        	
		        	}}}}}
		return e+"";
	}
	
	public static boolean isExistHd(String newName,int id) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__activity where name regexp '"+newName+"' and type_id ="+id;
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"鏂伴椈宸插瓨鍦?);
				b= true;}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return b;
		}finally{
			dbm.close();
		}
		
		
		return b;
	}
	
	public static boolean isExistXw(String newName,int id) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__news where name regexp '"+newName+"' and belong_type ="+id;
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"鏂伴椈宸插瓨鍦?);
				b= true;}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return b;
		}finally{
			dbm.close();
		}
		
		
		return b;
	}
	
}