<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pet.model.*"%>

<html lang="">


<%
	Integer memNo = Integer.parseInt(request.getParameter("memNo"));
	MemberService memSvc = new MemberService();
	Member memberV = memSvc.getOneMember(memNo);
	pageContext.setAttribute("memberV", memberV);
	Member member = (Member) session.getAttribute("member");
	List<Pet> list = memSvc.getPetsByMemNo(memberV.getMemNo());
	pageContext.setAttribute("list", list);
%>

<head>

<title>寵物 You & Me</title>
<%@ include file="memHead.file"%>
<style>
.pet {
	margin-top: 50px;
}

.member {
	margin-buttom: 100px;
}

#memeImg {
	float: left;
	padding: 20px;
}

/* #memSname { */
/* 	float: right; */
/* } */

/* .content { */
/* 	width: 300px; */
/* 	height: 300px; */
/* 	padding: 20px; */
/* 	overflow: hidden; */
/* } */
</style>


</head>

<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<div class="container-fluid">
		<div class="row">

			<div class="col-xs-12 col-sm-2 postion-left-group "></div>

			<div class="col-xs-12 col-sm-8 ">
				<div class="row">

					<div class="row">

						<div class="panel panel-info">

							<div class="panel-heading">
	
								<%@ include file="viewMemInfoTop.file"%>
								
							</div>

							<div class="panel-body">
								<div class="row">


									<div>
										<c:forEach var="pet" items="${list}" varStatus="s">
											<div class="row pet">
												<div class="col-md-3 col-lg-3 " align="center">
													<img alt="User Pic" id="petImg"
														src="<%=request.getContextPath() %>/PetImgReader?petNo=${pet.petNo}"
														height="350px" width="250px"
														class="img-circle img-responsive">
												</div>

												<div class=" col-md-9 col-lg-9 ">
													<table class="table table-user-information">

														<tbody>

															<tr>
																<td class="title">寵物姓名</td>
																<td>${pet.petName}</td>
															</tr>
															<tr>
																<td class="title">寵物分類</td>
																<td>${pet.petKind}</td>
															</tr>
															<tr>
																<td class="title">寵物性別</td>
																<td>${pet.petGender==0?"公":"母"}</td>
															</tr>
															<tr>
																<td class="title">寵物品種</td>
																<td>${pet.petSpecies}</td>
															</tr>
															<tr>
																<td class="title">生日</td>
																<td>${pet.petBday}</td>
															</tr>

															<tr>
																<td class="title">寵物介紹</td>
																<td>${pet.petIntro}</td>
															</tr>

														</tbody>
													</table>

												</div>
											</div>
										</c:forEach>
									</div>


								</div>
							</div>

						</div>
					</div>
				</div>

			</div>
		</div>
		<%@ include file="/front_end/frontEndButtom.file"%>
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