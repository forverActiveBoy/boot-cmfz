<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/6
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>$Title$</title>
	<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>

</head>
<body>


<table id="bannerTable"></table>

<div id="myTools">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toOpenAddDialog()">添加</a>
	<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="doMultiDelete()">更改图片状态</a>
</div>

<div id="updateDiv">
	<form method="post" id="updateForm">
		<input type="hidden" id="updateid" name="bannerId">
		图片：<input id="updateBanner_image_url" name="bannerImageUrl"/><br>
		名字：<input id="updateBanner_old_name" name="bannerOldName"/><br>
		描述：<input id="updateBanner_description" name="bannerDescription"/><br>
		<input type="button" value="修改" onclick="doUpdate()"/>
	</form>
</div>

<div id="addDiv">
	<form method="post" id="addForm" enctype="multipart/form-data">
		名字：<input type="text" name="bannerOldName"/><br>
		图片：<input type="text" name="bannerImageUrl"/><br/>
		描述：<input type="text" name="bannerDescription"/><br/>
		<input type="button" value="添加" onclick="doAdd()"/>
	</form>
</div>


<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#bannerTable").datagrid({
            title: "轮播图列表",
            url: "${pageContext.request.contextPath}/banner/show",

            // 让datagrid做分页
            pagination: true,

            // 工具栏
            toolbar:"#myTools",
            onDblClickRow:function(rowIndex, rowData){
                //打开修改对话
                //把原本的内容写入到修改对话框中对应的输入框中，显示出来
                toOpenUpdateDialog(rowIndex,rowData);

            },

            columns: [[
                //一个对象表示的是一列==》一个{}表示一列
                {checkbox: true, field: "bannerId"},
                {title: "图片名", field: "bannerOldName"},

                {
                    title: "图片使用情况", field: "bannerState",
                    formatter: function (value, rowData, rowIndex) {
                        //value:值
                        if (value == 1) {
                            return "未使用";
                        } else {
                            return "正在使用";
                        }
                    }
                },

                {
                    title: "图片上传时间", field: "bannerDate",
                    formatter: function (value, rowData, rowIndex) {
                        //value:值
                        if (value == null) {
                            return "未添加，请添加";
                        } else {
                            return value;
                        }
                    }
                },
                {title: "图片描述", field: "bannerDescription"},

            ]]

        });


        //修改②：把修改div标签转换成dialog控件效果
        $("#updateDiv").dialog({
            closed:true,
            width:280,
            title:"修改对话框"
        })

        $("#addDiv").dialog({
            closed:true,
            width:280,
            title:"增加对话框"
        })

    });

    function toOpenUpdateDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#updateDiv").dialog("open");

        //显示原始数据
        $("#updateid").val(rowData.bannerId);
        $("#updateBanner_image_url").val(rowData.bannerImageUrl);
        $("#updateBanner_old_name").val(rowData.bannerOldName);
        $("#updateBanner_description").val(rowData.bannerDescription);

    }
    function toOpenAddDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#addDiv").dialog("open");
    }

    //修改⑤：点击提交按钮，执行修改操作
    function doUpdate() {
        //通过form控件，以ajax的方式提交表单
        $("#updateForm").form("submit",{
            url:"${pageContext.request.contextPath}/banner/update",
            success:function(data){
                //如果提交成功：1.关闭修改对话框（dialog的close方法）；
                // 2.刷新datagrid（datagrid的reload/load）
                //如果失败，弹框提示
                if(data){
                    $("#updateDiv").dialog("close");
                    $("#bannerTable").datagrid("reload");
                }else{
                    //alert("修改失败，请确认")
                    $.messager.alert("修改提示框","修改失败，请确认","warning");
                }
            }
        })
    }

    function doAdd() {
        //通过form控件，以ajax的方式提交表单
        $("#addForm").form("submit",{
            url:"${pageContext.request.contextPath}/banner/insert",
            success:function(data){
                //如果提交成功：1.关闭修改对话框（dialog的close方法）；
                // 2.刷新datagrid（datagrid的reload/load）
                //如果失败，弹框提示
                if(data=="true"){
                    $("#addDiv").dialog("close");
                    $("#bannerTable").datagrid("reload");
                }else{
                    //alert("修改失败，请确认")
                    $.messager.alert("修改提示框","添加失败，请确认","warning");
                }
            }
        })
    }

    function doMultiDelete() {
        var selectedRows = $("#bannerTable").datagrid("getSelections");
        if(selectedRows.length==0){
            //没有选中内容
            $.messager.alert("更改状态提示框","请选中要删除的内容","info");
        }else{
            $.messager.confirm("更改状态确认框","请确认是否要修改图片状态",function(result){
                if(result){
                    //alert(0)

                    //把选中的内容的id获取到放在一个数组中
                    var selectedId = new Array(selectedRows.length);
                    for(var i=0;i<selectedRows.length;i++){
                        selectedId[i]=selectedRows[i].bannerId;
                    }

                    //发送ajax请求到后台，执行批量删除操作
                    $.ajax({
                        url:"${pageContext.request.contextPath}/banner/multiDel",
                        //data:"userIds="+selectedId,
                        data:{"ids":selectedId},//这样的写法，jQuery会对参数做处理==》jQuery深度序列化
                        traditional:true,
                        //dataType:"json",
                        success:function(data){
                            var isOk = true;
                            if(data){
                                $.messager.alert("更改状态提示框","更改状态成功","info");
                                $("#bannerTable").datagrid("reload")
                            }else{
                                $.messager.alert("更改状态提示框","更改状态失败，请确认","warning");
                            }

                        }
                    })
                }
            });
        }
    }


</script>

</body>
</html>
