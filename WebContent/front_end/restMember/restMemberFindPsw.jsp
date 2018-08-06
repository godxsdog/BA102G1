<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html lang="">
<head>

<title>寵物 You & Me</title>
<style type="text/css">
.aa{
	margin-left: 40%
}
.bb{
	margin-top: 10%
}
.cc{
	margin-top: 5%
}
</style>
<%@ include file="/front_end/actFiles/restFrontCss.file" %>	
</head> 
<body>
<%@ include file="/front_end/actFiles/restMemberNavBar2.file" %>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 bb">
				
				<h1 class="text-center">遺失密碼?</h1>

				<div class="form-horizontal">
				
					<form method="post" action="<%=request.getContextPath() %>/restMember/restMemberController">

							<div class="form-group">
								<label class="col-sm-3 control-label">
									會員帳號
								</label>
								<div class="col-sm-9">
									<input type="text" name="restMemId" class="form-control" value="" >
									
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									E-mail
								</label>
								<div class="col-sm-9">
									<input type="text" name="restMemEmail" class="form-control" value="" >
									
								</div>
							</div>
							
							<input type="hidden" name="action" value="findPsw">
							<input class="btn btn-primary btn-lg  login-button login aa"
								type="submit" value="寄送密碼">
								
							<div>
								<a href="<%=request.getContextPath() %>/front_end/restMember/restMemberLogin.jsp" class="btn btn-link cc">回登入頁面</a> 
						
							</div>	
						</form>	
					</div>
							
								
								
								
					</div>	
						
						
					
			</div>
		</div>
	
<%@ include file="/front_end/frontEndButtomFixed.file" %>      
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>