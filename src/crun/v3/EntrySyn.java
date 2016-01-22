package crun.v3;

import common.dao.DBManager;

import crun.v3.Dao.Entry;
import crun.v3.Dao.News;
import crun.v3.Dao.TName;
import crun.v3.Dao.Word;

public class EntrySyn{
	
	public static double  r = 0.3;
	public static double  r1 = 0.7;
	public static void main(String[] args){
		DeleteEntry(13);
		
		DeleteWord(13);
		DeleteConSeg(13);
	}
	
	public static void SynEvl(int id){
		int version = News.getNowVersion(id);
		Entry entry =Entry.getSynEntryEvl(id,version);
		//System.out.println(entry.getName());
		Entry.updateIsAllEvl(0,id,version);
		while(entry!=null){
			
			Entry[] conEntrys = Entry.getConEntryEvl(entry.getName(),id,version);
			int entryNum = entry.getNum();
			//System.out.println(entry.getName());
			
			if(conEntrys.length>0){
				int cNum = conEntrys[0].getNum();
			//	System.out.println(entry.getName());
				//System.out.println(cNum+":"+r1*entryNum+(cNum>r1*entryNum));
				if(cNum>r1*entryNum){
					Entry.updateIsById(entry.getId(), 1);
				}
				
				
			}
			
			for(Entry cEntry:conEntrys){
				int cNum = cEntry.getNum();
				
				if(cNum<r*entryNum){
					Entry.updateIsById(cEntry.getId(), 1);
				}
				
				
			}
			if(entry.isUpdate(0)){
				Entry.updateIsById(entry.getId(), 2);
			}
			entry =Entry.getSynEntryEvl(id,version);
		}

	}
	
	public static void Syn1(int id){
		Entry entry =Entry.getSynEntry(id);
		//System.out.println(entry.getName());
		Entry.updateIsAll(0,id);
		while(entry!=null){
			
			Entry[] conEntrys = Entry.getConEntry(entry.getName(),id);
			int entryNum = entry.getNum();
			//System.out.println(entry.getName());
			
			if(conEntrys.length>0){
				int cNum = conEntrys[0].getNum();
			//	System.out.println(entry.getName());
				//System.out.println(cNum+":"+r1*entryNum+(cNum>r1*entryNum));
				if(cNum>r1*entryNum){
					Entry.updateIsById(entry.getId(), 1);
				}
				
				
			}
			
			for(Entry cEntry:conEntrys){
				int cNum = cEntry.getNum();
				
				if(cNum<r*entryNum){
					Entry.updateIsById(cEntry.getId(), 1);
				}
				
				
			}
			if(entry.isUpdate(0)){
				Entry.updateIsById(entry.getId(), 2);
			}
			entry =Entry.getSynEntry(id);
		}

	}
	
	
	public static void DeleteEntry(int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("delete from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where tid = ")
		.append(id);
		
		DBManager dbm =new DBManager();
		
		dbm.deleteByStmt(sql.toString());
	}
	public static void DeleteWord(int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("delete from ").append(TName.WORD_TABLE_NAME)
		.append(" where tid = ")
		.append(id);
		
		DBManager dbm =new DBManager();
		
		dbm.deleteByStmt(sql.toString());
	}
	public static void DeleteConSeg(int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("delete from ").append(TName.NEWSSR_TABLE_NAME)
		.append(" where tid = ")
		.append(id);
		
		DBManager dbm =new DBManager();
		
		dbm.deleteByStmt(sql.toString());
	}
	public static void DeleteCon(int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("delete from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ")
		.append(id);
		
		DBManager dbm =new DBManager();
		
		dbm.deleteByStmt(sql.toString());
	}
	
	
}