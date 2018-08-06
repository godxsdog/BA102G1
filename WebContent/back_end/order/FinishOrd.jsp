<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.order.model.*"%>
<%
	OrdService ordDao = new OrdService();
	List<Ord> ordList = ordDao.getAll();
	session.setAttribute("ordList", ordList);
%>
<html>
<head>
<%@ include file="/back_end/order/page1.file" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-*.min.js"></script>
<style type="text/css">
.mm {
	margin-top: 2.5cm;
	
}
.m {
	margin-top: 0.5cm;
	
}

</style>

</head>
<body>
<%@ include file="/back_end/backEndNavBar.file"%>
<%@ include file="/back_end/backEndLSide.file"%>

<div class="row col-xs-10 col-sm-10 ">
<div>
<ul class="nav nav-tabs mm">
<li role="allorder"><a href="<%=request.getContextPath()%>/back_end/order/OrderManage.jsp">所有訂單</a></li>
<li role="dealorder"><a href="<%=request.getContextPath()%>/back_end/order/NoShipOrd.jsp">處理中訂單</a></li>
<li role="finishorder" class="active"><a href="<%=request.getContextPath()%>/back_end/order/FinishOrd.jsp">已完成訂單</a></li>
</ul>
</div>
<div>
<table class="table table-hover m">
	<thead>
		<tr style="background-color: #E8CCFF;">
			<th>訂單編號</th>
			<th>訂單日期</th>
			<th>訂單狀態</th>
			<th>顧客名稱</th>
			<th>合計</th>
		</tr>
	</thead>
<c:forEach var="ordList" items="${ordList}">
<form action="<%=request.getContextPath()%>/OrderUpdate" method="POST">
<c:if test="${ordList.ordStatus == 3 || ordList.ordStatus == 4}">
<tr>
   
	<td>${ordList.ordNo}</td>
	<td>${ordList.ordDate}</td>
	<c:if test="${ordList.ordStatus == 3}">
	<td><select name="ordstate" class="btn btn-default">
　		<option value="1">未出貨</option>
　		<option value="2">已出貨</option>
　		<option value="3" selected>已結案</option>
		<option value="4">已取消</option>
	</select></td>
	</c:if>
	<c:if test="${ordList.ordStatus == 4}">
	<td><select name="ordstate" class="btn btn-default">
　		<option value="1">未出貨</option>
　		<option value="2">已出貨</option>
　		<option value="3">已結案</option>
		<option value="4" selected>已取消</option>
	</select></td>
	</c:if>
	<td>${ordList.conName}</td>
	<td>${ordList.ordTotal}</td>
	<td><input type="submit" class="btn btn-primary" value="修改訂單"></td>
   	<input type="hidden" name="ordNo" value="${ordList.ordNo}">
</tr>
</c:if>
</form>
</c:forEach>
</table>
</div>
<div class="row col-xs-10 col-sm-10" align="center">
	  
		<a href="<%=request.getContextPath() %>/back_end/product/productManage.jsp" ><input type="button" class="btn btn-primary" value="商品管理"></a>
	  	
	</div>
</div>
</body>
</html>