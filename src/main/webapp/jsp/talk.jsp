<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
        <link rel="stylesheet" href="../boot/css/back.css">
        <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
        <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
        <script src="../boot/js/jquery-2.2.1.min.js"></script>
        <script src="../boot/js/bootstrap.min.js"></script>
        <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
        <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
        <script src="../boot/js/ajaxfileupload.js"></script>
        <script src="../kindeditor/kindeditor-all-min.js"></script>
        <script src="../kindeditor/lang/zh-CN.js"></script>
        <!-- 引入 echarts.js -->
        <script src="../echarts/echarts.min.js" charset="UTF-8"></script>
        <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
        <!-- 将https协议改为http协议 -->
        <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
        <script type="text/javascript">
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-3d071f1482a74517b136d152a1465b6b", //替换为您的应用appkey
            });
            goEasy. subscribe({
                channel: 'cmfz',
                onMessage: function(message){
                    if(message){
                        /*console.log(message);
                        console.log(message.content);*/

                        $("#meeg").append("<span>"+message.content+"</span></br>")
                       /* var xiaoxi = message.content.match(/&(.*?)\$/);
                        var username = xiaoxi[1];*/
                        //if(username!='管理员'){
                            $(".qq-exe").addClass('qq-new-m');
                       // }
                    }
                   // alert('Meessage received:'+message.content);//接收到推送的消息
                }
            });
           /* goEasy.publish({
                channel: "你自己的项目名称",
                //message: "#" + ner + "&管理员$" + fa_id + "!!",
                onSuccess: function(message) {
                    //    console.log(message)
                },
                onFailed: function(error) {
                    alert("消息发送失败，错误编码：" + error.code + " 错误信息：" + error.content);
                }
            })*/
            function talikOn() {
                $.post(
                    "${pageContext.request.contextPath}/user/talk",
                    $("#talkform").serialize(),

                    function (ret) {
                    },
                    "json");
            }
        </script>
    </head>
    <body>
    <div id="meeg"></div>
        <form method="post" id="talkform" >
            <input type="text" name="meg" id="talk"><input type="button" value="发送" id="stalk" onclick="talikOn()">
        </form>
    </body>
</html>
