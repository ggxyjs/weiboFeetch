package fetch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
//抓取weixinurl中的微信号内容


public class sougouwxhRun {
	
	static WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);

	static List<String[]> season = new ArrayList<String[]>() ;
	static boolean flag = false;//是否启用关键词过滤
	public static void main(String[] args) {
		
		List<String> id = new ArrayList<String>();
		//List<String> name = new ArrayList<String>();
		List<String> url = new ArrayList<String>();
		
	
		getSeason();
	
		
			
			DBManager dbm1 = new DBManager();
			ResultSet rs = null;
			
			String sql = "SELECT id,url FROM weixinurl ";
			//String sql = "SELECT id,url FROM weixinurl where id =7";
			
			rs = dbm1.retrieveByStmt(sql);
			try {
				
				while(rs.next()){
					
					System.out.print(rs.getString(1));
					id.add(rs.getString(1));
					url.add(rs.getString(2));
					
					}
				}
	
			 catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 


			
				
				for(int i=0;i<id.size();i++){
					
					String pid =id.get(i);
					//String pname =name.get(i);
					String purl =url.get(i);

					fetch(pid,purl);
				}

				
		
	}
	
	
	public static void fetch(String id,String url){
		
		
		DBManager dbm1 = new DBManager();

		HtmlPage page = null;
		
			try {
				page = client.getPage(url);
			} catch (FailingHttpStatusCodeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		
		if(page==null){
			System.out.println("page is null,换ip"+page);
			try {
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			
		Document doc = Jsoup.parse(page.asXml());
		String title = doc.title();
		
		System.out.println("doc："+doc.title());
		
		if(title.indexOf("请输入验证码")!=-1){
			client = new WebClient();
			try {
				page = client.getPage(url);
			} catch (FailingHttpStatusCodeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			doc = Jsoup.parse(page.asXml());
		}
		
		
		if(doc!=null){
			
			Elements ul = doc.getElementsByTag("h4");
			
			for(int i = 1;i<ul.size();i++){
				
				
				String newtime = "";

				if(doc.getElementsByClass("s-p").size()>=i){
					
					newtime= doc.getElementsByClass("s-p").get(i-1).text();
					newtime = getTime(newtime);
				}
			

				String link = "";
				String name  ="";
				
		    	 try {
		    		 	link =ul.get(i).getElementsByTag("a").get(0).absUrl("href");
						name = ul.get(i).getElementsByTag("a").text();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					name = name.replaceAll("'", "‘");
					
					if(flag&&isDelete(name)){
						continue;
					}
					
					String season_id = getSeasonId(name);
					String subjectId = "0";
					
					
					if(season_id.equals("0")){
						subjectId = getSbId(name);
					}else{
						subjectId = "3549";
					}
					
					if(subjectId.equals("0")){
						subjectId = "3550";
						
					}
					
					String xw_id =getXwId(name);
					String bid =getBid(name);

					
					
					StringBuilder sql = new StringBuilder();
					sql.append("insert into weixinurla(name,pid,subject_id,weixinurla.desc,time,bid,pname,xw_id,url,season_id) values('")
					.append(name).append("','").append(id).append("','")
										.append(subjectId).append("','','").append(newtime)
										.append("','").append(bid).append("','")
										.append("").append("','").append(xw_id)
										.append("','").append(link).append("','").append(season_id).append("')");
										 
					
					dbm1.insertByStmt(sql.toString());
					System.out.println("insert物业活动to本地："+name);
											
									
										
							
			
				}	
					
			}
		}
	}
	
	public static void getSeason() {
		//String regEx="驻|连锁|惠|文化|招|解开|百货|折|艺术|合作|布局|试|餐饮|抵|活动|关系|商业|促|发布|趋势|零售|VIP|公益|案例|购物中心|设计|奥特莱斯|主题|租金|节|相约|销|专柜|礼|邀请	|变";
	
		DBManager1 dbm =new DBManager1();
		ResultSet rs = null;
		String sql = "SELECT linkageid,description FROM `cre_linkage` where  keyid =21 and linkageid!=5410";
		rs = dbm.retrieveByStmt(sql);
		if(rs!=null){
			try {
				while(rs.next()){
					String[] sea = {rs.getString(1),rs.getString(2)};
					season.add(sea);
					//System.out.print(rs.getString(2));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static String getSeasonId(String newName) {
		//String regEx="驻|连锁|惠|文化|招|解开|百货|折|艺术|合作|布局|试|餐饮|抵|活动|关系|商业|促|发布|趋势|零售|VIP|公益|案例|购物中心|设计|奥特莱斯|主题|租金|节|相约|销|专柜|礼|邀请	|变";
	

		for(String[] sea:season){
			Pattern p=Pattern.compile(sea[1]); 
		    Matcher m=p.matcher(newName);//b为需要匹配的微博
		    
		  
			if(m.find()){
				System.out.println(sea[1]);
				return sea[0];
				}
			
		}
		return "0";
		
	}
	
		public static String getSbId(String newName) {
			//String regEx="驻|连锁|惠|文化|招|解开|百货|折|艺术|合作|布局|试|餐饮|抵|活动|关系|商业|促|发布|趋势|零售|VIP|公益|案例|购物中心|设计|奥特莱斯|主题|租金|节|相约|销|专柜|礼|邀请	|变";
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

		public static String getBid(String newName) {
			
			DBManager1 dbm = new DBManager1();
			ResultSet rs = null;
			String bid="";
		     String sql = "SELECT id FROM `cre__brand` where  '"+newName+"' REGEXP name";
		    
		     try {
		    	 rs = dbm.retrieveByStmt(sql);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     try {
				if(rs!=null&&rs.next()){
				 	bid=rs.getString(1);
				 }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					if(rs!=null){rs.close();}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(rs!=null){try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				dbm.close();
				
			}
			
			return bid;
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
	
	public static boolean isDelete(String newName) {


		
		String regEx1="示范|星座|态度|八卦|职场|持续|旅行|保养|妙用|提示|理论"+
		"|影讯|踏青|通知|攻略|刮刮乐|心灵鸡汤|学习|小雨|有效|使用|让利"+
		"|报名|选购|签到|故事|关注|百老汇|指南|多云|反季|开心|立减"+
		"|由来|法则|趣闻|全场一折|语录|万汇网|电视|交通|阅读|轻松一刻|爆笑"+
		"|天猫|开心一笑|回复|正能量|笑话|刷卡|怎样|旅行|扣除|技巧|收藏"+
		"|最佳|抓住|已经|团购|幸运|收藏|了解|小编|网购|神马|此刻"+
		"|功效|转载|出炉|行业动态|升级|高考|积分|转发|友情|特权卡|测试"+
		"|优惠|拥有|每日|限|小妙招|需要|银行|常|更多优惠|理由|使用"+
		"|部分特价|不适宜|仅售|拜年|朋友圈|应|禁忌|超值|中等|女神|技能"+
		"|感悟|最后一天|仅剩|资讯|保护|百科|券|抽奖|高等|晒|适合"+
		"|预防|雾霾|注意|爆料|爆笑|社会|房|如何|安全|控|论坛"+
		"|立冬|秘诀|人生|出游|答题|知识|养生|发放|暖男|微生活"+
		"|最新战报|中奖|互动|正确答案|大转盘|停车|常识|方法|提货卡|信用卡"+
		"|升级|格调|特价|必看|穿搭|兑奖|装修|现象|习俗|冰箱|符合"+
		"|假日|提醒|邀请|事|哪一种|思考|章|贴士|竞猜 |姿势|标准";
		
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//b为需要匹配的微博
	    
	
		if(m.find()){
			return true;
		}else{
			return false;
		}
		
		
		
	}
	public static boolean isExist(String newName) {

		boolean b =false;
		
		DBManager dbm1 = new DBManager();
		ResultSet rs1 = null;
		String  sql1 = "select count(*) from weixinurla where name regexp '"+newName+"'";
		rs1 = dbm1.retrieveByStmt(sql1);
		try {
			if(rs1!=null&&rs1.next()){
			
				if(rs1.getInt(1)>0){
					//System.out.println(newName+"新闻已存在");
					b= true;}
			}
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return b;
		}finally{
			try {
				
				if(rs1!=null){rs1.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dbm1.close();
		
			
		}
		
		
		return b;
	}
	
	public static boolean isExistWyhd(String newName) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__activity where name regexp '"+newName+"' and type_id =1";
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存在");
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
	
	public static boolean isExistWyxw(String newName) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__news where name regexp '"+newName+"' and belong_type =2";
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存在");
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
	
	public static boolean isExistPphd(String newName) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__activity where name regexp '"+newName+"' and type_id =2";
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存在");
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
	
	public static boolean isExistPpxw(String newName) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__news where name regexp '"+newName+"' and belong_type =3";
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存在");
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
	
	public static String getTime(String time) {
       
		
		if(time.indexOf("日")!=-1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			Date date = new Date();
			
			time = (date.getYear()+1900)+"年"+time;
			try {
				time = (sdf.parse(time).getTime()+"").substring(0,10);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			time = (new Date().getTime()+"").substring(0,10);
		}
		
        return time;
    }
	
	public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    //System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    
    
    
}