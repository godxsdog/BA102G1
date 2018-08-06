<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<html>
<head>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>
<%@ include file="page4.file"%>
<style>
 .panel.panel--styled {
    background: #F4F2F3;
}
.panelTop {
    padding: 30px;
}

.panelBottom {
    border-top: 1px solid #e7e7e7;
    padding-top: 20px;
}
.btn-add-to-cart {
    background: #FD5A5B;
    color: #fff;
}
.btn.btn-add-to-cart.focus, .btn.btn-add-to-cart:focus, .btn.btn-add-to-cart:hover  {
	color: #fff;   
    background: #FD7172;
	outline: none;
}
.btn-add-to-cart:active {
	background: #F9494B;
	outline: none;
}


span.itemPrice {
    font-size: 24px;
    color: #FA5B58;
}


/*----------------------
##star Rating Styles 
----------------------*/
.stars {
    padding-top: 10px;
	width: 100%;
	display: inline-block;
}
span.glyphicon {
    padding: 5px;
}
.glyphicon-star-empty {
    color: #9d9d9d;
}
.glyphicon-star-empty, .glyphicon-star { 
    font-size: 18px;
}
.glyphicon-star {
    color: #FD4;
    transition: all .25s;
}   
.glyphicon-star:hover { 
    transform: rotate(-15deg) scale(1.3); 
}


</style>
</head>
<body>
<%@ include file="/front_end/frontEndNavBar.file" %>
<%@ include file="page2.file" %>
		<div class="container">    
		<div class="row">
			<div class="col-md-8" style="margin-left:2cm;margin-top:2cm">				
				<div class="panel panel-default  panel--styled" >
					<div class="panel-body" >
						<div class="col-md-12 panelTop">	
							<div class="col-md-4">	
								<img class="img-responsive" src="<%=request.getContextPath() %>/front_end/images/222.jpg" alt=""/>
							</div>
							<div class="col-md-8" style="padding-left:4cm;padding-top:2cm;">	
								<font class="fancy" size="4">你的購物車尚未購買任何商品</font><br>
								<div style="padding-top:2cm;">
								<button type="submit" class="btn btn-lg btn-add-to-cart" onclick="window.location.href='<%=request.getContextPath()%>/front_end/product/BrowseProduct.jsp'">
								回首頁逛逛
								 </button>
								 </div>
							</div>
						</div>
						
						<div class="col-md-12 panelBottom">
						
						
    						
    					
							<div class="col-md-4 text-center">
							  
							</div>
							<div class="col-md-4 text-left">
								
							</div>
							<div class="col-md-4">
								<div class="stars">
								 <div id="stars" class="starrr">
								 
								 </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
    </div>

 <%@ include file="/front_end/frontEndButtom.file" %>
        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>