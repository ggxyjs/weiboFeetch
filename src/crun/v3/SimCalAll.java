package crun.v3;

import java.util.Vector;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.TName;
import crun.v3.Dao.Word;

public class SimCalAll{
	
	public static  void main(String[] args){
	
		
		News[] news = News.getNewsNoCopy();
		
		for(int i=0;i<news.length;i++){
			System.out.println(news);
			
			int pid = news[i].getId();
			String content = news[i].getContent();
			String contentSr ="";
			String newsSr ="";
			//�п�Ժ�ִ�
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
			
			//���ʲ�����ݿ�
			for(int j=0;j<words.length;j++){
				
				String word = words[j];
				//System.out.println(word);
				if(word.indexOf("/")!=-1){
					
					String wordContent =word.split("/")[0];
					String wordSpe =word.split("/")[1];
					
					if(wordSpe.indexOf("v")!=-1)
					{
						if(!Word.isWordExistCopy(pid,wordContent, "v"))
						{
							Word.insertCopy(pid,wordContent,1, "v");
						}else{
							Word tWord = new Word(pid,wordContent, "v");
							tWord.NumPlusCopy(1);
						}
						
						newsSr+= wordContent+"/v ";
					}else if(wordSpe.indexOf("n")!=-1)
					{
						if(!Word.isWordExistCopy(pid,wordContent, "n"))
						{
							Word.insertCopy(pid,wordContent, 1,"n");
						}else{
							Word tWord = new Word(pid,wordContent, "n");
							tWord.NumPlusCopy(1);
						}
						newsSr+= wordContent+"/n ";
					}else if(wordSpe.indexOf("w")!=-1)
					{
						newsSr+= wordContent+"/w ";
					}
					
				}

			}
			NewsSr.insertCopy(newsSr,pid);
			
			//�ϴ�
			
			Word.updateSingleWordCopy(pid);
			Word word = Word.getSingleWordCopy(pid);
			
			
			while(word!=null){
				
				Word[] wordsSyn = word.getWordsExceptMeCopy(pid);
				
				for(int k=0;k<wordsSyn.length;k++){

					isSyn(pid,word,wordsSyn[k],newsSr);
					isSyn(pid,wordsSyn[k],word,newsSr);
				}
				word.setTypeCopy(0);
				word = Word.getSingleWordCopy(pid);
				
			}
		}
		
		
		News[] news2 = News.getAllNewsCopy();
		
		
		//����Щ��
		Vector<String> v = new Vector<String>();
		
		for(int n=0;n<news2.length;n++){
			
			int pid =news2[n].getId();
			String[] words =Word.getWordsStr();
			String[] wordsCopy =Word.getWordsStrCopy(pid);
			
			float weight =0;
			float zweight = 0;
			for(int i=0;i<wordsCopy.length;i++){
				
				for(int j=0;j<words.length;j++){
					
					if(wordsCopy[i].equals(words[j])){
						
						Word word = new Word(wordsCopy[i]);
					
						v.add(wordsCopy[i]);
						weight+=word.getWeight();
						zweight+=word.getZweight();
					}
				}
			}
			System.out.println(weight);
			System.out.println(zweight);
			
		//���������
			String newsStrCopy = NewsSr.getNewsCopy(pid);
			String[] entry = Entry.getAllEntry(0);
			
			String[] newsSentence = newsStrCopy.split(TName.SEPARATOR);
			
			int entryNum =0;
			for(int i=0;i<entry.length;i++){
				
				String[] entryWords = entry[i].substring(1,entry[i].length()-1).split(", ");
				
				boolean isExist =true;
				for(int j=0;j<newsSentence.length&&isExist;j++){
					
					boolean flag =true;
					for(int k=0;k<entryWords.length&&flag;k++){
						
						if(newsSentence[j].indexOf(entryWords[k])==-1){
							flag = false;		
						}
					}
					
					if(flag ==true){
						System.out.println(newsSentence[j]+"��"+entry[i]);
						isExist=false;
						entryNum++;
					}
				}
			}
			NewsSr.updateZweight(pid, zweight*(2+(float)entryNum/10));
			NewsSr.updateWeight(pid, weight*2f);
		}
		
	}
	
	public static void isSyn(int pid,Word word,Word wordOther,String news){
		
		
		StringBuffer wordSyn = new StringBuffer();
		wordSyn.append(word.getName()).append("/").append(word.getSpe())
		.append(" ").append(wordOther.getName()).append("/").append(wordOther.getSpe());
		
		String wordSynStr = wordSyn.toString();
		//System.out.println(wordSynStr);
		int  num = (news.length()-news.replaceAll(wordSynStr, "").length())/wordSynStr.length();
	
		//���Ű�ϳɴ���Ŀ����0
		if(num>0){
			if(Word.isWordExistCopy(pid,word.getName()+wordOther.getName(), word.getSpe()))
			{
				Word wordNew = new Word(word.getName()+wordOther.getName(), word.getSpe());
				wordNew.NumPlusCopy(num);
				word.NumMinusCopy(num);
				wordOther.NumMinusCopy(num);
				
			}
			else{
				Word.insertCopy(pid,word.getName()+wordOther.getName(), num,word.getSpe());	
				word.NumMinusCopy(num);
				wordOther.NumMinusCopy(num);
			}
			NewsSr.updateCopy(wordSyn.toString(), word.getName()+wordOther.getName()+"/"+word.getSpe());
		}
		//System.out.println(wordSynStr+":"+num);
		
	}
	
}