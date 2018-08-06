<%@page import="com.restMember.model.RestMember"%>
<%@page import="com.restMember.model.RestMemberService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	RestMember restMember = (RestMember)request.getAttribute("restMember");
%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>
<!DOCTYPE html>
<html lang="">
<head>
<%@ include file="/front_end/actFiles/restFrontCss.file" %>
<title>寵物 You & Me</title>
<style type="text/css">
.aa {
	margin-top: 20px;
}




</style>

</head>
<body background="<%=request.getContextPath()%>/front_end/actFiles/11.jpg"  style="background-size:cover;">
<%@ include file="/front_end/actFiles/restMemberNavBar2.file" %>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">
				
				<h1 class="text-center">餐廳會員註冊</h1>

				<div class="form-horizontal">
				<c:if test="${not empty hasAUser}">
					${hasAUser}
				</c:if>
					<form method="post" action="<%=request.getContextPath()%>/restMember/restMemberController">
							
							<input type="hidden" name="restNo" value="${param.restNo}">
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳名稱
								</label>
								<div class="col-sm-9">
									<input type="text" name="restName" class="form-control" value="${param.restName}" readonly>
									
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳地址
								</label>
								<div class="col-sm-9">
									<input type="text" name="restAdd" class="form-control" value="${param.restAdd}" readonly>

								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳電話
								</label>
								<div class="col-sm-9">
									<input type="text" name="restPhone" class="form-control" value="${param.restPhone}" readonly>
									
								</div>
							</div>

							

							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳種類
								</label>
								<div class="col-sm-9">
									<c:if test="${param.restKind=='0'}">
										<input type="hidden" name="restKind"  value="0" >
										<input type="text" value="狗餐廳" class="form-control" readonly>
									</c:if>
									<c:if test="${param.restKind=='1'}">
										<input type="hidden" name="restKind"  value="1" >
										<input type="text" value="貓餐廳" class="form-control" readonly>
									</c:if>
									<c:if test="${param.restKind=='2'}">
										<input type="hidden" name="restKind"  value="2" >
										<input type="text" value="其他餐廳" class="form-control" readonly>
									</c:if>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳會員帳號
								</label>
								<div class="col-sm-9">
									<input type="text" name="restMemId" class="form-control" value="<%=(restMember==null)?"":restMember.getRestMemId()%>">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									餐廳會員密碼
								</label>
								<div class="col-sm-9">
									<input type="text" name="restMemPsw" class="form-control" value="<%=(restMember==null)?"":restMember.getRestMemPsw()%>">
									
								</div>
							</div>
							<input type="hidden" name="action" value="register">
							<input class="btn btn-primary btn-lg btn-block login-button login"
								type="submit" value="註冊此帳號">
							<div>
								<a href="<%=request.getContextPath() %>/front_end/restMember/restMemberLogin.jsp" class="btn btn-link">回登入頁面</a> 
								<a href="<%=request.getContextPath() %>/front_end/restMember/restMemberList.jsp" class="btn btn-link">回註冊首頁</a>
							</div>	
								
						</form>			
					</div>	
						
						
					
			</div>
		</div>
	</div>
<%@ include file="/front_end/frontEndButtomFixed.file" %>      
	
</body>
</html>