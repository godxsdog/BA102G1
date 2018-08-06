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

<title>寵物 You & Me</title>
<style type="text/css">
.aa {
	margin-top: 20px;
}

    	.img-bg{
    	max-width:100%;
    	}
    




</style>

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
    
    
	<h5 class=" page-header text-right">目前位置:餐廳資料設定</h5>
	
		<h1 class="text-center">餐廳資料設定</h1>

				<div class="form-horizontal">
				
					<form method="post" action="<%=request.getContextPath()%>/restaurant/restaurantController">
							<c:if test="${not empty updateError}">
								${updateError}
							</c:if>
							
							<input type="hidden" name="restNo" value="<%=restaurant.getRestNo() %>">
							<input type="hidden" name="restReviewStatus" value="<%=restaurant.getRestReviewStatus() %>">
							<input type="hidden" name="restLongtitude" class="form-control" value="<%=restaurant.getRestLongtitude() %>" >
							<input type="hidden" name="restLatitude" class="form-control" value="<%=restaurant.getRestLatitude() %>" >
							<input type="hidden" name="restLocate" class="form-control" value="<%=restaurant.getRestLocate() %>" >
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳名稱
								</label>
								<div class="col-sm-9">
									<input type="text" name="restName" class="form-control" value="<%=restaurant.getRestName() %>">
									
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳地址
								</label>
								<div class="col-sm-9">
									<input type="text" name="restAdd" class="form-control" value="<%=restaurant.getRestAdd() %>" readonly>

								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳電話
								</label>
								<div class="col-sm-9">
									<input type="text" name="restPhone" class="form-control" value="<%=restaurant.getRestPhone() %>" >
									
								</div>
							</div>

							
									<input type="hidden" name="restIntro" class="form-control" value="<%=restaurant.getRestIntro() %>" >
								

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳種類
								</label>
								<div class="col-sm-9">
									
									<select name="restKind" class="form-control">
									  <option value="0" ${(restaurant.restKind=="0")?'selected':''}>貓餐廳</option>
									  <option value="1" ${(restaurant.restKind=="1")?'selected':''}>狗餐廳</option>
									  <option value="2" ${(restaurant.restKind=="2")?'selected':''}>其他餐廳</option>
									</select>
 									
									
									
								</div>
							</div>
							
							
							<input type="hidden" name="action" value="updateRestMemData">
							<input type="submit" class="btn btn-primary btn-lg btn-block login-button login" 
									data-toggle="modal" data-target="#myModal" value="確認更改">	
											
							
														
						</form>	
								
					</div>	
				</div>
			</div>	
		</div>	
	</div>
	<%@ include file="/front_end/frontEndButtomFixed.file" %>      	
	
</body>
</html>