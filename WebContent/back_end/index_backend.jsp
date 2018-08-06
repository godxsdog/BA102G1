<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>


<%
	Emp emp = (Emp) session.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>


<!DOCTYPE html>
<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link href="<%=request.getContextPath()%>/back_end/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/nav.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/colorplan.css"
	rel="stylesheet">
<!-- Custom CSS -->
<link
	href="<%=request.getContextPath()%>/back_end/css/modern-business.css"
	rel="stylesheet">
<!-- Custom Fonts -->
<link
	href="<%=request.getContextPath()%>/back_end/font-awesome/css/font-awesome.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/back_end/css/backend.css"
	rel="stylesheet">
<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>

<body>

<%@ include file="backEndNavBar.file"%>

	<div class="container-fluid">
		<div class="row">




			<%@ include file="backEndLSide.file"%>





			<div class="col-xs-12 col-sm-8">


				<h4 class="page-header text-right"></h4>


				<div
					class="panel panel-default col-sm-offset-3 col-sm-6 text-center">
					<div class="panel-heading">
<!-- 						<h3 class="panel-title">待處理事項</h3> -->
						<div align="center">
							<Img
								src="<%=request.getContextPath()%>/back_end/images/logo.png"
								height="250px" width="400px" />
						</div>
					</div>

					<ul class="list-group">
<!-- 						<li class="list-group-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5 -->
<!-- 							筆待審核訂單 <span class="label label-info pull-right">5</span> -->
<!-- 						</li> -->
<!-- 						<li class="list-group-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1 -->
<!-- 							筆待審核餐廳 <span class="label label-info pull-right">1</span> -->
<!-- 						</li> -->
<!-- 						<li class="list-group-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2 -->
<!-- 							筆待處理申訴 <span class="label label-info pull-right">2</span> -->
<!-- 						</li> -->
<!-- 						<li class="list-group-item">0 筆待處理檢舉</li> -->
<!-- 						<li class="list-group-item">0 筆待審核活動</li> -->
						<h1>PET YOU & ME</h1>
						<H3>平臺管理系統</H3>
					</ul>







				</div>

		


			</div>

			<script src="https://code.jquery.com/jquery.js"></script>
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>