<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="crun.v3.Dao.Topic" %>
<%@ page import="javax.servlet.http.HttpSession" %>


<html class="no-js" lang="en"><!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]--><!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]--><!--[if !IE]

><!--><!--<![endif]-->
<head>

   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>微博话题跟踪系统</title>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta content="width=device-width, initial-scale=1.0" name="viewport">
   <meta content="" name="description">
   <meta content="" name="author">
   <meta name="MobileOptimized" content="320">
   
   <script type="text/javascript" src="weibo1.js"></script>
  
   <link href="http://xinqings.nlsde.buaa.edu.cn/bootstrap/css/bootstrap.min.css" rel="stylesheet">
   	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="nuaacempss_files/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="nuaacempss_files/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="nuaacempss_files/uniform.css" rel="stylesheet" type="text/css">
	<!-- END GLOBAL MANDATORY STYLES -->
	
	<!-- BEGIN THEME STYLES --> 
    <link href="nuaacempss_files/style-conquer.css" rel="stylesheet" type="text/css">
    <link href="nuaacempss_files/style.css" rel="stylesheet" type="text/css">
    <link href="nuaacempss_files/style-responsive.css" rel="stylesheet" type="text/css">
    <link href="nuaacempss_files/plugins.css" rel="stylesheet" type="text/css">
    <link href="nuaacempss_files/tasks.css" rel="stylesheet" type="text/css">
    <link href="nuaacempss_files/default.css" rel="stylesheet" type="text/css" id="style_color">
    <link href="nuaacempss_files/custom.css" rel="stylesheet" type="text/css">
    <!-- END THEME STYLES -->
    
   <!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
   <link href="nuaacempss_files/fullcalendar.css" rel="stylesheet" type="text/css">
   <!-- END PAGE LEVEL PLUGIN STYLES -->
   
   <link rel="shortcut icon" href="http://localhost:8080/SpringMVC3Demo/home/favicon.ico">
	
	<style type="text/css">
	
	.jqstooltip { position: absolute;left: 0px;top: 0px;visibility: hidden;background: rgb(0, 0, 0) transparent;background-color: rgba
	
	(0,0,0,0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);-ms-filter: "progid:DXImageTransform.Microsoft.gradient
	
	(startColorstr=#99000000, endColorstr=#99000000)";color: white;font: 10px arial, san serif;text-align: left;white-space: nowrap;padding: 5px;border: 1px solid 
	
	white;z-index: 10000;}.jqsfield { color: white;font: 10px arial, san serif;text-align: left;}
	
	</style>

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
   
   
<!-- BEGIN HEADER -->   
<div class="header navbar navbar-inverse navbar-fixed-top" style="height:84px;">
   <!-- BEGIN TOP NAVIGATION BAR -->
  <div class="header-inner" >
     <!-- BEGIN LOGO -->  
  
     
     <!-- <form class="search-form search-form-header" role="form" action="index.html" >
        
	<div class="input-icon right">
           <i class="icon-search"></i>
           <input type="text" class="form-control input-medium input-sm" name="query" placeholder="Search...">
        </div>
     </form> -->
     <!-- END LOGO -->
     <!-- BEGIN RESPONSIVE MENU TOGGLER --> 
     <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
     <img src="nuaacempss_files/menu-toggler.png" alt="">
     </a> 
     	  <ul class="nav navbar-nav">
         
         <li class="dropdown">

      <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="height:84px;width:215px;font-size:25px; line-height: 54px;">
              微博话题跟踪系统 
            </a>
         </li>
      </ul>
     
	  <ul class="nav navbar-nav" style = "">
         
         <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="height:84px;width:155px;font-size:25px; line-height: 54px;">
               微博采集 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
               <li class="divider"></li>
               <li><a href="#" onclick = "ShowFetch()">初始语料抓取</a></li>
               <li class="divider"></li>
               <li><a href="#" onclick = "FetchTopicNews()">根据特征词条抓取</a></li>
			   <li class="divider"></li>
               <li><a href="#"  onclick = "ShowFetch1()">普通方式抓取</a></li>
               <li class="divider"></li>
               <li><a href="#" onclick = "ShowFetch()">本地数据导入</a></li>
            </ul>
         </li>
      </ul>
	   <ul class="nav navbar-nav">
         
         <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"  style="height:82px;width:155px;font-size:25px; line-height: 54px;">
               本体构建 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              
               <li class="divider"></li>
               <li><a href="#" onclick = "wordCreate()">特征词本体生成</a></li>
               <li class="divider"></li>
               <li><a href="#" onclick = "ShowWord()">查看特征词本体</a></li>
			   <li class="divider"></li>
               <li><a href="#" onclick = "EntryCreate()">特征词条本体生成</a></li>
			   <li class="divider"></li>
               <li><a href="#" onclick = "ShowEntry()">查看特征词条本体</a></li>
               <li class="divider"></li> 
               <li id="topicImg"><a href="http://localhost:8080/weiboFetch/i1.jsp?id=0&tid=0" >查看话题本体云图</a></li>
            </ul>
         </li>
      </ul>
	   <ul class="nav navbar-nav">
         
         <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"  style="height:84px;width:155px;font-size:25px; line-height: 54px;">
              本体进化 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
               
               <li class="divider"></li>
               <li><a href="#" onclick = "EntryEvolve()">本体进化</a></li>
               <li class="divider"></li>
               <li><a href="#" onclick = "ShowEvolve()">查看进化本体</a></li>
               <li class="divider"></li>
               <li  id="topicEvlImg"><a a href="http://localhost:8080/weiboFetch/i1.jsp?id=0&tid=0">查看进化本体云图</a></li>
            </ul>
         </li>
      </ul>
	   <ul class="nav navbar-nav">
         
         <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"  style="height:84px;width:155px;font-size:25px; line-height: 54px;">
               话题跟踪 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
               
               <li class="divider"></li>
               <li><a href="#" onclick = "TopicTrack()">话题跟踪</a></li>
               <li class="divider"></li>
               <li><a href="#" onclick = "ShowTrackNews(1)">查看强相关微博</a></li>
                <li class="divider"></li>
             <li><a href="#" onclick = "ShowIreNews(1)">查看无关微博</a></li>

         </li>
      </ul>
     <!-- END RESPONSIVE MENU TOGGLER -->
     <!-- BEGIN TOP NAVIGATION MENU -->

     <!-- END TOP NAVIGATION MENU -->
  </div>
  <!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->
   
   <div class="clearfix"></div>
   <!-- BEGIN CONTAINER -->
   
   <div class="page-container">
      
		      
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar navbar-collapse collapse">
		   <!-- BEGIN SIDEBAR MENU -->        
		<ul class="page-sidebar-menu">
			<li>
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				<div class="sidebar-toggler"></div>
				<div class="clearfix"></div>
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			</li>
		
			
			<li class="" ><a href="javascript:;"> <i class="icon-cogs"></i>
					<span class="title" style="font-size:22px; line-height: 54px;">热点话题</span> <span class="arrow "></span> 
			</a>
				<ul class="sub-menu" style="display:block">
		
					<li>
										
		
								<% 
									Topic[] topics =Topic.getAllTopic();
									for(Topic topic:topics){
										out.print("<li>"
										+"<a href='#' onclick=\"getTopic('"+topic.getId()+"','1')\">"
										+topic.getName()+"</a></li>");
									}
								
								%>
				</ul></li>
			
				<li class=""><a href="#" onclick ="getTopic(0,1)"> <i
					class="icon-user"></i> <span class="title" style="font-size:22px; line-height: 54px;">待跟踪微博</span>
			</a></li>
		
		</ul>
		<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
		      
		      <!-- BEGIN PAGE -->
		 <div class="page-content">
		                  
		<!-- BEGIN STYLE CUSTOMIZER -->         
		
		<!-- END BEGIN STYLE CUSTOMIZER -->
		           
		         <!-- BEGIN PAGE HEADER-->
		         <div class="row" >
		            <div class="col-md-12">
		
		               <ul class="page-breadcrumb breadcrumb">
		                  <li>
		                     <i class="icon-home"></i>
		                    	<span id ="title1">热点话题</span>
		                     <i class="icon-angle-right"></i>
		                  </li>
		                  <li>	
		                  <span id ="title2">新增话题</span>
		                  </li>
		                  <li class="pull-right">
		                     <div id="dashboard-report-range" class="dashboard-date-range tooltips" data-placement="top" data-original-title="Change dashboard date range">
		                        <i class="icon-calendar"></i>
		                        <span></span>
		                        <i class="icon-angle-down"></i>
		                     </div>
		                  </li>
		               </ul>
		               <!-- END PAGE TITLE & BREADCRUMB-->
					<div id="news-content" class="box box-news">
					
				<!-- 		<table><tr><td style ="width:110px;text-align: center;vertical-align : middle;font-size:20px" ></td><td>
								<div>
			<textarea class="textbox" style="WIDTH:400PX;HEIGHT:400px;"></textarea>
								</div>
								</td></tr>
		</table>
								<div>
			
					<table><tr><td style ="width:110px;text-align: center;vertical-align : middle;font-size:20px" >时间本体</td><td>
								<div>
			<textarea class="textbox">2014年03月2日晚,2014年02日二月,214年3月1日晚9时2分,
		2014年3月2日5时,2014年3月2日晚,2014年03月2日18时,</textarea>
								</div>
								</td></tr>
								<tr><td style ="width:110px;text-align: center;vertical-align : middle;font-size:20px">地点本体</td><td>
								<br>
								<div>
								<textarea class="textbox">
									</textarea>
									</div>
									</td></tr>
									<tr><td style ="width:110px;text-align: center;vertical-align : middle;font-size:20px"">对象本体</td><td>
									<br>
								<div><textarea class="textbox" style="WIDTH:400PX;HEIGHT:200px;">
									</textarea>
								</div>
								</td></tr>
								</table>
								-->
						<input type="text" id= "atValue" name="keywords" value="请输入话题名称" style="height:30px;">
						<button  class ="button blue"  type="submit" onclick = "addTopic();" name="sub" value="">新增话题</button>
		
					</div>
		
		            </div>
		         </div>
		         <!-- END PAGE HEADER-->
		         
		         <div class="clearfix"></div>
		         <div class="row">
		            
		            
		         </div>
         

    
      </div>
      <!-- END PAGE -->
   </div>
   <!-- END CONTAINER -->
   <!-- BEGIN FOOTER -->
   <div class="footer" style="top:333">
      <div class="footer-inner">
         2014 © Nuaa 信管电商研究所舆情小组
      </div>
      <div class="footer-tools">
         <span class="go-top">
         <i class="icon-angle-up"></i>
         </span>
      </div>
   </div>
   <!-- END FOOTER -->
   <style type="text/css" media="screen">
   .textbox { 
  			WIDTH:400PX;HEIGHT:65px;
   			BACKGROUND: #BFCEDC; BORDER-TOP:#7F9DB9 1px solid; 
   			BORDER-LEFT: #7F9DB9 1px solid; 
   			BORDER-RIGHT: #7F9DB9 1px solid; 
   			BORDER-BOTTOM:#7F9DB9 1px solid; 
   			FONT-FAMILY: "宋体", "Verdana","Arial", "Helvetica"; 
   			FONT-SIZE: 12px; TEXT-ALIGN: LIFT;
   			
   			}
   			table{ 
			
			}
			
			td{	vertical-align: top;
		
				padding:5px;
				marign:20px;
				
			}

		</style>
	
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
   	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="/SpringMVC3Demo/plugins/respond.min.js" type="text/javascript"></script>
	<script src="/SpringMVC3Demo/plugins/excanvas.min.js" type="text/javascript"></script> 
	<![endif]-->   
	<script src="nuaacempss_files/jquery-1.js" type="text/javascript"></script>
	<script src="nuaacempss_files/jquery-migrate-1.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="nuaacempss_files/jquery-ui-1.js" type="text/javascript"></script>
	<script src="nuaacempss_files/bootstrap.js" type="text/javascript"></script>
	<script src="nuaacempss_files/twitter-bootstrap-hover-dropdown.js" type="text/javascript"></script>
	<script src="nuaacempss_files/jquery_002.js" type="text/javascript"></script>
	<script src="nuaacempss_files/jquery_006.js" type="text/javascript"></script>  
	<script src="nuaacempss_files/jquery_004.js" type="text/javascript"></script>
	<script src="nuaacempss_files/jquery_005.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
   <!-- BEGIN PAGE LEVEL PLUGINS -->
   <script src="nuaacempss_files/jquery.js" type="text/javascript"></script>
   <!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
   <script src="nuaacempss_files/fullcalendar.js" type="text/javascript"></script>
   <script src="nuaacempss_files/jquery_003.js" type="text/javascript"></script>  
   <!-- END PAGE LEVEL PLUGINS -->
   <!-- BEGIN PAGE LEVEL SCRIPTS -->
   <script src="nuaacempss_files/app.js" type="text/javascript"></script>
   <script src="nuaacempss_files/index.js" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
      jQuery(document).ready(function() {    
         App.init(); // initlayout and core plugins
         Index.init();
         Index.initCalendar(); // init index page's custom scripts
         Index.initPeityElements();
         Index.initKnowElements();
         Index.initDashboardDaterange();
      });
   </script>
   
   
   <!-- END JAVASCRIPTS -->

</body></html>