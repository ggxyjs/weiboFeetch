/**********Begin 全局变量**********/
var tid = 0;

var ajax = createAjaxObj();





/***********End 全局变量***********/
function alertMsg(msg)
{
	window.alert(msg);
}

function showNewsContent(content)
{
	//alert("news");
	var elem = document.getElementById("news-content");
	elem.innerHTML = content;
}

function showTopicContent()
{
	var ttid = Number(tid)+10000;
	//alert("news");
	var elem = document.getElementById("topicImg");
	elem.innerHTML = "<li id=\"topicImg\"><a href=\"http://localhost:8080/weiboFetch/i1.jsp?id="+tid+"&tid=0\" >查看话题本体云图</a></li>";

	var elem = document.getElementById("topicEvlImg");
	elem.innerHTML = "<li id=\"topicEvlImg\"><a href=\"http://localhost:8080/weiboFetch/i1.jsp?id="+tid+"&tid="+ttid+"\" >查看话题本体云图</a></li>";

}



function ShowButton1()
{
	FetchTopicNews
	
	var fetch ="<input type=\"text\" id= \"fUrl\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:26;\">"
		+"<input type=\"text\" id= \"fNum\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:26;width:140px;\">"
		+"<button class =\"button blue\" onclick = \"FetchNews('"+tid+"');\" >初始语料抓取</button>"
		+"<button class =\"button blue\" onclick = \"FetchTopicNews('"+tid+"');\" >根据特征词条抓取</button>"
	+"<button class =\"button blue\" onclick = \"FetchTopicNews('"+tid+"');\" >普通方式抓取</button>";
	
	showFetchContent(fetch);
}

function ShowButton2()
{

	var fetch ="<button class =\"button blue\" onclick = \"wordCreate('"+tid+"');\" >特征词本体生成</button>"
		+"<button class =\"button blue\" onclick = \"ShowWord('"+tid+"');\" >查看特征词本体</button>"
	+"<button class =\"button blue\" onclick = \"EntryCreate('"+tid+"');\" >特征词条本体生成</button>"
	+"<button class =\"button blue\" onclick = \"ShowEntry('"+tid+"');\" >查看特征词条本体</button>";

	showFetchContent(fetch);
}

function ShowButton3()
{
	var fetch ="<button class =\"button blue\" onclick = \"EntryEvolve('"+tid+"');\" >本体进化</button>"
	+"<button class =\"button blue\" onclick = \"ShowEvolve('"+tid+"');\" >查看进化本体</button>";
	

	showFetchContent(fetch);
}

function ShowButton4()
{

	var fetch ="<button class =\"button blue\" onclick = \"TopicTrack('"+tid+"');\" >话题跟踪</button>"
	+"<button class =\"button blue\" onclick = \"ShowTrackNews('"+tid+"',1);\" >查看相关微博</button>";
	
	showFetchContent(fetch);
}




function showTitleContent(content)
{
	var elem = document.getElementById("news-title");
	elem.innerHTML = content;
}

function showFetchContent(content)
{
	//alert(22);
	var elem = document.getElementById("fetch");
	elem.innerHTML = content;
	
}



function addTopic()
{
	
	var topicName = document.getElementById("atValue").value;

	if(topicName!=null&&!topicName==""&&!(topicName=="请输入话题名称")){
	
		var submitURL = "AddTopicServlet?topicName=" + encodeURI(encodeURI(topicName));
		ajax.open('POST', submitURL, true);
		ajax.onreadystatechange = showAddTopicRsps;
		ajax.send();
	}else{
		alert("话题名称不能为空");
	}

}

function getTopic(id,page)
{	
	tid =id;
	
	var submitURL = "GetTopicServlet?page="+page+"&topicId=" + id;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showGetTopicRsps;
	ajax.send();

}

function ShowFetch()
{	
	var fetch ="<input type=\"text\" id= \"fUrl\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:60;\">"
		+"<input type=\"text\" id= \"fNum\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:60;width:140px;\">"
		+"<button class =\"button blue\" onclick = \"FetchNews();\" >初始语料抓取</button>"
		"<input type=\"text\" id= \"fUrl1\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:26;\">"
		"<input type=\"text\" id= \"fNum1\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:26;width:140px;\">"
		"<button class =\"button blue\" onclick = \"FetchNews1();\" >初始语料抓取(baidu)</button>";

		showNewsContent(fetch);
}

function ShowFetch1()
{	
	var fetch ="<input type=\"text\" id= \"fUrl\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:60;\">"
		+"<input type=\"text\" id= \"fNum\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:60;width:140px;\">"
		+"<button class =\"button blue\" onclick = \"FetchNews1();\" >待跟踪微博抓取</button>"
		;//+"<input type=\"text\" id= \"fUrl1\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:26;\">"
		//+"<input type=\"text\" id= \"fNum1\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:26;width:140px;\">"
		//+"<button class =\"button blue\" onclick = \"FetchNews1();\" >初始语料抓取(baidu)</button>";

		showNewsContent(fetch);
}

function FetchNews1()

{	
	alert("初始语料抓取中》》》》");
	var url = document.getElementById("fUrl").value;
	var num = document.getElementById("fNum").value;
	url = url.replace(/\&/g,"%26");
	
	var submitURL = "FetchNewsServlet?topicId=" + 0+"&url="+ encodeURI(encodeURI(url))+"&num="+num;
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchNewsRsps;
	ajax.send();

}

function FetchNews()

{	
	alert("初始语料抓取中》》》》");
	var url = document.getElementById("fUrl").value;
	var num = document.getElementById("fNum").value;
	url = url.replace(/\&/g,"%26");
	
	var submitURL = "FetchNewsServlet?topicId=" + tid+"&url="+ encodeURI(encodeURI(url))+"&num="+num;
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchNewsRsps;
	ajax.send();

}

function FetchNews1()

{	
	alert("初始语料抓取中》》》》");
	var url = document.getElementById("fUrl1").value;
	var num = document.getElementById("fNum1").value;
	url = url.replace(/\&/g,"%26");
	
	var submitURL = "FetchNewsServlet1?topicId=" + tid+"&url="+ encodeURI(encodeURI(url))+"&num="+num;
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchNewsRsps;
	ajax.send();
}

function FetchTopicNews()
{
	alert("待跟踪微博抓取中>>>");
	var submitURL = "FetchTopicNewsServlet?topicId=" + tid;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchTopicNewsRsps;
	ajax.send();

}

function wordCreate()
{
	alert("特征词本体生成中>>>");
	var submitURL = "WordCreateServlet?topicId=" + tid;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showWordCreateRsps;
	ajax.send();

}

function EntryCreate(id)
{
	alert("特征词条本体生成中>>>");
	var submitURL = "EntryCreateServlet?topicId=" + tid;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryCreateRsps;
	ajax.send();

}

function ShowWord()
{
	//alert(tid);
	var submitURL = "ShowWordServlet?topicId=" + tid;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showWordRsps;
	ajax.send();

}

function ShowEntry()
{
	
	var submitURL = "ShowEntryServlet?topicId=" + tid;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryRsps;
	ajax.send();

}

function ShowEvolve()
{
	var id = (parseInt(tid)+10000);
	//alert(id);
	var submitURL = "ShowEntryServlet1?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryRsps;
	ajax.send();

}

function EntryEvolve()
{
	
	var submitURL = "EntryEvolveServlet?topicId=" + tid;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryEvolveRsps;
	ajax.send();

}

function TopicTrack()
{
	alert("话题跟踪中》》》》");
	var submitURL = "TopicTrackServlet?topicId=" + tid;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showTopicTrackRsps;
	ajax.send();

}

function ShowTrackNews(page)
{
	
	var submitURL = "ShowTrackNewsServlet?topicId=" + tid+"&page="+page;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showTrackNewsRsps;
	ajax.send();

}

function ShowIreNews(page)
{
	
	var submitURL = "ShowIreNewsServlet?topicId=" + tid+"&page="+page;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showTrackNewsRsps;
	ajax.send();

}

function showTrackNewsRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("获取失败！");
			}
			else
			{
				showNewsContent(state);
			}
		}
	}
}

function showTopicTrackRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("跟踪失败！");
			}
			else
			{
				alertMsg("跟踪完毕！请查看结果");
				//showNewsContent(state);
			}
		}
	}
}

function showFetchTopicNewsRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("获取失败！");
			}
			else
			{
				//showNewsContent(state);
			}
		}
	}
}

function showEntryEvolveRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("获取失败！");
			}
			else
			{
				//showNewsContent(state);
			}
		}
	}
}

function showWordRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("获取失败！");
			}
			else
			{
				//alertMsg(state);
				//showTitleContent("");
				showNewsContent(state);
			}
		}
	}
}

function showEntryRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("获取失败！");
			}
			else
			{
				showNewsContent(state);
			}
		}
	}
}

function showWordCreateRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("生成失败！");
			}
			else if("add_suc" == state)
			{
				alertMsg("生成成功！");
			}
			else
			{
				//showMainContent(state);
			}
		}
	}
}

function showEntryCreateRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("生成失败！");
			}
			else if("add_suc" == state)
			{
				alertMsg("生成成功！");
			}
			else
			{
				//showMainContent(state);
			}
		}
	}
}

function showFetchNewsRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("抓取失败！");
			}
			else if("add_suc" == state)
			{
				alertMsg("抓取成功，请刷新！");
			}
			else
			{
				//showMainContent(state);
			}
		}
	}
}

function showGetTopicRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText.split("$$$")[0];
			var id = ajax.responseText.split("$$$")[1];
			//alert(111);
			if("add_fail" == state)
			{
				//alert(222);
				alertMsg("添加失败！");
			}
			else
			{
				//alert(3333);
				
				showNewsContent(state);
				showTopicContent();
			}
		}
	}
}

function showAddTopicRsps()
{
	
	if(4 == ajax.readyState)
	{	
		if(200 == ajax.status)
		{
			
			
			var state = ajax.responseText;
			
			if("add_fail" == state)
			{
				alertMsg("添加失败！");
			}
			else if("add_suc" == state)
			{
				alertMsg("添加成功，请刷新！");
			}
			else
			{
				//showMainContent(state);
			}
		}
	}
}

function createAjaxObj() 
{
	var httprequest = false;
	if (window.XMLHttpRequest) 
	{
		httprequest = new XMLHttpRequest();
		if (httprequest.overrideMimeType) 
		{
			httprequest.overrideMimeType("text/xml");
		}
	} 
	else 
	{
		if (window.ActiveXObject) 
		{
			var ieArr = [ "Msxml2.XMLHTTP.8.0", "Msxml2.XMLHTTP.7.0",
					"Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.3.0",
					"Msxml2.XMLHTTP", "Microsoft.XMLHTTP" ];
			for ( var i = 0; i < ieArr.length; i=i+1) 
			{
				try
				{
					httprequest = new ActiveXObject(ieArr[i]);
				}
				catch (e) 
				{
					window.alert("Sorry，您的浏览器不支持AJAX。请升级您的浏览器...");
					return false;
				}
			}
		}
	}
	return httprequest;
}