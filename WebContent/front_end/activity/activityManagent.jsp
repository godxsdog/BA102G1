<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.restMember.model.RestMember"%>
<%@page import="java.util.*"%>
<%@page import="com.activity.model.ActivityService"%>
<%@ page import="com.activity.model.Activity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
	if(session.getAttribute("restMember")==null){
		request.getRequestDispatcher("/front_end/restMember/restMemberLogin.jsp").forward(request, response);
	}
	RestMember restMember = (RestMember)session.getAttribute("restMember");
	Activity activity = (Activity)request.getAttribute("activity");
	
// 	session.setAttribute("activity", activity);
	
	ActivityService activityService = new ActivityService();
	List<Activity> activitiyList = activityService.getAllById(restMember.getRestMemId());
	request.setAttribute("activitiyList", activitiyList);
	
			
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
	String actDateMin  = simpleDateFormat.format(new Date());
%>
<!DOCTYPE html>
<html lang="">
<title>寵物 You & Me</title>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
<head>

    	
    <!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	
	 
	
    <style type="text/css">
    	.img-bg{
    	max-width:100%;
    	}
    
			.btn123{
				margin-top: 10px;
			}
			
	</style>  
</head>

<body background="<%=request.getContextPath()%>/front_end/actFiles/11.jpg"  style="background-size:cover;">
<%@ include file="/front_end/actFiles/restMemberNavBar2.file" %>

    <%@ include file="/front_end/actFiles/restMemberNavBar.file" %>
	<%@ include file="/front_end/actFiles/overflow.file" %>
	
	
    <div class="container-fluid">
        <div class="row">
        	<%@ include file="/front_end/actFiles/restMemberList1.file" %>
        <div class="col-xs-12 col-sm-8">       
   
    <div class="panel-group col-sm-offset-2 col-sm-8">
    
    
		<h5 class=" page-header text-right">目前位置:管理我的活動</h5>
		
<!--      目前跌帶(紀數用)varStatus="activitiyE"  間隔 step="1"   從0開始${activitiyE.index}    從1開始${activitiyE.count} -->
		<%@ include file="/front_end/actFiles/pageActManagement.file" %>
			<c:forEach var="activity" items="${activitiyList}" varStatus="activitiyE" step="1" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			 <div class="panel panel-default">
		       <div class="panel-heading">
		       	
				
						<div class="row">
							<div class="col-xs-12 col-sm-6">
								<h3 class="panel-title">活動名稱:${activity.actName}</h3>
								<c:if test="${not empty updateErr }">
									${updateErr}
								</c:if>
							</div>
							<div class="col-xs-12 col-sm-6 text-right ">
								
									<footer>
										<c:if test="${activity.actStatus=='0'}"><font color="red">活動發起待審核</font></c:if>
										<c:if test="${activity.actStatus=='1'}"><font color="red">審核未通過</font></c:if>
										<c:if test="${activity.actStatus=='2'}"><font color="green">審核通過</font></c:if>	
										<c:if test="${activity.actStatus=='3'}"><font color="red">餐廳取消活動</font></c:if>
									</footer>
								
							</div>
						</div>
					
		       </div>
		       
			       <div class="col-xs-12 col-sm-4">
						<img src="<%=request.getContextPath() %>/activity/DBGifReader5?actNo=${activity.actNo}" class="img-responsive" >
					</div>
				
					
					
					<div class="col-xs-12 col-sm-8 ">
						<textarea class="overflow form-control" rows="8" cols="40"  style="resize:none;border:0px;background-color:white;font-weight:bold;" readonly>${activity.actContent}</textarea>
						
					</div>
					
					
					
				
			<form method="post" action="<%=request.getContextPath()%>/activity/activityController" enctype="multipart/form-data">
			
		       <div class="row">
					<div class="col-xs-12 col-sm-12">
						<div class="panel-body text-right" >
		         			<a href='#${activity.actNo}' data-toggle="modal" class="btn btn-primary">活動詳情</a>
								<div class="modal fade" id="${activity.actNo }">
									<div class="modal-dialog modal-lg">
										<div class="modal-content">
											
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title text-center">活動詳情</h4>
					</div>
			
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-sm-3">
									
											<script>
												$(function() {
													
													$("#actDate${activitiyE.index}").datetimepicker({
													format: 'Y-m-d',
													 minDate:'+1970/01/02',
													 allowTimes:[
											'11:00','11:30', '12:00', '12:30','13:00', '13:30','14:00','14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
													  '19:30', '20:00', '21:00', '22:00', '23:00'
													 ],
													 defaultDate: '17/09/01',
											// 		 step: 30 
													timepicker:false,
													             });
													             
													             
													 $("#actFDate${activitiyE.index}").datetimepicker({
													format: 'Y-m-d',
													 minDate:'+1970/01/02',
													 allowTimes:[
											'11:00','11:30', '12:00', '12:30','13:00', '13:30','14:00','14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00',
													  '19:30', '20:00', '21:00', '22:00', '23:00'
													 ],
													 defaultDate: '17/09/01',
											// 		 step: 30 
														timepicker:false,
													             });            	
													
													
										       });
											</script> 
									
											
											<script>
											
							
												var openFile${activitiyE.index} = function(e) {
												    var input = e.target;		
												    var reader = new FileReader();		    
												    reader.onload = function(){
													      var dateURL = reader.result;	      
														  var output = document.getElementById('outputIMG${activitiyE.index}');			    
														  output.src = dateURL;			    
												    };		    
												    reader.readAsDataURL(input.files[0]);		   
												};
												
											</script>
											
											<input type="hidden" name="actNo" value="${activity.actNo}">
											<input type="hidden" name="restMemId" value="${restMember.restMemId }">
											<input type="hidden" name="actStatus" value="${activity.actStatus }">
									
											<div class="form-group">
												<div class="col-xs-12 col-sm-12">
													<img src="<%=request.getContextPath()%>/activity/DBGifReader5?actNo=${activity.actNo}" 
													class="img-circle" id="outputIMG${activitiyE.index}" height="200px" width="100%" style="margin-top:50px;">
													
													<input type="file" name="actInitImg" class="form-control"
													 onchange="openFile${activitiyE.index}(event)">
													
												</div>
											</div>
							</div>	
								
							<div class="col-sm-6">	
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													活動名稱
												</label>
												<div class="col-sm-9">
													<input type="text" name="actName" class="form-control" value="${activity.actName }">	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													活動內容
												</label>
												<div class="col-sm-9">
													<textarea name="actContent" rows="3" cols="47" class="overflow form-control" style="resize:none">${activity.actContent }</textarea>	
												</div>
											</div>
										</div>	
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													活動日期
												</label>
												<div class="col-sm-9">
													<input type="text" name="actDate" id="actDate${activitiyE.index}" class="form-control" value="${activity.actDate }"
															min="<%=actDateMin %>">	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													報名截止日期
												</label>
												<div class="col-sm-9">
													<input type="text" name="actFDate" id="actFDate${activitiyE.index}" class="form-control" value="${activity.actFDate}"
													   min="<%=actDateMin %>">	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													人數上限
												</label>
												<div class="col-sm-9">
													<input type="text" name="actULimit" class="form-control" value="${activity.actULimit }">	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													人數下限
												</label>
												<div class="col-sm-9">
													<input type="text" name="actLLimit" class="form-control" value="${activity.actLLimit }">	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group">
												<label class="col-sm-3 control-label">
													種類限制
												</label>
												<div class="col-sm-9">
													<select id="testModal" name="actKind" class="form-control">
														
															<option value="0" ${(activity.actKind=="0")?'selected':''}>貓</option>
									  						<option value="1" ${(activity.actKind=="1")?'selected':''}>狗</option>
									  						<option value="2" ${(activity.actKind=="2")?'selected':''}>其他</option>
														
													</select>	
												</div>
											</div>
										</div>
										<div class="row">	
											<div class="form-group" id="aKindModal" style='display:'>
												<label class="col-sm-3 control-label">
													其他寵物種類
												</label>
												<div class="col-sm-9">
													<input type="text" name="actAnotherKind" class="form-control" 
													value="${activity.actAnotherKind}">	
												</div>
											</div>
										</div>
											
											<input type="hidden" name="action" value="updateActivity">
									</div>
								</div>		
							</div>
						</div>
								
				
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">更改資料</button>
				<button type="button" class="btn btn-warning" data-dismiss="modal">關閉</button>
				
			</div>
												
						</div>
					</div>
				</div>	         			
		   </div>
		</div>	
	
				
				
				
			</form>	
			
			
				
		     </div>
		     
			</c:forEach>
        <%@ include file="/front_end/actFiles/pageLatest.file" %>
		       </div>
		     </div>
        </div>
    </div>



    
<%@ include file="/front_end/frontEndButtomFixed.file" %>        
 <script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>     
    
    
</body>

</html>