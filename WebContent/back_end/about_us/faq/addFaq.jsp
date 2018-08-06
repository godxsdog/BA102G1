<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.faq.model.*"%>
<%
	Faq faq = (Faq) request.getAttribute("faq");
	Emp emp = (Emp) request.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>

<html>
<head>
<%@ include file="faqHeader.file"%>
<title>複合查詢 - listEmps_ByCompositeQuery.jsp</title>
<script>
	function newFAQ(){
		document.getElementById("faqCategory").value="其他問題";
		document.getElementById("faqTitle").value="我可以直接跟寵物主人要聯絡方式嗎?";
		document.getElementById("faqAnswer").value="在賣方沒有主動提供聯絡方式的狀態下，我們不鼓勵這類行為，請盡量不要造成對方困擾。";
	}
</script>
</head>
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

						<div class="panel-body">

							<div class="row">

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
											<td>常見問題類別:</td>
											<td><input type="TEXT" name="faqCategory" id="faqCategory" size="45" value="<%=(faq == null) ? "填入類別" : faq.getFaqCategory()%>" /></td>
										</tr>

										<tr>
											<td>常見問題標題:</td>
											<td><input type="TEXT" name="faqTitle" id="faqTitle" size="45" value="<%=(faq == null) ? "填入標題" : faq.getFaqTitle()%>" /></td>
										</tr>

										<tr>
											<td>常見問題解答:</td>
											<td><input type="TEXT" name="faqAnswer" id="faqAnswer" size="45" value="<%=(faq == null) ? "填入解答" : faq.getFaqAnswer()%>" /></td>
										</tr>

									</table>
									<br>
								<input type="hidden" name="action" value="insert">
								<input type="submit" value="送出新增"></FORM>
								<button onclick="newFAQ()">快速新增FAQ</button>
							</div>
						</div>
					</div>
				</div>

			</div>

			<script src="https://code.jquery.com/jquery.js"></script>
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

			<script>
				
			</script>
</body>
</body>
</html>
