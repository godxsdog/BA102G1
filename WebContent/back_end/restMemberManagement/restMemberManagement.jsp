<%@page import="com.restaurant.model.Restaurant"%>
<%@page import="com.restaurant.model.RestaurantService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>


<%
	RestaurantService restaurantService = new RestaurantService();
	List<Restaurant> restauranBacktList = (request.getAttribute("restauranBacktList")==null)?restaurantService.getAll():(List<Restaurant>)request.getAttribute("restauranBacktList");
	
	pageContext.setAttribute("restauranBacktList", restauranBacktList);
	
%>
<!DOCTYPE html>
<html lang="">

<head>
    <%@ include file="/back_end/actFiles/actBackCss.file" %>
</head>

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
		td{
			text-align: center;
		}
		
</style>
<body>
    <%@ include file="/back_end/backEndNavBar.file" %>
	<%@ include file="/back_end/backEndLSide.file" %>

    	<div class="container-fluid">
				<div class="row">
					<div class="col-xs-12 col-sm-10 ">
					<form method="post" action="<%=request.getContextPath()%>/restaurant/restaurantController" >
						<div class="form-group row">
							<div class="col-xs-2">
								餐廳編號:<input class="form-control" type="text" name="restNo" value="">
							</div>
							<div class="col-xs-2">
								餐廳名稱:<input class="form-control" type="text" name="restName" value="">
							</div>
							<div class="col-xs-2">	
								餐廳地址:<input class="form-control" type="text" name="restAdd" value="">
							</div>	
							<div class="col-xs-2">	
								餐廳電話:<input class="form-control" type="text" name="restPhone" value="">
							</div>
								<br>
								
							<input type="submit" class="btn btn-primary text-rigth" value="搜尋"> 
							
							<a href="<%=request.getContextPath()%>/back_end/restMemberManagement/restMemberManagement.jsp">
								<input type="button" class="btn btn-primary text-rigth" value="全部餐廳">
							</a>
							
							
							
							<input type="hidden" name="action" value="CompositeQueryBack">	
						</div>		
					</form>
						<table class="table table-hover table-striped table-condensed table-bordered actTable">
						<c:if test="${not empty reatManeErr}">
							${reatManeErr } 
						</c:if>
						
						
						<thead>
							<tr>
								
								<th>餐廳編號</th>
								<th>餐廳名稱</th>
								<th>餐廳地址</th>
								<th>餐廳電話</th>
								
								<th>餐廳種類</th>
								<th>餐廳狀態</th>
								<th>審核餐廳</th>
							</tr>
						</thead>
						<%@ include file="/back_end/actFiles/pageBack2.file"  %>
						<c:forEach var="restauranBack" items="${restauranBacktList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
								<form method="post" action="<%=request.getContextPath()%>/restaurant/restaurantController">
									<tr>
										<td>${restauranBack.restNo}</td>
										<td>${restauranBack.restName}</td>
										<td>${restauranBack.restAdd}</td>
										<td>${restauranBack.restPhone}</td>
										
										
											<c:if test="${restauranBack.restKind=='0'}">
												<td>貓</td>
											</c:if>
											<c:if test="${restauranBack.restKind=='1'}">
												<td>狗</td>
											</c:if>
											<c:if test="${restauranBack.restKind=='2'}">
												<td>其他</td>
											</c:if>
										<td>
											<select name="restReviewStatus">    
											  <option value="0" ${(restauranBack.restReviewStatus=='0')?'selected':''} >已審核通過</option>
											  <option value="1" ${(restauranBack.restReviewStatus=='1')?'selected':''} >審核未通過</option>
											  <option value="2" ${(restauranBack.restReviewStatus=='2')?'selected':''} >未審核</option>
											</select>
										</td>
										<td>
											<input type="hidden" name="restNo" value="${restauranBack.restNo}">
											<input type="hidden" name="action" value="restManagement">
											<input type="submit" class="btn btn-primary" value="修改">
										</td>
									</tr>
								</form>	
							</c:forEach>
								
							
						
						</table>
						<%@ include file="/back_end/actFiles/pageBackLatest.file" %>	
					</div>
				</div>
			</div> 
  
    <script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>
</body>

</html>