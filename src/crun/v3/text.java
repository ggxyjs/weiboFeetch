package crun.v3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class text{
	
	public static void main(String[] args){
		
		String url="http://news.baidu.com/ns?from=news&cl=2&bt=1393603200&y0=2014&m0=3&d0=1&y1=2014&m1=3&d1=2&et=1393700000&q1=%D4%C6%C4%CF%C0%A5%C3%F7%B1%A9%BF%D6%B0%B8&submit=%B0%D9%B6%C8%D2%BB%CF%C2&q3=&q4=&mt=0&lm=&s=2&begin_date=2014-3-1&end_date=2014-3-2&tn=newsdy&ct1=1&ct=1&rn=100&q6=&pn=100";		 Document doc = null;
		try {
			doc = Jsoup.connect(url).post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
   	 
   	   System.out.println(doc);
		t1();
	
	}
	
	public static void t1(){
		

		SegTag st = new SegTag(1);
		String sa = "";
		//System.out.println("a"+a[i]);
		try{
			
			SegResult sr = st.split("巴黎夏天法国代购:#法国杂志社遇袭#震惊了法国，但是大多数法国人可能还没有国内人消息知道的快，因为他们今天都在商场里扫货。今天是法国冬季打折第一天。总体感觉力度不够大。我来回扫了两圈还是高冷淡定地回来了。ps:我住在93省，离巴黎11区案发地点蛮远的。不过最近进货得快去快回了。 [位置]93160 显示地图 [组图共3张]");
			 sa = sr.getFinalResult();
		}
		catch (Exception e) 
		{
			e.printStackTrace(); 
		}
		System.out.println(sa);
	}
	
	public static void t2(){
		

		String s ="【/w 高三/n 撕/v 书/n 遭/v 制止/v 5月/t/ 31日/t 人/n 围/v 殴/v 老师/n 】/w 5月/t 30日/t ，/w 陕西/ns 长";
	
		Pattern p=Pattern.compile("[^ ]*/t[^/]*/t");

		  Matcher m=p.matcher(s);
		  int i=0;
		  while(m.find()){
		   i++;System.out.print(m.group());
		  }
		  System.out.println(i);
	
	}
	
}