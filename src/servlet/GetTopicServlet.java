package servlet;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crun.v3.Dao.News;
import crun.v3.Dao.Topic;


public class GetTopicServlet extends HttpServlet 
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
		int page;

		
		try
		{
			//System.out.print(1111);
			
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			page =  Integer.parseInt(request.getParameter("page")); 
			
			HttpSession session = request.getSession(); 
			session.setAttribute("id", topicId); 
			
			News[] news= News.getNewsByTid(topicId,page);
			result ="<div id=\"news-content\" class=\"box box-news\">" +
					"<table>";
			
			for(News wnew:news){
				
				//System.out.println(wnew.getContent()+wnew.getTime());
				result+="<tr><td><a href=\"\">"+wnew.getContent()+"</a></td>"
				+"<td  width=\"100px;\"><span>"+wnew.getTime()+"</span><td></tr>\n";
		
			}
			
			result+="</table>";
			
			int tPage= News.getTotalPage(topicId);
			int fPage = (tPage-1)/10+1;
		
			if(tPage>10){
				result+="<br><br><div class=\"page-side\"  style=\"margin-left:40%\">";
				int cPage = ((page-1)/10)+1;
				
				if(page>1){
					result+="<span><a href='javascript:void(0)' " +
					"onclick=\"getTopic('"+topicId+"','"+(page-1)+"')\">上一页;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</a></span>\n";
				
				}
				
				for(int i=(cPage-1)*10+1;i<=cPage*10&&i<=fPage;i++){
					if(i==page){
						result+="<span><a href='javascript:void(0)' " +
						"onclick=\"getTopic('"+topicId+"','"+i+"')\">"+i+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>";
					}else{
						result+="<span><a href='javascript:void(0)' " +
						"onclick=\"getTopic('"+topicId+"','"+i+"')\">"+i+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>";
					}
				}
				
				if(fPage>cPage*10){
					result+="<span>...</span><span class=\"\"><a href='javascript:void(0)' " +
					"onclick=\"getTopic('"+topicId+"','"+fPage+"')\">"+fPage+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>\n";
			
				}
				
				if(fPage>page){
					result+="<span><a href='javascript:void(0)' " +
					"onclick=\"getTopic('"+topicId+"','"+(page+1)+"')\">下一页</a></span>\n";
				}
				result+="</div>";	
			}
			
			result+="</div>";
			
			
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
			result+="$$$"+topicId;
			
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}
}
