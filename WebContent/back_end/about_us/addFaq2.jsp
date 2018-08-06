<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.faq.model.*"%>
<%
Faq faq = (Faq) request.getAttribute("faq");
%>

<html>
<head>
<title>FAQ新增 - addFAQ.jsp</title></head>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>FAQ新增 - addFAQ.jsp</h3>
		</td>
		<td>
		   <a href="<%=request.getContextPath()%>/back_end/about_us/faq/faq_select_page.jsp"><img src="" width="" height="" border="">回首頁</a>
	    </td>
	</tr>
</table>

<h3>FAQ新增:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'>請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" name="form1">
<table border="0">
	
	<tr>
		<td>常見問題編號:<font color=red><b>*</b></font></td>
		<td><input type="hidden" name="faqNo" value="">
<%-- 		<%=faq.getFaqNo()%> --%>
		</td>
	</tr>
	
	<tr>
		<td>員工編號:</td>
		<td><input type="hidden" name="empNo" value="">
<%-- 		<%=faq.getEmpNo()%> --%>
		</td>
	</tr>
	
	<tr>
		<td>常見問題類別:</td>
		<td><input type="TEXT" name="faqCategory" size="45" 
			value="<%= (faq ==null)? "填入類別" : faq.getFaqCategory()%>" /></td>
	</tr>
	
	<tr>
		<td>常見問題標題:</td>
		<td><input type="TEXT" name="faqTitle" size="45" 
			value="<%= (faq ==null)? "填入標題" : faq.getFaqTitle()%>" /></td>
	</tr>
	
	<tr>
		<td>常見問題解答:</td>
		<td><input type="TEXT" name="faqAnswer" size="45" 
			value="<%= (faq ==null)? "填入解答" : faq.getFaqAnswer()%>" /></td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>
