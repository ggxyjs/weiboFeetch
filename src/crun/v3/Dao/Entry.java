package crun.v3.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.dao.DBManager;

public class Entry {
	
	private static double minWeight =0.1;
	private int id;
	private String name;
	private int num;
	private int type;
	private double  weight;
	
	public Entry(int id,int num) {
		super();
		this.id = id;
		this.num = num;
	}
	
	public Entry(int id,String name,double weight) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
	}
	
	public Entry(int id,String name,int num,double weight) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.weight = weight;
	}
	public Entry(int id,String name,int num) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
	}
	public Entry(int id,String name) {
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
	public void setWeight(float weight) {
		this.weight = weight;
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME)
		.append(" set weight = ").append(weight).append(" where id=").append(this.id);
		System.out.println(sql);
		DBManager dbm =new DBManager();

		dbm.updateByStmt(sql.toString());	
		
	}
	

	
	public static String getMaxTime(int id){
		
		String entry =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select max(time) from ").append(TName.NEWS_TABLE_NAME)
		.append(" where tid =").append(id);
			System.out.print(sql.toString());
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				
				if(rs.next())
				{	
					entry = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}
	
public static Entry[] getMaxEntry(int id){
		
		Entry[] Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,weight from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where weight >").append(minWeight).append(" and tid =").append(id)
		.append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				Entrys = new Entry[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					Entrys[rs.getRow()-1] = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getDouble(4)
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
	
		return Entrys;
		
	}
public static Entry[] getEntryByIs2(int x,int id,int tid){
	
	Entry[] Entrys= null;
	
	StringBuffer sql =new StringBuffer();
	sql.append("select id,name,num,weight from ").append(TName.ENTRY_TABLE_NAME)
	.append(" where isUpdate =").append(x).append(" and (tid =").append(id)
	.append(" or tid =").append(tid).append(") order by num desc");
	System.out.println(sql.toString());
	DBManager dbm =new DBManager();
	ResultSet rs =null;
	
	try
	{
		rs = dbm.retrieveByStmt(sql.toString());
		if(null != rs)
		{	
			int count = DBManager.getRowNum(rs);
			Entrys = new Entry[count];
			rs.beforeFirst();
			while(rs.next())
			{	
				Entrys[rs.getRow()-1] = new Entry(
						rs.getInt(1)
						,rs.getString(2)
						,rs.getInt(3)
						,rs.getDouble(4)
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

	return Entrys;
	
}

public static List<String> getEvlEntryByIs(int x,int id,int version){
	
	String[] midWords = Word.getMidWordsStr(id);
	List<String> entrys= new ArrayList<String>();
	
	for(String word:midWords){
		
		StringBuffer sql =new StringBuffer();
		sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where name regexp '").append(word).append("' and isUpdate =").append(x).append(" and tid =").append(id)
		.append(" and version=").append(version).append(" and num>2").append(" order by num desc");
	
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				while(rs.next())
				{	

					if(!entrys.contains(rs.getString(1))){
						entrys.add(rs.getString(1));
					}
				}
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	StringBuffer sql =new StringBuffer();
	sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
	.append(" where ").append(" isUpdate =").append(x).append(" and tid =").append(id)
	.append(" order by num desc");

	DBManager dbm =new DBManager();
	ResultSet rs =null;
	
	try
	{
		rs = dbm.retrieveByStmt(sql.toString());
		if(null != rs)
		{	
			while(rs.next())
			{	

				if(!entrys.contains(rs.getString(1))){
					entrys.add(rs.getString(1));
				}
			}
		}

	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		dbm.close();
	}
	
	return entrys;

}


	public static List<String> getSortEntryByIs(int x,int id){
	
		String[] midWords = Word.getMidWordsStr(id);
		List<String> entrys= new ArrayList<String>();
		
		for(String word:midWords){
			
			StringBuffer sql =new StringBuffer();
			sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
			.append(" where name regexp '").append(word).append("' and isUpdate =").append(x).append(" and tid =").append(id)
			.append(" order by num desc");
		
			DBManager dbm =new DBManager();
			ResultSet rs =null;
			
			try
			{
				rs = dbm.retrieveByStmt(sql.toString());
				if(null != rs)
				{	
					while(rs.next())
					{	

						if(!entrys.contains(rs.getString(1))){
							entrys.add(rs.getString(1));
						}
					}
				}
	
			}
			catch(Exception e){
				e.printStackTrace();
			}
	
		}
		
		StringBuffer sql =new StringBuffer();
		sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where ").append(" isUpdate =").append(x).append(" and tid =").append(id)
		.append(" order by num desc");
	
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				while(rs.next())
				{	

					if(!entrys.contains(rs.getString(1))){
						entrys.add(rs.getString(1));
					}
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			dbm.close();
		}
		
		return entrys;
	
	}
	
	public static Entry[] getEntryByIs(int x,int id){
		
		Entry[] Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,weight from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where isUpdate =").append(x).append(" and tid =").append(id)
		.append(" order by num desc");
		System.out.println(sql.toString());
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				Entrys = new Entry[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					Entrys[rs.getRow()-1] = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getDouble(4)
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
	
		return Entrys;
		
	}
	
	public static Entry[] getEntryByIsEvl(int x,int id,int version){
		
		Entry[] Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,weight from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where weight>0").append("").append(" and tid =").append(id)
		.append(" and version = ").append(version)
		.append(" order by num desc");
		System.out.println(sql.toString());
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				Entrys = new Entry[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					Entrys[rs.getRow()-1] = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getDouble(4)
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
	
		return Entrys;
		
	}
	
	public static Entry[] getEntryByIs(int x){
		
		Entry[] Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num,weight from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where isUpdate =").append(x);
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				Entrys = new Entry[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					Entrys[rs.getRow()-1] = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
							,rs.getDouble(4)
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
	
		return Entrys;
		
	}
	
public static Entry getSynEntryEvl(int id,int version){
		
		Entry Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where num>2 AND isUpdate =0 and tid = ")
		.append(id).append(" and version = ").append(version)
		.append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				if(rs.next())
				{	
					Entrys = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
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
	
		return Entrys;
		
	}
	
public static Entry getSynEntry(int id){
		
		Entry Entrys= null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where num>2 AND isUpdate =0 and tid = ")
		.append(id).append(" order by num desc");
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				if(rs.next())
				{	
					Entrys = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
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
	
		return Entrys;
		
	}
public static Entry[] getConEntryEvl(String name,int id,int version){
	
	String[] words = name.replaceAll("\\[|\\]", "").split(",");
	StringBuffer wordStr =  new StringBuffer();
	wordStr.append("name regexp '").append(words[0]).append("'");
		
	for(int i =1;i<words.length;i++){
		wordStr.append(" and name regexp '").append(words[i]).append("'");
		
	}
	
	Entry[] entrys =null;
	
	StringBuffer sql =new StringBuffer();
	sql.append("select id,name,num from ").append(TName.ENTRY_TABLE_NAME)
	.append(" where isUpdate =0 and ").append(wordStr.toString())
	.append(" and tid = ").append(id).append(" and version =")
	.append(version).append(" order by num desc");
	
	//System.out.println(sql.toString());
	
	DBManager dbm =new DBManager();
	ResultSet rs =null;
	
	try
	{
		rs = dbm.retrieveByStmt(sql.toString());
		if(null != rs)
		{	
			int count = DBManager.getRowNum(rs);
			entrys = new Entry[count-1];
			rs.first();
			while(rs.next())
			{	
				entrys[rs.getRow()-2] = new Entry(
						rs.getInt(1)
						,rs.getString(2)
						,rs.getInt(3)
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

	return entrys;
	
}

	public static Entry[] getConEntry(String name,int id){
		
		String[] words = name.replaceAll("\\[|\\]", "").split(",");
		StringBuffer wordStr =  new StringBuffer();
		wordStr.append("name regexp '").append(words[0]).append("'");
			
		for(int i =1;i<words.length;i++){
			wordStr.append(" and name regexp '").append(words[i]).append("'");
			
		}
		
		Entry[] entrys =null;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select id,name,num from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where isUpdate =0 and ").append(wordStr.toString())
		.append(" and tid = ").append(id).append(" order by num desc");
		
		//System.out.println(sql.toString());
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{	
				int count = DBManager.getRowNum(rs);
				entrys = new Entry[count-1];
				rs.first();
				while(rs.next())
				{	
					entrys[rs.getRow()-2] = new Entry(
							rs.getInt(1)
							,rs.getString(2)
							,rs.getInt(3)
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
	
		return entrys;
		
	}
	
	public static String[] getEvlEntry(int id,int version){
		
		
		String[] entry =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where tid = ").append(id).append(" and version =").append(version);
			
		System.out.println(sql.toString());
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				entry = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					entry[rs.getRow()-1] = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}

	public static String[] getAllEntry(int id){
		
		
		String[] entry =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select name from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where tid = ").append(id);
			
		System.out.println(sql.toString());
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				entry = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					entry[rs.getRow()-1] = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}
	
	public static String[] getAllEntry(String word){
		
		String tname = TName.ENTRY_TABLE_NAME;
		
		String[] entry =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select name from ").append(tname)
		.append(" where name regexp '").append(word).append("'");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				entry = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					entry[rs.getRow()-1] = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}
	
	
	public static String getEntry(int i){
		
		String tname = TName.ENTRY_TABLE_NAME;
		if(i ==1){
			tname = TName.ENTRY_TABLE_NAME + "_copy";
		}
			
		
		
		String entry =  null;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select name from ").append(tname)
		.append(" where isUpdate is null limit 1");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				
				if(rs.next())
				{	
					entry = rs.getString(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}
	
	public static String[] getEntry(int i ,String word){
		
		String tname = TName.ENTRY_TABLE_NAME;
		if(i ==1){
			tname = TName.ENTRY_TABLE_NAME + "_copy";
		}
			
		
		
		String entry[] =  null;
		
		DBManager dbm = new DBManager();
		DBManager dbmIn = new DBManager();
		
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select name,id from ").append(tname)
		.append(" where  isUpdate is null and name regexp '")
		.append(word).append("'");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				int count = DBManager.getRowNum(rs);
				entry = new String[count];
				rs.beforeFirst();
				while(rs.next())
				{	
					entry[rs.getRow()-1] = rs.getString(1);
					
					sql.delete(0,sql.length());
					sql.append("update ").append(tname)
					.append(" set isUpdate = 1 where id = ")
					.append(rs.getInt(2));
					
					dbmIn.insertByStmt(sql.toString());
					
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return entry;
	}
	

	
	public static int getCount(){
		int count =  0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME);
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					count = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return count;
	}
	
	public static int getCount(String word){
		int count =  0;
		
		DBManager dbm = new DBManager();
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where name regexp '").append(word).append("'");
			
		try
		{
			rs = dbm.retrieveByStmt(sql.toString());
			if(null != rs)
			{
				if(rs.next())
				{	
					count = rs.getInt(1);
				}
			}
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			dbm.close();
		}
	
		return count;
	}
	
	public static int insert(String name,int type,int num,int id,int version){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.ENTRY_TABLE_NAME)
		.append("(name,type,num,isUpdate,tid,version) values('").append(name).append("','")
		.append(type).append("','").append(num).append("','0','")
		.append(id).append("','").append(version).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
	}
	

	
	public static int insert(String name,int type,int num,int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.ENTRY_TABLE_NAME)
		.append("(name,type,num,isUpdate,tid) values('").append(name).append("','")
		.append(type).append("','").append(num).append("','0','").append(id).append("')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
	}
	
	public static int insert21(String name,String type,int num){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into ").append(TName.ENTRY_TABLE_NAME1)
		.append("(name,type,num,isUpdate) values('").append(name).append("','")
		.append(type).append("','").append(num).append("','0')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
	}
	

	
	public static boolean isEmpty21(){
		
		boolean isExist =true;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where ").append(" isUpdate = 0");
		
		//System.out.println(sql.toString());
		
		DBManager dbm =new DBManager();
		ResultSet rs =null;
		
		rs = dbm.retrieveByStmt(sql.toString());
		try {
			if(rs!=null&&rs.next()){
				if(rs.getInt(1)>0){
					isExist = false;
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
	
	public static int insert3(String name,String type,int num,int x){
		
		StringBuffer sql =new StringBuffer();
		sql.append("insert into 1wb_").append(x)
		.append("entry (name,type,num,isUpdate) values('").append(name).append("','")
		.append(type).append("','").append(num).append("','0')");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.insertByStmt(sql.toString());
		
		return i;
	}
	
	public static boolean isEntryExist(String name,int id){
		
		String[] words = name.substring(1,name.length()-1).split(", ");
		StringBuffer wordStr =  new StringBuffer();
		wordStr.append("name regexp '").append(words[0]).append("'");
			
		for(int i =1;i<words.length;i++){
			wordStr.append(" and name regexp '").append(words[i]).append("'");
			
		}
		
		boolean isExist =false;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where tid= ").append(id).append(" and ").append(wordStr.toString());
		
		//System.out.println(sql.toString());
		
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
	
	public static boolean isEntryExist1(String name,int x){
		
		String[] words = name.split(",");
		StringBuffer wordStr =  new StringBuffer();
		wordStr.append("name regexp '").append(words[0]).append("'");
			
		for(int i =1;i<words.length;i++){
			wordStr.append(" and name regexp '").append(words[i]).append("'");
			
		}
		
		boolean isExist =false;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from 1wb_").append(x)
		.append("entry where ").append(wordStr.toString());
		
		//System.out.println(sql.toString());
		
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
		System.out.println(isExist);
		return isExist;
		
	}
	
	public static boolean isEntryExist21(String name){
		
		String[] words = name.split(",");
		StringBuffer wordStr =  new StringBuffer();
		wordStr.append("name regexp '").append(words[0]).append("'");
			
		for(int i =1;i<words.length;i++){
			wordStr.append(" and name regexp '").append(words[i]).append("'");
			
		}
		
		boolean isExist =false;
		
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME1)
		.append(" where isUpdate =0 and ").append(wordStr.toString());
		
		//System.out.println(sql.toString());
		
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
		System.out.println(isExist);
		return isExist;
		
	}
	
	public static int NumPlus(String name,int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME)
		.append(" set num = num+1")
		.append(" where name = '").append(name).append("'")
		.append(" and tid = ").append(id);
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}

	public static int updateIs(String name,int x){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME1)
		.append(" set isUpdate = ").append(x)
		.append(" where name = '").append(name).append("'");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
	public static int updateIsById(int id,int x){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME)
		.append(" set isUpdate = ").append(x)
		.append(" where id = ").append(id).append("");
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
	public  boolean isUpdate(int x){
		
		boolean isExist =false;
		StringBuffer sql =new StringBuffer();
		sql.append("select count(*) from ").append(TName.ENTRY_TABLE_NAME)
		.append(" where isUpdate = ").append(x)
		.append(" and  id = ").append(this.id);
		
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
	
	
	public static int updateIsAllEvl(int x,int id,int version){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME)
		.append(" set isUpdate = ").append(x).append(" where tid = ")
		.append(id).append(" and version=").append(version);
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
	public static int updateIsAll(int x,int id){
		
		StringBuffer sql =new StringBuffer();
		sql.append("update ").append(TName.ENTRY_TABLE_NAME)
		.append(" set isUpdate = ").append(x).append(" where tid = ")
		.append(id);
		
		DBManager dbm =new DBManager();
		
		int i = dbm.updateByStmt(sql.toString());
		return i;
	}
	
}