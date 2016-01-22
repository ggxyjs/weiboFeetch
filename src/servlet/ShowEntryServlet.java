package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crun.v3.WordSeg;
import crun.v3.WordSyn;
import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;


public class ShowEntryServlet extends HttpServlet 
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
			System.out.println("show特征词条本体");
		
			StringBuffer sr = new StringBuffer();
			topicId = Integer.parseInt(request.getParameter("topicId")); 
			

			sr.append("<div class=\"span8\"><div class=\"row-fluid\" id=\"tag_cloud_area\" style=\"visibility: visible;")
					.append("\"><table width=\"100%\" style=\"background-color: transparent;border-collapse: ")
					.append("collapse;border-spacing: 0;\"><tbody><tr><td width=\"33%\"></td><td width=\"33%\" align=\"center\">")
					.append("<h4 style=\"margin-left:40px\">特征词条本体</h4></td><td width=\"33%\" align=\"right\"></td></tr>");
			
			String[] maxWords = Word.getMaxWordsStr(topicId);
			String fWord ="";
			if(maxWords.length>0){
				fWord ="[";
				for(String word:maxWords){
					fWord+=word+", "; 
				}
				fWord = fWord.substring(0,fWord.length()-2);
				fWord+="]-";
			}
			List<String> tWord = Entry.getSortEntryByIs(2,topicId);
			int tSize = tWord.size();
			
			sr.append("<tr><td align =\"center\" colspan=\"3\"><textarea style= \"width:500px;height:500px\" class=\"textbox\">");

			for(int j= 0;j<tSize;j++){
					
				sr.append(fWord + tWord.get(j).replaceAll("/ns|/n|/v", "")).append(",\n");
			}

			sr.append("</textarea></td></tr></tbody></table></div></div>");
			

	
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
