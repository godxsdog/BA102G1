<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.diary.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.letter.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>寵物 You & Me</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/bootstrap.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/nav.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/colorplan.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<%=request.getContextPath()%>/front_end/css/modern-business.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/front_end/css/date.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<%=request.getContextPath()%>/front_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/front_end/css/frontend.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath() %>/front_end/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/front_end/css/fileinput-rtl.css"  rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/front_end/js/fileinput.js"  type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/front_end/themes/explorer/theme.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/front_end/ckeditor/ckeditor.js" ></script>
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
</style>
<body>
	
	<%@ include file="/front_end/frontEndNavBar.file"%>
    <%@ include file="leftbar.file" %>
    <div class="col-xs-12 col-sm-8 " >
        <div class="row">
    
                <h5 class="page-header text-right">目前位置:修改日誌</h5>
                
                
                <%-- 錯誤表列 --%>
                <div class="row">
                	<div class=" col-sm-8 col-sm-offset-2">
						<c:if test="${not empty errorMsgs}">
							<font color='red'>請修正以下錯誤:
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
							</font>
						</c:if>
                	</div>
                </div>
                <div class="row ">
                	<div class=" col-sm-8 col-sm-offset-2 ">
                	 <div class="row">
<!--                 	此為修改日誌區 -->
						<form class="form-group" action="<%=request.getContextPath()%>/front_end/diary/diary.do" method=post enctype="multipart/form-data">
							<input type="hidden" name="diano" value="${empty diary ?diaErr.diaNo:diary.diaNo}">
							<input type="hidden" name="action" value="update">
							<input type="hidden" name="backpath" value="${empty backpath? originSource:backpath  }">
							<div class="input-group">
								<label class="input-group-addon">日誌</label>
								<input type="text" id="fastName" name="dianame" class="form-control" value="${empty diary ?diaErr.diaName:diary.diaName}"><br>
							</div>
							<textarea name="diatext" id="fastText" style="resize:none;height:80px;" class="form-control">${empty diary ?diaErr.diaText:diary.diaText}</textarea>
							<input type="file" class="file" name="diaimg" data-show-upload="false" >
							<p>
							<div class="">	
								<input type="submit" class="btn btn-primary btn-block" value="確定">
							</div>
							</p>
						</form>
								<input type="button" style="display:inline-block;font-size: 10px;" value="更新" onclick="fastUpdate();">
                	</div>							
                </div>                
              </div>
				
        </div>
        
  </div>
        
        <%@ include file="/front_end/frontEndButtom.file"%>
        <%@ include file="fastShowData.file"%>
        
</body>
</html>