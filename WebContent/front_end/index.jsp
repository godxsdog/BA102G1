<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<style>
#banner{
	height:70%;
}

/* 最終如果要做畫面調整直接調整這個CSS */
</style>


<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<title>寵物 You & Me</title>
<!-- Bootstrap Core CSS -->
<link href="css/nav.css" rel="stylesheet">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">



<!-- Custom CSS -->
<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<!-- <link href="css/frontend.css" rel="stylesheet" type="text/css"> -->


<style>

#imgNav{
	max-width:30px;
	position:relative;
	transform:translateY(-21%) !important;
}


#logo{
width:2em;
margin-right:0.25em;
 margin-bottom:0.5em; 
}

.qa{
	margin-top: 5em;
	position:relative;
/* 	right:1em; */
}

#startdating{
	margin-top:1em;
}

 #main{ 
 	position:relative;
 	left:0.3em; 
 	


</style>

</head>




<html>

	<body class="landing">
		<div id="page-wrapper">
		
	
<%@ include file="frontEndNavBar.file"%>

			<!-- Header -->
<!-- 		<nav style="background:#032814;" class="navbar navbar-fixed-top  role="navigation" id="nav"> -->
			
<!-- 				<div class="navbar-header"> -->
<!-- 					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1"> -->
<!-- 						<span class="sr-only">PetYM</span> -->
<!-- 						<span class="icon-bar"></span> -->
<!-- 						<span class="icon-bar"></span> -->
<!-- 						<span class="icon-bar"></span> -->
<!-- 					</button> -->
<!-- 					<a class="navbar-brand" href="index.html">PetYM</a> -->



<!-- 				</div> -->
<!-- 				Collect the nav links, forms, and other content for toggling -->
<!-- 				<div class="collapse navbar-collapse" id="top-navbar-1"> -->
<!-- 					<ul class="nav navbar-nav navbar-right"> -->


<!--   <!--  ================================== -->         
<%-- 						<li><a href="<%=request.getContextPath() %>/front_end/dateitem/select_page.jsp">約會</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath() %>/front_end/product/BrowseProduct.jsp">商城</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath()%>/front_end/activityFront/activityIndex.jsp">活動</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath()%>/front_end/diary/diary.jsp">寵物日誌</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath() %>/front_end/product/Cart.jsp">購物車</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath()%>/front_end/letter/index_letter.jsp">站內信</a></li> --%>
<%-- 						<li><a href="<%=request.getContextPath()%>/front_end/letter/index_letter.jsp">會員專區</a></li> --%>
<%-- 						<c:if test="${member==null}"> --%>
<%-- 						<li><a href="<%=request.getContextPath() %>/front_end/member/login.jsp">登入</a></li> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${member!=null}"> --%>
<!-- 						<li> -->
<!-- 						<li><a href="#" data-toggle="modal" data-target="#logout">登出</a></li> -->
<%-- 						</c:if> --%>

<!-- 						========================= -->
<!-- 				<li id="selectsearch"><form class="navbar-form navbar-search" role="search"> -->
<!--                 <div class="input-group"> -->
        
<!--                     <input type="text" class="form-control"> -->
                
<!--                     <div class="input-group-btn" > -->
<!--                         <button type="button" class="btn btn-search btn-basic"> -->
<!--                             <span class="glyphicon glyphicon-search"></span> -->
<!--                             <span class="label-icon ">Search</span> -->
<!--                         </button> -->
<!--                         <button type="button" class="btn btn-basic dropdown-toggle" data-toggle="dropdown" id="searchgroup"> -->
<!--                             <span class="caret"></span> -->
<!--                         </button> -->
<!--                         <ul  class="dropdown-menu pull-right" role="menu"> -->
<!--                             <li> -->
<!--                                 <a href="#"> -->
<!--                                     <span class="glyphicon glyphicon-user spancontent"></span> -->
<!--                                     <span class="label-icon spancontent">Search By User</span> -->
<!--                                 </a> -->
<!--                             </li> -->
<!--                             <li> -->
<!--                                 <a href="#"> -->
<!--                                 <span class="glyphicon glyphicon-book spancontent"></span> -->
<!--                                 <span class="label-icon spancontent">Search By Organization</span> -->
<!--                                 </a> -->
<!--                             </li> -->
<!--                         </ul> -->
<!--                     </div> -->
<!--                 </div>   -->
<!--             </form>  </li> -->
<!-- 					</ul> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			
<!-- 		</nav> -->

			<!-- Banner -->
				<section id="banner">
					
				</section>

			<!-- Main -->
				<section id="main" class="container" >

					<section class="box special">

						<header class="major">
							<h2><img src="images/logo-sp.png" id="logo">Pet YM 
							<br />
							遇見有趣的人和寵物</h2>
							<p>無論你喜歡的是貓還是狗<br />
							馬上加入我們，開始你的寵物約會</p><br>
							<a href="<%=request.getContextPath() %>/front_end/dateitem/select_page.jsp" id="startdating" class="btn btn-info" >開始約會</a>
						
						</header>

						<span class="image featured"><img src="images/pic01.jpg" alt="" /></span>
					</section>

					<section class="box special features">
					
						<div class="features-row">

							<section>
								<a class="icon major fa-paw accent2 fourarea" href="<%=request.getContextPath() %>/front_end/dateitem/select_page.jsp"></a>
								<h3>約會</h3>
								<h3>Dating With Pets</h3>
								<p>拓展你的交友圈, 拉近人與人的距離 <BR> 用地圖搜尋適合你的約會, 在對的時間對的地點, 找到完美的約會 </p>
							</section>
							<section>
								<a class="icon major fa-shopping-cart accent3 fourarea" href="<%=request.getContextPath() %>/front_end/product/BrowseProduct.jsp"></a>
								<h3>寵物商城</h3>
								<h3>Shopping Area</h3>
								<p>最獨特的商品, 最獨家的價格, 檔期限定 <BR>
								每周更新架上商品, 為你家的寵物大人購買專屬商品, 宅配到府</p>
							</section>
						</div>
						<div class="features-row">
							<section>
								<a class="icon major fa-users accent4 fourarea" href="<%=request.getContextPath()%>/front_end/activityFront/activityIndex.jsp"></a>
								<h3>餐廳活動</h3>
								<h3>Group Activities</h3>
								<p>貓Party狗party, 交流交友的平台 <BR>
								 快進來看最新發起的活動, 最棒的活動體驗</p>
							</section>
							<section>
								<a class="icon major fa-book accent5 fourarea" href="<%=request.getContextPath()%>/front_end/diary/diary.jsp"></a>
								<h3>寵物日誌</h3>
								<h3>Pets' Diary</h3>
								<p> 追蹤寵物明星 , 分享生活點滴<br>
								過去的你我來不及參與, 未來絕對不錯過</p>
							</section>
						</div>
					</section>

					<div class="row">
						<div class="6u 12u(narrower) qa">

							<section class="box special">
								<span class="image featured"><img src="images/rest.jpg" alt="" /></span>
								<h3>更新餐廳資料</h3>
								<h3>Restaurant Owners' Club</h3>
								<p>你是寵物餐廳主人嗎? 快來發起一些活動吧</p>
								<ul class="actions">
									<li><a href="<%=request.getContextPath() %>/front_end/restMember/restMemberLogin.jsp" class="button alt btn btn-info">Learn More</a></li>
								</ul>
							</section>

						</div>
						<div class="6u 12u(narrower) qa">

							<section class="box special">
								<span class="image featured"><img src="images/qa.jpg" alt="" /></span>
								<h3>熱門QA</h3>
								<h3>Frequent Questions and Answers</h3>
								<p>完整網站使用指南, 你想知道的都能在這得到解答</p>
								<ul class="actions">
									<li><a href="<%=request.getContextPath() %>/front_end/about_us/faq/faq_user.jsp" class="button alt btn btn-info">Learn More</a></li>
								</ul>
							</section>

						</div>
					</div>

				</section>

			<!-- CTA -->
<!-- 				<section id="cta">

					<h2>Sign up for beta access</h2>
					<p>Blandit varius ut praesent nascetur eu penatibus nisi risus faucibus nunc.</p> -->

<!-- 					<form>
						<div class="row uniform 50%">
							<div class="8u 12u(mobilep)">
								<input type="email" name="email" id="email" placeholder="Email Address" />
							</div>
							<div class="4u 12u(mobilep)">
								<input type="submit" value="Sign Up" class="fit" />
							</div>
						</div>
					</form> -->

<!-- 				</section> -->

<%@ include file="/front_end/frontEndButtom.file"%>

		</div>


			
			<script src="js/bootstrap.js"></script>

	</body>
</html>


