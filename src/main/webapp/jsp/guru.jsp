<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#gurutable").jqGrid({
            //发送请求
            url:"${pageContext.request.contextPath}/guru/all",
            //响应格式
            datatype:"json",
            //表头
            colNames:["ID","名字","头像","状态","法名"],
            colModel:[
                {name:"id"},
                {name:"name",editable:true,},
                {name : 'photo',editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"},formatter:function (cellvalue, options, rowObject) {
                        /*return "<img src='${pageContext.request.contextPath}/"+cellvalue+"' style='width:100px;height:60px'>";*/
                        return "<img style='width: 80px;height: 80px' src='"+cellvalue+"'>";
                    }
                },
                {name : 'status',index : 'amount',width : 80,align : "center",editable: true,
                    formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==1){
                            return "展示";
                        }else{
                            return "不展示";
                        }
                    },
                    editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:不展示"}
                },
                {name:"nickName",editable:true,}
            ],
            //multiselect:true,多选框
            height:"300px",
            autowidth:true,
            pager:"#gurupager",
            styleUI: "Bootstrap",
            viewrecords:true,
            caption : "上师管理",
            editurl: "${pageContext.request.contextPath}/guru/changeMeg"
        }).jqGrid("navGrid","#gurupager",{edit: true, add: true, del: true},
            {
                closeAfterEdit:true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var guruId = response.responseJSON.guruId;

                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/guru/uploadHttp",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{guruId:guruId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#gurutable").trigger("reloadGrid");
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
                    var guruId = response.responseJSON.guruId;

                    $.ajaxFileUpload({
                        //上传到哪
                        url: "${pageContext.request.contextPath}/guru/uploadHttp",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{guruId:guruId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#gurutable").trigger("reloadGrid");
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





<table id="gurutable"></table>

<div id="gurupager" style="height: 100px"></div>
