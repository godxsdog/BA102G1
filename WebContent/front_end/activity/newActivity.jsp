<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="sun.java2d.pipe.SpanShapeRenderer.Simple"%>
<%@page import="com.activity.model.Activity"%>
<%@page import="com.restaurant.model.Restaurant"%>
<%@page import="com.restMember.model.RestMember"%>
<%@page import="com.restMember.model.RestMemberService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	RestMember restMember = (RestMember)session.getAttribute("restMember");
	Activity activity = (Activity)request.getAttribute("activity");
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
	String actDateMin  = simpleDateFormat.format(new Date());
	
	session.setAttribute("key", "val");
	
%>


<%-- 此頁採用 JSTL 與 EL 取值 --%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>寵物 You & Me</title>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>

<style type="text/css">
    	.img-bg{
    	max-width:100%;
    	}
    
</style> 
	

<script>

$(function(){
	
	$("#actDate").datetimepicker({
		format: 'Y-m-d',
		 minDate:'+1970/01/02',
		 allowTimes:[
'11:00','11:30', '12:00', '12:30','13:00', '13:30','14:00','14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
		  '19:30', '20:00', '21:00', '22:00', '23:00'
		 ],
		 defaultDate: '17/09/01',
// 		 step: 30 
		 timepicker:false,
		});
	
	$("#actFDate").datetimepicker({
		format: 'Y-m-d',
		 minDate:'+1970/01/02',
		 allowTimes:[
'11:00','11:30', '12:00', '12:30','13:00', '13:30','14:00','14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
		  '19:30', '20:00', '21:00', '22:00', '23:00'
		 ],
		 defaultDate: '17/09/01',
// 		 step: 30 
		 timepicker:false,
		});
})	;


	var openFile = function(e) {
	    var input = e.target;		
	    var reader = new FileReader();		    
	    reader.onload = function(){
		      var dateURL = reader.result;	      
			  var output = document.getElementById('outputIMG');			    
			  output.src = dateURL;			    
	    };		    
	    reader.readAsDataURL(input.files[0]);		   
	};
	
	
	function newAct(){
		document.getElementById("actName").value="靜好悠閒";
		document.getElementById("actContent").value="長長的尾巴倚著，好似在撒嬌一樣。\n\n和她對視了一眼，試著攏了他一下。\n\n沒走，緊緊依偎著好像認識許久般。";
		document.getElementById("actDate").value="2017-09-20";
		document.getElementById("actFDate").value="2017-09-10";
		document.getElementById("actULimit").value="30";
		document.getElementById("actLLimit").value="5";
	}

</script>



<style type="text/css">
.aa {
	margin-top: 20px;
}
</style>

<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
			<![endif]-->

</head>
<body background="<%=request.getContextPath()%>/front_end/actFiles/465.jpg"  style= background-size:cover;>
	<%@ include file="/front_end/actFiles/restMemberNavBar.file" %>
	<%@ include file="/front_end/actFiles/activity.file" %>
	
	<div class="container-fluid">
        <div class="row"> 
        	<%@ include file="/front_end/actFiles/restMemberList1.file" %>
        <div class="col-xs-12 col-sm-8">       
   
    <div class="panel-group col-sm-offset-2 col-sm-8">
    
    
	<h5 class="page-header text-right">目前位置:新增餐廳活動</h5>
	
		<h1 class="text-center">新增餐廳活動</h1>

				<div class="form-horizontal">
				
					<form method="post" action="<%=request.getContextPath()%>/activity/activityController" enctype="multipart/form-data">
							<c:if test="${not empty activityError}">
								${activityError}
							</c:if>
							
							<input type="hidden" name="restMemId" value="${restMember.restMemId}">
							<input type="hidden" name="actStatus" value="0">
							<div class="form-group" >
								<label class="col-sm-3 control-label"  >
									活動名稱
								</label>
								<div class="col-sm-9">
									<input type="text" name="actName" id="actName" class="form-control" 
									value="" placeholder="請輸入活動名稱" />
									
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									活動內容
								</label>
								<div class="col-sm-9">
									<textarea class="form-control" name="actContent" id="actContent" rows="3" cols="54" placeholder="請輸入活動內容" style="resize:none"></textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									活動日期
								</label>
								<div class="col-sm-9">
									<input  type="text" id="actDate" name="actDate" class="form-control" 
									value="" min="<%=actDateMin %>">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									報名截止日期	
								</label>
								<div class="col-sm-9">
									<input  type="text" id="actFDate" name="actFDate"  class="form-control" 
									value="" min="<%=actDateMin %>">
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									人數上限
								</label>
								<div class="col-sm-9">
									<input type="text" name="actULimit" id="actULimit" class="form-control" 
									value="" placeholder="請輸入人數上限">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									人數下限
								</label>
								<div class="col-sm-9">
									<input type="text" name="actLLimit" id="actLLimit" class="form-control" 
									value="" placeholder="請輸入人數下限">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									種類限制
								</label>
								<div  class="col-sm-9 ">
									<select id="test" name="actKind" class="form-control">    
									  <option value="0" >貓</option>
									  <option value="1" >狗</option>
									  <option value="2" >其他</option>
									</select>
								</div>
							</div>
							
							<div class="form-group" id="aKind" style='display:none'>
								<label class="col-sm-3 control-label">
									其他寵物種類
								</label>
								<div class="col-sm-9">
									<input type="text" name="actAnotherKind" class="form-control" 
									value="" placeholder="請輸入除了狗或貓以外的種類">
								</div>
							</div>
							
								
							
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									發起活動相片
								</label>
								<div class="col-sm-9">
									<input type="file" name="actInitImg" class="form-control" onchange="openFile(event)">
									<img id="outputIMG" height=70% width=70%>
								</div>
							</div>
							
							<input type="hidden" name="action" value="newActivity">
							<input class="btn btn-primary btn-lg btn-block login-button login"
								type="submit" value="確認發起">
									
								
						</form>	
						<button onclick="newAct()" >快速活動</button>		
					</div>	
				</div>
			</div>	
		</div>	
	</div>
	<%@ include file="/front_end/frontEndButtomFixed.file" %>      	
	<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>  
	
</body>
</html>