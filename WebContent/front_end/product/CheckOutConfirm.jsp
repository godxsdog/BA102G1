<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setCharacterEncoding("UTF-8");
%>

<%
	Member mem = (Member) session.getAttribute("member");
	String address = mem.getMemAddress();
	String[] addr = address.split("�A");
	String fCounty = addr[0];
	String fDistrict = addr[1];
	String fStreet = addr[2];
	pageContext.setAttribute("fCounty", fCounty);
	pageContext.setAttribute("fDistrict", fDistrict);
	pageContext.setAttribute("fStreet", fStreet);
%>

<html>
<head>
<script src="<%=request.getContextPath()%>/front_end/js/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/front_end/js/bootstrap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front_end/js/jquery.twzipcode.min.js"></script>
<%@ include file="page4.file"%>

<style>
.select-style {
	padding: 0;
	margin: 0;
	border: 1px solid #ccc;
	width: 200px;
	border-radius: 3px;
	overflow: hidden;
	background-color: #fff;
	background: #fff
		url("http://www.scottgood.com/jsg/blog.nsf/images/arrowdown.gif")
		no-repeat 90% 50%;
}

.select-style select {
	padding: 5px 8px;
	width: 130%;
	border: none;
	box-shadow: none;
	background-color: transparent;
	background-image: none;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
}

.select-style select:focus {
	outline: none;
}

.zipcode {
	display: none;
}

.county {
	background-color: #4169E1;
	color: #fff;
}

.district {
	background-color: #008000;
	color: #fff;
}
</style>

<style type="text/css">
div.inline {
	float: left;
}

.clearBoth {
	clear: both;
}
</style>

</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

	%>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<%@ include file="page2.file"%>


	<div class="container">
		<div style="margin-left: 20px;">
			<h2>�T�{�A���q���T</h2>
		</div>
		<div style="margin-top: 2cm">
			<form class="col-sm-8"
				action="<%=request.getContextPath()%>/OrderInsert" method="POST">
				<div class="form-group">
					<label for="name">�q�ʤH�m�W:</label> <input type="text"
						class="form-control" id="name" name="name" value="<%=mem.getMemName()%>" required>
				</div>
				<div class="form-group">
					<label for="addr">�q�ʤH��}:</label>
					<div class="input-group">
						<div id="twzipcode">
							<div class="select-style inline">
								<div data-role="county" data-style="Style Name" data-value="110"></div>
							</div>
							<div class="select-style inline">
								<div data-role="district" data-style="Style Name"
									data-value="�O�_��"></div>
							</div>
							<input type="text" class="form-control" name="memAddress"
								id="memAddress" placeholder="�п�J�a�}" value="${fStreet}" required />
						</div>
					</div>

<%-- 					<input type="text" class="form-control" id="addr" value="<%=mem.getMemAddress()%>"> --%>
				</div>
				<div class="form-group">
					<label for="tel">�q�ʤH�q��:</label> <input type="text"
						class="form-control" id="tel" name="tel" value="<%=mem.getMemPhone()%>" required>
				</div>
				<div class="form-group">
					<label for="tel">�q���`���B:</label> <input type="text"
						class="form-control" id="tel" readonly
						value="<%=session.getAttribute("amount")%>">
				</div>
				<button type="submit" class="btn btn-default">�T�w�I��</button>

			</form>
		</div>
	</div>

		<script>
			$(function() {


				$('#twzipcode').twzipcode({
					// �̧ǮM�Φܿ����B�m���Ϥζl���ϸ���
					'css' : [ 'county', 'district', 'zipcode' ],
					'countySel' : '${fCounty}',
					'districtSel' : '${fDistrict}'
				});
				

				
			});
		</script>

	<%@ include file="/front_end/frontEndButtom.file"%>


</body>
</html>