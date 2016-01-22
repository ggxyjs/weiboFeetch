package crun.v3;

import java.util.ArrayList;
import java.util.List;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.Word;

public class WordEntry{
	
	public static void EntryCreateEvl(int id){
		int version = News.getNowVersion(id);
		//String w = "】/w|【/w|！/w|:/w|。/w";
		
		System.out.println("初始词条生成》》》》》》");
		String w = "“/w|：/w|‘/w|、/w|”/w";
		
		
		//找出词条
		NewsSr[] newsSr = NewsSr.getEvlNews(id,version);
		StringBuffer newsSrAll = new StringBuffer();
		
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent()).append("。/w");
		}
		
		
		String[] maxWords = Word.getMaxWordsStr((id-10000));
		
		String newsSrAllStr = newsSrAll.toString();
		for(int i =0;i<maxWords.length;i++){
			newsSrAllStr = newsSrAllStr.replaceAll(maxWords[i], "");
		}
		
		String[] newsSentence = newsSrAllStr.replaceAll("[^ ]*/t", "").replaceAll(w, "").split("。/w|:/n|【/w|】/w|！/w|？/w");
			
		for(int i=0;i<newsSentence.length;i++){
			
			System.out.println(newsSentence[i]);
			for(int j=i+1;j<newsSentence.length;j++){
				//System.out.println(i+":"+j);
				getWordEntryEvl(newsSentence[i],newsSentence[j],id,version);
			}
		}
		System.out.println("初始词条生成》》》》》》");
	
		String[] entry = Entry.getEvlEntry(id,version);
		for(int j =0;j<entry.length;j++){
			
			for(int i=0;i<newsSentence.length;i++){
				
				String[] words =  entry[j].substring(1, entry[j].length()-1).split(",");
				boolean flag =true;
				for(int k=0;k<words.length;k++){
					
					if(newsSentence[i].indexOf(words[k])==-1){
						flag =false;
						break;
					}
				}
				if(flag){
					Entry.NumPlus(entry[j],id);
				}
			}
		}
	}
	
	public static void EntryCreate(int id){
		
		//String w = "】/w|【/w|！/w|:/w|。/w";
		
		System.out.println("初始词条生成》》》》》》");
		String w = "“/w|：/w|‘/w|、/w|”/w";
		
		
		//找出词条
		NewsSr[] newsSr = NewsSr.getAllNews(id);
		StringBuffer newsSrAll = new StringBuffer();
		
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent()).append("。/w");
		}
		
		
		String[] maxWords = Word.getMaxWordsStr(id);
		
		String newsSrAllStr = newsSrAll.toString();
		for(int i =0;i<maxWords.length;i++){
			newsSrAllStr = newsSrAllStr.replaceAll(maxWords[i], "");
		}
		
		String[] newsSentence = newsSrAllStr.replaceAll("[^ ]*/t", "").replaceAll(w, "").split("。/w|:/n|【/w|】/w|！/w|？/w");
			
		for(int i=0;i<newsSentence.length;i++){
			
			System.out.println(newsSentence[i]);
			for(int j=i+1;j<newsSentence.length;j++){
				//System.out.println(i+":"+j);
				getWordEntry(newsSentence[i],newsSentence[j],id);
			}
		}
		System.out.println("初始词条生成》》》》》》");
	
		String[] entry = Entry.getAllEntry(id);
		for(int j =0;j<entry.length;j++){
			
			for(int i=0;i<newsSentence.length;i++){
				
				String[] words =  entry[j].substring(1, entry[j].length()-1).split(",");
				boolean flag =true;
				for(int k=0;k<words.length;k++){
					
					if(newsSentence[i].indexOf(words[k])==-1){
						flag =false;
						break;
					}
				}
				if(flag){
					Entry.NumPlus(entry[j],id);
				}
			}
		}
	}
	
	public static void getWordEntryEvl(String sent1,String sent2,int id,int version){
		
		
		String[] words1 = sent1.split(" ");
		String[] words2 = sent2.split(" ");
		List<String> list =new ArrayList<String>();
		for(int i=0;i<words1.length;i++){
			
			for(int j=0;j<words2.length;j++){
				if(words1[i].equals(words2[j])&&!(words1[i].equals(""))&&(words1[i]!=null)){
					if(words1[i].replaceAll(" ", "").length()>3&&!list.contains(words1[i].replaceAll(" ", "")))
					list.add(words1[i].replaceAll(" ", ""));
				}
				
			}
		}
		if(list.size()>1){
			
			//System.out.println(list.toString());
			String name=list.toString();
			
			if(Entry.isEntryExist(name,id)){
				//Entry.NumPlus(name);
			}else{
				//System.out.println(name);
				Entry.insert(name,list.size(),0,id,version);
			}
			
		}
		
	}
	
	public static void getWordEntry(String sent1,String sent2,int id){
		
		
		String[] words1 = sent1.split(" ");
		String[] words2 = sent2.split(" ");
		List<String> list =new ArrayList<String>();
		for(int i=0;i<words1.length;i++){
			
			for(int j=0;j<words2.length;j++){
				if(words1[i].equals(words2[j])&&!(words1[i].equals(""))&&(words1[i]!=null)){
					if(words1[i].replaceAll(" ", "").length()>3&&!list.contains(words1[i].replaceAll(" ", "")))
					list.add(words1[i].replaceAll(" ", ""));
				}
				
			}
		}
		if(list.size()>1){
			
			//System.out.println(list.toString());
			String name=list.toString();
			
			if(Entry.isEntryExist(name,id)){
				//Entry.NumPlus(name);
			}else{
				//System.out.println(name);
				Entry.insert(name,list.size(),0,id);
			}
			
		}
		
	}
}