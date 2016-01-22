package crun.v3;

import java.sql.ResultSet;

import common.dao.DBManager;
import crun.v3.Dao.TName;


public class NewsSr{

	private int id;
	private String content;
	private int pid;
	public NewsSr(int id) {
		super();
		this.id = id;
	
	}	
	
	public NewsSr(int id,String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public NewsSr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public static NewsSr[] getEvlNews(int id,int version){
		NewsSr[] news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt from ").append(TName.NEWSSR_TABLE_NAME)
		.append(" where tid =").append(id).append(" and version = ").append(version);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new NewsSr[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new NewsSr(
							rs.getInt(1)
							,rs.getString(2)
						);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return news;
	}
	
	public static NewsSr[] getAllNews(int id){
		NewsSr[] news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt from ").append(TName.NEWSSR_TABLE_NAME)
		.append(" where tid =").append(id);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new NewsSr[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new NewsSr(
							rs.getInt(1)
							,rs.getString(2)
						);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return news;
	}
	
	public static double getWordWeight( int pid ){
		double weight =  0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sum(weight)/count(*) FROM `2wb_content` where tid =")
		.append(pid );
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					weight = rs.getDouble(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return weight*0.7;
	}
	
	public static double getTotleWeight( int pid ){
		double weight =  0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT (sum(weight)*0.7+sum(oweight)*0.3)/count(*) FROM `2wb_content` where tid =")
		.append(pid );
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					weight = rs.getDouble(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return weight*0.7;
	}
	
	
	public static int getNewsIdCopy( int pid ){
		int news =  0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id from ").append(TName.NEWSSR_TABLE_NAME)
		.append("_copy where pid =").append(pid );
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					news = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return news;
	}
	
	public static String getNewsCopy( int pid ){
		String news =  "";
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select txt from ").append(TName.NEWSSR_TABLE_NAME)
		.append("_copy where pid =").append(pid );
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					news = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return news;
	}
	
	public static String pid( int pid ){
		String news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select txt from ").append(TName.NEWSSR_TABLE_NAME)
		.append("_copy where pid =").append(pid );
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					news = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return news;
	}
	
	public static int insert(String word,int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.NEWSSR_TABLE_NAME)
		.append("(txt,tid) values('").append(word).append("','"+id+"') ");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int insert(String word,int id,int version){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.NEWSSR_TABLE_NAME)
		.append("(txt,tid,version) values('").append(word)
		.append("','"+id+"','").append(version).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int insertCopy(String word,int pid){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.NEWSSR_TABLE_NAME).append("_copy")
		.append("(txt,pid) values('").append(word).append("','")
		.append(pid).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int update(String word ,String wordTo){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.NEWSSR_TABLE_NAME)
		.append(" set txt = replace(txt,'").append(word).append("','")
		.append(wordTo).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int updateCopy(String word ,String wordTo){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.NEWSSR_TABLE_NAME).append("_copy")
		.append(" set txt = replace(txt,'").append(word).append("','")
		.append(wordTo).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int updateWeight(int id,float weight){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.NEWS_TABLE_NAME).append("")
		.append(" set weight = ").append(weight).append(" where id=").append(id);
		
		DBManager dbm =new DBManager();
		
		System.out.println(sql.toString());
		int i = dbm.updateByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int updateZweight(int id,double weight){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.NEWS_TABLE_NAME).append("")
		.append(" set oweight = ").append(weight).append(" where id=").append(id);
		
		DBManager dbm =new DBManager();
		
		System.out.println(sql.toString());
		int i = dbm.updateByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int updateCopyByPid(int pid,float weight ,int num){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.NEWSSR_TABLE_NAME).append("_copy")
		.append(" set weight = ").append(weight).append(" , num = ")
		.append(num).append(" where pid=").append(pid);
		
		DBManager dbm =new DBManager();
		
		System.out.print(sql.toString());
		int i = dbm.updateByStmt(sql.toString());
		
		return i;
		
	}
	
	

}