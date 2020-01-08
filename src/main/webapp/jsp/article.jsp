<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    $(function () {
        $("#articletable").jqGrid({
            //发送请求
            url:"${pageContext.request.contextPath}/article/page",
            //响应格式
            datatype:"json",
            //表头
            colNames:["ID","标题","图片","内容","创建日期","发表日期","状态","所属大师","操作"],
            colModel:[
                {name:"id",hidden:true},
                {name:"title"},
                {name:"img",edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {

                        return "<img style='width: 180px;height: 80px' src='"+cellvalue+"'>";}},
                {name:"content"},
                {name:"createDate"},
                {name : 'publishDate'},
                {name : 'status',index : 'amount',width : 80,align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==1){
                            return "展示";
                        }else{
                            return "冻结";
                        }
                    },
                    editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:不展示"}
                },
                {name:"guruId"},
                {name:"",formatter:function (cellvalue, options, rowObject) {
                        var button = "<button type=\"button\" class=\"btn btn-danger\" onclick=\"update('"+rowObject.id+"')\">修改</button>";
                        return button;
                    }}
            ],
            //multiselect:true,多选框
            height:"300px",
            autowidth:true,
            pager:"#articlepage",
            styleUI: "Bootstrap",
            rowNum:2,
            viewrecords:true,
            caption : "文章管理",
            editurl: "${pageContext.request.contextPath}/article/dee",
        }).jqGrid("navGrid","#articlepage",{del: true,edit: false, add: false, },
            {
                closeAfterDel:true,

            })
    });
    // 点击添加文章时触发事件
    function showArticle() {
        $("#id").val("");
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id","");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/all",
            datatype: "json",
            type: "post",
            success: function (data) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }
    // 文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/add",
            type: "post",
            // ajaxFileUpload 不支持serialize() 格式化形式
            // 只支持{"id":1,XXX:XX}
            // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码

            // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {
                $("#articletable").trigger("reloadGrid");
            }
        })
        $("#myModal").modal("hide");
    }
    // 点击修改时触发事件
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化 的问题
        // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articletable").jqGrid("getRowData",id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#editor_id",data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option = "";
        if(data.status=="展示"){
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        }else{
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);
        // 处理上师信息

        $.ajax({
            url: "${pageContext.request.contextPath}/guru/all",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id==data.guruId){
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }
        });
        $("#myModal").modal("show");
    }
</script>
<ul class="nav nav-tabs">
    <li><a>文章信息</a></li>
    <li><a class="btn-default bg-success" onclick="showArticle()">添加文章</a></li>
</ul>
<table id="articletable"></table>
<div id="articlepage" style="height: 50px"></div>
