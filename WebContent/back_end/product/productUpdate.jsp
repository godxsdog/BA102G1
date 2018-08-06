<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<html>
<head>
<%@ include file="/back_end/product/page1.file" %>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/fileinput.js"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/themes/fa/theme.js"></script>
<link href="<%=request.getContextPath() %>/back_end/product/filejs/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/piexif.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/sortable.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/plugins/purify.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/fileinput.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/themes/fa/theme.js"></script>
<script src="<%=request.getContextPath() %>/back_end/product/filejs/js/locales/<lang>.js"></script>
<style type="text/css">
.mm {
	margin-top: 4cm;
}
#DIV_SMALL{
   
    position:absolute;
    top:3cm;
    left:12cm;
}
</style>
	<script type="text/javascript">
	
	</script>
</head>
<body>
<%@ include file="/back_end/backEndNavBar.file"%>
<%@ include file="/back_end/backEndLSide.file"%>

	<div class="row col-xs-10 col-sm-10 ">
			
			
		<div style="padding-top:0em;">	
			
	  <form action="<%=request.getContextPath()%>/ProductUpload" method="POST" enctype="multipart/form-data" name="form" >
		<table class="table table-hover mm" style="background-color: #CCEEFF;">
			<thead>
				<tr style="background-color: #E8CCFF;">
					
					<th>��J�W�[�ӫ~��T
						<font color="red">
	   					<c:if test="${not empty errorMsgs}">
          				<%=request.getAttribute("errorMsgs") %>
       					</c:if>
	 					</font>
					</th>
				</tr>
			</thead>
			<tbody>
			  
				<tr>
					<td>�ӫ~�W��<input type="text" size="17" maxlength="15" name="proName" id="name"
						placeholder="�п�J�W�[�ӫ~�W��" required></td>
				</tr>
				<tr>
					<td>�ӫ~���O<input type="text" size="17" maxlength="4" name="proType" id="type"
						placeholder="�п�J�W�[�ӫ~���O" required></td>
				</tr>
				<tr>
					<td><div style="padding-top:0px;">�ӫ~�y�z</div><textarea rows="4" cols="25" name="proDescpt" id="descpt" 
						placeholder="�п�J�W�[�ӫ~�y�z" required></textarea>
					</td>
				</tr>
				<tr>
					<td>�ӫ~����<input type="text" size="17" maxlength="4" name="proPrice" id="price"
						placeholder="�п�J�W�[�ӫ~����" required >
						<p id="demo"></p>	
					</td>
				</tr>
				<tr>
					<td>�ӫ~�Ϥ�
							<label class="control-label">Select File</label>
							<input id="input-fa" name="prodimg" type="file" multiple class="file-loading">
							
						
					</td>
				</tr>
			</tbody>
			
						
			
		</table>
				<input type="submit" class="btn btn-primary" value="�s�W�ӫ~" >
				<input type="hidden" name="action"value="addprod">
				<a href="<%=request.getContextPath()%>/back_end/order/OrderManage.jsp"><input type="button" class="btn btn-primary" value="�q��޲z"></a>
				<a href="<%=request.getContextPath()%>/back_end/product/productManage.jsp"><input type="button" class="btn btn-primary" value="�ӫ~�޲z"></a>  
				<input type="button" value="insert" onclick="alter()" class="btn btn-primary">
			</form>	
				
		</div>		
	</div>
	
</body>

<script>
$("#input-fa").fileinput({
    theme: "fa",
    uploadUrl: "/file-upload-batch/2"
});
</script>
<script type="text/javascript">
function alter(){
	var proName = document.getElementById("name");
	var proDescpt = document.getElementById("descpt");
	var proPrice = document.getElementById("price");
	var proType = document.getElementById("type");
	proName.value="�����L�\�t�C�����L�\�`����";
	proDescpt.value="�����L�\�t�C���ѧ��Ū��J�ս�B�L�\�����Ҥ��ƦX�����Y���u�誺�תաC�o���u�誺�����ӷ��A���Ѧ����һݭn����q";
	proPrice.value="2040";
	proType.value="�d���}��";
	}
</script>
<!-- /.container -->
	<!-- jQuery -->
	<script src="<%=request.getContextPath()%>/front_end/js/jquery.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script
		src="<%=request.getContextPath()%>/front_end/js/bootstrap.min.js"></script>
	<!-- Script to Activate the Carousel -->
</html>