<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.member.model.*"%>
<%@ page import="com.letter.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>


<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>寵物 You & Me</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link href="<%=request.getContextPath()%>/front_end/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/nav.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/colorplan.css" rel="stylesheet"> 
    <link href="<%=request.getContextPath()%>/front_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/front_end/css/modern-business.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/frontend.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script> -->
</head>
<style>
.panel-body{
	padding:1px;
}
.ellipsis{
	overflow:hidden;
/* 	width:100%; */
	white-space:nowrap;
	text-overflow:ellipsis;
}
.hov:hover{
	border-left:5px #db70db solid;;
} 
.mymouse{
	cursor: pointer;
}


</style>
<body >
	
	
 	<%@ include file="/front_end/frontEndNavBar.file"%> 
    
        
    <div class="container-fluid" >
    	<div class="row">    
            <%@ include file="leftbar.file" %> 
    
    
			<div class="col-xs-12 col-sm-8 col-sm-push-3"  >
                <h5 class="page-header"></h5>
                 
                 <div class="row"  > 
                                	
                    <div class="panel panel-default " >                   
                        <div class="">                             
                            <div class="panel-body" >
					  	
								<c:forEach var="ltr" items="${letterSvc.getTagLtrs(member.getMemNo())}">
								 <div class=" panel-body hov mymouse" style="line-height:36px;height:36px;border-bottom:1px #a9a9a9 solid;${ltr.letterState =='0' ? 'background-color:#e7e7e7;': 'background-color:white;'}"> 
									  <div class="col-sm-1 text-center" style="padding:0px;margin-top:7px;">	
											<input type="hidden" value="${ltr.letterNo}" >
											<c:if test = "${ltr.letterTag =='1'}" var="outcome" scope="page">
											<input type="button" value="已標註"  class="btn btn-warning btn-xs" onclick="tag(this);" >						
											</c:if>
											<c:if test = "${outcome==false}">
											<input type="button" value="標註"  class="btn btn-info btn-xs" onclick="tag(this);">						
											</c:if>
									  </div>
									  <div class="col-sm-11" data-toggle="modal" data-target="#myModal" onclick="haveRead(this,${ltr.letterNo });" style="padding:0px;" >
									  	<div class="col-sm-1">
									  		<c:if test="${ltr.letterState == '0' }" var="hadread">
									  			<img src="<%=request.getContextPath()%>/front_end/images/mail.png">
									  		</c:if>
									  		<c:if test="${!hadread}">
									  			<img src="<%=request.getContextPath()%>/front_end/images/envelope.png">
									  		</c:if>
									  	</div>
									  	<c:if test="${hadread}">
										<div class="text-left col-sm-2 ellipsis" style="font-weight:bold;padding-left:0px;">${ltrtpSvc.getOneByPrimaryKey(ltr.letterTypeNo).letterTypeName}</div>																									
										</c:if>
									  	<c:if test="${!hadread}">
										<div class="text-left col-sm-2 ellipsis" style="padding-left:0px;">${ltrtpSvc.getOneByPrimaryKey(ltr.letterTypeNo).letterTypeName}</div>																									
										</c:if>
										
										<div class="ellipsis col-sm-7 text-left" style="color:#808A87;padding-left:0px;">${ltr.letterText}</div>	
										<div class="text-right col-sm-2"><fmt:formatDate value="${ltr.letterTime}" pattern="yyyy/MM/dd"/></div>										
									  </div>					
								</div>
								  				
								
								</c:forEach>
							</div>																										
																		
                            </div>
                         
                                               
                         </div>                                
                    </div>  
                                  
                </div>
				<%@ include file="/front_end/frontEndButtom.file"%>
            </div>
       </div>
       <!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title" id="modeltitle" style="font-weight:bold;"></h4>
		        </div>
		        <div class="modal-body">
		          <p id="modelbody"></p>
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-primary" data-dismiss="modal">確定</button>
		        </div>
		      </div>
		      
		    </div>
		</div>
     
     
                
                <script type="text/javascript">
                
                
                
	                function tag(e){
	                	var ltrNo = $(e).prev().val();
	                	if($(e).val()=="標註"){
	                		$.ajax({
								url:"<%=request.getContextPath()%>/front_end/letter/letter.do",
								data:{
									action : 'updateTag',
									letterno : ltrNo					
								},
								type : 'POST',
								error : function(xhr) {
									alert('Ajax request 發生錯誤');
									},
								success : function(result) {
									$(e).val("已標註");
									$(e).attr("class","btn btn-warning btn-xs");
								}
							});	
	                	}else if($(e).val()=="已標註"){
	                		$.ajax({
								url:"<%=request.getContextPath()%>/front_end/letter/letter.do",
								data:{
									action : 'updateNotTag',
									letterno : ltrNo					
								},
								type : 'POST',
								error : function(xhr) {
									alert('Ajax request 發生錯誤');
									},
								success : function(result) {
									$(e).val("標註");
									$(e).attr("class","btn btn-info btn-xs");
								}
							});	
	                	}
	                }
                function haveRead(e,ltrNo){
                	var title = $(e).find('div.col-sm-2.ellipsis').text();
                	$('#modeltitle').text(title);
                	
                	var content = $(e).find('div.ellipsis.col-sm-7').text();
                	$('#modelbody').text(content);
                	
                	if($(e).parent().css('background-color')=="rgb(231, 231, 231)"){     //231之間有空格
                	$.ajax({
						url:"<%=request.getContextPath()%>/front_end/letter/letter.do",
						data:{
							action : 'updateRead',
							letterno : 	ltrNo				
						},
						type : 'POST',
						error : function(xhr) {
							alert('Ajax request 發生錯誤');
							},
						success : function(result) {
							$(e).parent().css("background-color","white");
							$(e).find('div.col-sm-1 > img').attr("src","<%=request.getContextPath()%>/front_end/images/envelope.png");
							$("span.badge").text(parseInt($("span.badge").text())-1);
							$(e).find('div.col-sm-2.ellipsis').css("font-weight",'');
						}
					});	  
                	}
                }
                
                </script>
                
                
</body>
</html>