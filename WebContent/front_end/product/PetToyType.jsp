<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%
	ProductService prodService = new ProductService();
	List<Product> prodList = prodService.getAllByType("其他");
	session.setAttribute("prodList", prodList);

%>
<html>
<head>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>
<%@ include file="page4.file"%>
<script>
$(document).ready(function(){
    $(window).scroll(function () {
           if ($(this).scrollTop() > 50) {
               $('#back-to-top').fadeIn();
           } else {
               $('#back-to-top').fadeOut();
           }
       });
       // scroll body to 0px on click
       $('#back-to-top').click(function () {
           $('#back-to-top').tooltip('hide');
           $('body,html').animate({
               scrollTop: 0
           }, 800);
           return false;
       });
       
       $('#back-to-top').tooltip('show');

});
		</script>
<style type="text/css">
.mm{
	padding-left:2cm;
}

.cc{
	padding-left:0cm;
}
body {
  font-family: proxima-nova, helvetica, arial, sans-serif;
  color: #333;
  font-size: 14px;
  line-height: 20px;
}

.promo-card {
  overflow: hidden;
  width: 260px;
  height: 270px;
  margin-bottom: 50px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 4px 21px -12px rgba(0, 0, 0, .66);
  -webkit-transform: rotate(0deg);
  -ms-transform: rotate(0deg);
  transform: rotate(0deg);
  -webkit-transition: all 200ms ease;
  transition: all 200ms ease;
  font-size: 18px;
  cursor: pointer;
}

.promo-card:hover {
  box-shadow: 0 34px 32px -33px rgba(0, 0, 0, .18);
  -webkit-transform: translate(0px, -3px);
  -ms-transform: translate(0px, -3px);
  transform: translate(0px, -3px);
}

.blog-bar {
  width: 4px;
  height: 45px;
  margin-top: 16px;
  float: left;
}

.blog-bar.color-pink {
  background-color: #f75e90;
}

.blog-bar.color-purple {
  background-color: #a15dc0;
}

.blog-bar.color-blue {
  background-color: #23b9b6;
}

.blog-post-text {
  margin-top: 19px;
  margin-right: 20px;
  margin-left: 20px;
  font-size: 17px;
  text-transform: uppercase;
}

.blog-description {
  font-size: 15px;
  text-transform: none;
}

.blog-description.pink-text {
  color: #f75e90;
}

.blog-description.purple-text {
  color: #a15dc0;
}

.blog-description.blue-text {
  color: #23b9b6;
}

/* Titles & containers */

.section-title {
  color: #f75e90;
  font-size: 26px;
  font-weight: 400;
  text-align: center;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.title-underline {
  display: block;
  width: 100px;
  height: 2px;
  margin-top: -10px;
  margin-right: auto;
  margin-left: auto;
  background-color: #23b9b6;
}

.promotion-section {
  padding-bottom: 80px;
  
}

.promo-flex {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  margin-top: 48px;
  -webkit-justify-content: space-around;
  -ms-flex-pack: distribute;
  justify-content: space-around;
  -webkit-flex-wrap: wrap;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  -webkit-box-align: end;
  -webkit-align-items: flex-end;
  -ms-flex-align: end;
  align-items: flex-end;
}

@media (max-width: 991px) {
  .promo-card {
    -webkit-box-flex: 0;
    -webkit-flex: 0 auto;
    -ms-flex: 0 auto;
    flex: 0 auto;
  }
}

/* Webflow Basics */

.w-container {
  margin-left: auto;
  margin-right: auto;
  max-width: 940px;
}
.w-container:before,
.w-container:after {
  content: " ";
  display: table;
}
.w-container:after {
  clear: both;
}
.w-container .w-row {
  margin-left: -10px;
  margin-right: -10px;
}
.fixed{
	
  position: fixed;
  bottom: 10;
  right: 0;
  height:80px;
  width: 100px;
  background-color: #FFB7DD;

  
}
.imggg{
	background-image:url( '<%=request.getContextPath()%>/front_end/images/logo_re.jpg' );
}
.back-to-top {
    cursor: pointer;
    position: fixed;
    bottom: 20px;
    right: 20px;
    display:none;
}
</style>
</head>
<body>
<%@ include file="pageCount1.file"%>
	<%@ include file="/front_end/frontEndNavBar.file" %>
    <%@ include file="page2.file"%>
    
    <a id="back-to-top" href="#" class="btn btn-primary btn-lg back-to-top" role="button" data-toggle="tooltip" data-placement="left"><span class="glyphicon glyphicon-chevron-up"></span></a>
		<form action="<%=request.getContextPath()%>/ProductSearch" method="POST" class="mm">
			<div class="row">
                
                <div class="form-group adj">
                    <div class="input-group" style="margin-top:0px;">
                        <input class="form-control" type="text" name="search" placeholder="Search" />
                        <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><span class="glyphicon glyphicon-search" aria-hidden="true"><span style="margin-left:10px;">Search</span></button>
                        </span>
                       
                    </div>
                </div>
            </div>
			
		</form>
   
       <div class="promotion-section" style="padding:0px">
    <div class="w-container promotion-container" >
      <div class="row" style="padding-left:4cm">
      
      </div>
    <c:forEach var="product" items="${prodList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
    <c:if test="${product.prodType == '其他'}">
    <div class="promo-flex col-sm-4" style="color: rgba(0, 0, 0, .4);">
        <div data-ix="blog-card" class="w-clearfix w-preserve-3d promo-card" >
        
        <img style="width:200px;height:150px;margin-left:1cm;" width="100%" src="<%=request.getContextPath() %>/ProductImage?prodno=${product.prodNo}">
        
          <div class="blog-bar color-pink"></div>
          <div class="blog-post-text" style="width:180px;height:40px;">
            <font size="4" color="black"  style="font-family:DFKai-sb;">
            ${product.prodName}
            </font>
            
          </div>
          <div class="blog-post-text " style="margin-right:0px">
          	<div style="padding-left:0em" class="col-md-4">
          	<br>
            <font size="4" face="fantasy" color="#5599FF">$${product.prodPrice}</font>
            
            </div>
            	<div class="col-md-6">
            	<form action="<%=request.getContextPath()%>/ShoppingDetail.do" method="POST">
						
						<a class="btn cc" ><input style="position:right" type="submit" class="btn btn-primary" value="查看詳情"></a> 
						<input type="hidden" name="prodno" value="${product.prodNo}">
						
				</form>
            	</div>
          </div>
        </div>
        
      </div>
      
      		</c:if>
		</c:forEach> 
     <!--                 顯示頁數 -->
                               	   
                
        </div>
        </div>
        </div>
        <%@ include file="page.file" %> 
        
        <%@ include file="/front_end/frontEndButtom.file" %>
        </div>
        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>