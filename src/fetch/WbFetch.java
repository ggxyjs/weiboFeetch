package fetch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import crun.v3.Dao.TName;

public class WbFetch {
	
	static WebClient client = new WebClient();
//	private Website mWbst;
//
//	
//	public HtmlUnitTest(Website wbst){
//		this.mWbst = wbst;
//	}
	
	public void login() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		
    	
    	HtmlPage page = client.getPage("http://login.weibo.cn/login/");
       // System.out.println(page.asText());
       
       
        HtmlTextInput ln = (HtmlTextInput) page.getElementByName("mobile");
        page.setFocusedElement((HtmlElement)page.getElementByName("mobile"));
        page.tabToNextElement();
        HtmlPasswordInput pwd = (HtmlPasswordInput) page.tabToNextElement();
        
        ln.setText("heihaixd@126.com");
        pwd.setText("910720Yc");
       
        HtmlCheckBoxInput ckb = (HtmlCheckBoxInput) page.getElementByName("remember");
        ckb.setChecked(true);
        
        HtmlSubmitInput btn = (HtmlSubmitInput) page.getElementByName("submit");
        HtmlPage page2 = btn.click();
        
        System.out.println("\n\n\n\n\n");

       	}
	
	public void analysis(String url) throws IOException {
		Document doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(300000).get();
		
		Elements records = doc.getElementsByClass("c");
		
		if (records != null && records.size()>0) {
			for (int i = 0; i < records.size(); i++) {
				Element record = records.get(i);
				if (!record.hasAttr("id")) {
					continue;
				}
				
				//Blog blog =new Blog();
				
				String mblog = "";
        		String datetime = "";
        		int type=0;
        		String e_img = "";
        		
        		mblog = record.getElementsByClass("ctt").first().text();
        		datetime = record.getElementsByClass("ct").first().text();
        		
        		if (!record.getElementsByClass("cmt").isEmpty()) {
        			type = 1;
        		}
        		
        		if (!record.getElementsByClass("ib").isEmpty()) {
        			e_img = record.getElementsByClass("ib").attr("src");
        		}
        		
        		
        		System.out.println(mblog+" "+type+" "+datetime+" "+e_img);
//        		blog.update(this.mWbst,mblog,type,datetime,e_img);
			}
		}
	}
	
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, SQLException 
    {
    	for(int i=1;i<10;i=i+2){
        	String url = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E9%9B%85%E5%AE%89%E5%9C%B0%E9%9C%87&advancedfilter=1&starttime=20130420&endtime=" +
			"2014050"+i+"&sort=time&page=1";
        	crawl(0,url,50);
    	}
    	for(int i=10;i<30;i=i+2){
        	String url = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword=%E9%9B%85%E5%AE%89%E5%9C%B0%E9%9C%87&advancedfilter=1&starttime=20130420&endtime=" +
			"201405"+i+"&sort=time&page=1";
        	crawl(0,url,50);
    	}

    	
    }
    
    public static void crawl(int id,String furl,int num) throws FailingHttpStatusCodeException, MalformedURLException, IOException, SQLException 
    {
    	
    	
    	WbFetch hut = new WbFetch();       
        hut.login();
        DBManager dbm = new DBManager();
        int fnum = 0;
       // int mnum = 0;
        int start = Integer.parseInt(furl.substring(furl.lastIndexOf("=")+1, furl.length()));
        furl = furl.substring(0,furl.lastIndexOf("=")+1);  
        ok:
        //ResultSet rs =null;
        for(int i=start;i<20;i++){
        	
        	
        	String url =furl+i;
        	
        	System.out.println("抓取链接："+url);
        	  HtmlPage page = client.getPage(url);
        	  Document doc = Jsoup.parse(page.asXml());
        	  Elements els =  doc.getElementsByClass("ctt");
        	  System.out.println(doc);
        	  for(int j=0;j<els.size();j++){
        
        		String content = els.get(j).text();
        		
        		String time = doc.getElementsByClass("ct").get(j).text();
        		if(time.indexOf("月")!=-1){
        			time = "2015-"+time.substring(0,5).replaceAll("月", "-");
        		}else{
        			time = time.substring(0,10);
        		}
        		
        		//System.out.println(time);
        		content = content.replaceAll("'|:", "");
        		if(content.indexOf("（分享自")!=-1){
        			content = content.substring(0,content.indexOf("（分享自"));
        		}
        		String sql = "insert into "+TName.NEWS_TABLE_NAME+"(txt,tid,time) values('"+content+"',"+id+",'"+time+"') ";
        		if(!isExist(content,id)){
        			dbm.insertByStmt(sql);
        			fnum++;
        			if(fnum>=num){
        				break ok;
        			}
        			System.out.println("insert "+sql);
        		}else{
        			System.out.println("delete "+content);
        		}
        	}
        }
        
        
    }
    
    public static void BdCrawl(int id,String furl,int num) throws FailingHttpStatusCodeException, MalformedURLException, IOException, SQLException 
    {
    	
    	
    //	WbFetch hut = new WbFetch();       
       // hut.login();	
        DBManager dbm = new DBManager();
        int fnum = 0;

        ok:

        for(int i=0;i<10;i++){
        	
        	
        	String url =furl+ "&pn="+i+"00";
        		
    	  System.out.println("抓取链接："+url);
    	
    	  Document doc = Jsoup.connect(url).post();
    
    	  Elements els =  doc.getElementsByClass("result");
    	   System.out.println(doc);
    	  for(int j=0;j<els.size();j++){
    
    		String link =  els.get(j).getElementsByTag("a").get(0).absUrl("href");
    	
    		 Document linkDoc = Jsoup.connect(link).post();
    		 
    		String content = linkDoc.getElementsByTag("p").html();
    		String time = els.get(j).text();
    		//System.out.println(time);
    		content = content.replaceAll("'", "’");
    		String sql = "insert into "+TName.NEWS_TABLE_NAME+"(txt,tid,time) values('"+content+"',"+id+",'"+time+"') ";
    		
    		if(!isExist(content,id)){
    			dbm.insertByStmt(sql);
    			fnum++;
    			if(fnum>=num){
    				break ok;
    			}
    			System.out.println("insert "+sql);
    		}else{
    			System.out.println("delete "+content);
    		}
        	}
        }
        
        
    }
    
	public static boolean isExist(String s,int id) {
       boolean res = false;
       DBManager dbm = new DBManager();
       ResultSet rs =null;
        
       String sql ="select count(*) from "+TName.NEWS_TABLE_NAME+" where txt ='"+s+"' and tid ="+id;
       
       rs = dbm.retrieveByStmt(sql);
       
       try {
		if(rs.next()&&rs!=null){
			   if(rs.getInt(1)!=0){
				   res = true;
			   }
		   }
       } catch (SQLException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
       }
       
       
       
       return res;
        
        
        
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