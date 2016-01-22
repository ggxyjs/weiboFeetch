<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="crun.v3.Dao.Topic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="weibo.js"></script>
<link rel="stylesheet" type="text/css" href="weibo.css" media="screen">
<link rel="stylesheet" type="text/css" href="http://www.xuandz.com/css/reset.css" media="screen">
<link href="http://xinqings.nlsde.buaa.edu.cn/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="http://www.xuandz.com/css/richcontent.css" media="screen">
<script type="text/javascript" src="http://www.xuandz.com/lib/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="http://www.xuandz.com/lib/widget/easydialog/easydialog.css" media="screen">
<link rel="stylesheet" type="text/css" href="http://www.xuandz.com/lib/widget/scrollPane/jquery.scrollpane.css" media="screen">
<script type="text/javascript" src="http://www.xuandz.com/lib/widget/scrollPane/jquery.scrollpane.min.js"></script>

</head>
<body>

	<div id="container">
	    <div id="header">
	        <div class="inner">
	            <div id="logo">
				 <a href=""><h3 style="font-size:28px;black"> <br><h3>
	                <a href=""><h3 style="font-size:35px;color:#000000;font-weight:bold;text-align:center; "> 微博话题跟踪系统 <h3>
	            </div>
			</div>
    	</div> 
    </div>
    

    <div id="nav_outer">
				<div id="nav_filter" style="position: relative;">
							<div style="margin-left:24%">
				<ul>	
					<li class="f"><a style= "font-size:20px" href="#" onclick = "ShowButton1()">微博采集</a></li>
					<li class="f"><a style= "font-size:20px" href="#" onclick = "ShowButton2()">本体构建</a></li>
					<li class="f"><a style= "font-size:20px" href="#" onclick = "ShowButton3()">本体进化</a></li>
					<li class="f"><a style= "font-size:20px" href="#" onclick = "ShowButton4()">话题跟踪</a></li>
					
			</ul>
			</div></div>
			</div>
		<br>
	
	
	<div id="main" class="inner clearfix">
	
		<div id="area" class="side side-left" style="height:800px">
		
			<ul class="area-floor accordin">
			
				<li class="have">
					<h3 style="font-size:18px;color:#000000;font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;热点话题</h3>
					<ul style="display:block" class="show">
	
						<% 
							Topic[] topics =Topic.getAllTopic();
							for(Topic topic:topics){
								out.print("<li class=''><h3>"
								+"<a style='font-size:18px;color:#000000;font-weight:bold;' href='#' onclick=\"getTopic('"+topic.getId()+"','1')\">"
								+"&nbsp&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;"
								+topic.getName()+"</a></h3></li>");
							}
						
						%>
						
					
				
					</ul>
					

				
				</li>
				
				<li class="have">
					<h3>
					<a style="font-size:18px;color:#000000;font-weight:bold;" href='#' onclick="getTopic('0','1')">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;待跟踪微博</a>
					</h3>
					
					
				</li>
			</ul>
		</div>


		
			<div id="fetch" style="margin-left:20%;">
							<input type="text" id= "atValue" name="keywords" value="请输入话题名称" style="height:32;">
							<button  class ="button blue"  type="submit" onclick = "addTopic();" name="sub" value="">新增话题</button>
				
			</div>	
			
				
			<div  style= "margin-left:20%"class="box box-news">
				<h2 id="news-title" style="font-size:22px;color:#000000;font-weight:bold;margin-left:40%"></h2>
			</div>
			
<div id="news-content" class="box box-news"></div>
	
	
		
		
		<div class="page-side" style="clear:both">
	
		</div>	
		
		
		<div class="side side-right">
				
		</div>		

		
	</div>
			
			
			
			
			<style type="text/css" media="screen">
.button {  
    display: inline-block;  
    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
    *display: inline;  
    vertical-align: baseline;  
    margin: 0 2px;  
    outline: none;  
    cursor: pointer;  
    text-align: center;  
    text-decoration: none;  
    font: 14px/100% Arial, Helvetica, sans-serif;  
    padding: .5em 2em .55em;  
    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
    -webkit-border-radius: .5em;   
    -moz-border-radius: .5em;  
    border-radius: .5em;  
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
    box-shadow: 0 1px 2px rgba(0,0,0,.2);  
}  
.button:hover {  
    text-decoration: none;  
}  
.button:active {  
    position: relative;  
    top: 1px;  
}  
.blue {  
    color: #d9eef7;  
    border: solid 1px #0076a3;  
    background: #0095cd;  
    background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#0078a5));  
    background: -moz-linear-gradient(top,  #00adee,  #0078a5);  
    filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#00adee', endColorstr='#0078a5');  
} 
			table{ margin-left:25%;
			
			}
			
			td{padding:5px;marign:20px;}
			.span8 ul.weighted {
			float: left;
			display: block;
			width: 90%;
			height: 100px;
			overflow: auto;
			padding: 20px;
			background-color: #fff;
			border: 4px solid #aaa;
			border-radius: 20px;
			-moz-border-radius: 20px;
			}
			
			.area-floor {
				padding: 10px 0;
			}
			.area-floor li h3 {
				line-height: 30px;
				height: 30px;
				padding-left: 50px;
				cursor: pointer;
				border-left: 5px transparent solid;
				margin-left: -5px;
				background: url("http://www.xuandz.com/images/icon/data.png") 30px 9px no-repeat;
			}
			.area-floor li.have > h3 {
				background: url("http://www.xuandz.com/images/icon/data_category.png") 30px 9px no-repeat;
			}
			.area-floor li.current > h3 {
				background-color: #ffffff;
				color: #1184d1;
				border-left: 5px #ff9900 solid;
			}
			.area-floor li ul {
				display: none;
			}
			.area-floor li ul li h3 {
				background: url("http://www.xuandz.com/images/icon/data.png") 50px 9px no-repeat;
				padding-left: 70px;
			}
			.area-floor li a {
				color: #000;
				font-size: 12px;
			}
			.area-floor li a:hover {
				color: #1184d1;
			}</style>
			
		</body>
		

	</html>
