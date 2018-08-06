<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.faq.model.*"%>

<%-- 此頁練習採用 Script 的寫法取值 --%>
<%-- 取出 Concroller EmpServlet.java已存入request的EmpVO物件--%>
<%-- 取出 對應的DeptVO物件--%>
<% Faq faq=(Faq)request.getAttribute("faq");
	pageContext.setAttribute("faq",faq);
%>
<html>
<head>
<title>常見問題查詢 - listFaqNo.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>超爽der~取到值勒~</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>常見問題後端查詢 - listFaqNo.jsp</h3>
		<a href="<%=request.getContextPath()%>/back_end/about_us/faq/faq_select_page.jsp"><img src="" width="" height="" border="">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>常見問題編號</th>
		<th>發佈員工編號</th>
		<th>常見問題類別</th>
		<th>常見問題標題</th>
		<th>最新消息解答</th>
		<th>修改</th>
		<th>刪除</th>
		
	</tr>

		<tr align='center' valign='middle'><!--將修改的那一筆加入對比色而已-->
			<td>${faq.faqNo}</td>
			<td>${faq.empNo}</td>
			<td>${faq.faqCategory}</td>
			<td>${faq.faqTitle}</td>
			<td>${faq.faqAnswer}</td>
			
<%-- 			<td><c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%--                     <c:if test="${faq.deptno==deptVO.deptno}"> --%>
<%-- 	                    ${deptVO.deptno}【${deptVO.dname} - ${deptVO.loc}】 --%>
<%--                     </c:if> --%>
<%--                 </c:forEach> --%>
<%-- 			</td> --%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do">
			     <input type="submit" value="修改"> 
			     <input type="hidden" name="faqNo" value="${faq.faqNo}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="action"	value="update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do">
			    <input type="submit" value="刪除">
			    <input type="hidden" name="faqNo" value="${faq.faqNo}">
			    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			    <input type="hidden" name="action"value="delete"></FORM>
			</td>
		</tr>

</table>

</body>
</html>
