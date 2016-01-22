package crun.v3.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.dao.DBManager;

public class Topic {

	private int id;
	private String name;
	private int num;
	private int type;
	private double  weight;
	
	public Topic(int id,int num) {
		super();
		this.id = id;
		this.num = num;
	}
	
	public Topic(int id,String name,double weight) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
	}
	
	public Topic(int id,String name,int num,double weight) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.weight = weight;
	}
	public Topic(int id,String name,int num) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
	}
	public Topic(int id,String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getNum() {
		return num;
	}	
	public int getId() {
		return id;
	}	
	public String getName() {
		return name;
	}
	public double getWeight() {
		return weight;
	}

	
	public static Topic[] getAllTopic(){
		
		Topic[] topics= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name from ").append(TName.TOPIC_TABLE_NAME)
		.append(" order by id desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				topics = new Topic[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					topics[rs.getRow()-1] = new Topic(
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
	
		return topics;
		
	}
	
	public static int insert(String name){
		
		int i=0;
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.TOPIC_TABLE_NAME)
		.append("(name) values('").append(name).append("')");
		
		DBManager dbm =new DBManager();
		i = dbm.insertByStmt(sql.toString());
		dbm.close();
		return i;
	}
	
	
}