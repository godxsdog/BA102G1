<%@page import="com.restMember.model.RestMemberService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.restaurant.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>

<%
    RestaurantService restaurantSvc = new RestaurantService();
	Restaurant restaurant = new Restaurant();
    List<Restaurant> list = restaurantSvc.getAll();
    session.setAttribute("list",list);  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/front_end/actFiles/restFrontCss.file" %>
<head>
<title>寵物 You & Me</title>
</head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script>
	$(document).ready(function(){
		$("tr:even").css("background-color","white");
		$("tr:odd").css("background-color","#D9FFFF");
		
	})
	</script>
	<style type="text/css">
		.actTable{
			margin-top: 50px;
		}
		th{
			text-align: center;
			font-size: 16px;
			background: #40E0D0;
		}
		
		
</style>

<body background="<%=request.getContextPath()%>/front_end/actFiles/465.jpg"  style=background-size:cover;>

<%@ include file="/front_end/actFiles/restMemberNavBar2.file" %>
<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 col-sm-offset-1 col-sm-10">
					<div class="col-xs-12 col-sm-12"><br>
					<h1 class=" page-header text-left">狗狗貓貓友善餐廳</h1>
					
 				<!-- 					複合查詢                                                                        -->
					<form method="post" action="<%=request.getContextPath()%>/restaurant/restaurantController" >
						<div class="form-group row">
							<div class="col-xs-3">
								餐廳名稱:<input class="form-control" type="text" name="restName" value="">
							</div>
							<div class="col-xs-3">	
								餐廳地址:<input class="form-control" type="text" name="restAdd" value="">
							</div>	
							<div class="col-xs-3">	
								餐廳電話:<input class="form-control" type="text" name="restPhone" value="">
							</div>
								<br>
								
							<input type="submit" class="btn btn-primary text-rigth" value="搜尋"> 
							
							<a href="<%=request.getContextPath()%>/front_end/restMember/restMemberList.jsp">
								<input type="button" class="btn btn-primary text-rigth" value="全部餐廳">
							</a>
							
							<a href="<%=request.getContextPath() %>/front_end/restaurant/newRestaurant.jsp">
								<input type="button" class="btn btn-primary" value="新增餐廳">
							</a>
							
							<input type="hidden" name="action" value="CompositeQuery">	
						</div>		
					</form>
					
					
					<table class="table table-striped">
						<tr>
							
						<th width="150px" >&nbsp;&nbsp;&nbsp;&nbsp;餐廳名稱</th>
						<th>餐廳地址</th>
						<th>餐廳電話</th>
						
						<th>餐廳種類</th>
						<th>審核狀態</th>
						<th></th>
						
					</tr>
					<%@ include file="/front_end/actFiles/pageRestList.file"  %>
					<c:forEach var="restaurant" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr align='center' valign='middle'>
							
							<td>&nbsp;&nbsp;&nbsp;&nbsp;${restaurant.restName}</td>
							<td >${restaurant.restAdd}</td>
							<td>${restaurant.restPhone}</td>
							
							<td>
						
									<c:if test="${restaurant.restKind=='0'}">
										<input type="hidden" name="restKind" class="form-control" value="0" >貓餐廳
									</c:if>
									<c:if test="${restaurant.restKind=='1'}">
										<input type="hidden" name="restKind" class="form-control" value="1" >狗餐廳
									</c:if>
									<c:if test="${restaurant.restKind=='2'}">
										<input type="hidden" name="restKind" class="form-control" value="2" >其他餐廳
									</c:if>
												
							</td>
							<td>
									<c:if test="${restaurant.restReviewStatus=='0'}">
										<input type="hidden" name="restReviewStatus" class="form-control" value="0" >已審核通過
									</c:if>
									<c:if test="${restaurant.restReviewStatus=='1'}">
										<input type="hidden" name="restReviewStatus" class="form-control" value="1" >審核未通過
									</c:if>
									<c:if test="${restaurant.restReviewStatus=='2'}">
										<input type="hidden" name="restReviewStatus" class="form-control" value="2" >尚未審核
									</c:if>
									<c:if test="${restaurant.restReviewStatus=='3'}">
										<input type="hidden" name="restReviewStatus" class="form-control" value="2" >已註冊成功
									</c:if>
							</td>
							<td>
							<c:if test="${restaurant.restReviewStatus=='0'}">
								<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/front_end/restMember/restMemberRegister.jsp">
							     <input type="submit" class="btn btn-primary" value="註冊">
							     <input type="hidden" name="restNo" value="${restaurant.restNo}">
							     <input type="hidden" name="restName" value="${restaurant.restName}">
							     <input type="hidden" name="restAdd" value="${restaurant.restAdd}">
							     <input type="hidden" name="restPhone" value="${restaurant.restPhone}">
							     <input type="hidden" name="restIntro" value="${restaurant.restIntro}">
							     <input type="hidden" name="restKind" value="${restaurant.restKind}">
							     <input type="hidden" name="restReviewStatus" value="${restaurant.restReviewStatus}">
							     <input type="hidden" name="restLongtitude" class="form-control" value="<%=restaurant.getRestLongtitude() %>" >
								 <input type="hidden" name="restLatitude" class="form-control" value="<%=restaurant.getRestLatitude() %>" >
								 <input type="hidden" name="restLocate" class="form-control" value="<%=restaurant.getRestLocate() %>" >
							 </FORM>
							</c:if>
							
							<c:if test="${restaurant.restReviewStatus=='1'}">
								<input type="submit" class="btn btn-danger" value="審核未通過"  disabled>
							</c:if>
							<c:if test="${restaurant.restReviewStatus=='2'}">
								<input type="submit" class="btn btn-danger" value="未審核"  disabled>
							</c:if>
							<c:if test="${restaurant.restReviewStatus=='3'}">
								<input type="submit" class="btn btn-success" value="已註冊"  disabled>
							</c:if>
							  
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="/front_end/actFiles/pageLatest.file"  %>
				
				
			</div>
		</div>
	</div>	
</div>
<%@ include file="/front_end/frontEndButtomFixed.file" %>      
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
