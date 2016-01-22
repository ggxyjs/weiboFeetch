package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.EntrySyn;
import crun.v3.WordEntry;
import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.weightCal;
import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.Word;
import fetch.WbFetch;


public class EntryEvolveServlet extends HttpServlet 
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
		
		//System.out.println("evl");
		
		String result = "add_fail";
		int id = 0;
		try
		{
		
			StringBuffer sr = new StringBuffer();
			id = Integer.parseInt(request.getParameter("topicId")); 
			//crawl(topicId);
			int topicId = News.getTrackId(id);
			
			System.out.println("本体进化》》》"+topicId);
			
			while(News.isEvolve(topicId)){
				System.out.println("本体进化中》》》"+topicId);
				WordSeg.SegEvl(topicId);
				//WordSyn.Syn(topicId);
				weightCal.CalWord(topicId);
				
				WordEntry.EntryCreateEvl(topicId);
				EntrySyn.SynEvl(topicId);
				weightCal.CalEntryEvl(2,topicId);
				weightCal.deleteRepeat(id, topicId);
			}
			System.out.println("本体进化完成》》》"+topicId);
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
	

	
}
