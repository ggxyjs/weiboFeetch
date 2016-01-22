package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.Dao.News;
import crun.v3.Dao.Topic;
import fetch.WbFetch;


public class FetchNewsServlet extends HttpServlet 
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
		String url = "";
		int num = 0;
	

		try
		{

			topicId = Integer.parseInt(request.getParameter("topicId"));
			url = URLDecoder.decode(request.getParameter("url"),"UTF-8"); 
			num = Integer.parseInt(request.getParameter("num"));
			url = url.replaceAll("%26", "&");
			url = url.replaceAll(" ", "+");
			//System.out.println(url);
			
			WbFetch.crawl(topicId, url, num);
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
			
			System.out.println(result);
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}
}
