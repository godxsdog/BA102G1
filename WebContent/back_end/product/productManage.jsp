<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>

<%
	ProductService prodService = new ProductService();
	List<Product> prodList=prodService.getAll();
	session.setAttribute("prodList", prodList);
	
%>
<html>
<head>
	
	 <%@ include file="/back_end/product/page1.file" %>
<style type="text/css">
	.mm{
		margin-top:4cm;
		
	}
	
</style>
</head>
<body style="bgcolor:#fceaf0;">
<%@ include file="/back_end/backEndNavBar.file"%>
<%@ include file="/back_end/backEndLSide.file"%>

			<div class="row col-xs-10 col-sm-10 ">

  <table class="table table-hover mm" >
    <thead>
      <tr style="background-color:#E8CCFF;">
      	
        <th>商品名稱</th>
        <th>商品類別</th>
        <th>商品價格</th>
        <th>商品狀況</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
     <c:forEach var="product" items="${prodList}">
      
     
     
     <c:if test = "${product.prodState == 0}">
      <tr>
        
        <td><a href="<%=request.getContextPath()%>/back_end/product/ProdView.jsp?prodNo=${product.prodNo}" target="_blank">${product.prodName}</a></td>
        <td>${product.prodType}</td>
        <td>${product.prodPrice}</td>
        
        <td>上架中</td>
        <form action="<%=request.getContextPath() %>/ProductRemove" method="POST">  
        <input type="hidden" name="prodNo" id="no<%=prodList.size()%>" value="${product.prodNo}">
        <td><input class="btn btn-primary" type="submit" value="商品下架"></td>
         </form> 
        <form action="<%=request.getContextPath() %>/back_end/product/ProductAlter.jsp" method="POST">  
        <input type="hidden" name="proNo" value="${product.prodNo}">
        <td><input class="btn btn-primary" type="submit" value="商品修改" ></td>
         </form>
        
      </tr>
     
      </c:if>
      </c:forEach>
    </tbody>
  </table>
 
</div> 

	<div class="row col-xs-10 col-sm-10" align="center" style="padding-left:10cm;">
	  
		<a href="<%=request.getContextPath() %>/back_end/product/productUpdate.jsp" ><input type="submit" class="btn btn-primary" value="上架新商品"></a>
	  	<a href="<%=request.getContextPath() %>/back_end/order/OrderManage.jsp" ><input type="button" class="btn btn-primary" value="訂單管理"></a>
	</div> 

	
	
	<script type="text/javascript">
		
	</script>

			

	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>