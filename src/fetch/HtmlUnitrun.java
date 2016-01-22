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


public class HtmlUnitrun {
	
	static WebClient client = new WebClient();

	
	public static void main(String[] args) {
		
		String url = "http://weixin.sogou.com/gzh?openid=oIWsFt3uofDsHhO87egwgtSYRYfo";
		System.out.println("抓取物业"+url);
		
			
		fetch("1","1",url);

		/*DBManager1 dbm1 = new DBManager1();
		ResultSet rs = null;
		
		String sql = "SELECT id,name FROM cre__property limit 635,100000";
		
		rs = dbm1.retrieveByStmt(sql);
		try {
			int x =0;
			while(rs.next()){
				
				
				String id = rs.getString(1);
				String name = rs.getString(2);
				System.out.println(x+"抓取物业"+id+name);
			
				name =name.replaceAll("'", "‘");
				String url = "http://weixin.sogou.com/gzh?openid=oIWsFt-ohz5FhiffaeM8gi3hl7HY";
				System.out.println("抓取物业"+url);
				Document doc = null;
				
					
				fetch(id,name,url);

					x++;
				}
			}

		 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */

	}
	
	
	public static void fetch(String id,String pname,String url){
		
		DBManager dbm = new DBManager();

		Document doc = null;
		try {
		   	HtmlUnitrun hut = new HtmlUnitrun();       

       HtmlPage page = client.getPage(url);
       page =  ((HtmlElement) page.getElementById("wxmore").getElementsByTagName("a").get(0)).click();
       page =  ((HtmlElement) page.getElementById("wxmore").getElementsByTagName("a").get(0)).click();
       page =  ((HtmlElement) page.getElementById("wxmore").getElementsByTagName("a").get(0)).click();
       
       //doc = Jsoup.parse(page.asXml());
    	System.out.println(page.asText());
		} catch (IOException e) {

			e.printStackTrace();
		}

		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbm.close();
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