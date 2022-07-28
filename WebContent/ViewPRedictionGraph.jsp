<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 <%@page import= "connection.*"%>
 <%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
 
 
 
 
 <%Connection conn=DBconnect.getConnect(); %>

<% 

String sql="select * from airquuality ORDER BY id DESC LIMIT 1";
// Execute the query

PreparedStatement pt1=conn.prepareStatement(sql);    
ResultSet rs1=pt1.executeQuery();

if(rs1.next()){
	
		rs1.getString(5);
		rs1.getString(6);
		rs1.getString(7);
		rs1.getString(8);
		rs1.getString(9);
		
	
}




String sql3="select * from predict_airquuality ORDER BY id DESC LIMIT 1";
//Execute the query

PreparedStatement pt3=conn.prepareStatement(sql3);    
ResultSet rs3=pt3.executeQuery();

if(rs3.next()){
	
		rs3.getString(4);
		rs3.getString(5);
		rs3.getString(6);
		rs3.getString(7);
		rs3.getString(8);
}

%>





<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
 
map = new HashMap<Object,Object>(); map.put("x", "40"); map.put("y", Integer.parseInt(rs1.getString(5))); map.put("indexLabel", "Temp"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "120"); map.put("y", Integer.parseInt(rs1.getString(6))); map.put("indexLabel", "Hum"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "200"); map.put("y", Integer.parseInt(rs1.getString(7))); map.put("indexLabel", "MQ2"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "280"); map.put("y", Integer.parseInt(rs1.getString(8))); map.put("indexLabel", "MQ5"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "360"); map.put("y", Integer.parseInt(rs1.getString(9))); map.put("indexLabel", "MQ135"); list.add(map);




String dataPoints1 = gsonObj.toJson(list);
 
list = new ArrayList<Map<Object,Object>>();
map = new HashMap<Object,Object>(); map.put("x", "80"); map.put("y", Double. parseDouble(rs3.getString(4))); map.put("indexLabel", "Temp"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "160"); map.put("y", Double. parseDouble(rs3.getString(5))); map.put("indexLabel", "Hum"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "240"); map.put("y", Double. parseDouble(rs3.getString(6))); map.put("indexLabel", "MQ2"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "320"); map.put("y", Double. parseDouble(rs3.getString(7))); map.put("indexLabel", "MQ5"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", "400"); map.put("y", Double. parseDouble(rs3.getString(8))); map.put("indexLabel", "MQ135"); list.add(map);


String dataPoints2 = gsonObj.toJson(list);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
animationEnabled: true,
theme: "dark1",
title: {
text: "Air Quality Prediction Graph"
},
axisY: {
title: "in million tonnes",
},
toolTip: {
shared: true
},
legend: {
cursor: "pointer",
itemclick: toggleDataSeries
},
data: [{
type: "column",
name: "Production",
yValueFormatString: "#0.0# million tonnes",
showInLegend: true,
dataPoints: <%out.print(dataPoints1);%>
}, {
type: "column",
name: "Exports",
yValueFormatString: "#0.## million tonnes",
showInLegend: true,
dataPoints: <%out.print(dataPoints2);%>
}]
});
chart.render();
 
function toggleDataSeries(e) {
if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
e.dataSeries.visible = false;
}
else {
e.dataSeries.visible = true;
}
chart.render();
}
 
}
</script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html> 

