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

			<%@ include file="empHeader.file"%>
<style>
.submit {
	matgin-top: 20px;
}
</style>
<script>
	$(function(){
		
		$("#newEmp").click(function(){
			$("#empName").val("���f��");
			$("#empId").val("petym11");
			$("#sel1 option[value=��z]").attr("selected","selected");
			$("#empHireDate").val("2017-08-31");
			$("#empEmail").val("chrisyen8341@gmail.com");
			$(".empAuth").prop("checked", true);
		})
			
		
	});
	
</script>
</head>

<body>
	<%@ include file="/back_end/backEndNavBar.file"%>

	<div class="container-fluid">
		<div class="row">

			<%@ include file="/back_end/backEndLSide.file"%>

			<div class="col-xs-12 col-sm-8">


				<h5 class="page-header text-right"></h5>


				<div
					class="panel panel-default col-sm-offset-3 col-sm-6 text-center">

					<form
						action="<%=request.getContextPath()%>/back_end/emp/EmpRegister.do"
						method="post">

						<div class="form-group pwd">
							<label for="empName" class="cols-sm-2 control-label">�m�W</label><span
								id="memIdShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa"
										aria-hidden="true"></i></span> <input type="text"
										class="form-control" name="empName" id="empName"
										value="<%=(emp == null) ? "" : emp.getEmpName()%>"
										placeholder="�п�J�m�W" required />
								</div>
							</div>
						</div>


						<div class="form-group pwd">
							<label for="empId" class="cols-sm-2 control-label">�b��</label><span
								id="empIdShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa"
										aria-hidden="true"></i></span> <input type="text"
										class="form-control" name="empId" id="empId"
										value="<%=(emp == null) ? "" : emp.getEmpId()%>"
										placeholder="�п�J�b��" required />
								</div>
							</div>
						</div>


						<div class="form-group">
							<label for="sel1">¾��</label> 
							<select class="form-control" id="sel1" name="empJob">
								<option value="�`�g�z">�`�g�z</option>
								<option value="��z">��z</option>
								<option value="�M��">�M��</option>
								<option value="�uŪ��">�uŪ��</option>
							</select>
						</div>


						<div class="form-group">
							<label for="empHireDate" class="cols-sm-2 control-label">��¾��</label><span
								id="empHireDateShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" name="empHireDate" id="empHireDate" class="form-control"
										placeholder="��ܨ�¾��" />
								</div>
							</div>
						</div>



						<div class="form-group">
							<label for="empEmail" class="cols-sm-2 control-label">�q�l�H�c</label><span
								id="empEmailShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="empEmail" id="empEmail"
										placeholder="�п�J�z���q�l�H�c" />
								</div>
							</div>
						</div>



						<label for="empEmail" class="cols-sm-2 control-label">���u�v��</label><span
							id="empEmailShow"></span>
						<div class="text-left">


							<div class="row">
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4001">�e�ݬݪO�޲z</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4002">�|���b���޲z</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4003">�ӫ��޲z</label>
									</div>
								</div>
							</div>


							<div class="row">
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4004">���|�ӶD�޲z</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4005">�v���޲z</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4006">���ʺ޲z</label>
									</div>
								</div>
							</div>


							<div class="row">
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4007">�\�U�޲z</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="checkbox">
										<label><input type="checkbox" name="empAuth" class="empAuth" value="4008">�����H�޲z</label>
									</div>
								</div>
							</div>


						</div>



						<input
							class="btn btn-primary btn-lg btn-block login-button submit"
							type="submit" value="���U">


						<c:if test="${not empty errorMsgs}">
							<font color="red">
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</ul>
							</font>
						</c:if>



					</form>
					<button id="newEmp">�@��f��</button>


				</div>




			</div>



			<script>
				$(function() {
					//�ͤ�̤j�i��Ѽ�
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth() + 1; //January is 0!
					var yyyy = today.getFullYear();
					if (dd < 10) {
						dd = '0' + dd
					}
					if (mm < 10) {
						mm = '0' + mm
					}

					today = yyyy + '-' + mm + '-' + dd;
					document.getElementById("empHireDate").setAttribute("max",
							today);

					//�ͤ����� �ͤ餣�i�H�j�󤵤�
					$("#empHireDate")
							.blur(
									function(e) {
										console.log(e.target.id);
										var nDay = yyyy + mm + dd;
										if (e.target.id == "#empHireDate") {
											days = $("#empHireDate").val()
													.split("-");
											tDay = days[0] + days[1] + days[2];
											console.log(tDay);
											console.log(nDay);
											if (tDay > nDay) {
												$("#empHireDateShow")
														.html(
																"&nbsp;&nbsp;&nbsp;&nbsp;���X�檺�ͤ�")
														.css('color', 'red');
											} else {
												$("#empHireDateShow").html("");
											}
										}
									});

					//�H�c����
					$("#empEmail")
							.blur(
									function() {
										if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
												.test($("#empEmail").val())) {
											$("#empEmailShow").html("").css(
													'color', 'green');
											valids[6] = true;
										} else {
											$("#empEmailShow")
													.html(
															"&nbsp;&nbsp;&nbsp;&nbsp;���X�檺EMAIL")
													.css('color', 'red');
											valids[6] = false;
										}
									});

				});
			</script>
			
			
					<script>

	$(function(){

	
	$("#empHireDate").datetimepicker({
		format: 'Y-m-d',
		 timepicker:false,
	});

	 
	});


</script>
			
</body>

</html>