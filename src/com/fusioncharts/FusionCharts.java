/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fusioncharts;

public class FusionCharts {
	private String constructorTemplate = "<script type=\"text/javascript\">FusionCharts.ready(function () {new FusionCharts(__constructorOptions__);});</script>";
	private String renderTemplate = "<script type=\"text/javascript\">FusionCharts.ready(function () {                FusionCharts(\"__chartId__\").render();});</script>";
	private String[] chartOptions = new String[10];
	private String chartDataSource = "";

	public FusionCharts(String type, String id, String width, String height, String renderAt, String dataFormat,
			String dataSource) {
		this.chartOptions[0] = id;
		this.chartOptions[1] = width;
		this.chartOptions[2] = height;
		this.chartOptions[3] = renderAt;
		this.chartOptions[4] = type;
		this.chartOptions[5] = dataFormat;
		if (this.chartOptions[5].contains("url")) {
			this.chartOptions[6] = "\"" + dataSource + "\"";
		} else {
			this.chartOptions[6] = "__dataSource__";
			this.chartDataSource = this.addSlashes(dataSource.replaceAll("\n", ""));
		}
	}

	private String addSlashes(String str) {
		str = str.replaceAll("\\\\", "\\\\\\\\");
		str = str.replaceAll("\\n", "\\\\n");
		str = str.replaceAll("\\r", "\\\\r");
		str = str.replaceAll("\\00", "\\\\0");
		str = str.replaceAll("u003d", "=");
		str = str.replaceAll("'", "\\\\'");
		str = str.replaceAll("\\\\", "");
		str = str.replaceAll("\"\\{", "{");
		str = str.replaceAll("\"\\[", "[");
		str = str.replaceAll("\\}\\]\"", "}]");
		str = str.replaceAll("\"\\}\"", "\"}");
		str = str.replaceAll("\\}\"\\}", "}}");
		return str;
	}

	private String jsonEncode(String[] data) {
		String json = "{type: \"" + this.chartOptions[4] + "\",renderAt: \"" + this.chartOptions[3] + "\",width: \""
				+ this.chartOptions[1] + "\",height: \"" + this.chartOptions[2] + "\",dataFormat: \""
				+ this.chartOptions[5] + "\",id: \"" + this.chartOptions[0] + "\",dataSource: " + this.chartOptions[6]
				+ "}";
		return json;
	}

	public String render() {
		String outputHTML;
		if (this.chartOptions[5].contains("url")) {
			outputHTML = this.constructorTemplate.replace("__constructorOptions__", this.jsonEncode(this.chartOptions))
					+ this.renderTemplate.replace("__chartId__", this.chartOptions[0]);
		} else {
			if ("json".equals(this.chartOptions[5])) {
				outputHTML = this.constructorTemplate.replace("__constructorOptions__",
						this.jsonEncode(this.chartOptions).replace("__dataSource__", this.chartDataSource))
						+ this.renderTemplate.replace("__chartId__", this.chartOptions[0]);
			} else {
				outputHTML = this.constructorTemplate.replace("__constructorOptions__",
						this.jsonEncode(this.chartOptions).replace("__dataSource__",
								"\'" + this.chartDataSource + "\'"))
						+ this.renderTemplate.replace("__chartId__", this.chartOptions[0]);
			}
		}
		return outputHTML;
	}
}

/*
 * <div id="chart"> <!-- Step 2: Include the `FusionCharts.java` file as a
 * package in your project.
 * 
 * Step 3:Include the package in the file where you want to show FusionCharts.
 * 
 * --> <%@page import="com.fusioncharts.FusionCharts" %>
 * 
 * <!--
 * 
 * Step 4: Create a chart object using the FusionCharts JAVA class constructor.
 * Syntax for the constructor: `FusionCharts("type of chart", "unique chart id",
 * "width of chart", "height of chart", "div id to render the chart",
 * "data format", "data source")` --> <%
 * 
 * google-gson
 * 
 * Gson is a Java library facilitating conversion of Java objects into their
 * JSON representation and JSON strings into their equivalant Java objects. Gson
 * can also work with arbitrary Java objects including the pre-existing ones
 * that you may not have the source-code for. Read the note below this code for
 * more details on the google-gson library.
 * 
 * 
 * Gson gson1 = new Gson();
 * 
 * 
 * // Form the SQL query that returns the top 10 most populous countries String
 * sql1="select trsensor , count(trsensor ) from sensor group by trsensor "; //
 * Execute the query
 * 
 * PreparedStatement pt1=con.prepareStatement(sql1); ResultSet
 * rs1=pt1.executeQuery();
 * 
 * // The 'chartobj' map object holds the chart attributes and data. Map<String,
 * String> chartobj1 = new HashMap<String, String>();
 * 
 * chartobj1.put("caption", "Show Crime Report"); chartobj1.put("showValues",
 * "0"); chartobj1.put("theme", "zune");
 * 
 * // Push the data into the array using map object. ArrayList arrData1 = new
 * ArrayList(); while(rs1.next()) { Map<String, String> lv1 = new
 * HashMap<String, String>(); lv1.put("label", rs1.getString("trsensor"));
 * lv1.put("value", rs1.getString("count(trsensor)")); arrData1.add(lv1); }
 * 
 * //close the connection.
 * 
 * //create 'dataMap' map object to make a complete FC datasource. Map<String,
 * String> dataMap1 = new LinkedHashMap<String, String>();
 * 
 * gson.toJson() the data to retrieve the string containing the JSON
 * representation of the data in the array.
 * 
 * dataMap1.put("chart", gson1.toJson(chartobj)); dataMap1.put("data",
 * gson1.toJson(arrData));
 * 
 * FusionCharts columnChart1= new FusionCharts( //type of chart "column2d",
 * //unique chart ID "chart1", //width and height of the chart "500","300",
 * //div ID of the chart container "chart", //data format "json", //data source
 * gson1.toJson(dataMap1) );
 * 
 * %>
 * 
 * <!-- Step 5: Render the chart --> <%=columnChart.render()%> <br> <a class=""
 * href="checkCriminal.jsp"> Go to Police Home (Click Here) </a>
 */