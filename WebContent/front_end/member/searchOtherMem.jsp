<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.member.model.Member"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html lang="">

<head>
<title>寵物 You & Me</title>
<%@ include file="memHead.file"%>
<STYLE>

.title {
	width: 70px; /* 設定 H1 的樣式*/
}
</STYLE>
<style>

 .searchbtn{
 height:100%;
 }
 
 
 
 #custom-search-form{
 margin-top:100px;
 
 }


</style>


</head>

<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<div class="container-fluid">
		<div class="row">

			<div class="col-xs-12 col-sm-2 postion-left-group ">
				<%@ include file="memZoneLSide.file"%>
			</div>

			<div class="col-xs-12 col-sm-8 ">
				<div class="row">
					
						<form id="custom-search-form" action="<%=request.getContextPath() %>/front_end/member/member.do" method="post" class="form-search form-horizontal pull-left">

							<div class="input-group">
								<div class="input-group-btn search-panel">
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown">
										<span id="search_concept">會員</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li><a href="#contains">會員</a></li>
										<li class="divider"></li>
										<li><a href="#its_equal">寵物</a></li>
									</ul>
								</div>
								<input type="hidden" name="search_param" value="all"
									id="search_param"> 
									<input type="text"
									class="form-control" name="search" placeholder="請輸入搜尋內容">
									<input type="hidden" id="stype" name="type" value="會員">
									<input type="hidden" name="action" value="search">
								<span class="input-group-btn">
									
									<input type="submit" class="btn btn-default searchbtn glyphicon glyphicon-search" value="搜尋" >

								</span>
							</div>
							
							
						</form>
   

				</div>


				


			</div>
		</div>

		<%@ include file="/front_end/frontEndButtomFixed.file"%>

			<script>
			$(document).ready(function(e){
			    $('.search-panel .dropdown-menu').find('a').click(function(e) {
					e.preventDefault();
					var param = $(this).attr("href").replace("#","");
					var concept = $(this).text();
					$("#stype").val(concept);
					console.log($("#stype").attr("value"));
					$('.search-panel span#search_concept').text(concept);
					$('.input-group #search_param').val(param);
				});
			});
			
			
			
			
			$(function(){
				
				
			});
			</script>
</body>

</html>