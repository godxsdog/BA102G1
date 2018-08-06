<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%
	Emp emp = (Emp) request.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>

<jsp:useBean id="listEmps_ByCompositeQuery" scope="request"
	type="java.util.List" />

<html>
<head>
<%@ include file="empHeader.file"%>
<title>複合查詢 - listEmps_ByCompositeQuery.jsp</title>
</head>
<body>











	<%@ include file="/back_end/backEndNavBar.file"%>

	<div class="container-fluid">
		<div class="row">
		
		<%@ include file="/back_end/backEndLSide.file"%>
			

			<div class="col-xs-12 col-sm-8">


				<h5 class="page-header text-right"></h5>


				<div class="row">

					<div class="panel panel-info">

						<div class="panel-heading">
							<h3 class="panel-title">${emp.empName}</h3>
						</div>

						<div class="panel-body">


							<div class="row">

								<div class=" col-md-9 col-lg-9 ">
									<tbody>


													<h3>
														員工查詢結果
													</h3>



										<table border='1' bordercolor='#CCCCFF' width='800'>
											<tr>
												<th>員工編號</th>
												<th>員工姓名</th>
												<th>職位</th>
												<th>雇用日期</th>
												<th>Email</th>
												<th>修改</th>
												<th>刪除</th>
											</tr>
											<%@ include file="pages/page1_ByCompositeQuery.file"%>
											<c:forEach var="empVO" items="${listEmps_ByCompositeQuery}"
												begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
												<tr align='center' valign='middle'>
													<!--將修改的那一筆加入對比色而已-->
													<td>${empVO.empNo}</td>
													<td>${empVO.empName}</td>
													<td>${empVO.empJob}</td>
													<td>${empVO.empHireDate}</td>
													<td>${empVO.empEmail}</td>


													<td>
														<FORM METHOD="post"
															ACTION="<%=request.getContextPath()%>/back_end/emp/EmpServlet.do">
															<input type="submit" value="修改"> <input
																type="hidden" name="empno" value="${empVO.empNo}">
															<input type="hidden" name="requestURL"
																value="<%=request.getServletPath()%>">
															<!--送出本網頁的路徑給Controller-->
															<input type="hidden" name="whichPage"
																value="<%=whichPage%>">
															<!--送出當前是第幾頁給Controller-->
															<input type="hidden" name="action"
																value="getOne_For_Update">
														</FORM>
													</td>
													<td>
														<FORM METHOD="post" id="empSuspend${empVO.empNo}"
															ACTION="<%=request.getContextPath()%>/back_end/emp/EmpServlet.do">
															<a href='#' data-toggle="modal" data-target="#deleteModal${empVO.empNo}"> 
															<input type="button" value="停權"> 
															</a>
															<input type="hidden" name="empno" value="${empVO.empNo}">
															<input type="hidden" name="requestURL"
																value="<%=request.getServletPath()%>">
															<!--送出本網頁的路徑給Controller-->
															<input type="hidden" name="whichPage"
																value="<%=whichPage%>">
															<!--送出當前是第幾頁給Controller-->
															<input type="hidden" name="action" value="delete">
														</FORM>
													</td>
												</tr>
												
												
												
												
											<!-- 員工停權Modal-->
												<div class="modal fade" id="deleteModal${empVO.empNo}"
													role="dialog">
													<div class="modal-dialog">

														<!-- Modal content-->
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal">&times;</button>
																<h4 class="modal-title">員工停權</h4>
															</div>
															<div class="modal-body">
																<p>確定停權嗎?</p>
															</div>
															<div class="modal-footer">


																<button type="button" class="btn btn-primary"
																	data-dismiss="modal" id='deletebtn'
																	onclick="document.getElementById('empSuspend${empVO.empNo}').submit();">刪除</button>
																
																<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
															</div>
														</div>

													</div>
												</div>
												
												
												
											</c:forEach>
										</table>
										<%@ include file="pages/page2_ByCompositeQuery.file"%>


									</tbody>


									<c:if test="${not empty errorMsgs}">
										<font color="red">
											<ul>
												<c:forEach var="message" items="${errorMsgs}">
													<li>${message}</li>
												</c:forEach>
											</ul>
										</font>
									</c:if>
									
									


								</div>
							</div>
						</div>
					</div>
				</div>



			</div>

			<script src="https://code.jquery.com/jquery.js"></script>
			<script
				src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

			<script>
				
			</script>
</body>
</body>
</html>
