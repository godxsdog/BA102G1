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
	Integer memNo = new Integer(request.getParameter("memNo"));
    Member member = (Member)session.getAttribute("member");
	DiaryService diaSvc = new DiaryService();
	List<Diary> list = diaSvc.getAllFromMemNo(memNo);
	pageContext.setAttribute("list",list);
%>

	
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService"/>
<jsp:useBean id="diaMsgSvc" scope="page" class="com.diamsg.model.DiaMsgService"/>


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
	height:34px;
	width:50px;
	max-height: 100%;
	max-width:100%;
	padding-right:0;
}
.padmarg{
	margin:0;
	padding:0;
}
.msg{
	background-color:#F5F5F5;
	margin-bottom:0;
}
.dia-msg{
	padding-right:0;
	padding-left:0;
	margin-bottom:0;
}

.msgmemimg{
	height:34px;
	width:35px;
	max-height: 100%;
	max-width:100%;
}
</style>
<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
    <%@ include file="leftbar.file" %>
    <div class="col-xs-12 col-sm-8 " >
                <div class="row">
                <h5 class="page-header"></h5>
                <%@ include file="page1.file" %> 
                
          <c:forEach var="diary" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
             <div class="row">
                <div class="panel panel-default col-sm-8 col-sm-offset-2 top-margin-sm dia-msg">
                   <div class="panel">
                      <div class="panel-heading" >
                          <div class="row">
                        	  <div class="col-sm-10" >                        		
			                      <div class="panel-heading" style="border-bottom:1px #a9a9a9 solid;padding-left:0;">
			                         <img src="<%=request.getContextPath()%>/front_end/images/footprint-shape.png" >
			                         <span class="panel-title" >${diary.diaName}</span>
			                      </div>
	                      	  </div>
	                      	  <div class="col-sm-2" style="padding-left:0;">
	                      	   <div class="row">
	                      	   <c:if test="${ member.memNo == diary.memNo }">
		                      	    <div class="col-sm-7" style="width:55px;">       	    
									<!-- 修改日誌的部分 -->
		                        		<form action="<%=request.getContextPath()%>/front_end/diary/diary.do" method=post enctype="multipart/form-data" >
				                        	<input type="hidden" name="action" value="getOne_For_Update">
				                        	<input type="hidden" name="diano" value="${diary.diaNo}">
											<input class="btn btn-info btn-xs" type="submit" value="修改">
										</form>
									</div>
									<div class="col-sm-5 " style="padding-left:0;">
									<!-- 刪除日誌的部分 -->
										<form action="<%=request.getContextPath()%>/front_end/diary/diary.do" method=post enctype="multipart/form-data" >
				                        	<input type="hidden" name="action" value="delete">
				                        	<input type="hidden" name="diano" value="${diary.diaNo}">
											<input class="btn btn-danger btn-xs" type="submit" value="刪除"><br>
										</form>
									</div>
								</c:if>	
							   </div>	
                              </div>
                          </div>
                      </div>
                      <div class="panel-body">                       
		                   <div class="col-sm-2 memImg" >
		                      <img class="memImg" src="<%=request.getContextPath()%>/front_end/member/MemImgReader2.do?memNo=${diary.getMemNo()}">
		                   </div>
		                   <div class="col-sm-10 padmarg">
		                      <a href="<%=request.getContextPath()%>/front_end/diary/personalDiary.jsp?memNo=${diary.getMemNo()}" style="color:#191970;font-weight:bold;">${memSvc.getOneMember(diary.getMemNo()).getMemSname()}</a>
						
						<!-- 追蹤訂閱 -->
							  <c:set var="outcome" value="false" />	
		                      <c:forEach var="submem" items="${submSvc.getMemberAct(member.memNo)}">
		                      		<c:if test="${submem.beSubMemNo == diary.memNo }">
		                      			<c:set var="outcome" value="true" />
		                      		</c:if>			                      			                      		    		
		                      </c:forEach>

							  <input type="hidden" value="${diary.getMemNo()}">   
		                      <span class="btn btn-default btn-xs" onclick="submem(this);">
		                      		<img src="<%=request.getContextPath()%>/front_end/images/subscribe.png">	
		                      	<c:if test="${outcome }">
		                      		<span class="submember" style="color:red;font-weight:bold;">已追蹤</span>
		                      	</c:if>
		                      	<c:if test="${!outcome }">
		                      		<span class="submember">追蹤會員</span>
		                      	</c:if>
		                      </span>	         
		               <!-- 追蹤訂閱結束線 -->  
		                              
		                      <div style="font-size:10px;color:#BC8F8F;"><fmt:formatDate value="${empty diary.diaCreTime? diary.diaModTime:diary.diaCreTime}" pattern="HH:mm:ss yyyy/MM/dd"/></div>
		                   </div>		                	 
	                  </div> 
                      <div class="panel-body" style="margin-top:10px;">
                         <blockquote class="blockquote">
                           <p class="text-left">${diary.diaText}</p>                     
                          </blockquote>
                       </div>		
	                  <div class="panel-body"> 
	                      <div class="text-center">
	                         <c:if test="${diary.diaImgExtName =='image' }" var="imgformat">
	                         <img src="<%=request.getContextPath()%>/front_end/diary/ShowImage?diano=${diary.diaNo}" style='height:auto;width:540px;display:${empty diary.diaImg ? "none":""};'></img>
	                         </c:if>
	                         <c:if test="${!imgformat && diary.diaImgExtName =='video'  }">
	                         <div style="max-height: auto;max-width:540px;" >
								<video controls style="max-height: 80%;max-width: 80%;">
									<source src="<%=request.getContextPath() %>/front_end/diary/DiaryVideo?diano=${diary.diaNo}" type="video/mp4" alt="您的瀏覽器不支援此撥放程式!!">
								</video>
							 </div>	                         
	                      	 </c:if>
	                      </div>
	                  </div>  
                   </div>
                   <%@ include file="diamsg.file" %>
                </div>                
             </div>
          </c:forEach>
          
          						
               
<!--                 顯示頁數 -->
                              	   
                <%@ include file="pageLatestforPerson.file" %> 

                <%@ include file="/front_end/frontEndButtom.file"%>
                
                </div>
              </div>
              <script type="text/javascript">
              		
              		function submem(e){
              			var besubmemno = $(e).prev().val();		
              			if($(e).children('span').text()=='追蹤會員'){
              				$.ajax({ 
              					   url : "<%=request.getContextPath()%>/front_end/diary/subMem.do",
             					   data : {
             					     action : 'insert',
             					     beSubMemNo : besubmemno    												            						
             					  },
             					   type : 'POST',
             					   error : function(xhr) {
             					     alert('Ajax request 發生錯誤');
             					  },
             					   success : function(data) {		
             					     	
             						  $('span.submember').text('已追蹤').css({"color":"red","font-weight":"bold"});
              					    							
             					  }
             				});
              			}else if($(e).children('span').text()=='已追蹤'){
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
             						  $('span.submember').text('追蹤會員').css({"color":"","font-weight":""});
  							
             					  }
             				});
              			}	
              		}
              		
              
              </script>
       
</body>
</html>