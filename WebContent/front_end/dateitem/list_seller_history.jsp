<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.dateitem.model.*"%>
<%@ page import="com.restaurant.model.*" %>
<%@ page import="com.member.model.*" %>
<%@ page import="java.util.*"%>

<%@ include file="header.file"%>

<jsp:useBean id = "dSvc" scope="page" class="com.dateitem.model.DateItemService" />
<jsp:useBean id = "memSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id = "restSvc" scope="page" class="com.restaurant.model.RestaurantService"/>
<jsp:useBean id = "pSvc" scope="page" class="com.pet.model.PetService"/>

<%
Member member = (Member) session.getAttribute("member");
List<DateItemVO> list = dSvc.findBySeller_history(member.getMemNo());
pageContext.setAttribute("list",list);
%>




<head><title>賣家紀錄</title></head>
<body bgcolor='white'>

<%@ include file="/front_end/frontEndNavBar.file"%>
<%@ include file="sidelist.file"%>

<!-- JSP以一個包含格線的div開始, 但是結束的</div>寫在footer裡面 -->

<div class="col-xd-12 col-sm-8 col-sm-offset-1 main-page-show">

					<ul class="list-inline amos">
					<li class="text-sucess"><b><a href="<%=request.getContextPath() %>/front_end/dateitem/list_seller_future.jsp">進行中的約會</a></b></li>
					<li class="text-sucess"><b><a href="<%=request.getContextPath() %>/front_end/dateitem/list_seller_onsale.jsp">上架中的約會</a></b></li>
					<li class="text-sucess"><b><a href="<%=request.getContextPath() %>/front_end/dateitem/list_seller_history.jsp">過去的約會資料</a></b></li>
					</ul>
  
   <c:if test="${empty list}"> 
   <h1>您沒有任何約會紀錄!</h1> 
<%--    <c:remove var="itemPurchased" scope="request"/> --%>
   </c:if> 
   <c:if test="${not empty itemCanceled}">
  <h1>您已取消一筆約會，寫入您的紀錄!</h1>
   <c:remove var="itemCanceled" scope="request"/>
  </c:if>
  
  
     <div class="row">
        <div class="col-sm-12">
            <table class="table table-hover">
                <thead>
                    <tr>
                    	
                        <th>約會</th>
                        <th></th>
                        <th>時間</th>
                        <th class="text-center">餐廳</th>
                        <th class="text-center"></th>
                        <th></th>
                    </tr>
                </thead>
				<tobdy>
	
<c:forEach var="dateitem" items="${list}" >

<tr>
                        <td class="col-sm-5">
                        <div class="media">
                            <a class="thumbnail pull-left" > <img class="media-object" src="ImgReader?dateItemNo=${dateitem.dateItemNo}&action=dateImg" style="width: 100px; height: 100px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading"><strong>${dateitem.dateItemTitle}</strong></h4>
                                <h6 class="media-heading"><small> by </small> <a href="#"><small>${memSvc.getOneMember(dateitem.sellerNo).getMemSname()}</small></a></h6>
                            </div>
                        </div></td>
                        <td class="col-sm-1 "></td>
                        <td class="col-sm-1 " style="text-align: center">
                        ${dSvc.getTimeForItem(dateitem.dateMeetingTime)}
                        </td>
                       	
                        <td class="col-sm-3  text-center"><strong>${restSvc.getOneRest(dateitem.restListNo).getRestName()}</strong></td>
                        <td class="col-sm-2 ">
							<c:if test="${dateitem.dateItemStatus==3}">
  							<span class="text-success"><small>交易完成</small></span>
							</c:if>
							<c:if test="${dateitem.dateItemStatus==2}">
  							<span class="text-warning"><small>交易取消</small></span>
							</c:if>
                        
                    </tr>
 </c:forEach>

                   <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td class="text-center">共有約會</td>
                        <td class="text-left"><%=list.size()%>筆</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/front_end/frontEndButtomFixed.file"%>





