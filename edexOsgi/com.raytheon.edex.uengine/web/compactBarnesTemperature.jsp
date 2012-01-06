<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ASCII Request</title>
<STYLE TYPE="text/css">
h1    {text-align:center}
table {background-color:silver;border-style:solid;border-color:black;border-width:1}
td    {background-color:white;border-style:solid;border-color:black;border-width:1}
th    {background-color:white;border-style:solid;border-color:black;border-width:1}
</STYLE>
</head>
<body>
<table align=center>
<TR><TD><img src="rayAWIPS.jpg" align=middle></TD>
<TD><H1>&mu;Engine Demonstration<BR>JScript Script Runner<BR>Barnes Analysis</H1></TD>
<TD><img src="rayAWIPS.jpg" align=middle></TD></TR>
<TR><TD COLSPAN=3>
<CENTER>
This script demonstrates using Barnes Scheme analysis to correct a temperature grid.<BR>
This page uses <em>BarnesTemperatureAnalysis.js</em> from the script library.
</CENTER>
</TD></TR>
</table>

<form method=post action="runAction.jas">
<input type=hidden name=requesttype value=javascript>
<input type=hidden name=sortby value=timeobs>
<table align=center>
<tr><td colspan=2>
<textarea name="actionXML" cols="80" rows="22" style="background-color:aqua">
/* the compact action script */
include("BarnesAnalysis.js");
var barnes = new BarnesAnalysis();
/* set the grib search parameters */
barnes.addParameter(true,"paramid","Temperature");
barnes.addParameter(true,"levelinfo","2.0_m");
barnes.addParameter(true,"forecasttime","0");
barnes.addParameter(true,"gridid",212);
barnes.setCount(true,1);
barnes.setSortValue(true,"basetime");
/* set the lat/lon bounds*/
barnes.setSpatialBounds(43.00, -98.00, 37.00, -92.00);
/* set the metar search parameters */
barnes.addParameter(false,"refhour","20070601190000");
barnes.setCount(false,0);
barnes.setSortValue(false,"timeobs");
/* set analysis parameters */
barnes.setObParameter("temperature");
barnes.setBarnesParameters("50000.0","0.50","1","1");
/* set image properties */
barnes.setColorMap("GribTempRGB");
barnes.setFormat("png");
barnes.setScaleFactor(3.0);
/* execute the query */
barnes.execute();



</textarea>
</td></tr>
<tr><th colspan=2><em>Enter Values for Message</em></th></tr> 

<tr><td><B>Name:</B></td>
<td><input type=text name=name value="JS Request"></td></tr>

</td></td>
<tr><td><B>Action:</B></td><td>
<input type=radio name=function value=validate disabled>Validate
<input type=radio name=function value=subscribe disabled>Subscribe
<input type=radio name=function value=execute checked>Execute</tr>
</td></tr></table>
<br>
<div align=center>
<input type="submit" value="Get Report">
<input type=reset>
Timeout:
<select name=receiveTime>
   <option value=60000>1 minute
   <option value=120000>2 minutes
   <option value=180000>3 minutes
   <option value=240000 selected>4 minutes
   <option value=300000>5 minutes
   <option value=360000>6 minutes
   <option value=420000>7 minutes
   <option value=480000>8 minutes
   <option value=540000>9 minutes
   <option value=600000>10 minutes
</select>
</div>
</form>
</body>
</html>