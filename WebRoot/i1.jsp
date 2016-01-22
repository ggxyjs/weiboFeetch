<!DOCTYPE html>
<html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="common.dao.DBManager" %>
<%@ page import="crun.v3.Dao.Entry" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.ResultSet" %>


<head>

 <script type="text/javascript" src="weibo1.js"></script>
<script type="text/javascript" src="http://mbostock.github.com/d3/d3.v2.js?2.9.1"></script>
<style type="text/css">
.link { stroke: green; stroke-linejoin:bevel;}

.link1{stroke:red;stroke-linejoin:bevel;}
.link2{stroke:#EE00EE;stroke-linejoin:bevel;}
.link3{stroke:#CD9B1D;stroke-linejoin:bevel;}
.link4{stroke:#C1CDC1;stroke-linejoin:bevel;}
.link5{stroke:#B03060;stroke-linejoin:bevel;}
.link6{stroke:#98F5FF;stroke-linejoin:bevel;}
.link7{stroke:#8B668B;stroke-linejoin:bevel;}
.link8{stroke:#7B68EE;stroke-linejoin:bevel;}
.link9{stroke:#63B8FF;stroke-linejoin:bevel;}



.nodetext {

    font: 12px sans-serif;
    -webkit-user-select:none;
    -moze-user-select:none;
    stroke-linejoin:bevel;
    
}

#container{
    width:1200px;
    height:800px;
    border:1px solid gray;
    border-radius:5px;
    position:relative;
    margin:20px;
}
</style>
</head>
<body>


<div id='container'></div>
<script type="text/javascript">


function Topology(ele){
    typeof(ele)=='string' && (ele=document.getElementById(ele));
    var w=ele.clientWidth,
        h=ele.clientHeight,
        self=this;
        
    this.force = d3.layout.force().gravity(.05).distance(120).charge(-120).size([w, h]);
    this.nodes=this.force.nodes();
    this.links=this.force.links();
    this.clickFn=function(){};
    this.vis = d3.select(ele).append("svg:svg")
                 .attr("width", w).attr("height", h).attr("pointer-events", "all");

    this.force.on("tick", function(x) {
      self.vis.selectAll("g.node")
          .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

      self.vis.selectAll("line.link")
          .attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });
    });
}


Topology.prototype.doZoom=function(){
    d3.select(this).select('g').attr("transform","translate(" + d3.event.translate + ")"+ " scale(" + d3.event.scale + ")");

}


//增加节点
Topology.prototype.addNode=function(node){
    this.nodes.push(node);
}

Topology.prototype.addNodes=function(nodes){
    if (Object.prototype.toString.call(nodes)=='[object Array]' ){
        var self=this;
        nodes.forEach(function(node){
            self.addNode(node);
        });

    }
}

//增加连线
Topology.prototype.addLink=function(source,target){
    this.links.push({source:this.findNode(source),target:this.findNode(target)});
}

//增加多个连线
Topology.prototype.addLinks=function(links){
    if (Object.prototype.toString.call(links)=='[object Array]' ){
        var self=this;
        links.forEach(function(link){
            self.addLink(link['source'],link['target']);
        });
    }
}


//删除节点
Topology.prototype.removeNode=function(id){
    var i=0,
        n=this.findNode(id),
        links=this.links;
    while ( i < links.length){
        links[i]['source']==n || links[i]['target'] ==n ? links.splice(i,1) : ++i;
    }
    this.nodes.splice(this.findNodeIndex(id),1);
}

//删除节点下的子节点，同时清除link信息
Topology.prototype.removeChildNodes=function(id){
    var node=this.findNode(id),
        nodes=this.nodes;
        links=this.links,
        self=this;

    var linksToDelete=[],
        childNodes=[];
    
    links.forEach(function(link,index){
        link['source']==node 
            && linksToDelete.push(index) 
            && childNodes.push(link['target']);
    });

    linksToDelete.reverse().forEach(function(index){
        links.splice(index,1);
    });

    var remove=function(node){
        var length=links.length;
        for(var i=length-1;i>=0;i--){
            if (links[i]['source'] == node ){
               var target=links[i]['target'];
               links.splice(i,1);
               nodes.splice(self.findNodeIndex(node.id),1);
               remove(target);
               
            }
        }
    }

    childNodes.forEach(function(node){
        remove(node);
    });

    //清除没有连线的节点
    for(var i=nodes.length-1;i>=0;i--){
        var haveFoundNode=false;
        for(var j=0,l=links.length;j<l;j++){
            ( links[j]['source']==nodes[i] || links[j]['target']==nodes[i] ) && (haveFoundNode=true) 
        }
        !haveFoundNode && nodes.splice(i,1);
    }
}



//查找节点
Topology.prototype.findNode=function(id){
    var nodes=this.nodes;
    for (var i in nodes){
        if (nodes[i]['id']==id ) return nodes[i];
    }
    return null;
}


//查找节点所在索引号
Topology.prototype.findNodeIndex=function(id){
    var nodes=this.nodes;
    for (var i in nodes){
        if (nodes[i]['id']==id ) return i;
    }
    return -1;
}

//节点点击事件
Topology.prototype.setNodeClickFn=function(callback){
    this.clickFn=callback;
}

//更新拓扑图状态信息
Topology.prototype.update=function(){
  var link = this.vis.selectAll("line.link")
      .data(this.links, function(d) { return d.source.id + "-" + d.target.id; })
      .attr("class", function(d){
        
             var a = d.source.status>d.target.status?d.source.status:d.target.status;
     return 'link';
    /* switch(a){
     
     case 1:return 'link';break;
      case 2:return 'link link1';break;
       case 3:return 'link link2';break;
       case 4:return 'link link3';break;
       case 5:return 'link link4';break;
       case 6:return 'link link5';break;
       case 7:return 'link link6';break;
       case 8:return 'link link7';break;
       case 9:return 'link link8';break;
       case 10:return 'link link9';break;*/
     
  });

  link.enter().insert("svg:line", "g.node")
      .attr("class", function(d){
        
             var a = d.source.status>d.target.status?d.source.status:d.target.status;
     return 'link';
     /*  switch(a){
     
     case 1:return 'link';break;
    case 2:return 'link link1';break;
       case 3:return 'link link2';break;
       case 4:return 'link link3';break;
       case 5:return 'link link4';break;
       case 6:return 'link link5';break;
       case 7:return 'link link6';break;
       case 8:return 'link link7';break;
       case 9:return 'link link8';break;
       case 10:return 'link link9';break;*/
     
     
     
      	

});

  link.exit().remove();

  var node = this.vis.selectAll("g.node")
      .data(this.nodes, function(d) { return d.id;});

  var nodeEnter = node.enter().append("svg:g")
      .attr("class", "node")
      .call(this.force.drag);

  //增加图片，可以根据需要来修改
  var self=this;
  nodeEnter.append("svg:image")
      .attr("class", "rect")

      .attr("x", "-32px")
      .attr("y", "-32px")
      .attr("width", "164px")
      .attr("height", "164px")
      
nodeEnter.on('click',function(d){ d.expand && self.clickFn(d);})
  nodeEnter.append("svg:text")
      .attr("class", "")
      .attr("dx", 0)
      .attr("dy", 0)
      .text(function(d) { return d.id });
	
  
  node.exit().remove();

  this.force.start();
}




var topology=new Topology('container');

<%	

		 String sid=request.getParameter("id");
		 int id = Integer.parseInt(sid);
		
		
		String stid=request.getParameter("tid");
		 int tid = Integer.parseInt(stid);
		 
		DBManager dbm = new DBManager();
		ResultSet rs =null;
		
		DBManager dbmn = new DBManager();
		ResultSet rsn =null;
		
		DBManager dbms = new DBManager();
		ResultSet rss =null;
		
		StringBuilder url = new StringBuilder();
		StringBuilder links = new StringBuilder();
		

		Entry[] entrys = Entry.getEntryByIs2(2,tid,id);
		
		//String sql = "select name from 1wb_entry12 union select name from wb_entry1";
		//String sql = "select name from 2wb_entry where weight is not null";
		//	rs = dbm.retrieveByStmt(sql);
		
		url.append("var nodes=[");
		links.append("var links = [");
		int i=1;
		List<String> l =new ArrayList<String>();
		
		for(Entry entry:entrys){
			
			String name = entry.getName();
			name = name.substring(1,name.length()-1).replaceAll(" ","");
			String[] names= name.replaceAll("/n|/v","").split(",");
			
			if(i==1){
				
				if(!l.contains(names[0])){
				url.append(" {id:'"+names[0]+"',type:'router',status:"+i+"}");l.add(names[0]);
				}
				
				for(int j=1;j<names.length;j++){
				
					if(!l.contains(names[j])){
						url.append(", {id:'"+names[j]+"',type:'router',status:"+i+"}");
						l.add(names[j]);
					}
				}
				
			}else{
					for(int j=0;j<names.length;j++){
				
						if(!l.contains(names[j])){
						
						
						url.append(", {id:'"+names[j]+"',type:'router',status:"+i+"}");
						l.add(names[j]);
					}	}}
		
			

				
				for(int j=0;j<names.length-1;j++){
					if(i==1&&j==0){
						links.append("{source:'"+names[j]+"',target:'"+names[j+1]+"'}");
					}else{
					
					for(int k=0;k<=j;k++)
						links.append(",{source:'"+names[k]+"',target:'"+names[j+1]+"'}");
						
						}
				}
				
				i++;
				
			
		}
		url.append("];");
		links.append("];");
		url.append(links.toString());
		out.println(url.toString()+"\n");	
	System.out.println(l);
%>

topology.addNodes(nodes);
topology.addLinks(links);
//可展开节点的点击事件
topology.setNodeClickFn(function(node){
    if(!node['_expanded']){
        expandNode(node.id);
        node['_expanded']=true;
    }else{
        collapseNode(node.id);
        node['_expanded']=false;
    }
});
topology.update();


function expandNode(id){
    topology.addNodes(childNodes);
    topology.addLinks(childLinks);
    topology.update();
}

function collapseNode(id){
    topology.removeChildNodes(id);
    topology.update();
}

</script>
</body>
</html>