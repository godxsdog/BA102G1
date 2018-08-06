<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.album.model.*"%>
<%@ page import="com.albumimg.model.*"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Integer albumNo = (Integer)(request.getAttribute("albumNo"));
	AlbumService albumSvc = new AlbumService();
	MemberService memSvc = new MemberService();
	Member member = (Member) session.getAttribute("member");
	Set<AlbumImg> aImgsSet = albumSvc.getAlbumImgs(albumNo);
	pageContext.setAttribute("aImgsSet", aImgsSet);
%>

<html lang="">

<head>
<title>寵物 You & Me</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<link href="<%=request.getContextPath()%>/front_end/css/bootstrap.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/front_end/css/nav.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/front_end/css/colorplan.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/front_end/css/frontend.css"
	rel="stylesheet" type="text/css">
<!-- Custom CSS -->
<link
	href="<%=request.getContextPath()%>/front_end/css/modern-business.css"
	rel="stylesheet">
<!-- Custom Fonts -->
<link
	href="<%=request.getContextPath()%>/front_end/font-awesome/css/font-awesome.css"
	rel="stylesheet" type="text/css">
<%-- <link href="<%=request.getContextPath() %>/front_end/css/datev.css" rel="stylesheet"> --%>
<link
	href="<%=request.getContextPath()%>/front_end/css/fileinput.min.css"
	media="all" rel="stylesheet" type="text/css" />
<link
	href="<%=request.getContextPath()%>/front_end/css/jquery.fancybox.min.css"
	media="all" rel="stylesheet" type="text/css" />
	
<script src="<%=request.getContextPath()%>/front_end/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/front_end/js/bootstrap.min.js"></script>

<script
	src="<%=request.getContextPath()%>/front_end/js/fileinput.min.js"></script>
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
</script>
<script
	src="<%=request.getContextPath()%>/front_end/js/jquery.fancybox.min.js"></script>



<style>
.portfolio .thumbnail {
	position: relative;
}

.portfolio .thumbnail:hover {
	cursor: pointer;
}

.portfolio .caption {
	bottom: 0;
	position: absolute;
}

.portfolio .btn {
	opacity: 0.75;
}
</style>


<style>
.margin-top {
	margin-top: 70px;
	margin-bottom: 20px;
}

.img-thumbnail {
	margin-bottom: 10px;
}

.jumbotron {
	padding-top: 5px
}

.image-container {
	width: 100%;
}

.image-container img {
	position: relative;
	padding: 0;
	width: 100%;
	border-radius: 8px;
}

.overlap {
	opacity: 0;
	position: absolute;
	top: 5px;
	right: 20px;
}

.image-container:hover .overlap {
	opacity: 1;
}

.dropdownContent {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 100px;
	overflow: auto;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	right: 0;
	z-index: 1;
}

.dropdownContent a {
	color: black;
	text-decoration: none;
	display: block;
	padding: 12px 16px;
}

.dropdown a:hover {
	background-color: #f1f1f1;
}

.dropdown:hover .dropdownContent {
	display: block;
}

.errorMsgs {
	color: red;
}
</style>







</head>

<body>
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<div class="container-fluid">
		<div class="row">

			<div class="col-xs-12 col-sm-2 postion-left-group ">
				<%@ include file="memZoneLSide.file"%>
			</div>

			<div class="col-xs-12 col-sm-8 col-md-offset-1">
				<div class="row">


					<h5 class="page-header text-right">目前位置:會員專區</h5>

					<div class="row">

						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">${member.memId}</h3>
							</div>
							<div class="panel-body">

								<div class="row text-right" style="margin-right: 10px;">
									<button type="button" class="btn btn-primary"
										data-toggle="modal" data-target="#addAlbum">新增相片</button>
									<a href="<%=request.getContextPath() %>/front_end/album/albumShow.jsp">
									<button type="button" class="btn btn-primary"
										onclick="document.getElementById('updateImg${s.index}').submit();">返回相簿</button>
									</a>	
<!-- 									<div style="display:none;"> -->
<!-- 									<form method="post"> -->
<!-- 										<input type="hidden" name="" value=""> -->
<!-- 									</form> -->
<!-- 									</div> -->
								</div>
								




								<!-- Add photo MODAL -->
									<div class="modal fade" id="addAlbum" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true"  style="height:80vh;width:100vw">
									<div class="modal-dialog modal-lg" role="document">
										<div class="modal-content">


											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">
													<b>新增相片</b>
												</h4>
											</div>
											
											<input id="input-20" name="img" type="file"
												class="file-loading" multiple>
										
										</div>
										
									</div>
								</div>














								<div class="row" style="margin-top: 30px; max-width: 100%;">
									<c:if test="${not empty aImgsSet}">
										<ul>
											<c:forEach var="aImg" items="${aImgsSet}" varStatus="s">

												<c:if test="${s.index%4==0}">
													<div class="row">
												</c:if>


												<div class="col-md-3 col-sm-3 col-xs-6">
													<div class="image-container gallery">

														<%
															AlbumImg aImg = (AlbumImg) pageContext.getAttribute("aImg");
														%>

														<c:if
															test="<%=aImg.getImgExtName().startsWith(\"video\")%>">
															<div class="thumbnail">
																<a data-fancybox="gallery" class="fancybox" href="#video${s.index}" rel="gallery" data-caption="<h3>${aImg.imgTitle}</h3><h5>${aImg.imgDesc}</h5>" >
																<video controls class="img-responsive">
																	<source
																		src="<%=request.getContextPath() %>/front_end/album/AVideoReader.do?imgNo=${aImg.imgNo}"
																		type="video/mp4" alt="您的瀏覽器不支援此撥放程式"
																		data-toggle="modal" data-target="#myModal${s.index}">
																</video>
																<div class="caption">
																	<div class="clearfix"></div>
																</div>
																</a>
																<div style="display: none;max-height: 600px;max-width: 400px;" id="video${s.index}">
																<video controls style="max-height: 80%;max-width: 80%;margin: auto;">
																	<source 
																	src="<%=request.getContextPath() %>/front_end/album/AVideoReader.do?imgNo=${aImg.imgNo}" 
<%-- 																		src="${aImg.imgFile}" --%>
																	type="video/mp4">
																 </video>
																</div>
															</div>
														</c:if>


														<c:if
															test="<%=aImg.getImgExtName().startsWith(\"image\")%>">
															<div class="thumbnail">
																<a data-fancybox="gallery"  class="fancybox" rel="gallery" data-caption="<h3>${aImg.imgTitle}</h3><h5>${aImg.imgDesc}</h5>"  href="<%=request.getContextPath() %>/front_end/album/AImgReader.do?imgNo=${aImg.imgNo}">
																<img class="img-responsive" 
																	src="<%=request.getContextPath() %>/front_end/album/AImgReader.do?imgNo=${aImg.imgNo}"
																	alt="The awesome description">														
																</a>					
															</div>
														</c:if>


														<!--新增  修改 編輯dropdown -->
														<div class="overlap dropdown">
															<button class="btn btn-default btn-xs" type="submit"
																class='dropbtn'>
																<i class="fa fa-cog" aria-hidden="true"></i>
															</button>
															<div class='dropdownContent'
																id='dropdownContent${s.index}'>
																<form id="update${s.index}"
																	action="<%=request.getContextPath()%>/front_end/album/Album.do"
																	method="post">
																	<input type='hidden' name='action' value='changeCover'>
																	<input type='hidden' name='imgNo' value='${aImg.imgNo}'>
																	<input type='hidden' name='albumNo'
																		value='${aImg.albumNo}'> <a href='#'
																		id='setCover'
																		onclick="document.getElementById('update${s.index}').submit();">設成封面</a>
																</form>


																<%-- 																<form id="updateImg${s.index}" --%>
																<%-- 																	action="<%=request.getContextPath()%>/front_end/album/Album.do" --%>
																<!-- 																	method="post"> -->
																<!-- 																	<input type='hidden' name='action' value='updateImg'> -->
																<%-- 																	<input type='hidden' name='imgNo' value='${aImg.imgNo}'> --%>
																<%-- 																	<input type='hidden' name='albumNo' value='${aImg.albumNo}'>  --%>
																<a href='#' data-toggle="modal"
																	data-target="#updateModal${s.index}">編輯相片</a>
																<!-- 																</form> -->


																<form id="delete${s.index}"
																	action="<%=request.getContextPath()%>/front_end/album/Album.do"
																	method="post">
																	<input type='hidden' name='action' value='deleteImg'>
																	<input type='hidden' name='imgNo' value='${aImg.imgNo}'>
																	<input type='hidden' name='albumNo'
																		value='${aImg.albumNo}'> <a href='#'
																		data-toggle="modal"
																		data-target="#deleteModal${s.index}">刪除相片</a>
																</form>
															</div>
														</div>
													</div>
												</div>

												<c:if test="${s.index%4==3}">
								</div>
								</c:if>



<!-- 								修改Modal -->
								<div class="modal fade" id="updateModal${s.index}" role="dialog">
									<div class="modal-dialog modal-lg">

										Modal content
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">編輯相片</h4>
											</div>
											<div class="modal-body">
												<form id="updateImg${s.index}" method="post"
													action="<%=request.getContextPath()%>/front_end/album/Album.do"
													method="post">
													<input type='hidden' name='action' value='updateImg'>
													<input type='hidden' name='imgNo' value='${aImg.imgNo}'>
													<input type='hidden' name='albumNo' value='${aImg.albumNo}'>
													<input type="text" class="form-control" name="imgTitle"
														id="imgTitle" placeholder="為您的照片輸入標題吧!" style="margin-top:15px" value="${aImg.imgTitle}" required/>
													<textarea class="form-control" id="imgDesc" name="imgDesc"
														placeholder="為您的照片輸入點故事吧!" style="margin-top:15px" required>${aImg.imgDesc}</textarea>
													<c:if test="<%=aImg.getImgExtName().startsWith(\"video\")%>">
																<video controls class="img-responsive" style="margin-top:15px">
																	<source src="<%=request.getContextPath() %>/front_end/album/AVideoReader.do?imgNo=${aImg.imgNo}"
																		type="video/mp4" alt="您的瀏覽器不支援此撥放程式" >
																</video>
													</c:if>
													<c:if test="<%=aImg.getImgExtName().startsWith(\"image\")%>">
													
													<img class="img-responsive" style="margin-top:10px" src="<%=request.getContextPath() %>/front_end/album/AImgReader.do?imgNo=${aImg.imgNo}" alt="The awesome description">
													</c:if>											
												</form>
											</div>
											<div class="modal-footer">

												<button type="button" class="btn btn-primary"
													data-dismiss="modal" id='deletebtn'
													onclick="document.getElementById('updateImg${s.index}').submit();">修改</button>
												<button type="button" class="btn btn-warning"
													data-dismiss="modal">取消</button>
											</div>

										</div>

									</div>
								</div>











								<!-- 刪除Modal-->
								<div class="modal fade" id="deleteModal${s.index}" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">刪除相片</h4>
											</div>
											<div class="modal-body">
												<p>刪除相片後將無法復原，確定刪除嗎?</p>
											</div>
											<div class="modal-footer">
												
												
												<button type="button" class="btn btn-primary"
													data-dismiss="modal" id='deletebtn'
													onclick="document.getElementById('delete${s.index}').submit();">刪除</button>
												
												<button type="button" class="btn btn-warning"
													data-dismiss="modal">取消</button>


											</div>
										</div>

									</div>
								</div>








								<!--瀏覽相片Modal -->
								<div class="modal fade" id="myModal${s.index}" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">${aImg.imgTitle}</h4>
												<h5 class="modal-title" id="myModalLabel">${aImg.imgDesc}</h4>
											</div>
											<div class="modal-body">
												<c:if test="<%=aImg.getImgExtName().startsWith(\"video\")%>">
													<video controls class="img-responsive">
														<source src="<%=request.getContextPath() %>/front_end/album/AVideoReader.do?imgNo=${aImg.imgNo}" type="video/mp4" alt="您的瀏覽器不支援此撥放程式" >
													</video>
												</c:if>
												<c:if test="<%=aImg.getImgExtName().startsWith(\"image\")%>">
													<img class="img-responsive" src="<%=request.getContextPath() %>/front_end/album/AImgReader.do?imgNo=${aImg.imgNo}"
													alt="The awesome description">
												</c:if>
											</div>
										</div>
									</div>
								</div>

								</c:forEach>
								</ul>
								</c:if>
							</div>




						</div>

					</div>
				</div>
			</div>










			


		</div>
	</div>
<%@ include file="/front_end/frontEndButtom.file"%>

	<script>
		$("#input-20").fileinput({
		    uploadUrl: "<%=request.getContextPath()%>/front_end/album/AImgUpload.do?albumNo=<%=albumNo%>", // server upload action
		    uploadAsync: true,
		    maxFileCount: 10,
		    showBrowse: false,
		    browseOnZoneClick: true,
		    allowedFileTypes: ['image','video']
		});
		
		$('#input-20').on('filebatchuploadcomplete', function (event, data, previewId, index) {
			top.location.href="<%=request.getContextPath()%>/front_end/album/Album.do?albumNo=<%=albumNo%>&action=getUserAlbum";});
	
		
		
// 		$(document).ready(function() {
// 			$(".fancybox").fancybox();
// 			$(".fancybox").fancybox({
// 				helpers : { 
// 					    title : { type : 'inside' }
// 					   }, // helpers
	   
// 			beforeLoad: function() {
// 		        	console.log("This:"+$(this.element));
// 		        	console.log('title:'+$(this.element).attr('title'));
// 		        	console.log('Caption:'+$(this.element).attr('caption'));
// 		            this.title = $(this.element).attr('title') + " - " + $(this.element).attr('data-caption');
// 		        }
// 		    });
// 			console.log("test");
// 		}); // ready
		
		
		
		$(document).ready(function() {
			 $(".fancybox").fancybox({
			  afterLoad: function(){
			   this.title = $(this.element).next('.newTitle').html();
			  },
			  helpers: {
			   title : {
			    type : 'inside'
			   }
			  }
			 }); // fancybox
			 console.log($(this.element).next('.newTitle').html());
			}); // ready
		
		
	</script>



</body>

</html>