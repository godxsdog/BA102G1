<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.dateitem.model.*"%>
<%@ page import="com.restaurant.model.*" %>
<%@ page import="com.member.model.*" %>
<%@ page import="java.util.*"%>

<style>

 tilt{
transition: 1s ease;
}

.grow img, tilt:hover{
-webkit-transform: scale(1.3);
-ms-transform: scale(1.3);
transform: scale(1.3);
transition: 1s ease;
}

@font-face {
    font-family: DJB;
    src: url(../fonts/DJB.ttf);
    font-weight: normal;
    font-style: normal;
}

#fixedbutton-talk {
  position: fixed;
  bottom: 3%;
  right: 5%;
}

blockquote>p {
position:relative;
left:1rem;
padding-right:4rem;
}
.fortest button{
margin-left:1%;
float:right;
}
.fortest .a{
margin-left:1%;
float:right;
}
.btn-circle {
  display:block;
  height: 20px;
  width: 20px;
  border-radius: 50%;
  border: 1px solid grey;
  
}

.syotimer{
	font-family: 'DJB';
	padding:3px;
    text-align: center;
 	right:10px;
    margin: 0px auto 0;
    padding: 0 0 10px;
 
/*     border-bottom: 2px solid #80a3ca; */
}
.syotimer .syotimer-cell{
    display: inline-block;
    margin: 0 5px;
 
    width: 100px;
/*      background: url(../images/timer.png) no-repeat 0 0;  */
	background-color:#6fffd5;

}
.syotimer .syotimer-cell .syotimer-cell_value{
    font-size: 50px;
    color: #80a3ca;
 	font-family: 'DJB';
    height: 100px;
    line-height: 100px;
 
    margin: 0 0 5px;
}
.syotimer .syotimer-cell {
    font-family: 'DJB';
    font-size: 20px;
    text-transform: uppercase;
}





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




</style>

<%@ include file="header.file"%>




<jsp:useBean id = "dSvc" scope="page" class="com.dateitem.model.DateItemService" />
<jsp:useBean id = "memSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id = "restSvc" scope="page" class="com.restaurant.model.RestaurantService"/>
<jsp:useBean id = "pSvc" scope="page" class="com.pet.model.PetService"/>
<jsp:useBean id = "msgSvc" scope="page" class="com.msg.model.MsgService"/>
<%
	Member member = (Member) session.getAttribute("member");
	try{
	int memNo= member.getMemNo();
	}catch(Exception e){		};
    List<DateItemVO> list = dSvc.getAllWithOutImg();
    pageContext.setAttribute("list",list);
%>





<head><title>約會首頁</title>
<%-- <link href="<%=request.getContextPath() %>/front_end/css/modern-business.css" rel="stylesheet" type="text/css"> --%>
</head>
<body bgcolor='white'>



<%@ include file="/front_end/frontEndNavBar.file"%>
<%@ include file="sidelist.file"%>

<%-- <%@ include file="page3.file"%>	 --%>

<!-- JSP以一個包含格線的div開始, 但是結束的</div>寫在footer裡面 -->

<div class="col-xd-12 col-sm-10  main-page-show">
<div class="row">

			<div class="col-xd-12 col-sm-3"> 
			<a href="#" class="list-group-item"
				data-toggle="collapse" data-target="#search" data-parent="#menu">約會商品查詢
					<span class="glyphicon glyphicon-triangle-bottom pull-right"></span>
			</a>
				<div id="search" class="sublinks collapse">
					<a href="#" class="list-group-item small" data-toggle="modal"
						data-target="#searchSpec">以條件搜尋 </a>		
					<a class="list-group-item small"
						href='<%=request.getContextPath()%>/front_end/dateitem/googleMapQuery.jsp'>以地圖搜尋</a>
				</div>
			</div>

			<div class="col-xd-12 col-sm-2"> 
				<button class="btn btn-lg btn-default" id="button1" value="showppl">以主人顯示約會</button>
			</div>


	


			<!-- 複合查詢Modal content-->
			<div class="modal fade" id="searchSpec" role="dialog">
				<div class="modal-dialog">

					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">查詢約會商品</h4>
						</div>
						<div class="modal-body">

							<Form
								action="<%=request.getContextPath()%>/front_end/dateitem/dateitem.do"
								method="post">
								<table>
									<input type="hidden" name="action"
										value="listDItems_ByCompositeQuery">

									<tr>
										<td class="title">約會日期</td>
										<td><input type="text" id="dateMeetingTime"
											name="dateMeetingTime"></td>
									</tr>

									<tr>
										<td class="title">會員暱稱</td>
										<td><input type="text" id="memSname"
											name="memSname"></td>
									</tr>
									
									<tr>
										<td class="title">寵物姓名</td>
										<td><input type="text" id="petName"
											name="petName"></td>
									</tr>
									

									<tr>
										<td class="title">會員性別:</td>
										<td>
											<div class="select-style">
												<select class="filter" name="memGender">
													<option value="" disabled selected>請選擇主人性別</option>
													<option value="">皆可</option>
													<option value="0">男</option>
													<option value="1">女</option>
													<option value="2">不願透露</option>
												</select>
											</div>
										</td>
									</tr>

									<tr>
										<td class="title">寵物性別</td>
										<td>
											<div class="select-style">
												<select class="filter" name="petKind">
													<option value="" disabled selected>請選擇寵物</option>
													<option value="">皆可</option>
													<option value="狗">狗</option>
													<option value="貓">貓</option>
													<option value="其他">其他</option>
												</select>
											</div>
										</td>
									</tr>

								</table>

								<input type="submit" value="查詢" class="btn btn-primary">

							</Form>
						</div>


					</div>

				</div>
			</div>


 </div>
 
<%@ include file="pages/page1.file" %> 
  <div> 
  <c:forEach var="dateitem" items="${list}" varStatus="s" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
          <c:if test="${s.index%3==0}"> 
			<div class="row">
			</c:if>
          
          <div class="col-sm-4 ">
            <div class="bg-color">
            <div class="card hovercard">
                <div class="cardheader tilt2 " style="background-image:url('ImgReader?dateItemNo=${dateitem.dateItemNo}&action=dateImg');">

                <input  type="hidden" value="${dateitem.sellerNo}">
                <input class="no1" type="hidden" value="${dateitem.dateItemNo}">

                </div>
                <div class="avatar grow">
                	 <input  type="hidden" value="${dateitem.sellerNo}">
                    <img class="img1 tilt" src="ImgReader?sellerNo=${dateitem.sellerNo}&action=memImg">
                    <input  type="hidden" value="${dateitem.dateItemNo}">
  
                </div>
                <div class="info">
                    <div class="title dateDes">
                        <a class="dateDes" target="_blank" href="">${dateitem.dateItemTitle}</a>
                    </div>
                    <div class="desc">${memSvc.getOneMember(dateitem.sellerNo).getMemSname()}</div>
                    <div class="desc">${dSvc.getTimeForItem(dateitem.dateMeetingTime)}</div>
                    <div class="desc">${dateitem.dateItemLocate}</div>
                  
                </div>
                <div class="bottom">
                    <a class="btn btn-info"  data-toggle="modal" data-target="#modal-detail${dateitem.dateItemNo}" href="">
                        詳情
                    </a>
                </div>
                </div>
            </div>

        </div>
 
<!--  商品明細的跳窗 -->

<div id="modal-detail${dateitem.dateItemNo}" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">


    <!-- Modal content-->
    <div class="modal-content">
    <div id="simple-timer${dateItem.dateItemNo}" class="simple-timer"  style="color:black;" ></div>
    <input type="hidden" class="stime" value="${dateitem.dateMeetingTime}"/>
    
      <div class="modal-header">
  
  <div class ="main-container">

<div class=" highlight">
 <a href="" 
type="button" class = "btn btn-xs btn-basic pull-left btn-circle" data-toggle="modal" data-target="#rep${dateitem.dateItemNo}" >檢舉</a>

<h1><b>${dateitem.dateItemTitle}</b></h1>

	<div class="row">
  
  
  
		<div id="cc">
        <img id="memphoto-detail${dateitem.dateItemNo}"src ="ImgReader?sellerNo=${dateitem.sellerNo}&action=memImg" />
      
        </div>
        <ul>
        
        
            <li> <h2><a href="" style="color:white;">
            ${memSvc.getOneMember(dateitem.sellerNo).getMemSname()} && ${pSvc.getOnePet(dateitem.petNo).getPetName()}
            </a></h2></li>
            <li>餐廳:${restSvc.getOneRest(dateitem.restListNo).getRestName()} </li>
            <li>${dSvc.getTimeForItem(dateitem.dateMeetingTime)}</li>
            <li>參加人數上限:${dateitem.dateItemPeople}人  - 
            	寵物主人攜伴:
            	<c:if test="${dateitem.hasMate==true}">有</c:if>
            	<c:if test="${dateitem.hasMate==false}">沒有</c:if>           	
            	</li>
            <li><h3 class="cost"><span class="glyphicon glyphicon-usd"></span> ${dateitem.dateItemPrice} </h3></li>
           
        </ul>
        
        <div class="row">
        <blockquote >
		<p >${dateitem.dateItemText}</p>
<!-- 		<footer> -->
<%-- 		 <cite>人氣:${dateitem.dateItemViewer}</cite> --%>
<!-- 		</footer> -->
		</blockquote>
        
        </div>
        </div>        
        <button class = "btn btn-warning" data-dismiss="modal">回上一頁</button>
        
<!--         //限制自己不能買自己的約會 -->
		<c:if test="${ member.getMemNo()!=null}">
       <c:if test="${dateitem.sellerNo!=member.getMemNo()}" > 
      
       <a href="" 
       type="button" onclick="goajax('${dateitem.dateItemNo}')" data-toggle="modal" data-target="#confirm${dateitem.dateItemNo}" class="check btn btn-primary">預約</a> 
       </c:if>
       </c:if>
       <input type="hidden" value="${dateItem.dateItemPrice}"/>
        
    </div>
    
    <BR>
	</div>
	
	 
</div>
</div>
      </div>
    </div>

<!-- 確認付款與檢查儲值modal -->

 <div id="confirm${dateitem.dateItemNo}" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content fortest">
    
      <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">預約約會</h4>
      </div>
      <div class="modal-body" >
        <p></p>
        <p id="textshow${dateitem.dateItemNo}">價格$${dateitem.dateItemPrice}, 確定預約此約會?</p>
      </div>
      <div class="modal-footer">
      <button id="" type="button" class="btn btn-warning" data-dismiss="modal">回上一頁</button>
       <a href="<%=request.getContextPath() %>/front_end/dateitem/dateitem.do?action=buy_date&dateItemNo=${dateitem.dateItemNo}" 
       type="button" id="checkPrice${dateitem.dateItemNo}" class="check btn btn-primary">確定約會</a>
       <input type="hidden" value="${dateItem.dateItemPrice}"/>
        
      </div>
    </div>

  </div>
  </div>
 
 <!-- 檢舉的modal -->

 <div id="rep${dateitem.dateItemNo}" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content fortest">
      <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">檢舉</h4>
      </div>
      <form action="<%=request.getContextPath() %>/back_end/apprec/dateitemrep.do" method="post" >
      <div class="modal-body" >
        <p></p>
        
		
        <input type="text" class="form-control" name="repText" id="repText${dateitem.dateItemNo}"/>
        <p>請輸入檢舉原因 </p>
      </div>
      <div class="modal-footer">
      <button id="" type="button" class="btn btn-warning" data-dismiss="modal">回上一頁</button>
       <input type="submit" id="sendRep${dateitem.dateItemNo}" class="check btn btn-primary" value="確定送出">
       <input type="hidden" name="dateItemNo" value="${dateitem.dateItemNo}"/>
       
        <input type="hidden" name="whichPage" value="<%=whichPage %>"/>
        <input type="hidden" name="action" value="insert"/>
      </div>
      </form>
    </div>

  </div>
  </div>
 
 
 			<c:if test="${s.index%3==2}"> 
				</div>
			 </c:if>
 
 </c:forEach>

 </div>       

<%@ include file="pages/pageLatest.file" %> 

 </div>  
    

 
<%@ include file="/front_end/frontEndButtom.file"%>
<%@ include file="chat.file"%>


</div> 

<script>

<!--進場動畫-->
$(document).ready(function () {
    AnimateRotate();
    AnimateRotate2();
});

function AnimateRotate(d){

    $({deg: -25}).animate({deg: 0}, {
        duration: 500,
        step: function(now){
            $('.tilt').css({
                 transform: "rotate(" + now + "deg)"
            });
        }
    });
}

function AnimateRotate2(d){

    $({deg: 1}).animate({deg: 0}, {
        duration: 100,
        step: function(now){
            $('.tilt2').css({
                 transform: "rotate(" + now + "deg)"
            });
        }
    });
}










<!--檢查儲值 -->
function goajax(dateItemNo){
// 		alert(dateItemNo);
	    $.ajax({ 
	        url:  'dateitem.do?action=checkCharge',
	        type: 'POST',
	        data: { dateItemNo:dateItemNo,
			},
	        success: function(response){
// 	        	alert('success');
	        	if(response.length<4){
// 	        	alert('enough');					
	        	}else{
	        		
// 	 =================餘額不足,把按鈕改為儲值=====================
// 	        		alert('not enough');
  	        		$('#textshow'+dateItemNo).html('<h1>餘額不足請儲值<h1>');	
  	        		$('#confirm'+dateItemNo).modal('show');
  	        		$('#checkPrice'+dateItemNo).text('前往儲值');
  	        		$('#checkPrice'+dateItemNo).attr("href", "<%=request.getContextPath() %>/front_end/charge/chargePage.jsp?from=dateitem")
	        	}
	        },
	        error: function(){
	            alert("error");
	        }  
	    });  	
	};
$(document).ready(function(){

	$('.simple-timer').each(function(){
		var timeStr=$(this).next('input').val().trim();
		
		var yearStr = parseInt(timeStr.substring(0,4));
	
		var monthStr = parseInt(timeStr.substring(5,7));
		
		var dayStr = parseInt(timeStr.substring(8,10));
		
		var hourStr = parseInt(timeStr.substring(11,13));
		var minuteStr= parseInt(timeStr.substring(14,16));
		$(this).syotimer({
		    year: yearStr,
		    month: monthStr,
		    day: dayStr,
		    hour: hourStr,
		    minute: minuteStr,
		    effectType: 'opacity',
		    lang: 'eng'
		});		
	});



// 	$('#simple-timer').syotimer({
// 	    year: 2017,
// 	    month: 8,
// 	    day: 31,
// 	    hour: 20,
// 	    minute: 48,
// 	    effectType: 'opacity',
// 	});		
	
	
	
	
		var but1 = $('#button1');
		but1.click(function() {
			if(this.value == 'showppl'){
			but1.val('showpet');
			but1.html('以寵物顯示約會')
			
		
			$.ajax({
				url : 'dateitem.do',
				data : {
					action : this.value,
				},
				type : 'POST',
				error : function(xhr) {
					alert('Ajax request 發生錯誤');
				},
				success : function(result) {
// 					alert('sucess1');
					$('.cardheader').each(function(index){
// 						$(this).attr('style', "background-image: url("'ImgReader?sellerNo='+$(this).next('input').val()+'&action=memImg'")");
						$(this).css('background-image','url("ImgReader?sellerNo='+$(this).find('input').val()+'&action=memImg")');
					});
					$('.img1').each(function(index){
						$(this).attr('src','ImgReader?dateItemNo='+$(this).next('input').val()+'&action=dateImg');
					});
// 					$('.avatar').find('img').attr('src','ImgReader?dateItemNo=${dateitem.dateItemNo}&action=dateImg');
				}
			});
			this.value = "showpet";
			
			}else if(this.value == 'showpet'){
				but1.val('showppl');
				but1.html('以主人顯示約會')
				$.ajax({
					url : 'dateitem.do',
					data : {
						action : this.value,
					},
					type : 'POST',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
					},
					success : function(result) {
// 						alert('sucess2');
						$('.cardheader').each(function(index){
//	 						$(this).attr('style', "background-image: url("'ImgReader?sellerNo='+$(this).next('input').val()+'&action=memImg'")");
							$(this).css('background-image','url("ImgReader?dateItemNo='+$(this).find('.no1').val()+'&action=dateImg")');
						});
						$('.img1').each(function(index){
							$(this).attr('src','ImgReader?sellerNo='+$(this).prev('input').val()+'&action=memImg');
						});
//	 					$('.avatar').find('img').attr('src','ImgReader?dateItemNo=${dateitem.dateItemNo}&action=dateImg');
					}
				});
				this.value = "showppl";
			} 
			
			
			
			
		});
});


</script>
		<script>
			$(function() {

				$("#dateMeetingTime").datetimepicker({
					format : 'Y-m-d',
					timepicker : false,
					mindate : 0,
				});

			});
		</script>
</body>

</html>
