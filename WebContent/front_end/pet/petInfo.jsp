<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.pet.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="">

<head>
<title>�d�� You & Me</title>
<%@ include file="memHead.file"%>
<STYLE>
.title {
	width: 120px; /* �]�w H1 ���˦�*/
}

.pet {
	margin-top: 70px;
	color:Crimson ;
}

.addPet {
	margin-top: 20px;
}

</STYLE>
</head>

<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<div class="container-fluid">
		<div class="row">

			<div class="col-xs-12 col-sm-2 postion-left-group ">
				<!--�b�o�̭������X �|�����Ҧ��d�� �s��list -->
				<%@ include file="memZoneLSide.file"%>
			</div>

			<div class="col-xs-12 col-sm-8 col-md-offset-1">
				<div class="row">

					<h5 class="page-header text-right">�ثe��m:�|���M��</h5>


					<div class="row">

						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">${member.memId}</h3>
							</div>
							<div class="panel-body">
								<div class="row test">


									<c:forEach var="pet" items="${list}" varStatus="s">
										<div class="row pet">
											<div class="col-md-3 col-lg-3 " align="center">
												<img alt="User Pic" id="petImg"
													src="<%=request.getContextPath() %>/PetImgReader?petNo=${pet.petNo}" width="100%" class="img-rounded">
											</div>

											<div class=" col-md-9 col-lg-9 ">
												<table class="table table-user-information">

													<tbody>

														<tr>
															<td class="title">�d���m�W</td>
															<td>${pet.petName}</td>
														</tr>
														<tr>
															<td class="title">�d������</td>
															<td>${pet.petKind}</td>
														</tr>
														<tr>
															<td class="title">�d���ʧO</td>
															<td>${pet.petGender==0?"��":"��"}</td>
														</tr>
														<tr>
															<td class="title">�d���~��</td>
															<td>${pet.petSpecies}</td>
														</tr>
														<tr>
															<td class="title">�ͤ�</td>
															<td>${pet.petBday}</td>
														</tr>

														<tr>
															<td class="title">�d������</td>
															<td>${pet.petIntro}</td>
														</tr>

													</tbody>
												</table>


												<span>
													<form action="petInfoUpdate.jsp" method="post">
														<input type="hidden" name="petOrd" value=${s.index} >
														<input type="submit" class="btn btn-primary"
															value="�s���d����T">
													</form>


												</span>
											</div>
										</div>
									</c:forEach>
									
									
									
									<c:if test="${not empty errorMsgs}">
											<font color="red">
												<ul>
													<c:forEach var="message" items="${errorMsgs}">
														<li>${message}</li>
													</c:forEach>
												</ul>
											</font>
										</c:if>
									
									
									
									
									
									
									
									
									
									<% 
									int listSize=list.size();
									pageContext.setAttribute("listSize", listSize);
									%>
									
									<div class="col-md-3 col-md-push-3 col-lg-3 col-lg-push-3 addPet">
											
									<c:if test="${listSize<3}">
									<form action="petRegister.jsp" method="post" enctype="multipart/form-data">
										<input type="submit" class="btn btn-info" value="�s�W�d��">
									</form>
									</c:if >
									
									</div>
									
								</div>
							</div>


						</div>
					</div>
				</div>

				
			</div>
		</div>
		
				<script>
			$(function() {

				var cw = $('#petImg').width();
				$('#petImg').css({'height':cw+'px'});
				console.log(cw);
				

				
			});
		</script>
		
		
		<%@ include file="/front_end/frontEndButtom.file"%>

</body>

</html>