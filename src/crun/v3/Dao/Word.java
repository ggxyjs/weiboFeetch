package crun.v3.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.dao.DBManager;

public class Word{
	
	private static int minNum =2;
	private int id;
	private String name;
	private int num;
	private String spe;
	private int type;
	private float weight;
	private float zweight;
	private int pid;
	public Word(int id) {
		super();
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpe() {
		return spe;
	}
	public void setSpe(String spe) {
		this.spe = spe;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set type=").append(type).append(" where id=").append(this.id);
		System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());	
		
	}
	
	
	public float getWeight() {
		return this.weight;
	}
	
	public float getZweight() {
		return this.zweight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set weight = ").append(weight).append(" where id=").append(this.id);
		System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());	
		
	}
	
	public void setZweight(float weight) {
		this.weight = weight;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set zweight = ").append(weight).append(" where id=").append(this.id);
		System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());	
		
	}
	
	public Word(int id, String name, int num, String spe) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.spe = spe;
	}
	public Word(String name,String spe,int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where name = '").append(name).append("' and spe = '")
		.append(spe).append("' and tid = ").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				this.id = rs.getInt(1);
				this.name = rs.getString(2);
				this.num = rs.getInt(3);
				this.spe = rs.getString(4);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbm.close();
		}

	}
	public Word(String name,String spe,int id,int version){
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where name = '").append(name).append("' and spe = '")
		.append(spe).append("' and tid = ").append(id).append(" and version = " )
		.append(version);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				this.id = rs.getInt(1);
				this.name = rs.getString(2);
				this.num = rs.getInt(3);
				this.spe = rs.getString(4);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbm.close();
		}

	}
	
	public Word(String name){
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe,weight  from ").append(TName.WORD_TABLE_NAME)
		.append(" where name = '").append(name).append("'");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				this.id = rs.getInt(1);
				this.name = rs.getString(2);
				this.num = rs.getInt(3);
				this.spe = rs.getString(4);
				this.weight = (float) rs.getDouble(5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dbm.close();
		}

	}
	
	public static int insert(String word,int num,String spe,int tid){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.WORD_TABLE_NAME)
		.append("(name,num,spe,tid) values('").append(word)
		.append("','").append(num).append("','")
		.append(spe).append("','"+tid+"')");
		
		DBManager dbm =new DBManager();	
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}
	
	public static int insert(String word,int num,String spe,int tid,int version){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.WORD_TABLE_NAME)
		.append("(name,num,spe,tid,version) values('").append(word)
		.append("','").append(num).append("','")
		.append(spe).append("','"+tid+"','").append(version).append("')");
		
		DBManager dbm =new DBManager();	
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
		
	}

	
public static boolean isWordExist(String word,String spe,int id,int version){
		
		boolean isExist =false;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.WORD_TABLE_NAME)
		.append(" where name = '").append(word).append("' and spe = '")
		.append(spe).append("' and tid = ").append(id).append(" and version = ")
		.append(version);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				if(rs.getInt(1)>0){
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
	public static boolean isWordExist(String word,String spe,int id){
		
		boolean isExist =false;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.WORD_TABLE_NAME)
		.append(" where name = '").append(word).append("' and spe = '")
		.append(spe).append("' and tid = ").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				if(rs.getInt(1)>0){
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
	

	public int NumPlus(int num){
		this.num = this.num+num;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set num = ").append(this.num)
		.append(" where id = ").append(this.id);
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
	
	public int NumMinus(int num){
		this.num = this.num-num;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set num = ").append(this.num)
		.append(" where id = ").append(this.id);
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
	public static void updateSingleWord(int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set type=1 where length(name)<4 and tid= ").append(id);
		System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());

		
	}
	
	public static void updateUseWord(){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.WORD_TABLE_NAME)
		.append(" set type=2 where length(name)>4 and num >1 ");
	//	System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());

		
	}
	
	
	
	public static Word getUseWord(){
		
		Word word= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where type = 2 order by id");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					word = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return word;
		
	}
	public static  Word[] getWordOfEntry2(Entry entry){
		
		String e = entry.getName().replaceAll(",", "|");
		Word[] words= null;
		//System.out.println(e);
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num >1 and name not regexp '").append(e).append("'");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
						);
				}
			}
	
		}
		catch(Exception ee){
			ee.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return words;
		
	}
	

	
	public static Word getSingleWord(int id){
		
		Word word= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where type = 1 and tid = ").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					word = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return word;
		
	}
	
	public static  Word[] getTimeWord(int id){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where  num>").append(1)
		.append(" and spe regexp 't' and tid = ").append(id)
		.append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public static  Word[] getAdrWord(int id){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num>").append(minNum)
		.append(" and spe regexp 'ns' and tid = ")
		.append(id).append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public static  Word[] getObjWord(int id){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num>").append(minNum)
		.append(" and spe regexp 'n' and spe not regexp 'ns' and tid = ")
		.append(id).append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public  Word[] getLastUseWord(){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where type=2 and id>").append(this.id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public  Word[] getWordsExceptMe(int id){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where type=1 and name!='").append(this.name).append("' and tid =").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public static Word[] getWords(int id){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num>").append(minNum).append(" and tid =").append(id);
		
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public static Word[] getWordsEvl(int id,int version){
		
		Word[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,spe from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num>").append(minNum).append(" and tid =").append(id)
		.append(" and version =").append(version);
		
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new Word[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = new Word(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getString(4)
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
	
		return words;
		
	}
	
	public static String[] getWordsStr(int id){
		
		String[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select name from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num>").append(minNum).append(" and tid =").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = rs.getString(1);
						
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return words;
		
	}
	
	public static String[] getMaxWordsStr(int id){
		
		String[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select name from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num> ((SELECT max(num) FROM `2wb_word` where length(name)>4  and tid =")
		.append(id).append(")*0.5 ) and tid =").append(id);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = rs.getString(1);
						
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return words;
		
	}
	
	public static String[] getMidWordsStr(int id){
		
		String[] words= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select name from ").append(TName.WORD_TABLE_NAME)
		.append(" where length(name)>4 and num< ((SELECT max(num) FROM `2wb_word` where length(name)>4  and tid =")
		.append(id).append(")*0.5 ) and num> ((SELECT max(num) FROM `2wb_word` where length(name)>4  and tid =")
		.append(id).append(")*0.2 )  and tid =").append(id).append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				words = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					words[rs.getRow()-1] = rs.getString(1);
						
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return words;
		
	}
	
}