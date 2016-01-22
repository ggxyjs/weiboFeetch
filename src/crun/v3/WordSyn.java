package crun.v3;

import crun.v3.Dao.Word;

public class WordSyn{
	
	public static void Syn(int id){
		
		NewsSr[] newsSr = NewsSr.getAllNews(id);
		StringBuffer newsSrAll = new StringBuffer();
		for(int n=0;n<newsSr.length;n++){
			newsSrAll.append(newsSr[n].getContent());
		}
		
		Word.updateSingleWord(id);
		Word word = Word.getSingleWord(id);
		
		
		while(word!=null){
			
			Word[] words = word.getWordsExceptMe(id);
			
			for(int i=0;i<words.length;i++){

				isSyn(word,words[i],newsSrAll.toString(), id);
				isSyn(words[i],word,newsSrAll.toString(), id);
			}
			word.setType(0);
			word = Word.getSingleWord(id);
			
		}
		
		
		
	}
	
	public static void isSyn(Word word,Word wordOther,String news,int id){
		
		
		StringBuffer wordSyn = new StringBuffer();
		wordSyn.append(word.getName()).append("/").append(word.getSpe())
		.append(" ").append(wordOther.getName()).append("/").append(wordOther.getSpe());
		
		String wordSynStr = wordSyn.toString();
		//System.out.println(wordSynStr);
		int  num = (news.length()-news.replaceAll(wordSynStr, "").length())/wordSynStr.length();
	
		
		if(num>0){
			if(Word.isWordExist(word.getName()+wordOther.getName(), word.getSpe(),id))
			{
				Word wordNew = new Word(word.getName()+wordOther.getName(), word.getSpe(),id);
				wordNew.NumPlus(num);
				word.NumMinus(num);
				wordOther.NumMinus(num);
				
			}
			else{
				Word.insert(word.getName()+wordOther.getName(), num,word.getSpe(),id);	
				word.NumMinus(num);
				wordOther.NumMinus(num);
			}
			NewsSr.update(wordSyn.toString(), word.getName()+wordOther.getName()+"/"+word.getSpe());
		}
		System.out.println(wordSynStr+":"+num);
		
	}
	
}