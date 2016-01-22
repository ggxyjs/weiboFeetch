package crun.v3;

import java.util.ArrayList;
import java.util.List;

import crun.v3.Dao.Entry;
import crun.v3.Dao.Word;

	
public class WordEntry1{
	
	 public  static  String[] newsSentence =null;
	 public  static  int n2 =5;
	 public  static  int num3 =10;
	 public  static  double pp1 =0.5;
	 public  static  double pp2 =0.2;
	public static void main(String args[]){
		//entry2();
		
		while(!Entry.isEmpty21()){
			entry3();
		}
		

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
		
		//System.out.println(newsSrAll.toString());
		
		while(word!=null){
			
			Word[] words = word.getLastUseWord();
			
			for(int i=0;i<words.length;i++){

				PMI(word,words[i]);

			}
			word.setType(0);
			word = Word.getUseWord();
			
		}
	}
	
	public static void entry3(){
		
		String w = "“/w|：/w|‘/w|、/w|”/w";
		
		
		//找出词条
		NewsSr[] newsSr = NewsSr.getAllNews();
		StringBuffer newsSrAll = new StringBuffer();
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent()).append("。/w");
		}
		
		newsSentence = newsSrAll.toString().replaceAll(w, "").split("。/w|:/n|【/w|】/w|！/w|？/w");
			
		
		Entry[] entrys = Entry.getAllEntry21();
		
		for(Entry entry:entrys){
			boolean f = false;
			
			Word[] words = Word.getWordOfEntry2(entry);
			
			for(int i=0;i<words.length;i++){

				if(PMI(words[i],entry)==true){
					f =true;
				}
				
			}
			if(f==true){
				Entry.updateIs(entry.getName(), 2);
			}else{
				Entry.updateIs(entry.getName(), 1);
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
		if((num>num3||num>n2&&(p1>pp1||p2>pp1))){
			Entry.insert21(word.getName()+","+word2.getName(), num1+","+num2, num);
		}
		
		
	}
	
	private static boolean PMI(Word word, Entry entry) {
		String[] words = entry.getName().split(",");
		
		boolean flag =false;
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
		if(num>num3||(num>n2&&p1>pp2)){
			String n3 = num1+"";
			String e3 = word.getName();
			Boolean f =true;
			for(String w:words){
				e3+= ","+w;
				Word wd = new Word(w);
				int wnum = wd.getNum();
				n3+= ";" + wnum;
				double p =dnum/(double)(wnum);
				
				if(p<pp2&&num<num3){
					f =false;
					break;
				}
			}
			

			
			if(f&&!Entry.isEntryExist21(e3)){
				Entry.insert21(e3, n3, num);
				flag =true;
			}
		
		}
		//System.out.println(dnum);
		return flag;
	}
	

}