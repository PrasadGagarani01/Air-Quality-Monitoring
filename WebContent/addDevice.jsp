<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title> Welcome Corporation </title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Pooled Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript">
	
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 


</script>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="css/style.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" href="css/morris.css" type="text/css" />
<!-- Graph CSS -->
<link href="css/font-awesome.css" rel="stylesheet">
<!-- jQuery -->
<script src="js/jquery-2.1.4.min.js"></script>
<!-- //jQuery -->
<link
	href='//fonts.googleapis.com/css?family=Roboto:700,500,300,100italic,100,400'
	rel='stylesheet' type='text/css' />
<link href='//fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<!-- lined-icons -->
<link rel="stylesheet" href="css/icon-font.min.css" type='text/css' />
<!-- //lined-icons -->
</head>
<body>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<!--header start here-->
		
				<!--heder end here-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><h1>Air Quality Monitoring</h1></a> </li>
				</ol>
				<!--four-grids here-->

				<!--//four-grids here-->
				<!--agileinfo-grap-->
				<div class="agileinfo-grap">
				<h2>Add Device</h2><br><br>
					<div class="agileits-box">
						
							 <form action="AddDevice" method="post">
								
							<div class="form-group row">
                			<input type="hidden" name="action" value="add"/>
    						<label for="inputEmail3" class="col-sm-3 form-control-label">Area</label>
                	  		<div class="col-sm-9">
                    		<input type="text" cols="7" rows="1" class="form-control" name="areaTxt" placeholder="Enter Device Area" required></input>
                 			</div>
               			 	</div>
               			 	
               			 	<div class="form-group row">
                			<input type="hidden" name="action" value="add"/>
    						<label for="inputEmail3" class="col-sm-3 form-control-label">Location</label>
                	  		<div class="col-sm-9">
                    		<input type="text" cols="7" rows="1" class="form-control" name="locTxt" placeholder="Enter Device Location" required></input>
                 			</div>
               			 	</div>
               			 	
               			 	
               			 	
               			 		
							
                			
                			<div class="form-group mb-n">

									<center>
									<button type="submit" class="btn btn-primary">Add</button>
									</center>
								</div>
							</form>
						
						
					</div>
				</div>
				
				
			</div>
		</div>
		<!--//content-inner-->
		<!--/sidebar-menu-->
		<div class="sidebar-menu">
			<header class="logo1">
				<a href="#" class="sidebar-icon"> <span class="fa fa-bars"></span>
				</a>
			</header>
			<div style="border-top: 1px ridge rgba(255, 255, 255, 0.15)"></div>
			<div class="menu">
				<ul id="menu">
					<li><a href="index.html"><i class="fa fa-tachometer"></i>
							<span>Dashboard</span>
							<div class="clearfix"></div></a></li>


					
					
					
				</ul>
			</div>
		</div>
		
	</div>
			<div class="sidebar-menu">
		<header class="logo1">
			<a href="#" class="sidebar-icon"> <span class="fa fa-bars"></span>
			</a>
		</header>
<div style="border-top: 1px ridge rgba(255, 255, 255, 0.15)"></div>
		<div class="menu">
			<ul id="menu">
				<li><a href="welcomeCorpo.jsp"><i class="fa fa-home"></i>
						<span>Home</span>
				<li><a href="addDevice.jsp"><i class="fa fa-edit"></i>
						<span>Add Device</span>
		
				<li><a href="viewDevice.jsp"><i class="fa fa-edit"></i>
						<span>View Device</span>
						
						
						
						<li><a href="viewCorpoPrediction.jsp"><i class="fa fa-edit"></i>
						<span>Predict Air Quality</span>
				
				<li><a href="index.jsp"><i class="fa fa-home"></i>
						<span>Log Out</span>
		</div>
</div>
	
	
	<!--js -->
	<script src="js/jquery.nicescroll.js"></script>
	<script src="js/scripts.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
	<!-- /Bootstrap Core JavaScript -->
	<!-- morris JavaScript -->
	<script src="js/raphael-min.js"></script>
	<script src="js/morris.js"></script>

</body>
</html>