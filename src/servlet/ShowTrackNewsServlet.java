package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.NewsSr;
import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.Word;


public class ShowTrackNewsServlet extends HttpServlet 
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
		int page=1;
		try
		{
		
			
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			page =  Integer.parseInt(request.getParameter("page")); 
			
			News[] news= News.getNewsByTid(News.getTrackId(topicId),page);
			
			result ="<div id=\"news-content\" class=\"box box-news\">" +
					"<table>";
			double tw = NewsSr.getTotleWeight(topicId);
			double ww = NewsSr.getWordWeight(topicId);
			result+="<tr><td width=\"800px\">话题跟踪阈值</td>"
				+"<td width=\"100px\"><span></span>特征词模型<td>"
				+"<td width=\"100px\"><span>"+ww+"</span><td>"
				+"<td width=\"100px\"><span>特征词条模型</span><td>"
				+"<td width=\"100px\"><span>"+tw+"</span><td>";
				
				for(News wnew:news){
					
					result+="<tr><td width=\"800px\"><a href=\"\">"+wnew.getContent()+"</a></td>"
					+"<td width=\"100px\"><span>"+wnew.getTime()+"</span><td>"
					+"<td width=\"100px\"><span>"+wnew.getWtid()+"</span><td>"
					+"<td width=\"100px\"><span>"+wnew.getWordWeight()+"</span><td>"
					+"<td width=\"100px\"><span>"+wnew.getTotleWeight()+"</span><td>";
				}
				
				result+="</table>";
			
			int tPage= News.getTotalPage(News.getTrackId(topicId));
			int fPage = (tPage-1)/10+1;
		
			if(tPage>10){
				result+="<br><br><div class=\"page-side\" style=\"margin-left:40%\">";
				int cPage = ((page-1)/10)+1;
				
				if(page>1){
					result+="<span><a href='javascript:void(0)' " +
					"onclick=\"ShowTrackNews('"+(page-1)+"')\">上一页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</a></span>\n";
				
				}
				
				for(int i=(cPage-1)*10+1;i<=cPage*10&&i<=fPage;i++){
					if(i==page){
						result+="<span><a href='javascript:void(0)' " +
						"onclick=\"ShowTrackNews('"+i+"')\">"+i+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</a></span>";
					}else{
						result+="<span><a href='javascript:void(0)' " +
						"onclick=\"ShowTrackNews('"+i+"')\">"+i+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</a></span>";
					}
				}
				
				if(fPage>cPage*10){
					result+="<span>...</span><span class=\"\"><a href='javascript:void(0)' " +
					"onclick=\"ShowTrackNews('"+fPage+"')\">"+fPage+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp</a></span>\n";
			
				}
				
				if(fPage>page){
					result+="<span><a href='javascript:void(0)' " +
					"onclick=\"ShowTrackNews('"+(page+1)+"')\">下一页</a></span>\n";
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
			
			
			PrintWriter writer = response.getWriter();
			writer.write(result);
		}
	}
}
