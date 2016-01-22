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
		
		Map<String, String> fWord = new HashMap<String, String>();//����
		
		fWord.put("1", "��פ|��פ|��ҵ|��Ļ|����|�չ�|Ͷ��|����|�ղ�|����");
		fWord.put("2", "���|���|����|����|����|�滮|����|չʾ|����");	
		//fWord.put("3", "�Ƽ�|��Ϣ��|O2O|ǰ��|�ȷ�|����|�ǻ�");
		fWord.put("4", "��ҵ|��ҵ||��ģ|�г�|����|ģʽ|����|����|����|չ��|����|��̳|�˲�|����|ת��");
		fWord.put("5", "Ӫ���߻�|����İ�|����|����|����|����|����|�");
		fWord.put("6", "����|���ҷ�̳|��ʦ");
		//fWord.put("7", "����|������|�͵���|��Ʒ����|���ŷ���|����");
		//fWord.put("8", "����|����|����|�۵�|����|�ɻ�|����|Ƶ��");
		//fWord.put("9", "����|����|�ݶ�|���۶�|���۶�|��ֵ|ҵ��|Ʊ��|Ӫҵ��");
		Map<String, String> mainWords = new HashMap<String, String>();//����
		
		//mainWords.put("6059", "��վ;2;4;8;9|����;2;4;8;9|������;2;4;8;9|��ҳ;2;4;8;9|��Ƶ��վ;2;4;8;9|������վ;2;4;8;9");
		mainWords.put("6040", "����ʦ;2|����;2|�����滮;2|�������;2|�ռ����;2|С��;2|С��;2|��ҵ����;2|��ҵ����;2|��ҵ���;2|����;2|�Ļ��ز�;1;2;4|���εز�;1;2;4|��ҵ�ز�;1;2;4|���ڵز�;1;2;4|���ϵز�;1;2;4|��ҵ�ز�;1;2;4|��ҵ�ز�;1;2;4|������ҵ;1;2;4|������;1;2;4|���ز�;5;6|�ز����;5��6|�������;5��6");
		//mainWords.put("6029", "����;4;9|�Ƶ�;1;2;3;4|���þƵ�;1;2;3;4|��Ʒ�Ƶ�;1;2;3;4|ס��ҵ;1;2;3;4|�߶˾Ƶ�;1;2;3;4|�Ƶ꼯��;1;2;3;4|�Ƶ�ͷ�;2;3|�Ƶ����;2;3");
		//mainWords.put("6039", "����;2;3;4;7;9|�ҵ�;2;3;4;7;9|����;2;3;4;7;9|������;2;3;4;7;9|����;2;3;4;7;9|�յ�;2;3;4;7;9|����������;2;3;4;7;9|��ˮ��;2;3;4;7;9|�׼������;2|�����ٱ�;2");
		//mainWords.put("6032", "��ױƷ;4;7|������Ʒ;4;7|ҩ��;4;7|���;4;7|�������;4;7|�������;4;7|��ҵ;4;7|����ҵ;4;7|��ױƷר��;2");
		//mainWords.put("3402", "�ٻ�;1;2;3;4;6;7;9|������˹;1;2;3;4;6;7;9|�߶˳���;1;2;3;4;6;7;9|�Ҿ�����;1;2;3;4;6;7;9|���ʳ���;1;2;3;4;6;7;9");
		//mainWords.put("6038", "��װ;1;3;4;7|Ůװ;1;3;4;7|ͯװ;1;3;4;7|�˶�װ;1;3;4;7|ְҵװ;1;3;4;7|����װ;1;3;4;7|������Ʒ;1;3;4;7|���;1;3;4;7|��ñ;1;3;4;7|��ʱ��;1;3;4;7|���γ���;2|��ñ����;2|�����;2|���ʦƷ��;2|��װƷ�Ƽ��ϵ�;2|�׼������;2|�����ٱ�;2|");
		//mainWords.put("6079", "�Ļ���ѵ;3;4|�Ļ�����;3;4|ְҵ��ѵ;3;4|��ͯ��ѵ;3;4|������ѵ;3;4|������ѵ;3;4");
		//mainWords.put("3487", "����;1;2;3;4;7|�͹�;1;2;3;4;7|����;1;2;3;4;7|�������;1;2;3;4;7|���;1;2;3;4;7|������;1;2;3;4;7|��Ʒ;1;2;3;4;7|�決;1;2;3;4;7|��ʳ�㳡;1;2;3;4;7");
		//mainWords.put("3537", "�Ļ�����;1;2;4|������Ʒ;1;2;4|�ղ�;1;2;4|�Ļ���ý;1;2;4|�Ļ�;1;2;4|��������;1;2;4|��������;1;2;4|�����ƾ�;1;2;4|˽�˲�Ʒ;1;2;4|�����ҵ;1;2;4|����;1;2;4|��������;1;2;4|��洴��;1;2;4|ȫ����;1;2;4|�����;1;2;4|���������;1;2;4|�Ӿ�Ӫ��;2|��ữӪ��;2|���ֻ�Ӫ��;2|���ֻ�����;2|΢Ȥ��;2|");
		//mainWords.put("6092", "�鱦;2;3;4|����;2;3;4|�ӱ�;2;3;4|����;2;3;4|�ֱ�;2;3;4|�۾�;2;3;4|�鱦չ��;2|�۾�չ��;2|�ֱ�չ��;2");
		//mainWords.put("6034", "Ӳ��;3|�Ҿ�;3|�豸;3|������;3|����;3|����;3|��ҵ;3|ҽ��;3|��ͨ;3|�칫;3|����;3|��ȫ;3|����;3|����;3|�̳�;3");
		//mainWords.put("6037", "��ҵ;5;8|��Ͷ;5;8|��ʼ��;5;8|����;5;8|ÿ�շ���Ͷ��;5;8");
		//mainWords.put("6042", "��չҵ;4;6|����ҵ;4;6|����ҵ;4;6|����ҵ;4;6|����ҵ;4;6");
		//mainWords.put("6054", "����;3;4|��������;3;4|��ϴ����;3;4|��������;3;4|���ҵ����;3;4|��������;3;4|��ͥ����;3;4|���ܼҾ�;3;4|�����������;3;4|������;3;4|������ҵ;4|���ʵ���;4|��������;4|�����г�;4|�Ӽҷ���;4|");
		
		
		Iterator iter = mainWords.entrySet().iterator();
		while (iter.hasNext()) {
			
			Entry entry = (Entry) iter.next();
			String id = (String) entry.getKey();//6059
			String words = (String) entry.getValue();//��վ;2;4|����;4
			
			String[] word = words.split("\\|");
			
			for(int i=0;i<word.length;i++){
				String  keyWord[] = word[i].split(";"); //��վ;2;4
				String  rWord = keyWord[0];//��վ
				
				for(int j=1;j<keyWord.length;j++){
					String rId = keyWord[j];//2
					
					String rFword = fWord.get(rId);//���|���
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
		String url = "http://weixin.sogou.com/weixin?type=2&num=10&query="+toUtf8String(newName)+"&tsn=1";//ץȡ������Ϣ
		//String url = "http://weixin.sogou.com/weixin?type=2&num=100&query="+toUtf8String(newName)+"";//ץȡ��ʷ��Ϣ

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
			System.out.println("page is null,��ip"+page);

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
			
				name = name.replaceAll("'", "��");

					
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
	    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��?
	    
	
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