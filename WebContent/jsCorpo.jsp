<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 <%@page import= "connection.*"%>
 <%@page import="java.sql.*" %>
<%@page import="java.util.*" %>

 
<!DOCTYPE HTML>
<html>
<head>

</head>
<body>
<div id="chartContainer" style="height: 370px; width: 50%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>


<div class="panel panel-widget">
		
<h1>device Id: <%=request.getParameter("id") %></h1>

<li><a href="viewDevice.jsp">
						<span>Back</span></a></li>
						
</div>

<%Connection conn=DBconnect.getConnect(); %>

<% String id = request.getParameter("id");
String sql="SELECT * FROM device where id = '"+id+"'";
// Execute the query

PreparedStatement pt1=conn.prepareStatement(sql);    
ResultSet rs1=pt1.executeQuery();

if(rs1.next()){
	
	String temp = rs1.getString(4);
	String hum = rs1.getString(5);
	String mq2 = rs1.getString(6);
	String mq5 = rs1.getString(7);
	String mq135 = rs1.getString(8);
	
	System.out.println(rs1.getString(4));
	System.out.println(rs1.getString(5));
	System.out.println(rs1.getString(6));
	System.out.println(rs1.getString(7));
	System.out.println(rs1.getString(8));
}
%>


<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
 
map = new HashMap<Object,Object>(); map.put("x", 10); map.put("y", Double.parseDouble(rs1.getString(4))); map.put("indexLabel", "Temp"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", 20); map.put("y", Double.parseDouble(rs1.getString(5))); map.put("indexLabel", "Humidity"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", 30); map.put("y", Double.parseDouble(rs1.getString(6))); map.put("indexLabel", "MQ2"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", 40); map.put("y", Double.parseDouble(rs1.getString(7))); map.put("indexLabel", "MQ5"); list.add(map);
map = new HashMap<Object,Object>(); map.put("x", 50); map.put("y", Double.parseDouble(rs1.getString(8))); map.put("indexLabel", "MQ135"); list.add(map);

String dataPoints = gsonObj.toJson(list);
%>



<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	exportEnabled: true,
	title: {
		text: "Device Sensor Value Graph"
	},
	data: [{
		type: "column", //change type to bar, line, area, pie, etc
		//indexLabel: "{y}", //Shows y value on all Data Points
		indexLabelFontColor: "#5A5757",
		indexLabelPlacement: "outside",
		dataPoints: <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>











				
				
				
				


</body>
</html>