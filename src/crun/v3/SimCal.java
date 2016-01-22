package crun.v3;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;

import crun.v3.Dao.Word;

public class SimCal{
	
	public static  void main(String[] args){
		
	
		//Cal();
	}
	public static void Track(int id){
		News.updateNews(id);
	}
	
	public static void CalNow(int id){
		
		News[] news = News.getAllNews(id);

		String[] words =Word.getWordsStr(id);

		Entry[] entry = Entry.getEntryByIs(2,id);
		
		for(int n=0;n<news.length;n++){
			
			int pid =news[n].getId();
			String txt = news[n].getContent();
			//System.out.println(txt);

			float weight =0;
			double zweight =0;
		
			for(int j=0;j<words.length;j++){

				if(txt.indexOf(words[j])!=-1){
						
						Word word = new Word(words[j]);

						//v.add(words[j]);
						weight+=word.getWeight();
					}
				
			}
			
			NewsSr.updateWeight(pid, weight);
			

			
			
			for(int i=0;i<entry.length;i++){
				
				String[] entryWords = entry[i].getName().replaceAll("\\]|\\[|/ns|/nr|/n|/v| ", "").split(",");
				
				
				boolean isExist =true;
				for(int j=0;j<entryWords.length;j++){
					//System.out.println(entryWords[j]);
					if(txt.indexOf(entryWords[j])==-1){
						
						isExist =false;
					}

				}//System.out.println(isExist);
				
				if(isExist ==true){

					zweight+=entry[i].getWeight();
				}
			}
			
			NewsSr.updateZweight(pid, zweight);
		}
		
		
	}
	
	public static void Cal(int id){
	
		News[] news = News.getAllNews(0);

		String[] words =Word.getWordsStr(id);

		Entry[] entry = Entry.getEntryByIs(2,id);
		
		for(int n=0;n<news.length;n++){
			
			int pid =news[n].getId();
			String txt = news[n].getContent();
			System.out.println(txt);

			float weight =0;
			double zweight =0;
		
			for(int j=0;j<words.length;j++){

				if(txt.indexOf(words[j])!=-1){
						
						Word word = new Word(words[j]);

						//v.add(words[j]);
						weight+=word.getWeight();
					}
				
			}
			
			NewsSr.updateWeight(pid, weight);
			

			
			
			for(int i=0;i<entry.length;i++){
				
				String[] entryWords = entry[i].getName().replaceAll("\\]|\\[|/ns|/nr|/n|/v| ", "").split(",");
				
				
				boolean isExist =true;
				for(int j=0;j<entryWords.length;j++){
					
					if(txt.indexOf(entryWords[j])==-1){
						
						isExist =false;
					}

				}//System.out.println(isExist);
				
				if(isExist ==true){

					zweight+=entry[i].getWeight();
				}
			}
			
			NewsSr.updateZweight(pid, zweight);
		}
		
	}
}