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
	var elem = document.getElementById("news-content");
	elem.innerHTML = content;
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
	tid = id;
	//alert(11);
	var submitURL = "GetTopicServlet?page="+page+"&topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showGetTopicRsps;
	ajax.send();

}

function FetchNews(id)
{	
	alert("初始语料抓取中》》》》");
	var url = document.getElementById("fUrl").value;
	var num = document.getElementById("fNum").value;
	url = url.replace(/\&/g,"%26");
	
	var submitURL = "FetchNewsServlet?topicId=" + id+"&url="+ encodeURI(encodeURI(url))+"&num="+num;
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchNewsRsps;
	ajax.send();

}

function FetchTopicNews(id)
{
	alert("待跟踪微博抓取中>>>");
	var submitURL = "FetchTopicNewsServlet?topicId=" + id;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showFetchTopicNewsRsps;
	ajax.send();

}

function wordCreate(id)
{
	alert("特征词本体生成中>>>");
	var submitURL = "WordCreateServlet?topicId=" + id;
	
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showWordCreateRsps;
	ajax.send();

}

function EntryCreate(id)
{
	alert("特征词条本体生成中>>>");
	var submitURL = "EntryCreateServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryCreateRsps;
	ajax.send();

}

function ShowWord(id)
{
	
	var submitURL = "ShowWordServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showWordRsps;
	ajax.send();

}

function ShowEntry(id)
{
	
	var submitURL = "ShowEntryServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryRsps;
	ajax.send();

}

function ShowEvolve(id)
{
	id = (parseInt(id)+10000);
	alert(id);
	var submitURL = "ShowEntryServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryRsps;
	ajax.send();

}

function EntryEvolve(id)
{
	
	var submitURL = "EntryEvolveServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showEntryEvolveRsps;
	ajax.send();

}

function TopicTrack(id)
{
	alert("话题跟踪中》》》》");
	var submitURL = "TopicTrackServlet?topicId=" + id;
	//alert(submitURL);
	ajax.open('POST', submitURL, true);
	ajax.onreadystatechange = showTopicTrackRsps;
	ajax.send();

}

function ShowTrackNews(id,page)
{
	
	var submitURL = "ShowTrackNewsServlet?topicId=" + id+"&page="+page;
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
				showTitleContent("");
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
			//alertMsg(state);
			if("add_fail" == state)
			{
				alertMsg("添加失败！");
			}
			
			else
			{
				
				if(id==0){
					showTitleContent("待跟踪微博");
				}
				else{
					showTitleContent("话题语料");

					
			/**		var onto ="<div id=\"fetch\" style=\"margin-left:45%\">"
					+"<input type=\"text\" id= \"fUrl\" name=\"keywords\" value=\"请输入抓取网址\" style=\"height:26;\">"
					+"<input type=\"text\" id= \"fNum\" name=\"keywords\" value=\"请输入抓取数量\" style=\"height:26;width:140px;\">"
					+"<input  type=\"submit\" onclick = \"FetchNews('"+id+"');\" name=\"sub\" value=\"初始语料抓取\"></div>"
					+"<input  type=\"submit\" onclick = \"FetchTopicNews('"+id+"');\" " 
					+"name=\"sub\" value=\"待跟踪微博抓取\">";
					onto +="<input  type=\"submit\" onclick = \"wordCreate('"+id+"');\" "
					+"name=\"sub\" value=\"特征词本体生成\">"
					+"<input  type=\"submit\" onclick = \"ShowWord('"+id+"');\" "
					+"name=\"sub\" value=\"查看特征词本体\">"
					+"<input  type=\"submit\" onclick = \"EntryCreate('"+id+"');\" " 
					+"name=\"sub\" value=\"特征词条本体生成\">"
					+"<input  type=\"submit\" onclick = \"ShowEntry('"+id+"');\" " 
					+"name=\"sub\" value=\"查看特征词条本体\">"
					+"<input  type=\"submit\" onclick = \"TopicTrack('"+id+"');\" " 
					+"name=\"sub\" value=\"话题跟踪\">"
					+"<input  type=\"submit\" onclick = \"ShowTrackNews('"+id+"','1');\" " 
					+"name=\"sub\" value=\"查看相关微博\">"
					+"<input  type=\"submit\" onclick = \"EntryEvolve('"+id+"');\" " 
					+"name=\"sub\" value=\"本体进化\">"
					+"<input  type=\"submit\" onclick = \"ShowEvolve('"+id+"');\" " 
					+"name=\"sub\" value=\"查看进化本体\">";
					showFetchContent(onto);**/

				}
				
			
				showNewsContent(state);
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