<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.faq.model.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="">

<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link href="<%= request.getContextPath() %>/front_end/css/bootstrap.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/front_end/css/nav.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/front_end/css/colorplan.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<%= request.getContextPath() %>/front_end/css/modern-business.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/front_end/css/date.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<%= request.getContextPath() %>/front_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="<%= request.getContextPath() %>/front_end/css/frontend.css" rel="stylesheet" type="text/css">
    <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>

/*改寫colorplan裡面的這個標籤*/
.postion-left-group{
/*   position:relative; */
/*   margin-top: 28rem; */
    margin-top:2rem;
  display:block;
}

/*並在你的colorplan CSS中加入這個*/
.img-bg{
    opacity:1;
    background-color:#ccc;
    max-width: 100%;
    display: block;
    margin: 0 auto;
}

/* 備用側邊攔按鈕 */
input[type="submit"]{
 color: #000000;
 width: 100%;
}

/*麵包屑*/
.breadcrumb-container {
	margin-top: 0.5em;
}

#faq_category {
	margin-right: 1em;
}

#user_submit_title {
	width: 50%;
	margin-left: 1em;
}

.user_submit {
	resize: none;
	width: 100%;
	height: 100px;
	font-size: 16px;
}

</style>

</head>

<body>
<%@ include file="/front_end/frontEndNavBar.file"%>
    
<!-- 原本該有用的側邊攔 -->
<div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 col-sm-2">
             <div class="frontend"><img src="<%= request.getContextPath() %>/front_end/images/logo-sp.png" class="img-rounded img-bg try-color">
             </div>
                <div id="menu" data-spy="affix" data-offset-top="200">
<!-- 					<div class="panel list-group list-color"> -->
<!-- 						<a class="list-group-item" data-parent="#menu" data-target="#sm1" -->
<!-- 							data-toggle="collapse" href="" name="faqCategory" value="註冊問題" > 註冊問題 <span -->
<!-- 							class="glyphicon glyphicon-triangle-bottom pull-right"> </span> -->
<!-- 						</a> -->
						
<!-- 						<a class="list-group-item" data-parent="#menu" data-target="#sm2" -->
<!-- 							data-toggle="collapse" href="" name="faqCategory" value="登入問題"> 登入問題 <span -->
<!-- 							class="glyphicon glyphicon-triangle-bottom pull-right"> </span> -->
<!-- 						</a> -->

<!-- 						<a class="list-group-item" data-parent="#menu" data-target="#sm3" -->
<!-- 							data-toggle="collapse" href="" name="faqCategory" value="約會問題"> 約會問題 <span -->
<!-- 							class="glyphicon glyphicon-triangle-bottom pull-right"> </span> -->
<!-- 						</a> -->

<!-- 						<a class="list-group-item" data-parent="#menu" data-target="#sm4" -->
<!-- 							data-toggle="collapse" href="" name="faqCategory" value="交易問題"> 交易問題 <span -->
<!-- 							class="glyphicon glyphicon-triangle-bottom pull-right"> </span> -->
<!-- 						</a> -->

<!-- 						<a class="list-group-item" data-parent="#menu" data-target="#sm5" -->
<!-- 							data-toggle="collapse" href="" name="faqCategory" value="其他問題"> 其他問題 <span -->
<!-- 							class="glyphicon glyphicon-triangle-bottom pull-right"> </span> -->
<!-- 						</a> -->
	
<!-- 備用的側邊攔   -->
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" >
     <input type="submit" name="faqCategory" value="註冊問題">
     <input type="hidden" name="action" value="faq_for_user">
	 </FORM>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" >
     <input type="submit" name="faqCategory" value="登入問題">
     <input type="hidden" name="action" value="faq_for_user">
	 </FORM>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" >
     <input type="submit" name="faqCategory" value="約會問題">
     <input type="hidden" name="action" value="faq_for_user">
	 </FORM>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" >
     <input type="submit" name="faqCategory" value="交易問題">
     <input type="hidden" name="action" value="faq_for_user">
	 </FORM>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" >
	<input type="submit" name="faqCategory" value="其他問題">
	<input type="hidden" name="action" value="faq_for_user">
	</FORM>
						
<%-- 						<%=request.getContextPath()%>/back_end/faq/faq.do?faqCategory=其他問題&action=getRealtive_For_Category --%>
						
<!-- 					</div> -->
				</div>
			</div>

            <div class="col-xs-12 col-sm-10">
                <div class="row">


<!-- 把你要放的內容放在這=====================================================

        例如
        <div class="col-xs-12 col-sm-3">    1234</div>
        <div class="col-xs-12 col-sm-3">    1234</div>
        <div class="col-xs-12 col-sm-3">    1234</div>
        <div class="col-xs-12 col-sm-3">    1234</div>


一個完整的<div></div>以及隔線================================================ 
但要注意你一開始是不是有把結尾的</div>放在footer.file裡面

-->


<!-- 幻燈片這這 --!>
			<div class="col-xs-12 col-sm-12 ">
				<div>
					<div class="row">
						<div class="col-sm-10 col-sm-offset-1">
							<div class="carousel slide" data-ride="carousel" id="carousel-id">
								<!-- 幻燈片小圓點區 -->
								<ol class="carousel-indicators">
									<li class="" data-slide-to="0" data-target="#carousel-id">
									</li>
									<li class="" data-slide-to="1" data-target="#carousel-id">
									</li>
									<li class="" data-slide-to="2" data-target="#carousel-id">
									</li>
									<li class="" data-slide-to="3" data-target="#carousel-id">
									</li>
									<li class="" data-slide-to="4" data-target="#carousel-id">
									</li>
									<li class="" data-slide-to="5" data-target="#carousel-id">
									</li>
									<li class="active" data-slide-to="6" data-target="#carousel-id">
									</li>
								</ol>
								<!-- 幻燈片主圖區 -->
								<div class="carousel-inner">
									<div class="item">
										<img alt=""
											src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>問與答</h1>
											</div>
										</div>
										</img>
									</div>
									<div class="item">
										<img alt=""
											src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>FAQ</h1>
											</div>
										</div>
										</img>
									</div>
									<div class="item">
										<img alt=""
											src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>問與答</h1>
											</div>
										</div>
										</img>
									</div>
									<div class="item">
										<img alt=""
											src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>FAQ</h1>
											</div>
										</div>
										</img>
									</div>
									<div class="item">
										<img alt=""
											src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>問與答</h1>
											</div>
										</div>
										</img>
									</div>
									
									<div class="item active">
										<img alt="" src="<%= request.getContextPath() %>/front_end/images/pets_try3_resized.jpg">
										<div class="container">
											<div class="carousel-caption">
												<h1>FAQ</h1>
											</div>
										</div>
										</img>
									</div>
								</div>
								<!-- 上下頁控制區 -->
								<a class="left carousel-control" data-slide="prev"
									href="#carousel-id"> <span
									class="glyphicon glyphicon-chevron-left"> </span>
								</a> <a class="right carousel-control" data-slide="next"
									href="#carousel-id"> <span
									class="glyphicon glyphicon-chevron-right"> </span>
								</a>
							</div>
						</div>
					</div>
					
					<!-- 搜尋表單 -->
<!-- 					<div> -->
<!-- 						<form class="navbar-form navbar-right" role="search"> -->
<!-- 						<div class="form-group"> -->

<!-- 查詢功能在這 -->

<%--      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" > --%>
<!--      <input type="text" name="faqCategory" placeholder="請輸入想查詢的類別"> -->
<!--      <button class="btn btn-success" type="submit">搜尋</button> -->
<!--      <input type="hidden" name="action" value="faq_for_user"> -->
<!--  	 </FORM> -->
     
<%--      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" > --%>
<!--      <input type="text" name="faqTitle" placeholder="請輸入想查詢的標題"> -->
<!--      <button class="btn btn-success" type="submit">搜尋</button> -->
<!--      <input type="hidden" name="action" value="getRealtive_For_Title"> -->
<!--      </FORM> -->
     
<%--      <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back_end/faq/faq.do" > --%>
<!--      <input type="text" name="faqAnswer" placeholder="請輸入想查詢的答案"> -->
<!--      <button class="btn btn-success" type="submit">搜尋</button> -->
<!--      <input type="hidden" name="action" value="getRealtive_For_Answer"> -->
<!-- 	 </FORM> -->

<!-- 						</div> -->
<!-- 						</form> -->
<!-- 					</div> -->

<!-- 麵包屑在這 -->
					<div class="breadcrumb-container">
						<div class="breadcrumb-row">
							<ol class="breadcrumb">
								<li><a href="<%=request.getContextPath() %>/front_end/index.html"> 首頁 </a></li>
								<li><a href="<%=request.getContextPath() %>/front_end/about_us/faq/faq_user.jsp"> 常見問題 </a></li>
							</ol>
						</div>
					</div>
					
					
					<!-- faq摺疊選單 -->
					
					<div class="panel-group" id="accordion">						
							<div class="panel-collapse collapse in" id="collapseOne">
									<!-- faq重點在這 -->
							</div>
					</div>
						
						<div class="panel panel-success">
								
								<% int count=0; %>
								<c:forEach var="faq" items="${faqList}">
								<div class="panel-heading">
								<h4 class="panel-title">
									<a data-parent="#accordion" data-toggle="collapse"
										href="#FAQ<%=count%>"> ${faq.faqTitle}</a>
								</h4>
						        </div>
								<div class="panel-collapse collapse" id="FAQ<%=count%>">
									<div class="panel-body">${faq.faqAnswer}</div>
								</div>
							<% count++;%>
								</c:forEach>
								
						</div>
							
<!-- 						<div class="panel panel-success"> -->
<!-- 							<div class="panel-heading"> -->
<!-- 								<h4 class="panel-title"> -->
<!-- 									<a data-parent="#accordion" data-toggle="collapse" -->
<!-- 										href="#collapseTwo"> 問題在這邊。第 2 部分--show 方法 </a> -->
<!-- 								</h4> -->
<!-- 							</div> -->
<!-- 							<div class="panel-collapse collapse" id="collapseTwo"> -->
<!-- 								<div class="panel-body"> -->
<!-- 									<blockquote> -->
<!-- 											<p>這是ㄧ個blockquote</p> -->
<%-- 											<cite class=""> 單身狗關心您 </cite> --%>
<!-- 									</blockquote> -->
<!-- 									Nihil anim keffiyeh helvetica, -->
<!-- 									craft beer labore wes anderson cred nesciunt sapiente ea -->
<!-- 									proident. Ad vegan excepteur butcher vice lomo.</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="panel panel-info"> -->
<!-- 							<div class="panel-heading"> -->
<!-- 								<h4 class="panel-title"> -->
<!-- 									<a data-parent="#accordion" data-toggle="collapse" -->
<!-- 										href="#collapseThree"> 問題在這邊。第 3 部分--toggle 方法 </a> -->
<!-- 								</h4> -->
<!-- 							</div> -->
<!-- 							<div class="panel-collapse collapse" id="collapseThree"> -->
<!-- 								<div class="panel-body">Nihil anim keffiyeh helvetica, -->
<!-- 									craft beer labore wes anderson cred nesciunt sapiente ea -->
<!-- 									proident. Ad vegan excepteur butcher vice lomo.</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="panel panel-warning"> -->
<!-- 							<div class="panel-heading"> -->
<!-- 								<h4 class="panel-title"> -->
<!-- 									<a data-parent="#accordion" data-toggle="collapse" -->
<!-- 										href="#collapseFour"> 問題在這邊。第 4 部分--options 方法 </a> -->
<!-- 								</h4> -->
<!-- 							</div> -->
<!-- 							<div class="panel-collapse collapse" id="collapseFour"> -->
<!-- 								<div class="panel-body">Nihil anim keffiyeh helvetica, -->
<!-- 									craft beer labore wes anderson cred nesciunt sapiente ea -->
<!-- 									proident. Ad vegan excepteur butcher vice lomo.</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
					
					<!--  <script type="text/javascript">
                    $(function() {
                        $('#collapseFour').collapse({
                            toggle: false
                        })
                    });
                    $(function() { $('#collapseTwo').collapse('show') });
                    $(function() { $('#collapseThree').collapse('toggle') });
                    $(function() { $('#collapseOne').collapse('hide') });
                    </script> -->
					


<!-- 頁碼暫時用不到 -->
<!--                 <div class="text-center"> -->
<!--                     <ul class="pagination"> -->
<!--                         <li><a href="#">&laquo;</a></li> -->
<!--                         <li><a href="#">1</a></li> -->
<!--                         <li><a href="#">2</a></li> -->
<!--                         <li class="active"><a href="#">3</a></li> -->
<!--                         <li><a href="#">4</a></li> -->
<!--                         <li><a href="#">5</a></li> -->
<!--                         <li><a href="#">&raquo;</a></li> -->
<!--                     </ul> -->
<!--                     <br> -->
<!--                     <ul class="pager"> -->
<!--                         <li><a href="#">前一頁</a></li> -->
<!--                         <li><a href="#">下一頁</a></li> -->
<!--                     </ul> -->
<!--                 </div> -->
                
                
                	<!-- 這邊是user submit -->
<%--                 	<form action="<%=request.getContextPath() %>/back_end/faq/faq.do" method="post"> --%>
<!-- 					<div class="container"> -->
<!-- 						<div class="row"> -->
<!-- 							<div class="col-md-12 col-md-12"> -->
<!-- 								<div class="user-submit"> -->
<!-- 									<h4> -->
<!-- 										以上解答有幫助到你嗎? -->
<!-- 										<input type="radio" name="radio" value="yes">有 -->
<!-- 										<input type="hidden" name="action" value="user_insert" > -->
<!-- 										<input type="radio" name="radio" value="no" onclick="alert('yeah~');">沒有<p> -->
<!-- 									</h4> -->
										
<!-- 									<div class="dropdown"> -->
<!-- 										<h4> -->
<!-- 											問題類別 <select name="faqCategory"> -->
<!-- 												<option value="defaultValue">請選擇</option> -->
<!-- 												<option value="getRealtive_For_Category">註冊問題</option> -->
<!-- 												<option value="getRealtive_For_Category">登入問題</option> -->
<!-- 												<option value="getRealtive_For_Category">約會問題</option> -->
<!-- 												<option value="getRealtive_For_Category">交易問題</option> -->
<!-- 												<option value="getRealtive_For_Category">其他問題</option> -->
<!-- 											</select>  -->
<!-- 											主旨<input type="text" name="faqTitle" placeholder="你的問題是?"> -->
<!-- 										</h4> -->

<!-- 									</div> -->
<!-- 									placegolder 業障重 -->
<!-- 									<textarea class="user_submit" name="faqAnswer" placeholder="請描述一下問題內容" onfocus="this.select()" required -->
<!-- 										data-validation-required-message="Please enter a message."> -->
<!--                                 	</textarea> -->
<!-- 									</div> -->
								
<!--								<div class="btn-group"> -->
<!-- 									<a href="" class="btn btn-primary" role="button" onclick="safeSubmit" >確認</a> <a -->
<!-- 										href="" class="btn btn-default" role="button">取消</a> -->
<!-- 								</div> -->
								
<!-- 								確認submit -->
<!-- 								<button type="submit" class="btn btn-primary btn-mi" data-toggle="modal" data-target="#myModal">確認</button> -->
<!-- 								這邊送出資料 -->
<!-- 								<input type="hidden" name="action" value="user_insert" > -->
								
<!-- 								Modal -->
<!-- 								<div id="myModal" class="modal fade" role="dialog"> -->
<!-- 								  <div class="modal-dialog"> -->
								
<!-- 								    Modal content -->
<!-- 								    <div class="modal-content"> -->
<!-- 								      <div class="modal-header"> -->
<!-- 								        <button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 								        <h4 class="modal-title">感謝你的提交</h4> -->
<!-- 								      </div> -->
<!-- 								      <div class="modal-body"> -->
<!-- 								        <p>問題已經送出囉~</p> -->
<!-- 								      </div> -->
<!-- 								      <div class="modal-footer"> -->
<!-- 								        <button type="button" class="btn btn-primary" data-dismiss="modal">關閉</button> -->
<!-- 								      </div> -->
<!-- 								    </div> -->
								
<!-- 								  </div> -->
<!-- 								</div> -->
<!-- 								</form> -->
								
<!-- 								取消submit -->
<!-- 								<button type="button" class="btn btn-danger btn-mi" data-toggle="modal" data-target="#cancle-submit">取消</button> -->
								
<!-- 								Modal -->
<!-- 								<div id="cancle-submit" class="modal fade" role="dialog"> -->
								  <div class="modal-dialog">
								
								    <!-- Modal content-->
<!-- 								    <div class="modal-content"> -->
<!-- 								      <div class="modal-header"> -->
<!-- 								        <button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 								        <h4 class="modal-title">取消提交</h4> -->
<!-- 								      </div> -->
<!-- 								      <div class="modal-body"> -->
<!-- 								        <p>確認按下去就什麼都沒有了喔~</p> -->
<!-- 								      </div> -->
<!-- 								      <div class="modal-footer"> -->
<!-- 								        <button type="button" class="btn btn-danger" data-dismiss="modal">確定</button> -->
<!-- 								      </div> -->
<!-- 								    </div> -->
								
<!-- 								  </div> -->
<!-- 								</div> -->

<!--                                 用 JavaScript 抓 <textarea> 目前內部現有值請用 value ，抓一開始初始預設值請用 defaultValue ，就是不要用 innerHTML 。因為 <textarea> innerHTML 在 IE 是目前內部現有值，在 Firefox 、 Chrome 是初始預設值。 -->
<!--                                  <script> -->
<!-- 									var content = document.getElementById('user_submit'); -->
<!--                                 </script> -->
<!--                                 <input type="button" value="value" onclick="alert(content.value)"> -->
<!--                                 <input type="button" value="defaultValue" onclick="alert(content.defaultValue)"> -->
<!--                                 <input type="button" value="innerHTML" onclick="alert(content.innerHTML)"> -->
                                

							</div>
						</div>
					</div>
                

        </div>
        </div>
</div>

        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>