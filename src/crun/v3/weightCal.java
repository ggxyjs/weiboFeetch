package crun.v3;

import common.dao.DBManager;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.TName;
import crun.v3.Dao.Word;

public class weightCal{
	
	public static void main(String[] args){
	//	CalIs(2);
		//cal2();
	}
	
	public static void deleteRepeat(int id,int eid){
		
		Entry[] entrys =Entry.getEntryByIs(2,eid);
		
		for(Entry entry:entrys){
			if(Entry.isEntryExist(entry.getName(),id)){
				Entry.updateIsById(entry.getId(),3);
			}
		}
	}
	
	public static void CalWord(int id){
		int version = News.getNowVersion(id);
		Word[] words =Word.getWordsEvl(id,version);
		
		int totalNum = 0;
		for(int i=0;i<words.length;i++){
			
			totalNum+=words[i].getNum();
		}
		System.out.println(totalNum);
		for(int i=0;i<words.length;i++){
			
			float weight = ((float)words[i].getNum()/(float)totalNum);
			
			//System.out.println(weight);
			words[i].setWeight(weight);
		}
		
	}
	
	public static void CalWordEvl(int id){
		Word[] words =Word.getWords(id);
		
		int totalNum = 0;
		for(int i=0;i<words.length;i++){
			
			totalNum+=words[i].getNum();
		}
		System.out.println(totalNum);
		for(int i=0;i<words.length;i++){
			
			float weight = ((float)words[i].getNum()/(float)totalNum);
			
			//System.out.println(weight);
			words[i].setWeight(weight);
		}
		
	}
	public static void CalEntry(int x,int id){
		

		Entry[] Entrys =Entry.getEntryByIs(x,id);
		
		int totalNum = 0;
		for(int i=0;i<Entrys.length;i++){
			
			totalNum+=Entrys[i].getNum();
		}
		System.out.println(totalNum);
		for(int i=0;i<Entrys.length;i++){
			
			float weight = ((float)Entrys[i].getNum()/(float)totalNum);
			
			//System.out.println(weight);
			Entrys[i].setWeight(weight);
		}
		
	}
	
	public static void CalEntryEvl(int x,int id){
		
		int version = News.getNowVersion(id);
		Entry[] Entrys =Entry.getEntryByIsEvl(x,id,version);
		
		int totalNum = 0;
		for(int i=0;i<Entrys.length;i++){
			
			totalNum+=Entrys[i].getNum();
		}
		System.out.println(totalNum);
		for(int i=0;i<Entrys.length;i++){
			
			float weight = ((float)Entrys[i].getNum()/(float)totalNum);
			
			//System.out.println(weight);
			Entrys[i].setWeight(weight);
		}
		
	}
	
	static void cal1e(){
		Entry[] Entrys =Entry.getAllEntry();
		
		int totalNum = 0;
		for(int i=0;i<Entrys.length;i++){
			
			totalNum+=Entrys[i].getNum();
		}
		System.out.println(totalNum);
		for(int i=0;i<Entrys.length;i++){
			
			float weight = ((float)Entrys[i].getNum()/(float)totalNum);
			
			//System.out.println(weight);
			Entrys[i].setWeight(weight);
		}
		
	}
	
	static void cal2(int id){
		Word[] words =Word.getWords(id);
		
		int totalNum = 0;
		for(int i=0;i<words.length;i++){
			
			totalNum+=words[i].getNum();
		}
		
		float totalWeight = 0;
		//System.out.println(totalNum);
		
		int entryNum = Entry.getCount();
		
		for(int i=0;i<words.length;i++){
			
			float c = Entry.getCount(words[i].getName());
			float weight = ((float)words[i].getNum()/(float)totalNum);
			weight = weight*(1+c/(float)entryNum);
			totalWeight+=weight*(1+c/(float)entryNum);

		}
		
		
		for(int i=0;i<words.length;i++){
			
			float c = Entry.getCount(words[i].getName());
			float weight = ((float)words[i].getNum()/(float)totalNum);
			weight = weight*(1+c/(float)entryNum);
			
			if(c!=0){
				words[i].setZweight((weight*(1+c/(float)entryNum))/totalWeight);
			}else{
				words[i].setZweight((weight*(1+c/(float)entryNum))/(2*totalWeight));
			}
		}
		
	}
	
}