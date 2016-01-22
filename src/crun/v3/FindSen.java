package crun.v3;

import java.util.Vector;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.TName;
import crun.v3.Dao.Word;

public class FindSen{
	
	public static  void main(String[] args){
		
		News[] news = News.getAllNewsCopy();
		

		String[] words =Word.getWordsStr();

		Entry[] entry = Entry.getAllEntry2();
		for(int n=0;n<news.length;n++){
			
			int pid =news[n].getId();
			String[] txt = news[n].getContent().split("¡£/w|:/n|¡¾/w|¡¿/w|£¡/w|£¿/w");;
			System.out.println(txt);


			double zweight =0;
				
			
			for(int k=0;k<txt.length;k++){
				
				boolean isExist =true;
				
				for(int i=0;i<entry.length;i++){
				
					String[] entryWords = entry[i].getName().replaceAll("\\]|\\[|/n|/v", "").split(",");
					
					for(int j=0;j<entryWords.length;j++){
					
						if(txt[k].indexOf(entryWords[j])==-1){
							isExist =false;
								
						}
	
					}//System.out.println(isExist);

				}
			
				if(isExist==true){
					
				}
			}
		}	
		
	}
}