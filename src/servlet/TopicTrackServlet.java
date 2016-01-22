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

import crun.v3.SimCal;
import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;
import fetch.WbFetch;


public class TopicTrackServlet extends HttpServlet 
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
		
		System.out.println("topicId");
		
		String result = "add_fail";
		int topicId = 0;
		try
		{
		
			StringBuffer sr = new StringBuffer();
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			System.out.println(topicId+"跟踪进行中》》》");
			//
			
			SimCal.CalNow(topicId);
			SimCal.Cal(topicId);
			SimCal.Track(topicId);
			System.out.println("跟踪完毕》》》");
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
