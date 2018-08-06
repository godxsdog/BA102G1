<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%@ page import="com.member.model.*"%>
<%request.setCharacterEncoding("UTF-8");%>  
<%response.setCharacterEncoding("UTF-8");%> 
<%Member mem = (Member)session.getAttribute("member"); %>
<%
  if(mem==null){
	  RequestDispatcher dispatcher = request.getRequestDispatcher("/front_end/member/login.jsp");
		dispatcher.forward(request, response);
		return;
  }
%>
<html>

<head>
		<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
		<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>
		<%@ include file="page4.file" %>
		
		
		
		
	    <script src="<%=request.getContextPath() %>/front_end/charge/js/card.js"></script>	
		
		<link rel="stylesheet" href="<%=request.getContextPath() %>/front_end/charge/css/card.css">
		
		<style>
		select:invalid { color: gray; }
        .demo-container {
            width: 350px;
            margin: 50px auto;
        }

        form {
            margin: 0px;
        }
        input {
            width: 200px;
            margin: 10px auto;
            display: block;
        }
		
    	</style>
    	
	</head>
	<body>
	<%@ include file="/front_end/frontEndNavBar.file" %>
<%@ include file="page2.file" %>
		
		<div class="container">
			<!-- Top Navigation -->
<!-- 			<font size="8" style="margin-left:0px;font-family:DFKai-sb;">PETYM儲值頁面</font> -->
	<div class="row"  id="show">
		
        <div class="col-md-4 col-md-offset-3">
          						
			<div class="main clearfix" style="padding-top:0px;">
			   
				<div class="demo-container">
			        <div class="card-wrapper"></div>

			        <div class="form-container active" >
			            <form action="<%=request.getContextPath() %>/chargePage" method="POST">
			            		 
			               	 卡號<br><input id="num" placeholder="Card number" maxlength="19" class="btn btn-default" style="margin-left:0px;width:350px;" type="text" name="number" required><br>
			                                                         姓名<br><input id="name" placeholder="Full name"  class="btn btn-default" style="margin-left:0px;width:350px;" type="text" name="name" value="<%=mem.getMemName() %>" required><br>
			                                                        有效期限<br><input id="date" placeholder="MM/YY"  maxlength="7" class="btn btn-default" style="margin-left:0px;width:350px;" type="text" name="expiry" required><br>
			                CVC碼<br><input id="cvc" placeholder="CVC" class="btn btn-default" style="margin-left:0px;width:350px;" type="text" name="cvc" required>
			               <div class="row">
			 				  <div class="col-md-6 col-sm-6 col-xs-6 pad-adjust" style="margin-left:0cm;margin-top:5px;width:350px;">
                  			  <input type="hidden" name="action" value="payment"> 
                  		                    儲值金額<br>
                  		      <select name="chargeNum" class="btn btn-default" style="width:350px;" id="chargeNum" name="chargeNum" required placeholder="請輸入金額">
                  			  <option value="" disabled selected hidden>請選擇儲值金額</option>
                  			  <option value="1000">1000</option>
                  			  <option value="2000">2000</option>
                  			  <option value="5000">5000</option>
                  			  <option value="10000">10000</option>
                  			  <option value="100000">100000</option>
                  			  </select>
                  			  <br>
                  			  <input type="submit" class="btn btn-warning btn-block" style="width:350px;" value="支付" />
                  			  <font color="red"> 
                                   <c:if test="${not empty errorMsgs}">
                                     <%=request.getAttribute("errorMsgs") %>
                                   </c:if>
                        		</font>
                  			  <input type="button" value="儲" style="margin-left:0cm;margin-right:0cm;width:1cm;"onclick="insert()">
                  			  
             				  </div>
             				</div>
             				
			            </form>
			            
			            <input type="hidden" value="<%=mem.getMemName() %>" id="memname"> 
			            
                        
			        </div>
			    </div>
			</div>
		</div>
	</div>
			 
			
			
		</div>
		
		 
		<!-- /container -->
		
	    <script>
	        $('.active form').card({
	            container: $('.card-wrapper')
	        })
	    </script>
	    <script type="text/javascript">
	    function insert() {
	    	var num = document.getElementById("num");
	    	var one = document.getElementById("name");
	    	var date = document.getElementById("date");
	        var memname = document.getElementById("memname");
	        var cvc = document.getElementById("cvc");
	        num.value="5432123456788881";
	        date.value="12/21";
	        one.value=memname.value; 
	        cvc.value="310"
	    }
		</script>
		<script type="text/javascript">
		document.getElementById("num").addEventListener("input", function(){
		    var op="";
		    var tmp = document.getElementById("num").value.replace(/\D/g, "");
		    for (var i=0;i<tmp.length;i++)
		    {
		        if (i%4===0 && i>0)
		        {
		            op = op + " " + tmp.charAt(i);
		        } else {
		            op = op + tmp.charAt(i);

		        }
		    }
		    document.getElementById("num").value = op;
		});
		</script>
		<script type="text/javascript">
		document.getElementById("date").addEventListener("input", function(){
		    var op="";
		    var tmp = document.getElementById("date").value.replace(/\D/g, "");
		    for (var i=0;i<tmp.length;i++)
		    {
		        if (i==2 && i>0)
		        {
		            op = op + "/" + tmp.charAt(i);
		        } else {
		            op = op + tmp.charAt(i);

		        }
		    }
		    document.getElementById("date").value = op;
		});
		</script>
	<%@ include file="/front_end/frontEndButtomFixed.file" %>	
</body>
</html>