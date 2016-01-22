package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;
import fetch.WbFetch;


public class FetchTopicNewsServlet extends HttpServlet 
{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Charset","UTF-8");
		
		
		
		String result = "add_fail";
		int topicId = 0;
		try
		{
			System.out.println("待跟踪微博抓取开始》》》》》》》》》》》");
			StringBuffer sr = new StringBuffer();
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			crawl(topicId);
			
			Track(topicId);
			System.out.println("待跟踪微博抓取结束》》》》》》》》》》》");
			result = sr.toString();
		}
		catch (NumberFormatException e)
		{
			result = "add_fail";
			e.printStackTrace();
		}
		catch (Exception e)
		{
			result = "add_fail";
			e.printStackTrace();
		}
		finally
		{
			
			
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}
	
	private void Track(int topicId) {
		// TODO Auto-generated method stub
		
	}

	public static void crawl(int topicId){
		
		Entry[] entrys = Entry.getMaxEntry(topicId);
		
		
		String time = Entry.getMaxTime(topicId);
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Calendar c = Calendar.getInstance();
        c.setTime(d);
       // System.out.println("当前时间:" + f.format(c.getTime()));
        c.add(Calendar.DAY_OF_MONTH, 2);

        String time1 = f.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 2);

        String time2 = f.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 2);

        String time3 = f.format(c.getTime());
        
		
		for(Entry entry:entrys){
			String words = WbFetch.toUtf8String(entry.getName().replaceAll("\\[|\\]|/ns|/v|/nx|/nr|/ng|/n| ", "").replaceAll(",", "%20"));
			
			
			
			String 	url = "http://weibo.cn/search/mblog?hideSearchFrame=" +
					"&keyword=" +words+
					"&starttime=20120101&endtime="+time1+"&sort=time&page=1"; 

			try {
					WbFetch.crawl(0, url, 30);
		
					
					url = "http://weibo.cn/search/mblog?hideSearchFrame=" +
					"&keyword=" +words+
					"&starttime=20120101&endtime="+time2+"&sort=time&page=1"; 
		
					WbFetch.crawl(0, url, 30);
			
					url = "http://weibo.cn/search/mblog?hideSearchFrame=" +
					"&keyword=" +words+
					"&starttime=20120101&endtime="+time3+"&sort=time&page=1"; 
		
					WbFetch.crawl(0, url, 30);
					
			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
	}
	
	
}
