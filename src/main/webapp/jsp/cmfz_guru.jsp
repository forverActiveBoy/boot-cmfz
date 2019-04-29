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
	<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="doMultiDelete()">冻结/解冻</a>
	<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="doOutput()">数据导出</a>
	<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="doInput()">导入数据</a>
</div>

<div id="outputDiv">
	<form method="post" id="outputForm">
		请输入要存储文件的名字:<input id="outputName" type="text" name="fileName">
		<input type="button" value="导出" onclick="outPut()"/>
	</form>
</div>

<div id="inputDiv">
	<form method="post" id="inputForm" enctype="multipart/form-data">
		请选择文件:<input id="inputName" type="file" name="file">
		<input type="button" value="导入" onclick="inPut()"/>
	</form>
</div>

<div id="updateDiv">
	<form method="post" id="updateForm" enctype="multipart/form-data">
		<input type="hidden" id="updateid" name="guruId">
		图片：<input type="file" id="updateBanner_image_url" name="name"/><br>
		名字：<input id="updateBanner_old_name" name="guruName"/><br>
		法号：<input id="updateBanner_description" name="guruNickname"/><br>
		<input type="button" value="修改" onclick="doUpdate()"/>
	</form>
</div>

<div id="addDiv">
	<form method="post" id="addForm" enctype="multipart/form-data">
		名字：<input name="guruName"/><br>
		图片：<input type="file" name="name"/><br/>
		法号：<input name="guruNickname"/><br/>
		<input type="button" value="添加" onclick="doAdd()"/>
	</form>
</div>


<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#bannerTable").datagrid({
            title: "上师列表",
            url: "${pageContext.request.contextPath}/guru/getAllGuruByLimit",

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
                {checkbox: true, field: "Id", width:100},
                {title: "上师名", field: "guruName", width:100},

                {
                    title: "用户昵称", field: "guruNickname", width:100

                },

                {
                    title: "上师图片", field: "guruImage", width:100,
                    formatter: function (value, rowData, rowIndex) {
                        // alert(value);
						return "<img style='width:50;length:50' src='${pageContext.request.contextPath}/image/" + value + "'/>";
                    }
				},

                {
                    title: "个人状态", field: "guruStatus",
                    formatter: function (value, rowData, rowIndex) {
                        //value:值
                        if (value == 1) {
                            return "正常";
                        } else {
                            return "已冻结";
                        }
                    }
                }


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

        $("#outputDiv").dialog({
            closed:true,
            width:280,
            title:"导出对话框"
        })

        $("#inputDiv").dialog({
            closed:true,
            width:280,
            title:"导出对话框"
        })

    });

    function toOpenUpdateDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#updateDiv").dialog("open");

        //显示原始数据
        $("#updateid").val(rowData.guruId);
        $("#updateBanner_old_name").val(rowData.guruName);
        $("#updateBanner_description").val(rowData.guruNickname);

    }
    function toOpenAddDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#addDiv").dialog("open");
    }

    //修改⑤：点击提交按钮，执行修改操作
    function doUpdate() {
        //通过form控件，以ajax的方式提交表单
        $("#updateForm").form("submit",{
            url:"${pageContext.request.contextPath}/guru/updateGuru",
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
            url:"${pageContext.request.contextPath}/guru/insertGuru",
            success:function(data){
                //如果提交成功：1.关闭修改对话框（dialog的close方法）；
                // 2.刷新datagrid（datagrid的reload/load）
                //如果失败，弹框提示
                if(data=="true"){
                    $("#addDiv").dialog("close");
                    $("#bannerTable").datagrid("reload");
                }else{
                    //alert("修改失败，请确认")
                    $.messager.alert("修改提示框","修改失败，请确认","warning");
                }
            }
        })
    }

    function doMultiDelete() {
        var selectedRows = $("#bannerTable").datagrid("getSelections");
        if(selectedRows.length==0){
            //没有选中内容
            $.messager.alert("提示框","请选中要修改的内容","info");
        }else{
            $.messager.confirm("确认框","请确认是否要修改选中的内容",function(result){
                if(result){
                    //alert(0)

                    //把选中的内容的id获取到放在一个数组中
                    var selectedId = new Array(selectedRows.length);
                    for(var i=0;i<selectedRows.length;i++){
                        selectedId[i]=selectedRows[i].guruId;
                    }

                    //发送ajax请求到后台，执行批量删除操作
                    $.ajax({
                        url:"${pageContext.request.contextPath}/guru/multiDel",
                        //data:"userIds="+selectedId,
                        data:{"ids":selectedId},//这样的写法，jQuery会对参数做处理==》jQuery深度序列化
                        traditional:true,
                        //dataType:"json",
                        success:function(data){
                            var isOk = true;
                            if(data){
                                $.messager.alert("提示框","冻结/解冻成功","info");
                                $("#bannerTable").datagrid("reload");
                            }else{
                                $.messager.alert("提示框","冻结/解冻失败，请确认","warning");
                            }

                        }
                    })
                }
            });
        }
    }

    function doOutput() {
        //打开导出对话框；调用dialog的open方法，把对话框打开
        $("#outputDiv").dialog("open");
    }

    function outPut() {
        var fileName = $("#outputName").val();
        //发送ajax请求到后台，执行批量删除操作
        $.ajax({
            url:"${pageContext.request.contextPath}/guru/output",
            //data:"userIds="+selectedId,
            data:{"fileName":fileName},//这样的写法，jQuery会对参数做处理==》jQuery深度序列化
            traditional:true,
            //dataType:"json",
            success:function(data){
                if(data){
                    $.messager.alert("提示框","导出成功，请在'F://服务器'路径下查看名为"+ fileName +"的文件","info");
                    $("#outputDiv").dialog("close");
                }else{
                    $.messager.alert("提示框","导出失败，请找人解决","warning");
                }

            }
        })
    }

    function doInput() {
        //打开导出对话框；调用dialog的open方法，把对话框打开
        $("#inputDiv").dialog("open");
    }

    function inPut() {
        $("#inputForm").form("submit",{
            url:"${pageContext.request.contextPath}/guru/input",
            success:function(data){
                //如果提交成功：1.关闭修改对话框（dialog的close方法）；
                // 2.刷新datagrid（datagrid的reload/load）
                //如果失败，弹框提示
                if(data=="true"){
                    $("#inputDiv").dialog("close");
                    $("#bannerTable").datagrid("reload");
                    $.messager.alert("导入提示框","导入成功","info");
                }else{
                    //alert("修改失败，请确认")
                    $.messager.alert("导入提示框","导入失败，快找人修理","warning");
                }
            }
        })
    }

</script>

</body>
</html>
