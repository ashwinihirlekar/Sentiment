<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/LoginFormStyle.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>


	<form action="authenticateUser.htm" method="post">


		<div class="content">
			<div>
				<fieldset class="fieldset">

					<div class="text_label">User ID:</div>
					<input class="text" name="txtUserName" /><br></br>
					<div class="text_label">Password:</div>
					<input type="password" class="text" name="txtPassword" /><br></br>
					<div class="btn_middle">
						<input type="submit" class="submit" value=""></input>
					</div>
				</fieldset>

			</div>
		</div>
		<div class="new">
			<a href="user/facebook.htm"><img id="fb"
				src="images/facebook_icon.png" alt="Facebook" title="Facebook"></img></a>
		</div>

	</form>


</body>
</html>