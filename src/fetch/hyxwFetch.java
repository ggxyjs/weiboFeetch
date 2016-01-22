 package fetch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;




public class hyxwFetch {
	
	static WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Map<String, String> fWord = new HashMap<String, String>();//副词
		
		fWord.put("1", "入驻|进驻|开业|开幕|并购|收购|投资|布局|收藏|开发");
		fWord.put("2", "设计|风格|概念|主题|布局|规划|创意|展示|体验");	
		//fWord.put("3", "科技|信息化|O2O|前沿|先锋|智能|智慧");
		fWord.put("4", "行业|产业||规模|市场|竞争|模式|报告|分析|加盟|展会|会议|论坛|人才|趋势|转型");
		fWord.put("5", "营销策划|广告文案|创作|比赛|竞赛|大赛|代理|活动");
		fWord.put("6", "名人|名家访坛|大师");
		//fWord.put("7", "零售|客流量|客单价|新品发布|新闻发布|连锁");
		//fWord.put("8", "上线|点评|故事|观点|分享|干货|孵化|频道");
		//fWord.put("9", "排名|排行|份额|零售额|销售额|产值|业绩|票房|营业额");
		Map<String, String> mainWords = new HashMap<String, String>();//主词
		
		//mainWords.put("6059", "网站;2;4;8;9|电商;2;4;8;9|互联网;2;4;8;9|网页;2;4;8;9|视频网站;2;4;8;9|购物网站;2;4;8;9");
		mainWords.put("6040", "建筑师;2|建筑;2|建筑规划;2|室内设计;2|空间设计;2|小区;2|小镇;2|商业布局;2|商业动线;2|商业组合;2|环境;2|文化地产;1;2;4|旅游地产;1;2;4|商业地产;1;2;4|金融地产;1;2;4|养老地产;1;2;4|产业地产;1;2;4|工业地产;1;2;4|社区商业;1;2;4|代理行;1;2;4|房地产;5;6|地产广告;5；6|房产广告;5；6");
		//mainWords.put("6029", "旅游;4;9|酒店;1;2;3;4|经济酒店;1;2;3;4|精品酒店;1;2;3;4|住宿业;1;2;3;4|高端酒店;1;2;3;4|酒店集团;1;2;3;4|酒店客房;2;3|酒店设计;2;3");
		//mainWords.put("6039", "数码;2;3;4;7;9|家电;2;3;4;7;9|电器;2;3;4;7;9|电热器;2;3;4;7;9|冰箱;2;3;4;7;9|空调;2;3;4;7;9|空气净化器;2;3;4;7;9|净水器;2;3;4;7;9|首家体验店;2|潮流速报;2");
		//mainWords.put("6032", "化妆品;4;7|护理用品;4;7|药房;4;7|体检;4;7|健身机制;4;7|健身会所;4;7|美业;4;7|美容业;4;7|化妆品专柜;2");
		//mainWords.put("3402", "百货;1;2;3;4;6;7;9|奥特莱斯;1;2;3;4;6;7;9|高端超市;1;2;3;4;6;7;9|家居中心;1;2;3;4;6;7;9|生鲜超市;1;2;3;4;6;7;9");
		//mainWords.put("6038", "男装;1;3;4;7|女装;1;3;4;7|童装;1;3;4;7|运动装;1;3;4;7|职业装;1;3;4;7|休闲装;1;3;4;7|户外用品;1;3;4;7|箱包;1;3;4;7|衣帽;1;3;4;7|快时尚;1;3;4;7|服饰橱窗;2|衣帽橱窗;2|箱包店;2|设计师品牌;2|服装品牌集合店;2|首家体验店;2|流行速报;2|");
		//mainWords.put("6079", "文化培训;3;4|文化教育;3;4|职业培训;3;4|儿童培训;3;4|外语培训;3;4|技能培训;3;4");
		//mainWords.put("3487", "餐厅;1;2;3;4;7|餐馆;1;2;3;4;7|餐饮;1;2;3;4;7|火锅料理;1;2;3;4;7|快餐;1;2;3;4;7|自助餐;1;2;3;4;7|甜品;1;2;3;4;7|烘焙;1;2;3;4;7|美食广场;1;2;3;4;7");
		//mainWords.put("3537", "文化艺术;1;2;4|工艺礼品;1;2;4|收藏;1;2;4|文化传媒;1;2;4|文化;1;2;4|艺术金融;1;2;4|艺术新闻;1;2;4|艺术财经;1;2;4|私人藏品;1;2;4|创意产业;1;2;4|创意;1;2;4|艺术创意;1;2;4|广告创意;1;2;4|全球创意;1;2;4|疯狂广告;1;2;4|海报广告语;1;2;4|视觉营销;2|社会化营销;2|数字化营销;2|数字化管理;2|微趣闻;2|");
		//mainWords.put("6092", "珠宝;2;3;4|轻奢;2;3;4|钟表;2;3;4|怀表;2;3;4|手表;2;3;4|眼镜;2;3;4|珠宝展柜;2|眼镜展柜;2|手表展柜;2");
		//mainWords.put("6034", "硬件;3|家居;3|设备;3|传感器;3|城市;3|社区;3|产业;3|医疗;3|交通;3|办公;3|政务;3|安全;3|养老;3|健康;3|商场;3");
		//mainWords.put("6037", "创业;5;8|创投;5;8|创始人;5;8|创新;5;8|每日风险投资;5;8");
		//mainWords.put("6042", "会展业;4;6|金融业;4;6|银行业;4;6|评估业;4;6|法律业;4;6");
		//mainWords.put("6054", "社区;3;4|家政社区;3;4|干洗社区;3;4|便利社区;3;4|快递业社区;3;4|家政服务;3;4|家庭服务;3;4|智能家居;3;4|本地生活服务;3;4|便利店;3;4|社区商业;4|生鲜电商;4|社区电商;4|外卖市场;4|居家服务;4|");
		
		
		Iterator iter = mainWords.entrySet().iterator();
		while (iter.hasNext()) {
			
			Entry entry = (Entry) iter.next();
			String id = (String) entry.getKey();//6059
			String words = (String) entry.getValue();//网站;2;4|电商;4
			
			String[] word = words.split("\\|");
			
			for(int i=0;i<word.length;i++){
				String  keyWord[] = word[i].split(";"); //网站;2;4
				String  rWord = keyWord[0];//网站
				
				for(int j=1;j<keyWord.length;j++){
					String rId = keyWord[j];//2
					
					String rFword = fWord.get(rId);//设计|风格
					String[] rFwords = rFword.split("\\|");
					
					for(int k=0;k<rFwords.length;k++){
						System.out.println("sss"+rWord+rFwords[k]);
						fetch(id,rWord+rFwords[k]);
					}
				}
			}
			
			
		}


	}
	
	
	public static void fetch(String id,String newName){
		
		
		DBManager dbm = new DBManager();
		String url = "http://weixin.sogou.com/weixin?type=2&num=10&query="+toUtf8String(newName)+"&tsn=1";//抓取当天信息
		//String url = "http://weixin.sogou.com/weixin?type=2&num=100&query="+toUtf8String(newName)+"";//抓取历史信息

		HtmlPage page = null;
		
			try {
				page = client.getPage(url);
			} catch (FailingHttpStatusCodeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		if(page==null){
			System.out.println("page is null,锟斤拷ip"+page);

		}else{
			
			
		Document doc = Jsoup.parse(page.asXml());
		
		
		if(doc!=null){
			
			Elements ul = doc.getElementsByTag("h4");
		

			for(int i = 0;i<ul.size()&&ul.get(i).getElementsByTag("a").size()>0;i++){
					
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

					
					String xw_id =ClassDao.getXwId(name);
					String bid =getBid(name);
					String newtime = "";
					if(doc.getElementsByClass("s-p").size()>i){
						newtime= doc.getElementsByClass("s-p").get(i).text();
						newtime = getTime(newtime);
					}
					//System.out.println("1"+name);
					if(!isExist(name)){
						//System.out.println("2"+name);
						String sql ="insert into cre__news_hy(name,class_id,belong,content,create_time,pid,keyword,url) "
								+" values('"+name+"','"+xw_id+"','"+newName+"','"+""+"','"+newtime+"','"+id+"','','"+link+"')";
						 dbm.insertByStmt(sql);
						// System.out.println(sql);
						 System.out.println("insert"+name);
					}
					
					
				}	
		
				dbm.close();
			}


		}

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
		
		
	
	
	public static boolean isDelete(String newName) {


		
		String regEx1="";
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//b为锟斤拷要匹锟斤拷锟轿拷锟?
	    
	
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
		String  sql1 = "select count(*) from cre__news_hy where name regexp '"+newName+"'";
		rs1 = dbm1.retrieveByStmt(sql1);
		try {
			if(rs1!=null&&rs1.next()){
			
				if(rs1.getInt(1)>0){
					//System.out.println(newName+"锟斤拷锟斤拷锟窖达拷锟斤拷");
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
				//System.out.println(newName+"锟斤拷锟斤拷锟窖达拷锟斤拷");
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
				//System.out.println(newName+"锟斤拷锟斤拷锟窖达拷锟斤拷");
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
				//System.out.println(newName+"锟斤拷锟斤拷锟窖达拷锟斤拷");
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
				//System.out.println(newName+"锟斤拷锟斤拷锟窖达拷锟斤拷");
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
       
		
		if(time.indexOf("锟斤拷")!=-1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy锟斤拷MM锟斤拷dd锟斤拷");
			Date date = new Date();
			
			time = (date.getYear()+1900)+"锟斤拷"+time;
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