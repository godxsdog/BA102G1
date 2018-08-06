<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.album.model.*"%>
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
	Set<Album> albumSet = memSvc.getAlbumsByMemNo(memNo);
	pageContext.setAttribute("albumSet", albumSet);
%>

<head>
<title>Ãdª« You & Me</title>
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



<style type="text/css">
.wrimagecard {
	margin-top: 0;
	margin-bottom: 1.5rem;
	text-align: left;
	position: relative;
	background: #fff;
	box-shadow: 12px 15px 20px 0px rgba(46, 61, 73, 0.15);
	border-radius: 4px;
	transition: all 0.3s ease;
}

.wrimagecard .fa {
	position: relative;
	font-size: 70px;
}

.wrimagecard-topimage_header {
	padding: 0px;
}

a.wrimagecard:hover, .wrimagecard-topimage:hover {
	box-shadow: 2px 4px 8px 0px rgba(46, 61, 73, 0.2);
}

.wrimagecard-topimage a {
	display: block;
}

.wrimagecard-topimage_title {
	padding: 20px 24px;
	height: 80px;
	padding-bottom: 0.75rem;
	position: relative;
}

.wrimagecard-topimage a {
	border-bottom: none;
	text-decoration: none;
	color: #525c65;
	transition: color 0.3s ease;
}

.aImg {
	max-width: 100%;
}

.panel-body {
	padding: 20px;
}
</style>


<style>
.margin-top {
	margin-top: 70px;
	margin-bottom: 20px;
}

.img-thumbnail {
	margin-bottom: 10px;
}

.jumbotron {
	padding-top: 5px
}

.image-container {
	width: 100%;
}

.image-container img {
	position: relative;
	padding: 0;
	width: 100%;
	border-radius: 8px;
}

.overlap {
	opacity: 0;
	position: absolute;
	top: 5px;
	right: 20px;
}

.image-container:hover .overlap {
	opacity: 1;
}

.dropdownContent {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 100px;
	overflow: auto;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	right: 0;
	z-index: 1;
}

.dropdownContent a {
	color: black;
	text-decoration: none;
	display: block;
	padding: 12px 16px;
}

.dropdown a:hover {
	background-color: #f1f1f1;
}

.dropdown:hover .dropdownContent {
	display: block;
}

.errorMsgs {
	color: red;
}
</style>






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


								<div class="row" style="margin-top: 30px; max-width: 100%;">


									<c:if test="${not empty albumSet}">
										<ul>

											<c:forEach var="album" items="${albumSet}" varStatus="s">
												
												<c:if test="${s.index%4==0}">
													<div class="row">
												</c:if>

												<div class="col-md-3 col-sm-3 col-xs-6">
													<div class="image-container gallery">


														<div class="wrimagecard wrimagecard-topimage">
															<a
																href="<%=request.getContextPath() %>/front_end/member/viewMemAImg.jsp?albumNo=${album.albumNo}&memNo=${memberV.memNo}">

																<img class="aImg" src="<%=request.getContextPath() %>/front_end/album/AlbumReader.do?albumNo=${album.albumNo}">

																<div class="wrimagecard-topimage_title"
																	style="background-color: rgba(130, 93, 9, 0.1)">
																	<h4>${album.albumTitle}
																		<div class="pull-right badge" id="WrThemesIcons"></div>
																	</h4>
																</div>
															</a>
														</div>
														
													</div>
												</div>
												<c:if test="${s.index%4==3}">
													</div>
												</c:if>

											</c:forEach>
										</ul>
									</c:if>
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