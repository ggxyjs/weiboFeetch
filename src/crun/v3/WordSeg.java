package crun.v3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.Word;

public class WordSeg{
	
	public static void main(String[] args){
		
		News[] news = News.getAllNews(13);
		
		for(int i=0;i<news.length;i++){
			
			String content = news[i].getContent();
			String contentSr ="";
			String newsSr ="";
			
			SegTag st = new SegTag(1);
			
			try{
				SegResult sr = st.split(content);
				contentSr = sr.getFinalResult();
				System.out.print(contentSr);
			}
			catch (Exception e) 
			{
				e.printStackTrace(); 
			}
		}
	}
	
	public static void SegEvl(int id){
		
		News[] news = News.getElvNews(id);
		int version = News.getNowVersion(id)+1;
		for(int i=0;i<news.length;i++){
			
			
			
			String content = news[i].getContent();
			String contentSr ="";
			String newsSr ="";
			
			SegTag st = new SegTag(1);
			
			try{
				SegResult sr = st.split(content);
				contentSr = sr.getFinalResult();
			}
			catch (Exception e) 
			{
				e.printStackTrace(); 
			}
			
			String[] words = contentSr.split(" ");
			
			for(int j=0;j<words.length;j++){
				
				String word = words[j];
				//System.out.println(word);
				if(word.indexOf("/")!=-1){
					
					String wordContent =word.split("/")[0];
					String wordSpe =word.split("/")[1];
					
					if(wordSpe.indexOf("v")!=-1)
					{
						if(!Word.isWordExist(wordContent, "v",id,version))
						{
							Word.insert(wordContent,1, "v",id,version);
						}else{
							Word tWord = new Word(wordContent, "v",id,version);
							tWord.NumPlus(1);
						}
						
						newsSr+= wordContent+"/v ";
					}else if(wordSpe.indexOf("ns")!=-1)
					{
						wordContent = wordContent.replaceAll("市|省", "");
						if(!Word.isWordExist(wordContent,"ns",id,version))
						{
							Word.insert(wordContent, 1,"ns",id,version);
						}else{
							Word tWord = new Word(wordContent, "ns",id,version);
							tWord.NumPlus(1);
						}
						newsSr+= wordContent+"/ns ";
					}else if(wordSpe.indexOf("n")!=-1)
					{
						if(!Word.isWordExist(wordContent,"n",id,version))
						{
							Word.insert(wordContent, 1,"n",id,version);
						}else{
							Word tWord = new Word(wordContent,"n",id,version);
							tWord.NumPlus(1);
						}
						newsSr+= wordContent+"/n ";
					}else if(wordSpe.indexOf("t")!=-1)
					{

						newsSr+= wordContent+"/t ";
					}else if(wordSpe.indexOf("w")!=-1)
					{
						newsSr+= wordContent+"/w ";
					}
					
				}

			}
			NewsSr.insert(newsSr,id,version);
			System.out.println(newsSr);
		}
		
		News.updateElvNews(id,version);
	}
	
	public static void Seg(int id){
		
		News[] news = News.getAllNews(id);
		
		for(int i=0;i<news.length;i++){
			
			String content = news[i].getContent();
			String contentSr ="";
			String newsSr ="";
			
			SegTag st = new SegTag(1);
			
			try{
				SegResult sr = st.split(content);
				contentSr = sr.getFinalResult();
			}
			catch (Exception e) 
			{
				e.printStackTrace(); 
			}
			
			String[] words = contentSr.split(" ");
			
			for(int j=0;j<words.length;j++){
				
				String word = words[j];
				//System.out.println(word);
				if(word.indexOf("/")!=-1){
					
					String wordContent =word.split("/")[0];
					String wordSpe =word.split("/")[1];
					
					if(wordSpe.indexOf("v")!=-1)
					{
						if(!Word.isWordExist(wordContent, "v",id))
						{
							Word.insert(wordContent,1, "v",id);
						}else{
							Word tWord = new Word(wordContent, "v",id);
							tWord.NumPlus(1);
						}
						
						newsSr+= wordContent+"/v ";
					}else if(wordSpe.indexOf("ns")!=-1)
					{
						wordContent = wordContent.replaceAll("市|省", "");
						if(!Word.isWordExist(wordContent,"ns",id))
						{
							Word.insert(wordContent, 1,"ns",id);
						}else{
							Word tWord = new Word(wordContent, "ns",id);
							tWord.NumPlus(1);
						}
						newsSr+= wordContent+"/ns ";
					}else if(wordSpe.indexOf("n")!=-1)
					{
						if(!Word.isWordExist(wordContent,"n",id))
						{
							Word.insert(wordContent, 1,"n",id);
						}else{
							Word tWord = new Word(wordContent,"n",id);
							tWord.NumPlus(1);
						}
						newsSr+= wordContent+"/n ";
					}else if(wordSpe.indexOf("t")!=-1)
					{

						newsSr+= wordContent+"/t ";
					}else if(wordSpe.indexOf("w")!=-1)
					{
						newsSr+= wordContent+"/w ";
					}
					
				}

			}
			NewsSr.insert(newsSr,id);
			System.out.println(newsSr);
		}
		
		
	}
	
	public static void SegTime(int id){
		NewsSr[] newsSr = NewsSr.getAllNews(id);
		StringBuffer newsSrAll = new StringBuffer();
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent());
		};
	   	String time = Entry.getMaxTime(id);
	   	String[] t = time.split("-");
	   	if(t[1].substring(0,1).equals("0")){
	   		t[1] = t[1].substring(1,2);
	   	}
	   	if(t[2].substring(0,1).equals("0")){
	   		t[2] = t[2].substring(1,2);
	   	}
		String news = newsSrAll.toString();
		Pattern p=Pattern.compile("[^ ]*/t[^/]*/t");
		//  String u="abcdefsfsaffsabadfewfadfgea";
		  Matcher m=p.matcher(news);
		 // int i=0;
		  while(m.find()){
		  // System.out.print(m.group());
		   String w = m.group();
		   String w1 = w.split(" ")[0].split("/")[0];
		   String w2 = w.split(" ")[1].split("/")[0];
		   String nw = w1+w2+"/t";
		   news = news.replace(w, nw);
		   m=p.matcher(news);
		   
		  }
		  
		  
			Pattern p1=Pattern.compile("[^ ]*/t");
			
			 Matcher m1=p1.matcher(news);
		
			  while(m1.find()){
			  
			   String w = m1.group();
			   String w1 = w.split("/")[0].replaceAll("0", "");
			   if(isDelete(w1)){
				  
				   
				   if(w1.indexOf("日")==-1){
					   w1 =t[2]+"日"+w1;
				   }
				   
				   if(w1.indexOf("月")==-1){
					   w1 =t[1]+"月"+w1;
				   }
				   
				   if(w1.indexOf("年")==-1){
					   w1 =t[0]+"年"+w1;
				   }

					if(!Word.isWordExist(w1,"t",id))
					{
						Word.insert(w1, 1,"t",id);
					}else{
						Word tWord = new Word(w1,"t",id);
						tWord.NumPlus(1);
					}
				   }
			  }
		  
		 // System.out.println(i);
		
	}
	
	public static boolean isDelete(String w){
		
		String words ="点今日目前今晨瞬间当晚当前现在春夏秋冬今天最近明天童年过去老年凌晨立冬吴当今后来昨日";
		if(words.indexOf(w)!=-1){
				return false;
		}
		
		return true;
	}
}