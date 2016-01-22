package crun.v3;

import java.util.ArrayList;
import java.util.List;

import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;

	
public class Word2Entry{
	
	 public  static  String[] newsSentence =null;
	
	public static void main(String args[]){
		
		entry3(5);

	}
	
	public static void entry2(){
		
		String w = "“/w|：/w|‘/w|、/w|”/w";
		
		
		//找出词条
		NewsSr[] newsSr = NewsSr.getAllNews();
		StringBuffer newsSrAll = new StringBuffer();
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent()).append("。/w");
		}
		
		newsSentence = newsSrAll.toString().replaceAll(w, "").split("。/w|:/n|【/w|】/w|！/w|？/w");
			
		Word.updateUseWord();
		Word word = Word.getUseWord();
		
		System.out.println(newsSrAll.toString());
		
		while(word!=null){
			
			Word[] words = word.getLastUseWord();
			
			for(int i=0;i<words.length;i++){

				PMI(word,words[i]);

			}
			word.setType(0);
			word = Word.getUseWord();
			
		}
	}
	
	public static void entry3(int x){
		
		String w = "“/w|：/w|‘/w|、/w|”/w";
		
		
		//找出词条
		NewsSr[] newsSr = NewsSr.getAllNews();
		StringBuffer newsSrAll = new StringBuffer();
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent()).append("。/w");
		}
		
		newsSentence = newsSrAll.toString().replaceAll(w, "").split("。/w|:/n|【/w|】/w|！/w|？/w");
			
		
		Entry[] entrys = Entry.getAllEntry2();
		
		for(Entry entry:entrys){
			
			Word[] words = Word.getWordOfEntry2(entry);
			
			for(int i=0;i<words.length;i++){

				PMI(words[i],entry,x);
				
			
			}

			
		}
	}
	

	
	private static void PMI(Word word, Word word2) {
		
		int num =0;
		for(int i=0;i<newsSentence.length;i++){
			
			if(newsSentence[i].indexOf(word.getName())!=-1&&newsSentence[i].indexOf(word2.getName())!=-1)
			{
				num++;
			}
		
		}
		
		double dnum = num;
		int num1= word.getNum();
		int num2= word2.getNum();
		//System.out.println(dnum);
		double p1 =dnum/(double)(num1);
		double p2 = dnum/(double)num2;
		//System.out.println(word.getName()+","+word2.getName()+"    "+p1+p2);
		if(num>2&&(p1>0.5||p2>0.5)){
			Entry.insert2(word.getName()+","+word2.getName(), num1+","+num2, num);
		}
		
		
	}
	
	private static void PMI(Word word, Entry entry,int x) {
		String[] words = entry.getName().split(",");
		
		
		int num =0;
		for(int i=0;i<newsSentence.length;i++){
			
			if(newsSentence[i].indexOf(word.getName())!=-1)
			{	Boolean f =true;
				for(String w:words){
					if(newsSentence[i].indexOf(w)==-1){
						f =false;
						break;
					}
				}
				if(f){
					num++;
				}
				
			}
		
		}
		
		double dnum = num;
		int num1= word.getNum();
		double p1 =dnum/(double)(num1);
		if(num>4&&p1>0.2){
			String n3 = num1+"";
			String e3 = word.getName();
			Boolean f =true;
			for(String w:words){
				e3+= ","+w;
				Word wd = new Word(w);
				int wnum = wd.getNum();
				n3+= ";" + wnum;
				double p =dnum/(double)(wnum);
				
				if(p<0.2){
					f =false;
					break;
				}
			}
			

			
			if(f&&!Entry.isEntryExist1(e3,x+1)){
				Entry.insert3(e3, n3, num,x+1);
				
			}
		
		}
		//System.out.println(dnum);

		
		
	}


	public static void getWordEntry(String sent1,String sent2){
		
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
			
			System.out.println(list.toString());
			String name=list.toString();
			
			if(Entry.isEntryExist(name)){
				//Entry.NumPlus(name);
			}else{
				Entry.insert(name,list.size(),0);
			}
			
		}
		
	}
}