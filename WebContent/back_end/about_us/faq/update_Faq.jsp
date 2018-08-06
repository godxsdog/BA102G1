<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.faq.model.*"%>

<%
	Faq faq = (Faq) request.getAttribute("faqUpdate"); //EmpServlet.java (Controller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>

<html>
<head>
<%@ include file="faqHeader.file"%>
<title>最新消息修改 - update_Faq.jsp</title>
</head>

<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div>

<body>

<%@ include file="/back_end/backEndNavBar.file"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/back_end/backEndLSide.file"%>

			<div class="col-xs-12 col-sm-8">


				<h5 class="page-header text-right">目前位置:後端首頁</h5>


				<div class="row">

					<div class="panel panel-info">

						<div class="panel-heading">
							<h3 class="panel-title">${emp.empName}</h3>
						</div>

<h3>最新消息修改/刪除:</h3>
<!-- 錯誤表列   -->
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
		<td><input type="hidden" name="faqNo" value="${faqUpdate.faqNo}">
		${faqUpdate.faqNo}
		</td>
	</tr>
	<tr>
		<td>常見問題類別:</td>
		<td><input type="text" name="faqCategory" value="<%=faq.getFaqCategory() %>" required>
		</td>
	</tr>
	<tr>
		<td>常見問題標題:</td>
		<td><input type="TEXT" name="faqTitle" size="30"	value="<%=faq.getFaqTitle() %>" required></td>
	</tr>
		<tr>
		<td>常見問題解答:</td>
		<td><input type="TEXT" name="faqAnswer" size="45"	value="<%=faq.getFaqAnswer() %>" required></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="updateSuccess">
<%-- <input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"><!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用--> --%>
<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">  <!--只用於:listAllEmp.jsp-->
<input type="submit" value="送出修改"></FORM>

</body>
</html>
