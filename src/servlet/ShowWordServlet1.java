package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.Dao.Word;


public class ShowWordServlet1 extends HttpServlet 
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
			

			sr.append("<div class=\"span8\"><div class=\"row-fluid\" id=\"tag_cloud_area\" style=\"visibility: visible;")
					.append("\"><table width=\"100%\" style=\"background-color: transparent;border-collapse: ")
					.append("collapse;border-spacing: 0;\"><tbody><tr><td width=\"33%\"></td><td width=\"33%\" align=\"center\">")
					.append("<h4 style=\"margin-left:40px\">时间本体</h4></td><td width=\"33%\" align=\"right\"></td></tr>");
			
			Word[] tWord = Word.getTimeWord(topicId);
			int tSize = tWord.length;
			
			sr.append("<tr><td colspan=\"3\"><ul class=\"weighted\" style=\"fon")
			.append("t-size: 50%;margin-left:5%;height:100px\" id=\"tags_ul\">");
			for(int i= 0;i<(tSize-1)/3+1;i++){
			
				sr.append("<li><span style=\"font-size:2ex\">");
			
				for(int j= 3*i;j<3*(i+1)&&j<tSize;j++){
					
					sr.append(tWord[j].getName()).append(",");
				}
				
				sr.append("</span></li>");
					
			}
			
			sr.append("</ul></td></tr></tbody></table></div></div>");
			

			

			sr.append("<div class=\"span8\"><div class=\"row-fluid\" id=\"tag_cloud_area\" style=\"visibility: visible;")
					.append("\"><table width=\"100%\" style=\"background-color: transparent;border-collapse: ")
					.append("collapse;border-spacing: 0;\"><tbody><tr><td width=\"33%\"></td><td width=\"33%\" align=\"center\">")
					.append("<h4 style=\"margin-left:40px\">地点本体</h4></td><td width=\"33%\" align=\"right\"></td></tr>");
			
			Word[] aWord = Word.getAdrWord(topicId);
			int aSize = aWord.length;
			
			sr.append("<tr><td colspan=\"3\"><ul class=\"weighted\" style=\"fon")
			.append("t-size: 50%;margin-left:5%;height:100px\" id=\"tags_ul\">");
			for(int i= 0;i<(aSize-1)/5+1;i++){
			
				sr.append("<li><span style=\"font-size:2ex\">");
			
				for(int j= 5*i;j<5*(i+1)&&j<aSize;j++){
					
					sr.append(aWord[j].getName()).append(",");
				}
				
				sr.append("</span></li>");
					
			}
			
			sr.append("</ul></td></tr></tbody></table></div></div>");
			
			
			sr.append("<div class=\"span8\"><div class=\"row-fluid\" id=\"tag_cloud_area\" style=\"visibility: visible;")
			.append("\"><table width=\"100%\" style=\"background-color: transparent;border-collapse: ")
			.append("collapse;border-spacing: 0;\"><tbody><tr><td width=\"33%\"></td><td width=\"33%\" align=\"center\">")
			.append("<h4 style=\"margin-left:40px\">对象本体</h4></td><td width=\"33%\" align=\"right\"></td></tr>");
	
			Word[] oWord = Word.getObjWord(topicId);
			int oSize = oWord.length;
			
			sr.append("<tr><td colspan=\"3\"><ul class=\"weighted\" style=\"fon")
			.append("t-size: 50%;margin-left:5%;\" id=\"tags_ul\">");
			for(int i= 0;i<(oSize-1)/5+1;i++){
			
				sr.append("<li><span style=\"font-size:2ex\">");
			
				for(int j= 5*i;j<5*(i+1)&&j<oSize;j++){
					
					sr.append(oWord[j].getName()).append(",");
				}
				
				sr.append("</span></li>");
					
			}
			
			sr.append("</ul></td></tr></tbody></table></div></div>");
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
