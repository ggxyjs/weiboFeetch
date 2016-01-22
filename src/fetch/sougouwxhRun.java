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
//ץȡweixinurl�е�΢�ź�����


public class sougouwxhRun {
	
	static WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_9);

	static List<String[]> season = new ArrayList<String[]>() ;
	static boolean flag = false;//�Ƿ����ùؼ��ʹ���
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
			System.out.println("page is null,��ip"+page);
			try {
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
			
		Document doc = Jsoup.parse(page.asXml());
		String title = doc.title();
		
		System.out.println("doc��"+doc.title());
		
		if(title.indexOf("��������֤��")!=-1){
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
					
					name = name.replaceAll("'", "��");
					
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
					System.out.println("insert��ҵ�to���أ�"+name);
											
									
										
							
			
				}	
					
			}
		}
	}
	
	public static void getSeason() {
		//String regEx="פ|����|��|�Ļ�|��|�⿪|�ٻ�|��|����|����|����|��|����|��|�|��ϵ|��ҵ|��|����|����|����|VIP|����|����|��������|���|������˹|����|���|��|��Լ|��|ר��|��|����	|��";
	
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
		//String regEx="פ|����|��|�Ļ�|��|�⿪|�ٻ�|��|����|����|����|��|����|��|�|��ϵ|��ҵ|��|����|����|����|VIP|����|����|��������|���|������˹|����|���|��|��Լ|��|ר��|��|����	|��";
	

		for(String[] sea:season){
			Pattern p=Pattern.compile(sea[1]); 
		    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��
		    
		  
			if(m.find()){
				System.out.println(sea[1]);
				return sea[0];
				}
			
		}
		return "0";
		
	}
	
		public static String getSbId(String newName) {
			//String regEx="פ|����|��|�Ļ�|��|�⿪|�ٻ�|��|����|����|����|��|����|��|�|��ϵ|��ҵ|��|����|����|����|VIP|����|����|��������|���|������˹|����|���|��|��Լ|��|ר��|��|����	|��";
			String regEx1="��ҵ|����|�µ�|פ|��Ļ|����";
			String regEx2="Ԫ|����|��|��|��|��ɱ|��|˦|����|��|л|��|�ڹ���|����|�Ż�|����|�ۿ�|����|��|���|��|����|��|�ͼ�";
			String regEx3="��|����|����|����";
			String regEx5="���|����|��ף";
			String regEx6="��Ʒ|����|����|����";
			Pattern p=Pattern.compile(regEx1); 
		    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��

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


			
			String regEx1="�仧|��ҵ|����|�µ�|פ|��Ļ|����|��¼|�ǳ�|��Ӫҵ|����|��Ա";
			String regEx2="��л|����|�ж�|VIP|��|��|����|�ۿ�|��|��|��|���|��|����|��|�ͼ�|���|��|��|����|����|����|��Ʒ|����|����|����";
			String regEx3="����|Я��|�ϰ�|����|����|�ƹ�|�Ƴ�|���|��ף";
			//String regEx4="�|����|����|���˽�|���׽�|��Ϧ|ĸ�׽�|��ͯ��";
			String regEx4="�ܼ�|���³�|�ܾ���|�Ļ�|��չ|�ƺ�|����|��|����|����|����|������|����";
			String regEx5="����|ǩԼ|Э��|����|�Ӳ�|ר��|ͦ��|�����|�Ի�|����|����|��Ȩ|����|�쵼|��Ļ|��Ļ|����|����";
			String regEx6="����|����|����|�о�";
			String regEx7="ϵ��|�׷�|Ʒ��|�Ϲ�|����|�ϼ�|����|����|�¿�|��Ʒ|����|����|����|����|ϵ��";
			Pattern p=Pattern.compile(regEx1); 
		    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��
		    
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


		
		String regEx1="ʾ��|����|̬��|����|ְ��|����|����|����|����|��ʾ|����"+
		"|ӰѶ|̤��|֪ͨ|����|�ι���|���鼦��|ѧϰ|С��|��Ч|ʹ��|����"+
		"|����|ѡ��|ǩ��|����|��ע|���ϻ�|ָ��|����|����|����|����"+
		"|����|����|Ȥ��|ȫ��һ��|��¼|�����|����|��ͨ|�Ķ�|����һ��|��Ц"+
		"|��è|����һЦ|�ظ�|������|Ц��|ˢ��|����|����|�۳�|����|�ղ�"+
		"|���|ץס|�Ѿ�|�Ź�|����|�ղ�|�˽�|С��|����|����|�˿�"+
		"|��Ч|ת��|��¯|��ҵ��̬|����|�߿�|����|ת��|����|��Ȩ��|����"+
		"|�Ż�|ӵ��|ÿ��|��|С����|��Ҫ|����|��|�����Ż�|����|ʹ��"+
		"|�����ؼ�|������|����|����|����Ȧ|Ӧ|����|��ֵ|�е�|Ů��|����"+
		"|����|���һ��|��ʣ|��Ѷ|����|�ٿ�|ȯ|�齱|�ߵ�|ɹ|�ʺ�"+
		"|Ԥ��|����|ע��|����|��Ц|���|��|���|��ȫ|��|��̳"+
		"|����|�ؾ�|����|����|����|֪ʶ|����|����|ů��|΢����"+
		"|����ս��|�н�|����|��ȷ��|��ת��|ͣ��|��ʶ|����|�����|���ÿ�"+
		"|����|���|�ؼ�|�ؿ�|����|�ҽ�|װ��|����|ϰ��|����|����"+
		"|����|����|����|��|��һ��|˼��|��|��ʿ|���� |����|��׼";
		
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��
	    
	
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
					//System.out.println(newName+"�����Ѵ���");
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
				//System.out.println(newName+"�����Ѵ���");
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
				//System.out.println(newName+"�����Ѵ���");
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
				//System.out.println(newName+"�����Ѵ���");
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
				//System.out.println(newName+"�����Ѵ���");
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
       
		
		if(time.indexOf("��")!=-1){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
			Date date = new Date();
			
			time = (date.getYear()+1900)+"��"+time;
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