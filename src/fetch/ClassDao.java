package fetch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ClassDao{
	
	public static String getHdId(String newName) {
	
		String regEx1="��ҵ|����|�µ�|פ|��Ļ|����";
		String regEx2="Ԫ|����|��|��|��|��ɱ|��|˦|����|��|л|��|�ڹ���|����|�Ż�|����|�ۿ�|����|��|���|��|����|��|�ͼ�";
		String regEx3="��|����|����|����";
		String regEx5="���|����|��ף";
		String regEx6="��Ʒ|����|����|����";
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��

	    int e=0;
		boolean bb=m.find();
		if(bb){
			e = 3548;
			}else {
			p=Pattern.compile(regEx2); 
			m=p.matcher(newName);
			bb=m.find();
			if(bb){
				e = 3546;
			}else {
				p=Pattern.compile(regEx3); 
				m=p.matcher(newName);
				bb=m.find();
				if(bb){
					e = 3545;
				}else {
	        			p=Pattern.compile(regEx5); 
	        			m=p.matcher(newName);
	        			bb=m.find();
	        			if(bb){
	        				e = 3549;
	        			}else {
		        			p=Pattern.compile(regEx6); 
		        			m=p.matcher(newName);
		        			bb=m.find();
		        			if(bb){
		        				e = 3550;
		        			}
		        	
		        	}}}}
		return e+"";
	}
	
	public static String getXwId(String newName) {


		
		String regEx1="�仧|��ҵ|����|�µ�|פ|��Ļ|����|��¼|�ǳ�|��Ӫҵ|����|��Ա";
		String regEx2="��л|����|�ж�|VIP|��|��|����|�ۿ�|��|��|��|���|��|����|��|�ͼ�|���|��|��|����|����|����|��Ʒ|����|����|����";
		String regEx3="����|Я��|�ϰ�|����|����|�ƹ�|�Ƴ�|���|��ף";
		//String regEx4="�|����|����|���˽�|���׽�|��Ϧ|ĸ�׽�|��ͯ��";
		String regEx4="�ܼ�|���³�|�ܾ���|�Ļ�|��չ|�ƺ�|����|��|����|����|����|������|����";
		String regEx5="����|ǩԼ|Э��|����|�Ӳ�|ר��|ͦ��|�����|�Ի�|����|����|��Ȩ|����|�쵼|��Ļ|��Ļ|����|����";
		String regEx6="����|����|����|�о�";
		String regEx7="ϵ��|�׷�|Ʒ��|�Ϲ�|����|�ϼ�|����|����|�¿�|��Ʒ|����|����|����|����|ϵ��";
		Pattern p=Pattern.compile(regEx1); 
	    Matcher m=p.matcher(newName);//bΪ��Ҫƥ���΢��
	    
	    int e=0;
		boolean bb=m.find();
		if(bb){
			e = 3446;
			}else {
			p=Pattern.compile(regEx2); 
			m=p.matcher(newName);
			bb=m.find();
			if(bb){
				e = 5340;
			}else {
				p=Pattern.compile(regEx3); 
				m=p.matcher(newName);
				bb=m.find();
				if(bb){
					e = 6003;
				}else {
	        			p=Pattern.compile(regEx4); 
	        			m=p.matcher(newName);
	        			bb=m.find();
	        			if(bb){
	        				e = 6001;
	        			}else {
		        			p=Pattern.compile(regEx5); 
		        			m=p.matcher(newName);
		        			bb=m.find();
		        			if(bb){
		        				e = 6000;
		        			}else{
		        				p=Pattern.compile(regEx6); 
			        			m=p.matcher(newName);
			        			bb=m.find();
			        			if(bb){
			        				e = 6004;
			        			}
		        			else{
		        				p=Pattern.compile(regEx7); 
			        			m=p.matcher(newName);
			        			bb=m.find();
			        			if(bb){
			        				e = 6005;
			        			}
		        			}
		        	
		        	}}}}}
		return e+"";
	}
	
	public static boolean isExistHd(String newName,int id) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__activity where name regexp '"+newName+"' and type_id ="+id;
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存�?);
				b= true;}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return b;
		}finally{
			dbm.close();
		}
		
		
		return b;
	}
	
	public static boolean isExistXw(String newName,int id) {

		boolean b =false;
		
		DBManager1 dbm = new DBManager1();
		ResultSet rs = null;
		String  sql = "select count(*) from cre__news where name regexp '"+newName+"' and belong_type ="+id;
		//System.out.println(sql);
		rs = dbm.retrieveByStmt(sql);
		try {
			if(rs!=null&&rs.next()){
			
				if(rs.getInt(1)>0){
				//System.out.println(newName+"新闻已存�?);
				b= true;}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return b;
		}finally{
			dbm.close();
		}
		
		
		return b;
	}
	
}