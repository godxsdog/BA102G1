<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Member mem = (Member) session.getAttribute("member");
	String address = mem.getMemAddress();
	String[] addr = address.split("�A");
	String fCounty = addr[0];
	String fDistrict = addr[1];
	String fStreet = addr[2];
	String memAddr = fCounty + fDistrict + fStreet;
	pageContext.setAttribute("memAddr", memAddr);
%>
<html lang="">

<head>
<title>�d�� You & Me</title>
<%@ include file="memHead.file"%>
<style>
</style>


</head>

<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<div class="container-fluid">
		<div class="row">

			<div class="col-xs-12 col-sm-2  postion-left-group ">
				<%@ include file="memZoneLSide.file"%>
			</div>

			<div class="col-xs-12 col-sm-8 col-md-offset-1">
				<div class="row">


					<h5 class="page-header text-right">�ثe��m:�|���M��</h5>

					<div class="row">

						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">${member.memId}</h3>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-md-3 col-lg-3 " align="center">
										<img alt="User Pic" id="memImg"
											src="<%=request.getContextPath()%>/DBGifReader"
											width="100%" class="img-rounded">
										<!-- 										<video  autoplay loop muted width="150px" height="200px"> -->
										<%-- 											<source src="<%=request.getContextPath()%>/DBGifReader" type="video/mp4"> --%>
										<!-- 										</video> -->
									</div>


									<div class=" col-md-9 col-lg-9 ">
										<table class="table table-user-information">
											<tbody>
												<tr>
													<td class="title">�ʺ�</td>
													<td>${member.memSname}</td>
												</tr>
												<tr>
													<td class="title">�m�W</td>
													<td>${member.memName}</td>
												</tr>
												<tr>
													<td class="title">�ͤ�</td>
													<td>${member.memBday}</td>
												</tr>
												<tr>
													<td class="title">���</td>
													<td>${member.memPhone}</td>
												</tr>
												<tr>
													<td class="title">�ʧO</td>
													<%
														String memGender = String.valueOf(member.getMemGender());
														HashMap mGender = (HashMap) application.getAttribute("mGender");
													%>
													<td><%=mGender.get(memGender)%></td>
												</tr>
												<tr>

													<td class="title">�P��</td>
													<%
														String memRelation = String.valueOf(member.getMemRelation());
														HashMap mRelation = (HashMap) application.getAttribute("mRelation");
													%>
													<td><%=mRelation.get(memRelation)%></td>
												</tr>
												<tr>
													<td class="title">����</td>
													<td>${member.memFollowed}�H</td>
												</tr>
												<tr>
													<td class="title">�I��</td>
													<td>${member.memPoint}�I</td>
												</tr>
												<tr>
													<td class="title">Email</td>
													<td>${member.memEmail}</td>
												</tr>
												<td class="title">�a�}</td>
												<td>${memAddr}</td>
												<tr>
													<td class="title">�����</td>
													<td>${member.memSelfintro}</td>
												</tr>


											</tbody>
										</table>
										<c:if test="${not empty errorMsgs}">
											<font color="red">
												<ul>
													<c:forEach var="message" items="${errorMsgs}">
														<li>${message}</li>
													</c:forEach>
												</ul>
											</font>
										</c:if>
										<a href="memberInfoUpdate.jsp" class="btn btn-primary">�s��ӤH��T</a>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>

			</div>

		</div>
		<%@ include file="/front_end/frontEndButtomFixed.file"%>
		<script>
			$(document).ready(function(e) {
				$('.search-panel .dropdown-menu').find('a').click(function(e) {
					e.preventDefault();
					var param = $(this).attr("href").replace("#", "");
					var concept = $(this).text();
					$('.search-panel span#search_concept').text(concept);
					$('.input-group #search_param').val(param);
				});
			});
		</script>
</body>

</html>