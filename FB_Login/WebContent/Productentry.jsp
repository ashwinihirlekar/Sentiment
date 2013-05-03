<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import ="java.io.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.ProductEntryServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order your products Now..!!!!!!!!!!</title>
<style>
.htmlForm td{
font-family : Verdana, Arial, Helvetica,sans-serif;
font-size  : 12px
colour : #CC3300;
border-bottom:1px #EAE3C8 solid;
}

.htmlForm input{
	border  : 1px #BDB597 solid;
    font-family:tahoma;
	font-size:12px;
    padding:2px;
}
.points{
    font-family:tahoma;
    font-size:11px;
    color:#CC3300;
    padding-left:20px;
    padding-top:20px;
}
.points li{
    padding-top:5px;
}
.formHeading{
    font-family:Arial, Helvetica, sans-serif;
    font-size:16px;
    font-weight:bold;
    color:#CC3300;
    padding:10px;
}

</style>

<script type="text/javascript">

function my_curr_date() {      
	var currentDate = new Date()
	      var day = currentDate.getDate();
	      var month = currentDate.getMonth() + 1;
	      var year = currentDate.getFullYear();
	 var my_date = day+"/"+month+"/"+year;
	 document.getElementById("dateField").value=my_date ;
	 }

function calcDisc(){
	
	var mrp = document.getElementById("prod_mrp").value;
	var qty = document.getElementById("prod_qty").value;
	var disc = document.getElementById("disc").value;
	var finalPrice = (mrp - (mrp*(disc/100)))*qty;
	
	document.getElementById("price").value = finalPrice;
     
	}

</script>
</head>
<body onload='return my_curr_date()';>
<form name="productEntry" action="ProductEntryServlet" method="GET">
<table width="650px" border="0" cellpadding="2px" cellspacing="1px" bgcolor="#FFCC66">
        <tr bgcolor="#FFFFDD">
            <td width="2px" bgcolor="#FFCC66"></td>
            <td width="400px">
                <div class="formHeading">Order Form</div>

			<table width="100%" border="0" cellpadding="3px" class="htmlForm" cellspacing="0">
<tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>

	<tr>
		<td align = "left" >Date</td>
		 <td><input type="text" name="dateField" id="dateField" value =""/></td>  
	</tr> 
	<tr>
		<td align = "left">Enter Product name</td>
		<td><input type="text" size = "32" name="prod_name"></td>
	</tr>
	<tr>
	<td align = "left">Enter Product MRP</td>
	<td><input type="text" size = "32" id = "prod_mrp" name = "prod_mrp"></td>
	</tr>
	<tr>
	<td align = "left">Enter Product QTY</td>
	<td><input type="text" size = "32"  id  = "prod_qty" name="prod_qty"></td>
	</tr>
	<tr>
	<td align = "left">Discount</td>
	<td><input type="text" size = "32" id = "disc"  name = "disc"></td>
	</tr>

	<tr>
	<td align = "left">Price after discount</td>
	<td><input type="text" size = "32" name="price" id = "price" value=""></td><td></td>
	</tr>
	<tr>
	<td align = "left"><input type = "button" onclick="calcDisc();" value = "Calculate"></td>
	<td><input type="submit" value="submit"></td>
	</tr>
	</table>
	</td></tr>

</table>
</form>
</body>
</html>