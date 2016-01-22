package crun.v3;

import java.util.Vector;

import crun.v3.Dao.Entry;

public class EntrySeg{
	
	public static void main(String[] args){
		
		
		String[] entrys = Entry.getAllEntry(0);
		entry
		String entry = Entry.getEntry(0);
		
		/*String[] entrys = Entry.getAllEntry(0);
		Vector<String> vec = new Vector<String>();
		
		for(String entry:entrys){
			
			String[] words = entry.substring(1,entry.length()-1).split(", ");
			
			for(String word:words){
				
				if(!vec.contains(word)){
					vec.add(word);
				}
				
			}
		}
		
		for(int i=0;i<1;i++){
			String[] entrysByWord = Entry.getAllEntry(vec.get(i));
			for(String entry:entrysByWord){
				
				System.out.println(entry);
			}
			
		}
		*/
	}
}