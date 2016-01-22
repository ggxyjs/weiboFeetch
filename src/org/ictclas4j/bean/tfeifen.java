package org.ictclas4j.bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ictclas4j.bean.SegResult;

import org.ictclas4j.segment.SegTag;

import common.dao.DBManager;
import common.dao.DBManager1;

public class tfeifen {

	public static void main(String[] args) throws IOException {


		DBManager dbmt = new DBManager();
		
		DBManager dbm = new DBManager();
		DBManager dbmu = new DBManager();
		ResultSet rst = null;
		ResultSet rs = null;
		String s =new String();
		String[] v =new String[1000];
		String[] vn =new String[1000];
		String[] a =new String[1000];
		int[] b =new int[1000];
		//System.out.println(levenshtein(";1;3;4;8;10;12;17;19;22;23;24;27;28;29;30;31;32;33;35;36;39;40;43;51;59;60;66;67;68;70;71;72;73;74;75;78;85;87;90;91;97;98;100;101;104;105;109;110;117;119;120;123;125;126;127;128;129;131;133;135;137;143;145;148;161;162;164;165;166;167;168;170;171;175;176;178;179;180;183;189;190;194;200;201;208;209;210;213;214;215;218;219;220;223;226;229;232;234;235;238;242;245;246;247;248;250;254;256;257;259;260;263;266;267;270;271;272;275;276;280;281;282;283;284;287;288;289;290;291;292;293;294;295;296;298;299;301;302;303;305;306;308;310;311;313;318;319;320;322;334;335;336;337;339;340;343;349;350;351;352;353;354;355;356;358;359;364;366;367;368;369;371;372;374;375;376;377;380;381;382;384;386;387;388;389;391;392;393;394;",";1;2;3;4;5;6;7;9;10;11;12;13;14;15;17;18;20;21;24;25;26;27;28;29;30;31;32;33;35;36;37;38;39;40;41;43;44;45;47;48;49;50;51;52;53;55;57;58;59;64;66;67;68;69;70;71;72;73;74;75;78;84;88;89;90;91;92;93;94;95;96;97;98;100;101;102;103;105;107;109;114;115;117;118;119;120;121;122;123;124;125;126;127;128;129;131;133;134;135;137;139;140;141;142;143;144;145;148;149;150;151;155;156;157;160;161;162;163;164;166;168;170;171;172;173;175;176;177;178;179;180;182;183;185;186;187;188;190;193;194;196;197;198;199;201;203;206;207;208;209;210;211;212;213;214;215;216;217;219;221;222;223;224;226;228;229;231;232;233;234;235;236;238;240;242;243;244;246;247;248;249;250;253;255;256;257;259;260;263;264;265;266;267;268;270;272;273;275;277;279;280;281;282;283;284;285;286;287;289;290;291;292;294;295;296;297;298;299;301;302;303;304;305;306;307;308;309;310;311;312;313;314;316;317;319;320;322;323;324;326;327;328;331;332;334;335;336;337;338;340;341;342;343;345;346;348;350;351;352;353;355;356;358;359;360;361;362;363;364;366;367;368;369;371;373;374;375;376;377;379;380;381;382;384;385;386;387;388;389;390;391;392;393;396;"));
		try {
			
			String txt ="";
			String sqlt="select txt from zweibo";
			rst=dbmt.retrieveByStmt(sqlt);
			try {
				while(rst.next()){
					txt+=rst.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String sql1 = "SELECT name,tnum FROM `zogainian` where LENGTH(name)>3 and spe REGEXP 'v' and tnum>=20 ORDER BY tnum DESC";
			rs = dbmu.retrieveByStmt(sql1);
			int q=0;
			if(rs!=null){
				
				while(rs.next()){
					 v[q] = rs.getString(1);
					vn[q] =  rs.getString(2);
					// System.out.println( a[i] );
					 q++;
				}
		
			
				String sql = "SELECT name,tnum FROM `zogainian` where LENGTH(name)>3 and spe REGEXP 'n' and tnum>=30 ORDER BY tnum DESC;";
				rs = dbm.retrieveByStmt(sql);
				int i=0;
				if(rs!=null){
				
						while(rs.next()){
							 a[i] = rs.getString(1);
							 b[i] = rs.getInt(2);
							// System.out.println( a[i] );
							 i++;
						}
				
				}
				 // System.out.println(i);
				for(int j=0;j<i;j++){
						
						int k=j;
					    for(;k<i;k++){
					    	 
					    	if(b[k]<b[j]*0){
					    		break;
					    	}
					    }
					  
					    for(int l=j+1;l<k;l++){
					    	//String name = a[j]+a[l];
					    	//if(countSubstr(name,txt)>0)System.out.println(name +":"+countSubstr(name,txt));
					    	String[] cont = txt.split("，|。");
					    	//System.out.println("mmmmmm"+cont.length);
					    	for(int n=0;n<q;n++){
					    		int vnum = 0;
						    	for(int m=0;m<cont.length;m++){
						    	
						    		
						    		if(a[j].indexOf(a[l])==-1&&a[l].indexOf(a[j])==-1){
						    		if(cont[m].indexOf(a[j])!=-1&&cont[m].indexOf(a[l])!=-1&&cont[m].indexOf(v[n])!=-1){
						    			vnum++;
						    		}
						    		}else if(a[j].indexOf(a[l])!=-1){
						    			if(cont[m].indexOf(a[j])!=-1&&cont[m].indexOf(v[n])!=-1&&cont[m].replaceAll(a[j], "").indexOf(a[l])!=-1){
						    				
							    			vnum++;
							    		}
						    		}else {
						    			if(cont[m].indexOf(a[l])!=-1&&cont[m].indexOf(v[n])!=-1&&cont[m].replaceAll(a[l], "").indexOf(a[j])!=-1){
						    				
							    			vnum++;
							    		}
						    		}
						    		
						    	
						    	}
						    			if(vnum>10&&!v[n].equals("来自")&&vnum<=b[l])System.out.println(a[j]+"~"+b[j]+"~"+a[l]+"~"+b[l]+"~"+v[n] +":"+vnum+":"+vn[n]);
							    		
					    	}
					    }
					 
							
					    }
					
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}
	
//  判断substr字符串在str中出现的次数  isIgnore是否忽略大小写!
	public static int countSubstr(String a,String b){
			String c = b.replaceAll(a, "");
			int i =( b.length()-c.length())/a.length();
			
			return i;
	}


}
