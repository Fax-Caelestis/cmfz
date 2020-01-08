<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>

        <style type="text/css">
                              #carousel-example-generic  .carousel-inner > .item > img {
                                  display: block;
                                  width:100%;
                                  height:300px;
                              }
        </style>

    <script>
        function changeImg() {
            document.getElementById("imgs").src="${pageContext.request.contextPath}/admin/ma?xx="+Math.random()
        }
        function login() {
            $.post(
                "${pageContext.request.contextPath}/admin/login",
                $("#loginForm").serialize(),

                function (ret) {
                    if(ret=="ok"){
                        location.href="${pageContext.request.contextPath}/jsp/main.jsp";
                    }
                    if(ret=="codeerror"){
                       document.getElementById("msg").innerHTML="<a style='color: red'>验证码错误</a> ";
                    }
                    if(ret=="error"){
                        document.getElementById("msg").innerHTML="<a style='color: red'>账号或密码错误</a> ";
                    }

                },
            "json");

        }
    </script>
</head>
<body style=" background: url(../img/7411.png); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <span id="msg"></span>
            <div class="form-group">
                <img src="${pageContext.request.contextPath}/admin/ma" id="imgs">
                <a href="javascript:void(0)" onclick="changeImg()">看不清换一张</a><br>
                <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code">
            </div>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
