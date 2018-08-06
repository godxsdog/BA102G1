<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<html lang="en">

<head>
<%@ include file="memHead.file"%>
<title>寵物 You & Me</title>

<script>
	$(function() {

		//照片上傳預覽
		$("#petImg").change(function() {
			readURL(this);
		});

		function readURL(input) {

			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#petPic').attr('src', e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#autoAddPet").click(function() {
			$("#petName").val("單身狗");
		});

	});
</script>


</head>

<body>
	<!-- Navigation -->
	<%@ include file="/front_end/frontEndNavBar.file"%>
	<!-- Header Carousel -->


	<div class="container frontend">

		<div class="row main">

			<div class="col-sm-6 col-sm-offset-3">

				<form class="" action="<%=request.getContextPath() %>/front_end/pet/pet.do" method="post"
					enctype="multipart/form-data">



					<div id="petDiv">

						<div align="center">
							<Img src="<%=request.getContextPath() %>/front_end/images/logo.png" height="250px" width="100%" />
						</div>


						<div class="form-group">
							<label for="petName" class="cols-sm-2 control-label">寵物姓名</label><span
								id="petNameShow"></span>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
										type="text" class="form-control" name="petName" id="petName"
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

					<input type="hidden" name="action" value="petRegister"> <input
						class="btn btn-primary btn-lg btn-block login-button"
						type="submit" value="註冊">

					<c:if test="${not empty errorMsgs}">
						<font color="red">
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
						</font>
					</c:if>


				</form>

				<button type="button" class="btn btn-info" id="autoAddPet"
					style="margin-top: 10px">新增寵物</button>

			</div>
		</div>




	</div>
		<%@ include file="/front_end/frontEndButtom.file"%>


</body>

</html>