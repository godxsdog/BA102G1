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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>


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
<li role="allorder" class="active"><a href="<%=request.getContextPath()%>/back_end/order/OrderManage.jsp">�Ҧ��q��</a></li>
<li role="dealorder"><a href="<%=request.getContextPath()%>/back_end/order/NoShipOrd.jsp">�B�z���q��</a></li>
<li role="finishorder"><a href="<%=request.getContextPath()%>/back_end/order/FinishOrd.jsp">�w�����q��</a></li>
</ul>
</div>
<div>
<table class="table table-hover m" style="padding:0px;bgcolor:#BBFFEE;">
	<thead>
		<tr style="background-color: #E8CCFF;">
			<th>�q��s��</th>
			<th>�q����</th>
			<th>�q�檬�A</th>
			<th>�U�ȦW��</th>
			<th>�X�p</th>
		</tr>
	</thead>
<c:forEach var="ordList" items="${ordList}">
	<form action="<%=request.getContextPath()%>/OrderUpdate" method="POST">

<tr>
   
	<td>${ordList.ordNo}</td>
	<td>${ordList.ordDate}</td>
	<c:if test="${ordList.ordStatus == 0 || ordList.ordStatus == 1}">
	<td >
	
	 <select name="ordstate" id="tte" class="btn btn-default"> 
  		<option value="1" selected>���X�f</option> 
  		<option value="2">�w�X�f</option>
  		<option value="3">�w����</option>
  		<option value="4">�w����</option>
	 </select>

	
	</td>
	</c:if>
	<c:if test="${ordList.ordStatus == 2}">
	<td>
	<select name="ordstate" id="tte" class="btn btn-default"> 
  		<option value="1">���X�f</option> 
  		<option value="2" selected>�w�X�f</option>
  		<option value="3">�w����</option>
  		<option value="4">�w����</option>
	 </select>
	</td>
	</c:if>
	<c:if test="${ordList.ordStatus == 3}">
	<td>
	<select name="ordstate" id="tte" class="btn btn-default"> 
  		<option value="1">���X�f</option> 
  		<option value="2">�w�X�f</option>
  		<option value="3" selected>�w����</option>
  		<option value="4">�w����</option>
	 </select>
	</td>
	</c:if>
	<c:if test="${ordList.ordStatus == 4}">
	<td>
	<select name="ordstate" id="tte" class="btn btn-default"> 
  		<option value="1">���X�f</option> 
  		<option value="2">�w�X�f</option>
  		<option value="3">�w����</option>
  		<option value="4" selected>�w����</option>
	 </select>
	</td>
	</c:if>
	<td>${ordList.conName}</td>
	<td>${ordList.ordTotal}</td>
	<td>
	
	<input id="changede" type="submit" value="�T�w�ק�" class="btn btn-primary">	
	
	</td>
	
   	<input type="hidden" name="ordNo" value="${ordList.ordNo}">
</tr>

</form>
</c:forEach>
</table>
</div>

<div class="row col-xs-10 col-sm-10" align="center" >
	  
		<a href="<%=request.getContextPath() %>/back_end/product/productManage.jsp" ><input type="button" class="btn btn-primary" value="�ӫ~�޲z"></a>
	  	
	</div> 
</div>

</body>
<script>
function chg(){
	var press = document.getElementById("press");
	var tte = document.getElementById("tte");
	var confirm = document.getElementById("confirm");
	var change = document.getElementById("change");
	var changede = document.getElementById("changede");
	press.style.display='none';
	tte.style.display='';
	change.style.display='none';
	changede.style.display='';
}
</script>
<!-- /.container -->
	<!-- jQuery -->
	<script src="<%=request.getContextPath()%>/front_end/js/jquery.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script
		src="<%=request.getContextPath()%>/front_end/js/bootstrap.min.js"></script>
	<!-- Script to Activate the Carousel -->
	  
</html>