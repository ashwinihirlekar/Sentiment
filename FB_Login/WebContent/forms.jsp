<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/FormStyle.css" rel="stylesheet" type="text/css"  />


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


<script type="text/javascript">

</script>

</head>
<body>
<div id="container">
  <div id="tabs" >
    <ul>
      <li><a href="#tab-1">Product</a></li>
          
    </ul>
    
    <form method="post"  action="addTicketDetails.htm" name="myform">
    
    	<div id="tab-1" class="menu" >						
    	<fieldset class="fieldset">
    	<div class="text_label">Date:</div><input class="text"  name="txtTicketNo"  />
    	<div class="text_label">Product name:</div><input class="text"   name="txtDescription" /><br></br>
    	<div class="text_label">Product MRP:</div>  <input class="text"   name="txtResolutionTime"   />
    	<div class="text_label">Product QTY:</div>  <input class="text"   name="txtResolutionsummary"   /><br></br>     	
    	<div class="text_label">Discount:</div>  <input id="datepicker" class="text"   name="txtDevStartDate"   />
    	<div class="text_label">Price after discount:</div>  <input id="datepicker1" class="text"   name="txtPlannedDate"   /><br></br>
    	
    	
    	<div class="btn_middle"><input type="submit" onclick="show()" value="" class="submit" name="btnDeveloper"></input>
    							
    	</div>
    	</fieldset>	                     
    	</div>
    	
    	
    	</fieldset>	
    	  </form>	
    	</div>
   
  </div>
</div>

</body>
</html>
