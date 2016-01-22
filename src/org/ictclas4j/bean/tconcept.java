package org.ictclas4j.bean;


import common.dao.DBManager;


public class tconcept {

	public String name;
	public int num;
	public String spe;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSpe() {
		return spe;
	}

	public void setSpe(String spe) {
		this.spe = spe;
	}

	public tconcept(String name ,int num,String spe){
		this.name = name;
		this.num = num;
		this.spe=spe;
	}
	
	public static int insertCon(String name,int num){
		int i = 0;
		String sql = "insert into zogainian(name,tnum) values ('"+name+"','"+num+"')";
		DBManager dbm = new DBManager();
		i = dbm.insertByStmt(sql);
		return i;			
		
	}


}
