package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.weightCal;


public class WordCreateServlet extends HttpServlet 
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
		
			
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			
			System.out.println("特征词本体生成中");
			
		
			WordSeg.Seg(topicId);
			WordSeg.SegTime(topicId);
			WordSyn.Syn(topicId);
			weightCal.CalWord(topicId);
			
			System.out.println("特征词本体生成完毕");
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
