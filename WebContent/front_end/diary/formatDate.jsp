<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>formatDate.jsp 簡測</title>
<style>
q {
    quotes: "\00ab" "\00bb" "\2039" "\203A";
}
</style>
</head>
<body>

<%  
java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String formatDate = df.format(new java.util.Date());
out.println(formatDate);
%>


<jsp:useBean id="rightNow" scope="page" class="java.util.Date" />

<pre><b><q>以下各種日期格式參見:java.text.SimpleDateFormat類別</q></b><span style="text-decoration:underline;">

yyyy-MM-dd               :<fmt:formatDate value="${rightNow}" pattern="yyyy-MM-dd"/>

yyyy-MM-dd HH:mm:ss      :<fmt:formatDate value="${rightNow}" pattern="yyyy-MM-dd HH:mm:ss"/>

yyyy-MM-dd HH:mm:ss.SSS  :<fmt:formatDate value="${rightNow}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>  (S:毫秒)

yyyy-MM-dd HH:mm:ss.SSSZ :<fmt:formatDate value="${rightNow}" pattern="yyyy-MM-dd HH:mm:ss.SSSZ"/> (Z:時區)

</span></pre><br>

</body>
</html>