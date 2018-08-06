<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<%request.setCharacterEncoding("UTF-8");%>  
<%response.setCharacterEncoding("UTF-8");%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="page4.file" %>
</head>
<body>

<%

Map<String,Vector<Product>> CartMap = (Map<String,Vector<Product>>) session.getAttribute("shoppingcartMap");
HashMap<String,Integer> qtyMap = (HashMap<String,Integer>) session.getAttribute("qtyMap");
Member mem = (Member)session.getAttribute("member");
Vector<Product> buylist = CartMap.get(mem.getMemName());
String amount =  (String) request.getAttribute("amount");

%>	
<%@ include file="/front_end/frontEndNavBar.file" %>
<%@ include file="page2.file" %>
<table  width="720">
<tr bgcolor="#5599FF">
<th width="200">名稱</th>
<th width="200">價格</th>
<th width="200">數量</th>
</tr>
<%	
	
	for (int i = 0; i < buylist.size(); i++) {
			Product order = buylist.get(i);
			String name = order.getProdName();
			int price = order.getProdPrice();
			
	%>
	<tr>
		<td  width="200"><div align="center"><b><%=name%></b></div></td>
		<td  width="200"><div align="center"><b><%=price%></b></div></td>
		<td  width="200"><div align="center"><b><%=qtyMap.get(name)%></b></div></td>
		
	</tr>
	<%
		}
	%>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td><div align="center"><font color="red"><b>總金額：</b></font></div></td>
		<td></td>
		<td> <font color="red"><b>$<%=amount%></b></font> </td>
		<td></td>
	</tr>
</table>
<div>
<%@ include file="/front_end/frontEndButtom.file" %>
 <script src="https://code.jquery.com/jquery.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>