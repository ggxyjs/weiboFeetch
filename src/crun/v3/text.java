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
			
			SegResult sr = st.split("�������취������:#������־����Ϯ#���˷��������Ǵ���������˿��ܻ�û�й�������Ϣ֪���Ŀ죬��Ϊ���ǽ��춼���̳���ɨ���������Ƿ����������۵�һ�졣����о����Ȳ�����������ɨ����Ȧ���Ǹ��䵭���ػ����ˡ�ps:��ס��93ʡ�������11�������ص���Զ�ġ�������������ÿ�ȥ����ˡ� [λ��]93160 ��ʾ��ͼ [��ͼ��3��]");
			 sa = sr.getFinalResult();
		}
		catch (Exception e) 
		{
			e.printStackTrace(); 
		}
		System.out.println(sa);
	}
	
	public static void t2(){
		

		String s ="��/w ����/n ˺/v ��/n ��/v ��ֹ/v 5��/t/ 31��/t ��/n Χ/v Ź/v ��ʦ/n ��/w 5��/t 30��/t ��/w ����/ns ��";
	
		Pattern p=Pattern.compile("[^ ]*/t[^/]*/t");

		  Matcher m=p.matcher(s);
		  int i=0;
		  while(m.find()){
		   i++;System.out.print(m.group());
		  }
		  System.out.println(i);
	
	}
	
}