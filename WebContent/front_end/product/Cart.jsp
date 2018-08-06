<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<html>
<head>
<head>
<%@ include file="page4.file" %>
<style type="text/css">
.center{
width: 150px;
  margin: 40px auto;
  
}
</style>
<script src="<%=request.getContextPath() %>/front_end/js/jquery.js"></script>
<script src="<%=request.getContextPath() %>/front_end/js/bootstrap.min.js"></script>

</head>
<body>
<%@ include file="/front_end/frontEndNavBar.file" %>
<%@ include file="page2.file" %>

<% Vector<Product> buylist = (Vector<Product>) session.getAttribute("shoppingcart");%>
<%Map<String,Integer> qtyMap = (Map<String,Integer>) session.getAttribute("qtyMap");%>   
<%Member mem = (Member)session.getAttribute("member");%>
<%if(mem == null){
	RequestDispatcher rd = request.getRequestDispatcher("/front_end/member/login.jsp");
	rd.forward(request, response);
	return;
}

                	%>
<%try{
	buylist.get(0); 
		}catch(Exception e)
			{
			RequestDispatcher rd = request.getRequestDispatcher("/front_end/product/NoItemCart.jsp");
			rd.forward(request, response);
			return;
				}%>
<%	
	int totalall=0;
	for (int i = 0; i < buylist.size(); i++) {
	Product order = buylist.get(i);
	int price = order.getProdPrice();
	String prodName = order.getProdName();
	int quality = qtyMap.get(prodName);
	totalall += (price * quality);
}

String amount = String.valueOf(totalall);
session.setAttribute("amount", amount);
			

	%>


	
	<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th class="text-center">Price</th>
                        <th class="text-center">Total</th>
                        
                    </tr>
                </thead>
                <tbody>
                
                
                
                <%
					
	 				for (int index = 0; index < buylist.size(); index++) {
					Product prod = buylist.get(index);
					int total=prod.getProdPrice()*qtyMap.get(prod.getProdName()); 
					session.setAttribute("total",total);
					
				%>
                    <tr>
                        <td class="col-sm-8 col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="<%=request.getContextPath() %>/ProductImage?prodno=<%=prod.getProdNo()%>" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading"><a href="<%=request.getContextPath() %>/ShoppingDetail.do?prodno=<%=prod.getProdNo()%>"><%=prod.getProdName()%></a></h4>
                            </div>
                        </div></td>
                        <td class="col-sm-1 col-md-1" style="text-align: center">
                          
                       		 
                       		 
                       		 <input type="number" min="1" max="10" name="num<%=index %>"  autocomplete="off" form="ProdNumChange" class="form-control" id="num<%=index %>" value="<%=qtyMap.get(prod.getProdName())%>" onchange="calculate(this,<%=index%>)">
                             
                               
                       
                      </td>
                        <td  class="col-sm-1 col-md-1 text-center"><p>$<%=prod.getProdPrice()%></p></td>
                        <% %>
                        <td  class="col-sm-1 col-md-1 text-center">
                          <input type="hidden" id="select<%=index %>">
                          <div>$<%=total %></div>
                        	
                        	
                        </td>
                        <td class="col-sm-1 col-md-1">
							<input type="hidden" id="index" value="<%=buylist.size()%>">
                            <form  name="deleteForm" action="<%=request.getContextPath() %>/ShoppingCar.do" method="POST">
              					<input type="hidden" name="action" value="delete">
              					<input type="hidden" name="del" value="<%=index%>">
              					<input type="hidden" name="delname" id="name<%=index %>" value=<%=prod.getProdName()%>> 
              					<input type="hidden" name="price"  id="pri<%=index %>" value=<%=prod.getProdPrice()%>>
             				    <input type="hidden" name="prodno" value=<%=prod.getProdNo()%>>
              					<input type="submit" class="btn btn-danger" value="刪除">
       					 		
       					 </form>
                        </td>
                    </tr>
                  <%}%> 
                     <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><h3>Total</h3></td>
                        	
                        <td>
                        
                        <input class="btn btn-info btn-lg"  name="amount" id="amo" type="button" value="<%=amount %>" style="margin-top:10px;margin-left:-20px;">
                        
                        </td>
                    </tr>
                   		
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                         <form action="<%=request.getContextPath() %>/ProdNumChange" id="ProdNumChange" method="POST">
                         	<%for (int index = 0; index < buylist.size(); index++) {
        					Product prod = buylist.get(index); %>
                        	<input type="hidden" name="test" id="keep<%=index %>" value="<%=qtyMap.get(prod.getProdName())%>">
              					
             				<%} %>
                         	<input type="hidden" name="action" value="back">
                        	<button type="submit" class="btn btn-default">
                            	<span class="glyphicon glyphicon-shopping-cart">繼續購物</span>
                        	</button>
                         </form>
                        </td>
                        <td>
                        <form action="<%=request.getContextPath() %>/ProdNumChange" id="ProdNumChange" method="POST">
                        <%for (int index = 0; index < buylist.size(); index++) {
        					Product prod = buylist.get(index); %>
                        <input type="hidden" name="test" id="pay<%=index %>" value="<%=qtyMap.get(prod.getProdName())%>">
              					
             				    <%} %>
                        <input type="hidden" name="action" value="pay">
                        <button type="submit" class="btn btn-success"> 
                        	結帳
                        </button>
                        </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<%@ include file="/front_end/frontEndButtom.file" %>
<script>

function calculate(e,idx){
	if($(e).val()>10){
		 $(e).val(10);
	}
	else if($(e).val()<0){
		 $(e).val(1);
	}
	var numb = $(e).val();
	$('#keep'+idx).val(numb);
	$('#pay'+idx).val(numb);
	console.log($('#keep'+idx).val(numb));
	
	
	var sum=0;
	var index = $("#index").val();
	for(var i=0;i<index;i++){

	var num = "#" + "num" + i;
	var pri = "#" + "pri" + i;
	
	var str = $(num).val()*$(pri).val();
	var asd = $("#select"+i).next();
	asd.text("$"+str);
	
	sum += parseInt(str);
	console.log($(num).val());
	}
	$("#amo").val("$"+sum);
	
}








</script>
<script type="text/javascript">

</script>
</body>
</html>