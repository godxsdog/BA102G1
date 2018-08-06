<%@page import="com.restaurant.model.Restaurant"%>
<%@page import="com.restMember.model.RestMember"%>
<%@page import="com.restMember.model.RestMemberService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%-- <% Restaurant restaurant = (Restaurant)request.getParameter("restaurant"); %> --%>
<%-- �����ĥ� JSTL �P EL ���� --%>
<!DOCTYPE html>
<html lang="">
<head>

<%@ include file="/front_end/actFiles/restFrontCss.file" %>

<script
	src="<%=request.getContextPath()%>/front_end/js/jquery.twzipcode.min.js"></script>
<title>�d�� You & Me</title>

<style type="text/css">
.aa {
	margin-top: 20px;
}




</style>
<style>
 


.select-style {
    padding: 0;
    margin: 0;
    border: 1px solid #ccc;
    width: 200px;
    border-radius: 3px;
    overflow: hidden;
    background-color: #fff;

    background: #fff url("http://www.scottgood.com/jsg/blog.nsf/images/arrowdown.gif") no-repeat 90% 50%;
}

.select-style select {
    padding: 5px 8px;
    width: 130%;
    border: none;
    box-shadow: none;
    background-color: transparent;
    background-image: none;
    -webkit-appearance: none;
       -moz-appearance: none;
            appearance: none;
}

.select-style select:focus {
    outline: none;
}






.zipcode {
	display:none;
}
.county {
    background-color: #4169E1;
    color: #fff;
}
.district {
    background-color: #008000;
    color: #fff;
}
</style>


<style type="text/css">
	div.inline { float:left; }
	.clearBoth { clear:both; }
</style>

<script>
	$(function(){
		$("#newRest").click(function(){
			$("#restName").val("�������d���\�U");
			$("#restAdd").val("�å���306��");
			$("#restPhone").val("(02)2231-8882");
			
		})
	})

</script>

</head>
<body background="<%=request.getContextPath()%>/front_end/actFiles/465.jpg"  style=background-size:cover;>
	<%@ include file="/front_end/actFiles/restMemberNavBar2.file" %>
	
	
	
	<div class="container">
		<div class="row">
        	
    
    
    	<div class="col-sm-offset-3">
			<div class="col-sm-9">
				
				<h1 class="text-center">�s�W�����\�U</h1>

				<div class="form-horizontal">
				<c:if test="${not empty newRestErr}">
					<font color="red" style="font-weight:bold">${newRestErr}</font>
				</c:if>
					<form method="post" action="<%=request.getContextPath()%>/restaurant/restaurantController">
							
							<input type="hidden" name="restReviewStatus" class="form-control" value="2" >
							
							<div class="form-group">
								<label class="col-sm-3 control-label">
									�\�U�W��
								</label>
								<div class="col-sm-9">
									<input type="text" name="restName" id="restName" class="form-control" 
									value="" placeholder="�п�J�\�U�W��" >
									
								</div>
							</div>

							<div class="form-group">
								
									<label class="col-sm-3 control-label">
										�\�U�a�}
									</label>
									<div class="col-sm-9">
										<div id="twzipcode">
											
												<div class="select-style inline">
													<div data-role="county" data-style="Style Name"></div>
												</div>
												<div class="select-style inline">
													<div data-role="district" data-style="Style Name"></div>
												</div>
											
										</div>
										
											<input type="text" name="restAdd" id="restAdd" class="form-control" 
											value="" placeholder="�п�J�\�U�a�}">
									</div>
								
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">
									�\�U�q��
								</label>
								<div class="col-sm-9">
									<input type="text" name="restPhone" id="restPhone" class="form-control" 
									value="" placeholder="�п�J�\�U�q��">
									
								</div>
							</div>

							

							<div class="form-group">
								<label class="col-sm-3 control-label">
									�\�U����
								</label>
								<div class="col-sm-9">
									<select name="restKind" class="form-control">
									  <option value="0">���\�U</option>
									  <option value="1">���\�U</option>
									  <option value="2">��L�\�U</option>
									</select>
								</div>
							</div>
							
							
							<input type="hidden" name="action" value="newRestaurant">
							<input class="btn btn-primary btn-lg btn-block login-button login"
								type="submit" value="�s�W���\�U">
							<div>
								<a href="<%=request.getContextPath() %>/front_end/restMember/restMemberLogin.jsp" class="btn btn-link">�^�n�J����</a> 
								<a href="<%=request.getContextPath() %>/front_end/restMember/restMemberList.jsp" class="btn btn-link">�^���U����</a>
							</div>	
								
						</form>			
					</div>	
						
					<button id="newRest">�s�W�\�U</button>	
					
				</div>
			</div>
		</div>
	</div>
<%@ include file="/front_end/frontEndButtomFixed.file" %>  

		<script>
			$(function() {
	
				
				$('#twzipcode').twzipcode({
				    // �̧ǮM�Φܿ����B�m���Ϥζl���ϸ���
				    'css': ['county', 'district', 'zipcode'],
				});
				
			});
			
	
	
			
		</script>
    
	
</body>
</html>