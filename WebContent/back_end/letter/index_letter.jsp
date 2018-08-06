<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.lettertype.model.*"%>
<%@ page import="java.util.*"%>


<%
	Emp emp = (Emp) session.getAttribute("emp");
	pageContext.setAttribute("emp", emp);
%>

<jsp:useBean id="ltrTypeSvc" scope="page" class="com.lettertype.model.LetterTypeService"/>



<!DOCTYPE html>
<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>寵物 You & Me</title>
<link href="<%=request.getContextPath()%>/back_end/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/nav.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/back_end/css/colorplan.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="<%=request.getContextPath()%>/back_end/css/modern-business.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="<%=request.getContextPath()%>/back_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/back_end/css/backend.css" rel="stylesheet">
</head>

<body>
		<%@ include file="/back_end/backEndNavBar.file"%> 
		<%@ include file="/back_end/backEndLSide.file"%> 

			<div class="col-xs-12 col-sm-8">


				<h5 class="page-header text-right">目前位置:站內信首頁</h5>


				<div class="panel panel-default  text-center">
					<div class="panel-heading">
						<h3 class="panel-title">管理站內信區</h3>						
					</div>
					<div class="panel-body text-left" style="padding-bottom:0px;">
						<a href='#modal-id' data-toggle="modal" class="btn btn-primary">新增種類</a>
							<div class="modal fade" id="modal-id">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<div class="modal-title"><textarea cols="70" id="insname" placeholder="種類名稱......">哈哈</textarea></div>
										</div>
										<div class="modal-body">
											<textarea cols="80" rows="5" id="instext" placeholder="新增內文......">你好</textarea>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary" data-dismiss="modal" id="checkval" >送出新增</button>
										</div>
									</div>
								</div>
							</div>					
					</div>
					<div class="panel-body">
					
						<table border='1' bordercolor='#CCCCFF' width='780' id="typetab">
							<tr >
								<th class="text-center">種類編號</th>
								<th class="text-center">種類名稱</th>
								<th class="text-center">種類內文</th>
								<th class="text-center">按鈕</th>
							</tr>
						<c:forEach var="ltrtype" items="${ltrTypeSvc.getAll()}">
							<tr >
								<td>${ltrtype.letterTypeNo}</td>	
								<td>${ltrtype.letterTypeName}</td>	
								<td>${ltrtype.letterTypeText}</td>	
								<td><input type="button" value="修改" class="btn btn-info" onclick="update(this);" ></td>
							</tr>
						</c:forEach>
							<tr id="insert"></tr>
						</table>
					</div>
				</div>
			</div>

			<script src="https://code.jquery.com/jquery.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
			
			<script type="text/javascript">
			$('#checkval').click(function(){
				if($("#insname").val()==""){
					alert("名稱不能空白");
				}else if($('#instext').val()==""){
					alert("內文不能空白");
				}else{
					insert();
				}
			});
			function insert(){
								
				$.ajax({
					url:"<%=request.getContextPath()%>/back_end/letter/letterType.do",
					data:{
						action : 'insert',
						lettertypename: $('#insname').val(),
						lettertypetext: $('#instext').val()						
					},
					type:'POST',
					dataType : 'text',
					error : function(xhr) {
						alert('Ajax request 發生錯誤');
					},
					success: function(data) {
						var obj = JSON.parse(data);//轉成json格式				
						$('<td>').appendTo($('#insert')).text(obj.curr);
						$('<td>').appendTo($('#insert')).text($("#insname").val());
						$('<td>').appendTo($('#insert')).text($("#instext").val());
						$('<td>').appendTo($('#insert')).attr("id","newbtn");
						$('<input>').attr({"type":"button","value":"修改","class":"btn btn-info","onclick":"update(this);"}).appendTo($('#newbtn'));						
						$('#insert').removeAttr('id');
						$('#newbtn').removeAttr('id');
						$('<tr>').appendTo($('#typetab')).attr("id","insert");						
					}
				});			
			}
			
			function update(e){
				var modupda = $(e).parent(); //td
				//一次只能修改一個
				modupda.parent().prevAll().children().find('input').attr({"class":"btn disabled","onclick":""});
				modupda.parent().nextAll().children().find('input').attr({"class":"btn disabled","onclick":""});
		
				var name = modupda.prev().prev().text();
				var txt = modupda.prev().text();
				if($(e).val()=='修改'){					
					//type Name 的修改
					modupda.prev().prev().text("");
					$('<textarea>').text(name).attr("id","modname").appendTo(modupda.prev().prev());
							
					//type Text 的修改
					modupda.prev().text("");
					$('<textarea>').text(txt).attr("id","modtext").appendTo(modupda.prev());
					
					$(e).val("確定");
					$(e).attr("class","btn btn-success");
					$('<input>').attr({"type":"button","value":"取消","onclick":"cancal(this);","class":"btn btn-warning"}).appendTo(modupda);
					
				}else if($(e).val()=='確定'){
					$.ajax({
						url:"<%=request.getContextPath()%>/back_end/letter/letterType.do",
						data:{
							action : 'update',
							lettertypeno : modupda.prev().prev().prev().text(),
							lettertypename: $('#modname').val(),
							lettertypetext: $('#modtext').val()						
						},
						type : 'POST',
						error : function(xhr) {
							alert('Ajax request 發生錯誤');
							},
						success : function(result) {
							modupda.prev().prev().text($('#modname').val());
							modupda.prev().text($('#modtext').val());
							$('#modname').remove();
							$('#modtext').remove();
							$(e).val('修改');
							$(e).attr("class","btn btn-info");
							$(e).next().remove();	
							modupda.parent().prevAll().children().find('input').attr({"class":"btn btn-info","onclick":"update(this);"});
							modupda.parent().nextAll().children().find('input').attr({"class":"btn btn-info","onclick":"update(this);"});
						}
					});			
				}	
			}
			function cancal(e){
				var modupda = $(e).parent(); //td
				modupda.prev().prev().text($('#modname').text());
				modupda.prev().text($('#modtext').text());
				$('#modname').remove();
				$('#modtext').remove();
				$(e).prev().val('修改');	
				$(e).prev().attr("class","btn btn-info");
				$(e).remove();	
				
				modupda.parent().prevAll().children().find('input').attr({"class":"btn btn-info","onclick":"update(this);"});
				modupda.parent().nextAll().children().find('input').attr({"class":"btn btn-info","onclick":"update(this);"});
			}
			
			</script>
</body>

</html>