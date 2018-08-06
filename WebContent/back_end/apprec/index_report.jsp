<%@page import="com.restaurant.model.*"%>
<%@page import="com.member.model.*"%>
<%@page import="com.pet.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>


<%
	Emp emp = (Emp) session.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
	MemberService mmSvc = new MemberService();
	PetService pSvc = new PetService();
	RestaurantService restSvc = new RestaurantService();
%>

<jsp:useBean id="dateitemrep" scope="page" class="com.dateitemrep.model.DateItemRepService"/>
<jsp:useBean id="dSvc" scope="page" class="com.dateitem.model.DateItemService"/>
<jsp:useBean id="appreprec" scope="page" class="com.appreprec.model.AppRepRecService"/>
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>



<!DOCTYPE html>
<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>寵物 You & Me</title>
<link href="<%=request.getContextPath()%>/back_end/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/nav.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/colorplan.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="<%=request.getContextPath()%>/back_end/css/modern-business.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="<%=request.getContextPath()%>/back_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/back_end/css/backend.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/front_end/css/itemdetail.css" rel="stylesheet">
</head>

<body>
		<%@ include file="/back_end/backEndNavBar.file"%> 
		<%@ include file="/back_end/backEndLSide.file"%> 

			<div class="col-xs-12 col-sm-8">


				<h5 class="page-header text-right">目前位置:檢舉處理首頁</h5>


				<div class="panel panel-default  text-center">
					<div class="panel-heading">
						<h3 class="panel-title">管理約會商品檢舉區</h3>						
					</div>
					<div class="panel-body text-left" style="padding-bottom:0px;">
							
					</div>
					<div class="panel-body">
					
						<table class="table table-condensed table-hover" >
							<tr >
								<th class="text-center">檢舉人</th>
								<th class="text-center">被檢舉約會商品</th>
								<th class="text-center">檢舉原因</th>
								<th class="text-center">檢舉日</th>
								<th class="text-center">被檢舉人</th>
								<th class="text-center">被檢舉人近一個月次數</th>
								<th class="text-center">處理狀況</th>
								
							</tr>
						<c:forEach var="report" items="${dateitemrep.getAll()}">
							<tr >
								<td>${memSvc.getOneMember(report.memNo).memName}</td>	
								<td><input type="button" value="商品詳情" class="btn btn-info" data-toggle="modal" data-target="#modal-detail" onclick="dateDetail(${report.dateItemNo });"></td>	
								<td>${report.repText }</td>	
								<td>${report.repDate }</td>	
								<td>${memSvc.getOneMember(dSvc.findByPK(report.dateItemNo).sellerNo).memName }</td>	
								<td>${appreprec.findOneMonthTimes(dSvc.findByPK(report.dateItemNo).sellerNo) }</td>	
								<td>
									<c:if test="${report.repState==0}" var="outcome">
										<input type="hidden" value="${report.repNo}">
										<input type="button" value="下架" class="btn btn-success"  onclick="pass(this);">
										<input type="button" value="否決" class="btn btn-danger" onclick="deny(this);" >
									</c:if>
									<c:if test="${outcome==false}">
										<img src="<%=request.getContextPath()%>/back_end/images/checked.jpg" style='height:40px;width:50px;'></img>
									</c:if>
								</td>
									
							</tr>
						</c:forEach>		
						</table>
						
					</div>
				</div>
			</div>
			
	<!--  商品明細的跳窗 -->

		<div id="modal-detail" class="modal fade" role="dialog">
		  <div class="modal-dialog modal-lg">
		    <!-- Modal content-->
		    <div class="modal-content">
	<div class ="main-container">
	<div class=" highlight" style="padding:4%;">
		<h3 ><b id="dateTitle"></b></h3>
			<div class="row">
				<div id="cc">
		        <img id="dateImg" src ="" />  
		        </div>
		        <ul>
		            <li> 
		            	<h2 id="dateName"></h2>
		            </li>
		            <li id="dateRest"></li>
		            <li id="dateTime"></li>
		            <li id="datePeople"></li>
		            <li>
		            	<h3 id="datePrice" class="cost"></h3>
		            </li>
		        </ul>
		        
		        <div class="row">
			        <blockquote >
						<p id="dateText"></p>
					</blockquote>
		        </div>
		        </div>        
		        <button class = "btn btn-primary btn-sm" data-dismiss="modal">確定</button>
			    </div>
			</div>
		</div>
	 </div>
	</div>		

			<script src="https://code.jquery.com/jquery.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
			
			<script type="text/javascript">
			function pass(e){
				var repno = $(e).prev().val();
				var rep = $(e).parent();
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/apprec/dateitemrep.do",
					data:{
						action : 'updatepass',
						repNo : repno					
					},
					type : 'POST',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
						},
					success : function(result) {
						$(e).parent().prev().text(parseInt($(e).parent().prev().text())+1);
						$(e).parent().empty();
						$('<img>').attr('src',"<%=request.getContextPath()%>/back_end/images/checked.jpg").css({"height":"40px","width":"50px"}).appendTo(rep);
						
					}
				});	
			}
			function deny(e){
				var repno = $(e).prev().prev().val();
				var rep = $(e).parent();
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/apprec/dateitemrep.do",
					data:{
						action : 'updatedeny',
						repNo : repno					
					},
					type : 'POST',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
						},
					success : function(result) {
						
						$(e).parent().empty();
						$('<img>').attr('src',"<%=request.getContextPath()%>/back_end/images/checked.jpg").css({"height":"40px","width":"50px"}).appendTo(rep);
						
					}
				});	
			}
			
			function dateDetail(dNo){
				
				var dateitemno = dNo;
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/apprec/dateitemrep.do",
					data:{
						action : 'getOneDateItem',
						dateItemNo : dateitemno					
					},
					type : 'POST',
					dataType:'text',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
						},
					success : function(data) {
 						var obj = JSON.parse(data);
						
						$('#dateTitle').text(obj.dateItemTitle);
						$('#dateImg').attr("src","<%=request.getContextPath()%>/front_end/dateitem/ImgReader?sellerNo="+obj.sellerNo+"&action=memImg");
						$('#dateName').text(obj.sellerName +"&&"+obj.petName);
						$('#dateRest').text('餐廳:'+obj.restName);
						$('#dateTime').text('約會時間:'+obj.dateMeetingTime);						
						$('#datePeople').text('參加人數上限:'+obj.dateItemPeople+'人  - 寵物主人攜伴:'+obj.hasMate);
						$('#datePrice').html('<span class="glyphicon glyphicon-usd"></span>'+ obj.dateItemPrice);
						$('#dateText').text(obj.dateItemText);
						
						
						
					}
				});	
				
				
				
			}
			
			
			
			
			
			</script>
</body>

</html>