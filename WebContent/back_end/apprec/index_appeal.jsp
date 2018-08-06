<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>


<%
	Emp emp = (Emp) session.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>


<jsp:useBean id="dateitemSvc" scope="page" class="com.dateitem.model.DateItemService"/>
<jsp:useBean id="appSvc" scope="page" class="com.dateitemapp.model.DateItemAppService"/>
<jsp:useBean id="appreprecSvc" scope="page" class="com.appreprec.model.AppRepRecService"/>
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


				<h5 class="page-header text-right">目前位置:申訴處理首頁</h5>


				<div class="panel panel-default  text-center">
					<div class="panel-heading">
						<h3 class="panel-title">管理約會商品申訴區</h3>						
					</div>
					<div class="panel-body text-left" style="padding-bottom:0px;">
							
					</div>
					<div class="panel-body">
					
						<table class="table table-condensed table-hover" >
							<tr >
								<th class="text-center">申訴人</th>
								<th class="text-center">被申訴約會商品</th>
								<th class="text-center">申訴標題</th>
								<th class="text-center">申訴內文</th>
								<th class="text-center">申訴日</th>
								<th class="text-center">被申訴人</th>
								<th class="text-center">被申訴近一個月次數</th>
								<th class="text-center">處理狀況</th>
								
							</tr>
						<c:forEach var="appeal" items="${appSvc.getAll()}">
							<tr >
								<td>${memSvc.getOneMember(appeal.memNo).memName }</td>	
								<td><input type="button" value="商品詳情" class="btn btn-info"  data-toggle="modal" data-target="#modal-detail" onclick="dateDetail(${appeal.dateItemNo });"></td>	
								<td>${appeal.appTitle }</td>	
								<td>
									<a href='#${appeal.appNo }' data-toggle="modal" class="btn btn-info" >申訴詳情</a>	
								</td>	
								<td>${appeal.appDate }</td>	
								<td>${memSvc.getOneMember(dateitemSvc.findByPK(appeal.dateItemNo).sellerNo).memName }</td>	
								<td>${appreprecSvc.findOneMonthTimes(dateitemSvc.findByPK(appeal.dateItemNo).sellerNo) }</td>	
								<td>
									<c:if test="${appeal.appState==0}" var="outcome">
										<input type="hidden" value="${appeal.appNo}">
										<input type="button" value="退款" class="btn btn-success"  onclick="pass(this);">
										<input type="button" value="否決" class="btn btn-danger" onclick="deny(this);" >
									</c:if>
									<c:if test="${outcome==false}">
										<img src="<%=request.getContextPath()%>/back_end/images/checked.jpg" style='height:40px;width:50px;'></img>
									</c:if>
								</td>									
							</tr>
							
							<div class="modal fade" id="${appeal.appNo }">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<div class="modal-title text-left">${appeal.appTitle }</div>
										</div>
										<div class="modal-body text-left">
											${appeal.appText }
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary" data-dismiss="modal" >確定</button>
										</div>
									</div>
								</div>
							</div>			
							
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
				var appNo = $(e).prev().val();
				var app = $(e).parent();
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/apprec/dateitemapp.do",
					data:{
						action : 'updatepass',
						appno : appNo					
					},
					type : 'POST',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
						},
					success : function(result) {
						$(e).parent().prev().text(parseInt($(e).parent().prev().text())+1);
						$(e).parent().empty();
						$('<img>').attr('src',"<%=request.getContextPath()%>/back_end/images/checked.jpg").css({"height":"40px","width":"50px"}).appendTo(app);
						
					}
				});	
			}
			function deny(e){
				var appNo = $(e).prev().prev().val();
				var app = $(e).parent();
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/apprec/dateitemapp.do",
					data:{
						action : 'updatedeny',
						appno : appNo					
					},
					type : 'POST',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
						},
					success : function(result) {
						
						$(e).parent().empty();
						$('<img>').attr('src',"<%=request.getContextPath()%>/back_end/images/checked.jpg").css({"height":"40px","width":"50px"}).appendTo(app);
						
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