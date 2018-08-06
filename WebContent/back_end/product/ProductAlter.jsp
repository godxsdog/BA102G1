<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>

<html>
<head>
<%@ include file="/back_end/product/page1.file" %>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/fileinput.js"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/themes/fa/theme.js"></script>
<link href="<%=request.getContextPath() %>/back_end/product/filejs/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/piexif.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/sortable.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/purify.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/fileinput.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/themes/fa/theme.js"></script>

<style type="text/css">
	.mm{
		margin-top:4cm;
		
	}
</style>

</head>
<body>
<%@ include file="/back_end/backEndNavBar.file"%>
<%@ include file="/back_end/backEndLSide.file"%>
<%
int proNo = Integer.valueOf(request.getParameter("proNo"));
ProductService prodDao = new ProductService();
Product prod = (Product)prodDao.getOneProduct(proNo);
session.setAttribute("prod",prod);
%>

<div class="row col-xs-10 col-sm-10 ">
<form action="<%=request.getContextPath() %>/ProductAlter" method="POST" enctype="multipart/form-data" name="form">
<table class="table table-hover mm" style="background-color:#CCEEFF;">
<tr><td>商品編號<br><input type="text" name="proNo" value="<%=proNo%>" class="btn btn-default" required readonly></td></tr>
<tr><td>商品名稱<br><input type="text" size="25" maxlength="15" id="proName" name="proName" class="btn btn-default" value="<%=prod.getProdName() %>" required></td></tr>
<tr><td>商品描述<br>
			  <div class="form-group">
				<textarea rows="4" cols="25" name="proDesc" id="proDescpt" maxlength="100" class="form-control" required><%=prod.getProdDescpt() %></textarea>
			  </div>
</td></tr>
<tr><td>商品價格<br><input type="text" name="proPrice" maxlength="7" id="proPrice" value="<%=prod.getProdPrice() %>" class="btn btn-default" required></td></tr>
<tr><td>商品類別<br>
			<c:if test="${prod.prodType=='寵物飼料'}">
				<select name="proType" class="btn btn-default" id="proType"> 
  					<option value="寵物飼料" selected>寵物飼料</option> 
  					<option value="寵物用品">寵物用品</option>
  					<option value="其他">其他</option>
	 			</select>
	 		</c:if>
	 		<c:if test="${prod.prodType=='寵物用品'}">
				<select name="proType" class="btn btn-default" id="proType"> 
  					<option value="寵物飼料" >寵物飼料</option> 
  					<option value="寵物用品" selected>寵物用品</option>
  					<option value="其他">其他</option>
	 			</select>
	 		</c:if>
	 		<c:if test="${prod.prodType=='其他'}">
				<select name="proType" class="btn btn-default" id="proType"> 
  					<option value="寵物飼料">寵物飼料</option> 
  					<option value="寵物用品">寵物用品</option>
  					<option value="其他" selected>其他</option>
	 			</select>
	 		</c:if>
	 		
</td></tr>
<tr><td>商品圖片<input id="input-fa" name="prodimg" type="file" class="file-loading"></td></tr>
<tr><td><div style="padding-left:8cm;">
<input class="btn btn-info btn-pressure btn-sensitive" type="submit" value="送出修改" >

<button class="btn btn-info btn-pressure btn-sensitive" href="<%=request.getContextPath() %>/back_end/product/productManage.jsp">
回商品頁面
</button>
<input type="button" value="alter" onclick="alter()" class="btn btn-primary">
</div>
<input class="btn btn-primary" type="hidden" name="action" value="alter">
</td></tr>

</table>
</form>
</div>




</body>
<script>
$("#input-fa").fileinput({
    theme: "fa",
    uploadUrl: "/file-upload-batch/2"
});
</script>
<script>
	function alter(){
	var proName = document.getElementById("proName");
	var proDescpt = document.getElementById("proDescpt");
	var proPrice = document.getElementById("proPrice");
	var proType = document.getElementById("proType");
	proName.value="無穀全齡低敏挑嘴配方";
	proDescpt.value="全齡階段全犬種●高優質全天然鮭魚肉，滿足愛犬挑剔的味蕾●100%無穀類配方，有效預防過敏●高含量亞麻油酸，有效護膚亮毛";
	proPrice.value="2000";
	proType.value="寵物飼料";
	}
</script>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>