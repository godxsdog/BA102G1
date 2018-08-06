<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="">
<%
	Emp emp = (Emp) request.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link href="<%=request.getContextPath()%>/back_end/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/nav.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/colorplan.css"
	rel="stylesheet">
<!-- Custom CSS -->
<link
	href="<%=request.getContextPath()%>/back_end/css/modern-business.css"
	rel="stylesheet">
<!-- Custom Fonts -->
<link
	href="<%=request.getContextPath()%>/back_end/font-awesome/css/font-awesome.css"
	rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/back_end/css/backend.css"
	rel="stylesheet">
<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<style>
.submit {
	matgin-top: 20px;
}
</style>
<script type="text/javascript">
	function bigBoss(){
		document.getElementById("empId").value="petym1";
		document.getElementById("empPwd").value="p123456";
		
	}
</script>
</head>

<body>
	<%@ include file="/back_end/backEndNavBar.file"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/back_end/backEndLSide.file"%>

			<div class="col-xs-12 col-sm-8">

				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">

						<div align="center">
							<Img
								src="<%=request.getContextPath()%>/back_end/images/logo.png"
								height="250px" width="400px" />
						</div>

						<form class=""
							action="<%=request.getContextPath()%>/back_end/emp/EmpLogin2.do"
							method="post">


							<div class="form-group">
								<label for="empId" class="cols-sm-2 control-label">帳號</label><span
									id="memIdShow"> <c:if test="${not empty errorMsgs}">
										<font color="red">&nbsp;&nbsp;帳號密碼錯誤</font>
									</c:if>

								</span>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-user fa" aria-hidden="true"></i></span> <input
											type="text" class="form-control" name="empId" id="empId"
											value="<%=(emp == null) ? "" : emp.getEmpId()%>"
											placeholder="請輸入帳號" required />
									</div>
								</div>
							</div>


							<div class="form-group pwd">
								<label for="empPwd" class="cols-sm-2 control-label">密碼</label><span
									id="empPwdShow"></span>
								<div class="cols-sm-10">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="fa fa-user fa" aria-hidden="true"></i></span> <input
											type="password" class="form-control" name="empPwd"
											id="empPwd" value="<%=(emp == null) ? "" : emp.getEmpPwd()%>"
											placeholder="請輸入帳號" required />
									</div>
								</div>
							</div>



							<input
								class="btn btn-primary btn-lg btn-block login-button login"
								type="submit" value="登錄">

							<input type="hidden" name="action" value="login">
						</form>
						<button onclick="bigBoss()">老闆</button>	
					</div>
				</div>





			</div>

			<script src="https://code.jquery.com/jquery.js"></script>
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>