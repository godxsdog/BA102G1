<%@page import="com.restaurant.model.Restaurant"%>
<%@page import="com.restMember.model.RestMember"%>
<%@page import="com.restMember.model.RestMemberService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	RestMember restMember = (RestMember)session.getAttribute("restMember");
	Restaurant restaurant = (Restaurant)session.getAttribute("restaurant");
%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>寵物 You & Me</title>
<style type="text/css">
.aa {
	margin-top: 20px;
}
.img-bg{
	max-width:100%;
}

    
	</style> 


</style>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
			<![endif]-->

</head>
<body background="<%=request.getContextPath()%>/front_end/actFiles/465.jpg"  style= background-size:cover;>
	<%@ include file="/front_end/actFiles/restMemberNavBar.file" %>
	
	
	<div class="container-fluid">
        <div class="row"> 
        	<%@ include file="/front_end/actFiles/restMemberList.file" %>
        <div class="col-xs-12 col-sm-8">       
   
    <div class="panel-group col-sm-offset-2 col-sm-8">
    
    
	<h5 class=" page-header text-right">目前位置:變更密碼</h5>
	
		<h1 class="text-center">變更密碼</h1>

				<div class="form-horizontal">
				
					<form method="post" action="<%=request.getContextPath()%>/restMember/restMemberController">
							<c:if test="${not empty changePswError}">
								${changePswError}
							</c:if>
							<input type="hidden" name="restNo" value="<%=restaurant.getRestNo() %>">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳會員帳號
								</label>
								<div class="col-sm-9">
									<input type="text" name="restMemId" class="form-control" value="${restMember.restMemId}" readonly>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳會員舊密碼
								</label>
								<div class="col-sm-9">
									<input type="password" name="restMemOldPsw" class="form-control" value="">
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳會員新密碼
								</label>
								<div class="col-sm-9">
									<input type="password" name="restMemNewPsw" class="form-control" value="">
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									再次輸入新密碼
								</label>
								<div class="col-sm-9">
									<input type="password" name="restMemPsw" class="form-control" value="">
									
								</div>
							</div>
							<input type="hidden" name="action" value="changeRestMemPsw">
							<input class="btn btn-primary btn-lg btn-block login-button login"
								type="submit" value="確認更改">
									
								
						</form>	
								
					</div>	
				</div>
			</div>	
		</div>	
	</div>
	<%@ include file="/front_end/frontEndButtomFixed.file" %>      	
	
</body>
</html>