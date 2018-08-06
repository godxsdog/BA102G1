<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<html lang="en">
<%@ page import="com.member.model.*"%>
<%@ page import="com.pet.model.*"%>
<head>
<% 
Member mem=(Member)request.getAttribute("fMem");
Pet pet=(Pet)request.getAttribute("fPet");
String fCounty=(String)request.getAttribute("fCounty");
String fDistrict=(String)request.getAttribute("fDistrict");
pageContext.setAttribute("mem", mem);
pageContext.setAttribute("pet", pet);
pageContext.setAttribute("fCounty", fCounty);
pageContext.setAttribute("fDistrict", fDistrict);
%>

<%@ include file="memHead.file"%>
<%@ include file="registerTest.file"%>
<title>寵物 You & Me</title>
 <script src="<%=request.getContextPath() %>/front_end/js/jquery.twzipcode.min.js"></script> 

 
 <style>

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






.zipcode {
	display:none;
}
.county {
    background-color: #4169E1;
    color: #fff;
}
.district {
    background-color: #008000;
    color: #fff;
}
</style>


<style type="text/css">
	div.inline { float:left; }
	.clearBoth { clear:both; }
</style>


 
</head>

<body>
	<!-- Navigation -->
	<%@ include file="/front_end/frontEndNavBar.file"%>


	<div class="container frontend">


		<div class="row main">

			<div class="col-sm-6 col-sm-offset-3">

				<form class=""
					action="<%=request.getContextPath()%>/front_end/member/member.do"
					method="post" enctype="multipart/form-data" id="register">

					<div class="row">

						<div class="col-sm-6 form-group">
							<label for="memId" class="cols-sm-2 control-label">帳號</label><span
								id="memIdShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa"
										aria-hidden="true"></i></span> <input type="text"
										class="form-control" name="memId" id="memId"
										placeholder="帳號長度需大於4" value="<%= (mem==null)? "" : mem.getMemId() %>" required />
								</div>
							</div>
						</div>

						<div class="col-sm-6 form-group">
							<label for="memSname" class="cols-sm-2 control-label">暱稱</label><span
								id="memSnameShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="memSname" id="memSname"
										placeholder="請輸入暱稱" value="<%= (mem==null)? "" : mem.getMemSname() %>" required />
								</div>
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="memPwd" class="cols-sm-2 control-label">密碼</label><span
								id="memPwdShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-envelope fa" aria-hidden="true"></i></span> <input
										type="password" class="form-control" name="memPwd" id="memPwd"
										placeholder="密碼長度需大於4" value="<%= (mem==null)? "" : mem.getMemPwd() %>" required />
								</div>
							</div>
						</div>

						<div class="col-sm-6 form-group">
							<label for="conpwd" class="cols-sm-2 control-label">確認密碼</label><span
								id="conPwdShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-users fa" aria-hidden="true"></i></span> <input
										type="password" class="form-control" name="conpwd" id="conPwd"
										placeholder="請再次輸入密碼" value="<%= (mem==null)? "" : mem.getMemPwd() %>" required />
								</div>
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="memName" class="cols-sm-2 control-label">姓名</label><span
								id="memNameShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="memName" id="memName"
										placeholder="請輸入您的姓名" value="<%= (mem==null)? "" : mem.getMemName() %>" required />
								</div>
							</div>
						</div>



						<div class="col-sm-6 form-group">
							<label for="memIdNo" class="cols-sm-2 control-label">身份證字號</label><span
								id="memIdNoShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="memIdNo" id="memIdNo"
										placeholder="請輸入身份證字號" value="<%= (mem==null)? "" : mem.getMemIdNo() %>" required />
								</div>
							</div>
						</div>
					</div>




					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="memBday" class="cols-sm-2 control-label">生日</label><span
								id="memBdayShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										name="memBday" id="memBday" class="form-control"
										placeholder="Confirm your Password" value="<%= (mem==null)? "" : mem.getMemBday() %>" required />
								</div>
							</div>
						</div>


						<div class="col-sm-6 form-group">
							<label for="memPhone" class="cols-sm-2 control-label">手機</label><span
								id="memPhoneShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="memPhone" id="memPhone"
										placeholder="請輸入您的手機" value="<%= (mem==null)? "" : mem.getMemPhone() %>" required />
								</div>
							</div>
						</div>
					</div>


					<div class="form-group">
						<label for="memGender" class="control-label">性別</label><br> <label
							class="radio-inline"><input type="radio" name="memGender"
							checked="true" value="0">男</label> <label class="radio-inline"><input
							type="radio" id="girlMem" name="memGender" value="1">女</label> <label
							class="radio-inline"><input type="radio" name="memGender"
							value="2">第三性</label>
					</div>


					<div class="form-group">
						<label for="memAddress" class="cols-sm-2 control-label">地址</label><span
							id="memAddressShow"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
<!-- 									<div id="twzipcode" class="selectpicker"></div> -->
									
									<div id="twzipcode">

									  	<div class="select-style inline">
									  	<div data-role="county" data-style="Style Name" data-value="110"></div>
									  	</div>
									  	<div class="select-style inline">								
  										<div data-role="district" data-style="Style Name" data-value="臺北市"></div>
  										</div>
<!--   										<div data-role="zipcode" data-style="Style Name" data-value="信義區"></div> -->
										 <input
										type="text" class="form-control" name="memAddress" id="memAddress"
										placeholder="請輸入地址" value="<%= (mem==null)? "" : mem.getMemAddress() %>" required />
										</span> 
									</div>
									
									
									
<!-- 								<textarea class="form-control" id="memAddress" name="memAddress" -->
<%-- 									placeholder="請輸入您的地址" required><%= (mem==null)? "" : mem.getMemAddress() %></textarea> --%>
							</div>
						</div>
					</div>


					<div class="form-group">
						<label for="memEmail" class="cols-sm-2 control-label">電子信箱</label><span
							id="memEmailShow"></span>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									type="text" class="form-control" name="memEmail" id="memEmail"
									placeholder="請輸入您的電子信箱" value="<%= (mem==null)? "" : mem.getMemEmail() %>" required />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="memImg" class="control-label ">照片</label><span
								id="memImgShow"></span> <br> <input type="file"
								name="memImg" id="memImg" placeholder="" /> <img
								src="https://api.fnkr.net/testimg/350x200/00CED1/FFF/?text=img+placeholder"
								height="200px" width="150px" id="memPic" style="margin-top: 5px"><br>
						</div>

						<div class="col-sm-6 form-group">
							<label for="petOrNot" class="control-label">有無養寵物</label><br>
							<label class="radio-inline"><input type="radio"
								name="petOrNot" id="petOrNot_0" value="0" checked="true">無</label>
							<label class="radio-inline"><input type="radio"
								name="petOrNot" id="petOrNot_1" value="1">有</label>
						</div>
					</div>

					<div id="petDiv" style="display: none;">
						<div class="form-group">
							<label for="petName" class="cols-sm-2 control-label">寵物姓名</label><span
								id="petNameShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="petName" value="<%= (pet==null)? "" : pet.getPetName() %>" id="petName"
										placeholder="請輸入您的寵物姓名" />
								</div>
							</div>
						</div>


						<div class="row">
							<div class="col-sm-6 form-group">
								<label for="petKind" class="control-label">寵物分類</label><br>
								<label class="radio-inline"><input type="radio"
									name="petKind" id="petOrNot_0" value="狗" checked="true">狗</label>
								<label class="radio-inline"><input type="radio"
									name="petKind" id="petOrNot_1" value="貓">貓</label> <label
									class="radio-inline"><input type="radio" name="petKind"
									id="petOrNot_2" value="其他">其他</label>
							</div>


							<div class="col-sm-6 form-group">
								<label for="petGender" class="control-label">寵物性別</label><br>
								<label class="radio-inline"><input type="radio"
									name="petGender" value="0" checked="true">男</label> <label
									class="radio-inline"><input type="radio"
									name="petGender" value="1">女</label>
							</div>

						</div>


						<div class="form-group">
							<label for="petImg" class="control-label">寵物照片</label><span
								id="petImgShow"></span><br> <input type="file"
								name="petImg" id="petImg" /> <img
								src="https://api.fnkr.net/testimg/350x200/00CED1/FFF/?text=img+placeholder"
								height="200px" width="150px" id="petPic" style="margin-top: 5px"><br>

						</div>

					</div>

					<input type="hidden" name="action" value="register">


					<!-- 					google Invisible reCAPTCHA  -->
					<div id='recaptcha' class="g-recaptcha"
						data-sitekey="6LeBbC0UAAAAAEd3C3R3zbSpsfxg2A7zZarw2mZT"
						data-callback="onSubmit" data-size="invisible"></div>
					<button id='sub'
						class="btn btn-primary btn-lg btn-block login-button">註冊</button>

				</form>
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>請修正以下錯誤:
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li>${message}</li>
							</c:forEach>
						</ul>
					</font>s
				</c:if>

				<button type="button" class="btn btn-info" id="autoAddMem"
					style="margin-top: 10px">新增一般會員</button>
				<button type="button" class="btn btn-info" id="autoAddMemPet"
					style="margin-top: 10px">新增寵物會員</button>

			</div>
		</div>


	</div>
	<%@ include file="/front_end/frontEndButtom.file"%>


	<script>
		function onSubmit(token) {
			document.getElementById('register').submit();
		}

		function validate(event) {

			event.preventDefault();
			grecaptcha.execute();
		}

		function onload() {
			var element = document.getElementById('sub');
			element.onclick = validate;
		}
	</script>
	<script src="https://www.google.com/recaptcha/api.js" async defer></script>


	<script>
		onload();
	</script>


	<script>
		$(function() {

			$("#memBday").datetimepicker({
				format : 'Y-m-d',
				timepicker : false,
				maxDate : '0',
			});


			
			$('#twzipcode').twzipcode({
			    // 依序套用至縣市、鄉鎮市區及郵遞區號框
			 'css': ['county', 'district', 'zipcode'],
		    'countySel': '${fCounty}', 
		    'districtSel': '${fDistrict}'
			});
			
		});
		

		

		
	</script>
		



</body>

</html>