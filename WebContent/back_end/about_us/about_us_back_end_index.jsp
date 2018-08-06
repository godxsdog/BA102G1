<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8"></meta>
<meta content="IE=edge" http-equiv="X-UA-Compatible"></meta>
<meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport"></meta>

<title>Backend-Index</title>

<link href="<%= request.getContextPath() %>/back_end/css/bootstrap.css" rel="stylesheet"></link>
<link href="<%= request.getContextPath() %>/back_end/css/nav.css" rel="stylesheet"></link>
<link href="<%= request.getContextPath() %>/back_end/css/colorplan.css" rel="stylesheet"></link>
<!-- Custom CSS -->
<link href="<%= request.getContextPath() %>/back_end/css/modern-business.css" rel="stylesheet"></link>
<!-- Custom Fonts -->
<link href="<%= request.getContextPath() %>/back_end/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"></link>
<link href="<%= request.getContextPath() %>/back_end/css/backend.css" rel="stylesheet"></link>

<!--[if lt IE 9]>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
       
</head>

<style type="text/css">

#btn btn-success btn-md{
    margin-left: auto;
    margin-bottom: 1em; 
    }
    
</style>

    <body>
        <nav class="navbar navbar-light navbar-fixed-top" id="emerald-nav" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button class="navbar-toggle" data-target="#bs-example-navbar-collapse-1" data-toggle="collapse" type="button">
                        <span class="sr-only">
                            Toggle navigation
                        </span>
                        <span class="icon-bar">
                        </span>
                        <span class="icon-bar">
                        </span>
                        <span class="icon-bar">
                        </span>
                    </button>
                    <a class="navbar-brand logo" href="index.html">
                        寵物You&Me
                    </a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                    </ul>
                    <div class="nav collapse navbar-collapse navbar-right" id="login">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="contact.html">
                                    管理員登出
                                </a>
                            </li>
                        </ul>
                    </div>
                    <!-- /.navbar-collapse -->
                    <!-- /.container -->
                </div>
            </div>
        </nav>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-2 postion-left-group-b">
                    <div id="menu">
                        <div class="panel list-group list-color">
                            <a class="list-group-item" href="">
                                後端首頁
                            </a>
                            <a class="list-group-item" data-parent="#menu" data-target="#sm1" data-toggle="collapse" href="#">
                                前端看板管理
                                <span class="glyphicon glyphicon-triangle-bottom pull-right">
                                </span>
                            </a>
                            <div class="sublinks collapse" id="sm1">
                                <a class="list-group-item small" href="#">
                                    幻燈片管理
                                </a>
                                <a class="list-group-item small" href="#">
                                    最新消息管理
                                </a>
                            </div>
                            <a class="list-group-item" data-parent="#menu" data-target="#sm2" data-toggle="collapse" href="#">
                                會員帳號管理
                                <span class="glyphicon glyphicon-triangle-bottom pull-right">
                                </span>
                            </a>
                            <div class="sublinks collapse" id="sm2">
                                <a class="list-group-item small" href="#">
                                    一般會員管理
                                </a>
                                <a class="list-group-item small" href="#">
                                    餐廳會員管理
                                </a>
                            </div>
                            <a class="list-group-item" data-parent="#menu" data-target="#sm3" data-toggle="collapse" href="#">
                                商城管理
                                <span class="glyphicon glyphicon-triangle-bottom pull-right">
                                </span>
                            </a>
                            <div class="sublinks collapse" id="sm3">
                                <a class="list-group-item small" href="#">
                                    商品管理
                                </a>
                                <a class="list-group-item small" href="#">
                                    訂單管理
                                </a>
                            </div>
                            <a class="list-group-item" data-parent="#menu" data-target="#sm4" data-toggle="collapse" href="#">
                                檢舉申訴管理
                                <span class="glyphicon glyphicon-triangle-bottom pull-right">
                                </span>
                            </a>
                            <div class="sublinks collapse" id="sm4">
                                <a class="list-group-item small" href="#">
                                    約會商品檢舉管理
                                </a>
                                <a class="list-group-item small" href="#">
                                    約會商品申訴管理
                                </a>
                            </div>
                            <a class="list-group-item" href="#">
                                活動審核
                            </a>
                            <a class="list-group-item" href="#">
                                管理員權限管理
                            </a>
                            <a class="list-group-item" href="#">
                                餐廳審核
                            </a>
                            <a class="list-group-item" href="#">
                                管站內信管理
                            </a>
                        </div>
                    </div>
                </div>
                <!-- 抬頭在這 -->
                <div class="jumbotron">
                    <h2>
                        關於我們
                    </h2>
                    <p>
                        本頁面包含最新消息、FAQ、使用者提交FAQ、廣告、幻燈片管理
                    </p>
                    <!-- 主要功能圖示 -->
                    <div class="row">
                        <!--  <div class="col-xs-12 col-sm-2">
                            <div class="to-do-list">
                                <button class="btn btn-success btn-md" style="margin-left: 5em; margin-bottom: 1em;" type="to-do-list-button">
                                    What to Do?
                                    <span class="badge">
                                        10
                                    </span>
                                </button>
                                <a data-toggle="to-do-list" href="#" title="Get Your Job Done!">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/images/about_us/list.png" style="margin-left: 3em; width:200px; height:200px; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div> -->
                        <div class="col-xs-12 col-sm-2">
                            <div class="news-list">
                                <button class="btn btn-success btn-md" type="news-button">
                                    Post Something New?
                                </button>
                                <a data-toggle="news-list" href="<%= request.getContextPath() %>/back_end/about_us/news/news_select_page.jsp" title="Got New Stuff?">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/about_us/images/news.png" style="margin-left: auto; width:100%; height:100%; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-2">
                            <div class="faq-list">
                                <button class="btn btn-success btn-md" type="faq-button">
                                    Got New FAQ for User?
                                </button>
                                <a data-toggle="faq-list" href="<%= request.getContextPath() %>/back_end/about_us/faq/faq_select_page.jsp"" title="Get New FAQ?">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/about_us/images/faq.png" style="margin-left: auto; width:100%; height:100%; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-2">
                            <div class="user-submit-list">
                                <button class="btn btn-success btn-md" type="user-submit-button">
                                  User Submit FAQ!
                                    <span class="badge">
                                        7
                                    </span>
                                </button>
                                <a data-toggle="user-submit-list" href="#" title="Someone Just Submit the Problem">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/about_us/images/user_submit.png" style="margin-left: auto; width:100%; height:100%; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-2">
                            <div class="ad-list">
                                <button class="btn btn-success btn-md" type="ad-button">
                                    Nees Some ADs?
                                    <span class="badge">
                                        7
                                    </span>
                                </button>
                                <a data-toggle="ad-list" href="#" title="No ADs No Wage!">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/about_us/images/billboard.png" style="margin-left: auto; width:100%; height:100%; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-2">
                            <div class="slide-manage">
                                <button class="btn btn-success" type="slide-button">
                                    Want a New Slide?
                                </button>
                                <a data-toggle="slide-manage" href="#" title="Post Something New Plz">
                                    <img alt="" src="<%= request.getContextPath() %>/back_end/about_us/images/slider.png" style="margin-left: auto; width:100%; height:100%; border:10;">
                                    </img>
                                </a>
                            </div>
                        </div>
                    </div>
                    <!-- 不知道要放什麼 -->
                    <div class="container" style="margin-top:2em; margin-left: 20em">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    標題
                                </h3>
                            </div>
                            <div class="panel-body">
                                內容文字
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 跳窗在這邊 -->
                <a class="btn btn-primary" data-toggle="modal" href="#modal-id">
                    確認
                </a>
                <div class="modal fade" id="modal-id">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">            
                                </button>
                                <h4 class="modal-title">
                                    確認標題在這
                                </h4>
                            </div>
                            <div class="modal-body">
                                反正就是確認的廢話
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-default" data-dismiss="modal" type="button">
                                    取消
                                </button>
                                <button class="btn btn-primary" type="button">
                                    確定
                                </button>
                            </div>
                        </div>
                    </div>
                </div>


<footer class="footer" style="background-color: black; color: white; padding: 1em; margin-top: 1em;">
  <div class="container">
    <p class="text-muted"><h4>這是sticky footer</h4></p>
  </div>
</footer>


                <script src="https://code.jquery.com/jquery.js">
                </script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js">
                </script>
            </div>
        </div>
    </body>
</html>