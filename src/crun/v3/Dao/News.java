package crun.v3.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.dao.DBManager;
import crun.v3.NewsSr;


public class News{
	public static int pageSize =10;
	private int id;
	private String content;
	private String time;
	
	public News(int id) {
		super();
		this.id = id;
	
	}	
	
	public News(int id,String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public News(int id,String content,String time) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
	}
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	
	public int getWtid() {
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		int wtid = 1;
		StringBuffer sql = new StringBuffer();
		sql.append("select wtid from ").append(TName.NEWS_TABLE_NAME)
		.append(" where id = ").append(this.id);
	
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					wtid = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return wtid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public String getTime() {
		return time;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public static int getTrackId(int id){
		return id+10000;
	}
	
	public static int getTrackId1(int id){
		return id+20000;
	}
	
	public double getWordWeight() {
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		double ww = 1;
		StringBuffer sql = new StringBuffer();
		sql.append("select weight from ").append(TName.NEWS_TABLE_NAME)
		.append(" where id = ").append(this.id);
		System.out.println(sql.toString());
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					ww = rs.getDouble(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return ww;
	}
	
	public double getTotleWeight() {
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		double ww = 1;
		StringBuffer sql = new StringBuffer();
		sql.append("select (weight*0.5+oweight*0.5) from ").append(TName.NEWS_TABLE_NAME)
		.append(" where id = ").append(this.id);
		System.out.println(sql.toString());
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					ww = rs.getDouble(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return ww;
	}
	
	public static void updateNews(int id){

		
		DBManager dbm = new DBManager();
		
		double tw = NewsSr.getTotleWeight(id);
		double ww = NewsSr.getWordWeight(id);
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("update ").append(TName.NEWS_TABLE_NAME).append(" set wtid =").append(1)
				.append(" where weight>").append(ww).append(" and tid =0 ");
			
		dbm.updateByStmt(sql2.toString());
		
		StringBuffer sql3 = new StringBuffer();
		sql3.append("update ").append(TName.NEWS_TABLE_NAME).append(" set wtid =").append(0)
				.append(" where tid =0 and (wtid is null or wtid =0)");
			
		dbm.updateByStmt(sql3.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(TName.NEWS_TABLE_NAME).append(" set tid =").append(getTrackId(id))
							.append(" where weight>").append(ww).append(" and tid =0 ");
		//.append(" where (weight*0.5+oweight*0.5)>").append(tw).append(" and tid =0 ");
			
		dbm.updateByStmt(sql.toString());
		
		StringBuffer sql1 = new StringBuffer();
		sql1.append("update ").append(TName.NEWS_TABLE_NAME).append(" set tid =").append(getTrackId1(id))
				
		.append(" where tid =0 ");
			
		dbm.updateByStmt(sql1.toString());
		

	}

	
	public static News[] getNewsByTid(int id,int page){
		News[] news =  null;
		
		int start = (page-1)*pageSize;
		//int end = (page)*pageSize;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt,time from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id).append(" order by time,weight desc limit ").append(start)
		.append(",").append(10);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new News[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new News(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getString(3)
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
	
	public static News[] getAllNews(int id){
		News[] news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new News[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new News(
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
	
	

	
	public static News[] getElvNews(int id){
		News[] news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id)
		.append(" and version is null order by time limit 0,50");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new News[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new News(
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
	
	public static int getNowVersion(int id){
		

		DBManager dbm = new DBManager();
		ResultSet rs = null;
		int now = 1;
		StringBuffer sql = new StringBuffer();
		sql.append("select max(version) from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id);
			System.out.println(sql);
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					now = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return now;
	}
	
	public static int getTotalPage(int id){
		

		DBManager dbm = new DBManager();
		ResultSet rs = null;
		int tPage = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					tPage = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return tPage;
	}
	public static void updateElvNews(int id,int version){

		
		DBManager dbm = new DBManager();	
		StringBuffer sql2 = new StringBuffer();
		sql2.append("update ").append(TName.NEWS_TABLE_NAME).append(" set version =")
		.append(version).append(" where tid =").append(id).append(" and version is null order by time limit 50");
			
		dbm.updateByStmt(sql2.toString());
		
	}
	public  static boolean isEvolve(int id){
		
		boolean isExist =false;
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid = ").append(id).append(" and version is null");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		System.out.println(sql);
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				
				if(rs.getInt(1)>50){
					System.out.println(rs.getInt(1));
					isExist = true;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbm.close();
		}

		return isExist;
	}
	
	public static News[] getAllNews(){
		News[] news =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,txt from ").append(TName.NEWS_TABLE_NAME).append("");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				news = new News[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					news[rs.getRow()-1] = new News(
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
	

	
}