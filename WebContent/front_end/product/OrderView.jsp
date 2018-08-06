<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.order.model.*"%>
<%@ page import="com.member.model.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	
	Member mem = (Member)session.getAttribute("member");
	if(mem == null){
		RequestDispatcher rd = request.getRequestDispatcher("/front_end/member/login.jsp");
		rd.forward(request, response);
		return;}
	OrdService Ord = new OrdService();
	List<Ord> OrdFk = Ord.getOneOrdByFk(mem.getMemNo());
	List<Ord> OrdAll = Ord.getAll();
	for(int i=0;i<OrdFk.size();i++){
		String address = OrdFk.get(i).getConAdd();
		
		String[] addr = address.split("�A");
		String fCounty = addr[0];
		String fDistrict = addr[1];
		String fStreet = addr[2];
		String memAddr = fCounty + fDistrict + fStreet;
		OrdFk.get(i).setConAdd(memAddr);
		
		}
	session.setAttribute("OrdAll", OrdAll);
	session.setAttribute("OrdFk", OrdFk);
%>

<html>
<head>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>
<%@ include file="page4.file"%>
</head>
<body>
	<%@ include file="/front_end/frontEndNavBar.file" %>
	<%@ include file="page2.file"%>
	
	<table class="table table-hover" width="200px">
		<tr>
			
			<th>�q����</th>
			<th>����a�}</th>
			<th>�q����B</th>
			<th>�|���m�W</th>
			<th>�q�檬�A</th>
			<th></th>
		</tr>
				<c:forEach var="ordAll" items="${OrdFk}" varStatus="index">
				
			<tr>
				
				<td><span>${ordAll.ordDate}</span></td>
				
				<td><span>${ordAll.conAdd}</span></td>
				<td><span>${ordAll.ordTotal}</span></td>
				<td><span>${ordAll.conName}</span></td>
				
				<c:if test="${ordAll.ordStatus ==0}">
				<td><span>���X�f</span></td>
				<form action="<%=request.getContextPath()%>/OrdCancel" method="POST">
				<td><input type="submit" class="btn btn-primary" value="�����q��"></td>
				<input type="hidden" name="ordNo" value="${ordAll.ordNo}">
				</form>
				</c:if>
				<c:if test="${ordAll.ordStatus ==1}">
				<td><span>���X�f</span></td>
				<form action="<%=request.getContextPath()%>/OrdCancel" method="POST">
				<td><input type="submit" class="btn btn-primary" value="�����q��"></td>
				<input type="hidden" name="ordNo" value="${ordAll.ordNo}">
				</form>
				</c:if>
				<c:if test="${ordAll.ordStatus ==2}">
				<td><span>�w�X�f</span></td>
				<td></td>
				</c:if>
				<c:if test="${ordAll.ordStatus ==3}">
				<td><span>�w����</span></td>
				<td></td>
				</c:if>
				<c:if test="${ordAll.ordStatus ==4}">
				<td><span>�q�����</span></td>
				<td></td>
				</c:if>
			</tr>
				</c:forEach>	
	</table>
	
	  <%@ include file="/front_end/frontEndButtomFixed.file" %>
        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>