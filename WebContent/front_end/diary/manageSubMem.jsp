<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.diary.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.submem.model.*"%>
<%@ page import="com.letter.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    Member member = (Member)session.getAttribute("member");//此行先留著
%>

	
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>寵物 You & Me</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    
    <link href="<%=request.getContextPath()%>/front_end/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/nav.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/colorplan.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/front_end/css/modern-business.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/date.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/front_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/front_end/css/frontend.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
body{  
  background-image:url('../images/diary-background5.jpg') !important; */
 	 
  }  
.panel-body{
	padding:1px;
}
.img-bg{
    opacity:1;
    background-color:#ccc;
    max-width: 100%;
    display: block;
    margin: 0 auto;
}
.memImg{
	height:60px;
	width:60px;
	max-height: 100%;
	max-width:100%;
	padding-right:0;
}

.box{
	width: 240px;
	background-color: #ccc;
	padding: 10px;
	position: relative;
	float: left;
	margin: 20px;
}
</style>
<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
    <%@ include file="leftbar.file" %>
    <div class="col-xs-12 col-sm-8 " >
                <div class="row">
                <h5 class="page-header text-right">目前位置:追蹤管理頁面</h5>
  
                     <c:forEach var="submem" items="${submSvc.getMemberAct(member.memNo)}">
                        <div class="box">
                        	<div class="col-sm-8" style="padding:0px;">  
                            	<img class="memImg" src="<%=request.getContextPath()%>/front_end/member/MemImgReader2.do?memNo=${submem.beSubMemNo}">
			  					<a href="<%=request.getContextPath()%>/front_end/diary/personalDiary.jsp?memNo=${submem.beSubMemNo}" style="color:#191970;font-weight:bold;">${memSvc.getOneMember(submem.beSubMemNo).getMemSname()}</a>
			  					
				            </div>
				            <div class="col-sm-4" style="padding:0px;">
				            	<input type="hidden" value="${submem.beSubMemNo}">    
				                <div class="btn btn-default btn-xs" onclick="submem(this);">
				                      <img src="<%=request.getContextPath()%>/front_end/images/subscribe.png">	
				                      <span class="submember" style="color:red;font-weight:bold;">已追蹤</span>
				                </div>
				            </div>   	         
                         </div>
                     </c:forEach>     
                </div>
              </div>
              
              <%@ include file="/front_end/frontEndButtom.file"%>
              
             <script>
             function submem(e){
              			var besubmemno = $(e).prev().val();
              				$.ajax({ 
              					   url : "<%=request.getContextPath()%>/front_end/diary/subMem.do",
             					   data : {
             					     action : 'delete',
             					     beSubMemNo : besubmemno    												            						
             					  },
             					   type : 'POST',
             					   error : function(xhr) {
             					     alert('Ajax request 發生錯誤');
             					  },
             					   success : function(data) {		
             					     	
             						  $(e).parent().parent().remove();					    							
             					  }
             				});
              			
              			
              			
              		}
              		
              
              </script>
</body>
</html>