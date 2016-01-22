package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.Dao.Topic;


public class AddTopicServlet extends HttpServlet 
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
		String topicName = "";
		
		try
		{
			topicName = URLDecoder.decode(request.getParameter("topicName"),"UTF-8"); 
			int i = Topic.insert(topicName);
			if(i>0){
				result = "add_suc";
			}
			
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
			System.out.println();
			System.out.println("Add Topic");
			System.out.println("Topic Name: " + topicName);
			
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}
}
