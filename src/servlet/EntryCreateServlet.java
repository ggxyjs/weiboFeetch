package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.EntrySyn;
import crun.v3.NewsSr;
import crun.v3.SimCal;
import crun.v3.WordEntry;
import crun.v3.weightCal;


public class EntryCreateServlet extends HttpServlet 
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
		
			System.out.println("词条抽取中》》》》》》");
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			
			WordEntry.EntryCreate(topicId);
			EntrySyn.Syn1(topicId);
			weightCal.CalEntry(2,topicId);
			SimCal.CalNow(topicId);
			
			System.out.println("词条抽取完毕》》》》》》");
			result = "add_suc";
			
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
