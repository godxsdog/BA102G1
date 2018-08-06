<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.member.model.*"%>
<%@ page import="com.diary.model.*"%>
<%@ page import="com.album.model.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pet.model.*"%>

<html lang="">


<%
	DiaryService diaSvc = new DiaryService();
	Integer memNo = Integer.parseInt(request.getParameter("memNo"));
	List<Diary> dialist = diaSvc.getAllFromMemNo(memNo);
	pageContext.setAttribute("dialist",dialist);
	MemberService memSvc = new MemberService();
	Member memberV = memSvc.getOneMember(memNo);
	pageContext.setAttribute("memberV", memberV);
	Member member = (Member) session.getAttribute("member");
	List<Pet> list = memSvc.getPetsByMemNo(memberV.getMemNo());
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="memSvcC" scope="page" class="com.member.model.MemberService"/>

<head>

<title>寵物 You & Me</title>
<%@ include file="memHead.file"%>
<style>
.panel-body{
	padding:1px;
}
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

							<div class="panel-body" >
							
							
<!-- 							你的日誌內容 -->
							<c:forEach var="diary" items="${dialist}" >
					             <div class="row">
					                <div class="panel panel-default col-sm-8 col-sm-offset-2 top-margin-sm dia-msg" style="margin-bottom:20px;background-color:#FAF0E6;" >
					                   <div class="panel" style="background-color:#FAF0E6;">
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
						                      	   
												   </div>	
					                              </div>
					                          </div>
					                      </div>
					                      <div class="panel-body">                       
							                   <div class="col-sm-2 memImg" >
							                      <img class="memImg" src="<%=request.getContextPath()%>/front_end/member/MemImgReader2.do?memNo=${diary.getMemNo()}">
							                   </div>
							                   <div class="col-sm-10 padmarg">
							                      <a href="<%=request.getContextPath()%>/front_end/diary/personalDiary.jsp?memNo=${diary.getMemNo()}" style="color:#191970;font-weight:bold;">${memSvcC.getOneMember(diary.getMemNo()).getMemSname()}</a>
											
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
						                         <c:if test="${!imgformat }">
						                         <div style="max-height: auto;max-width:540px;" >
													<video controls style="max-height: 80%;max-width: 80%;">
														<source src="<%=request.getContextPath() %>/front_end/diary/DiaryVideo?diano=${diary.diaNo}" type="video/mp4" alt="您的瀏覽器不支援此撥放程式!!">
													</video>
												 </div>	                         
						                      	 </c:if>
						                      </div>
						                  </div>  
					                   </div>
					                   
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