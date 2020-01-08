<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#excal").click(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/banner/esayExcal",
                type:"post",
                datatype:"json",
                success:function (data) {
                    alert("ok");
                }
            })


        })
        $("#loadExcal").click(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/banner/loadExcal",
                type:"post",
                datatype:"json",
                success:function (data) {
                    alert("ok");
                }
            })


        })
        $("#list2").jqGrid({
            //发送请求
            url:"${pageContext.request.contextPath}/banner/page",
            //响应格式
            datatype:"json",
            //表头
            colNames:["ID","标题","路径","日期","描述","状态"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true,},
                {name : 'url',editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/"+cellvalue+"' style='width:100px;height:60px'>";
                        /*return "<img style='width: 180px;height: 80px' src='"+cellvalue+"'>";*/
                    }
                },
                {name:"createDate",editable:true,editrules:{required:true},edittype: "date"},
                {name:"descript",editable:true},
                {name : 'status',index : 'amount',width : 80,align : "center",editable: true,
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==1){
                            return "不展示";
                        }else{
                            return "展示";
                        }
                    },
                    editrules:{required:true},edittype:"select",editoptions: {value:"2:展示;1:不展示"}
                }
            ],
            //multiselect:true,多选框
            height:"300px",
            autowidth:true,
            pager:"#pager",
            styleUI: "Bootstrap",
            rowNum:3,
            viewrecords:true,
            caption : "轮播图管理",
            editurl: "${pageContext.request.contextPath}/banner/changeMeg"
        }).jqGrid("navGrid","#pager",{edit: true, add: true, del: true,deltext:"修改状态"},
            {
                closeAfterEdit:true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#list2").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }

            },
            {
                closeAfterAdd:true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/banner/uploadHttp",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{bannerId:bannerId},
                        // 指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {

                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },
            {
                closeAfterDel: true,
            });
    });

</script>
<ul class="nav nav-tabs">
    <li><a id="excal">导出轮播图信息</a></li>
    <li><a id="loadExcal">导入轮播图信息</a></li>
    <li><a>Excel模板下载</a></li>
</ul>
<table id="list2"></table>
<div id="pager" style="height: 100px"></div>