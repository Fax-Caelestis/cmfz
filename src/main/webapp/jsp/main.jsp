<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
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

        // KindEditor初始化时必须放在head标签中,不然会出现无法初始化的情况
        KindEditor.ready(function (K) {
            // K.create("textarea的Id")
            // 如需自定义配置 在id后使用,{}的形式声明
            window.editor = K.create('#editor_id', {
                width : '500px',
                uploadJson: '${pageContext.request.contextPath}/article/uploadImg',
                allowFileManager: true,
                fileManagerJson: '${pageContext.request.contextPath}/article/showAllImg',
                // 失去焦点后 触发的事件
                afterBlur: function () {
                    // 同步数据方法
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<%--头--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持法明州后台管理系统</a>
        </div>
        <div>
            <!--向右对齐-->
            <c:if test="${sessionScope.admin.username!=null}">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="btn-default" href="${pageContext.request.contextPath}/admin/out">
                            退出登录
                        </a>
                    </li>
                </ul>
                <p class="navbar-text navbar-right">
                    欢迎管理员:${sessionScope.admin.username}</p>
            </c:if>
            <c:if test="${sessionScope.admin.username==null}">

                <p class="navbar-text navbar-right">
                    <a href="${pageContext.request.contextPath}/jsp/login.jsp">登录</a></p>
            </c:if>
        </div>
    </div>
</nav>
<%--栅栏--%>
<div class="container-fluid">
    <div class="row">
        <%--手风琴--%>
        <div class="col-xs-3">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#centerLay').load('./user.jsp')">用户管理</a></li>
                                <li><a href="javascript:$('#centerLay').load('./usershow.jsp')">近期用户注册查询</a></li>
                                <li><a href="javascript:$('#centerLay').load('./userlocation.jsp')">用户所在地查询</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                上师模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a  href="javascript:$('#centerLay').load('./guru.jsp')">上师管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#centerLay').load('./article.jsp')">文章管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                专辑模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#centerLay').load('./album.jsp')">专辑管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion"
                                   href="#collapseFive">
                                    轮播图模块
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse">
                            <div class="panel-body">
                                <ul class="nav">
                                    <li><a href="javascript:$('#centerLay').load('./banner.jsp')">轮播图管理</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <%--主页--%>
        <div class="col-xs-9">
            <div class="jumbotron" id="centerLay">
            <%--标题--%>
            <div class="container">
                <h1>欢迎使用持明法洲管理系统</h1>
                <%--轮播图--%>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="../img/1.png" alt="First slide">
                            <div class="carousel-caption">黑呆</div>
                        </div>
                        <div class="item">
                            <img src="../img/2.jpg" alt="Second slide">
                            <div class="carousel-caption">saber</div>
                        </div>
                        <div class="item">
                            <img src="../img/3.jpg" alt="Third slide">
                            <div class="carousel-caption">带毛</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align: center">@百知教育 @baizhi.com.cn</h4>
</div>
<!-- 添加文章模态框（Modal） -->

<div class="modal fade" id="myModal" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body" style="overflow:auto; ">
                <form role="form" id="kindForm">
                    <div class="form-group">
                        <input type="hidden" class="form-control" name="id" id="id" placeholder="请输入id">
                    </div>
                    <div class="form-group">
                        <label for="title">标题</label>
                        <input type="text" class="form-control" name="title" id="title" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面</label>
                        <!-- name不能起名和实体类一致 会出现使用String类型接受二进制文件的情况 -->
                        <input type="file" id="inputfile" name="inputfile">
                    </div>
                    <div class="form-group" style="width: 400px">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:300px;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>
                    </div>
                    <div class="form-group">
                        <label for="status">状态</label>
                        <select class="form-control" id="status" name="status">
                            <option value="1">展示</option>
                            <option value="2">冻结</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="guru_list">上师列表</label>
                        <select class="form-control" id="guru_list" name="guruId">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sub()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>