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
	<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="doMultiDelete()">删除</a>
		输入要查询的管理员名：<input id="inName" type="text" name="username">
		<input type="submit" value="搜索" onclick="aaa()">

</div>

<div id="updateDiv">
	<form method="post" id="updateForm" enctype="multipart/form-data">
		管理员id：<ul id="updateId"></ul><br/>
		<input type="hidden" id="updateIId" name="id"/><br>
		管理员邮箱：<input id="updateEmail" name="username"/><br>
		管理员密码：<input id="updatePwd" type="password" name="password"/><br>
		确认密码：<input id="updateConfirmPwd" type="password"/><br>
		<input type="button" value="修改" onclick="doUpdate()"/>
	</form>
</div>

<div id="addDiv">
	<form method="post" id="addForm" enctype="multipart/form-data">
		管理员邮箱：<input id="addEmail" name="username"/><br>
		管理员密码：<input id="addPwd" type="password" name="password"/><br>
		确认密码：<input id="addConfirmPwd" type="password"/><br>
		<input type="button" value="添加" onclick="doAdd()"/>
	</form>
</div>


<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#bannerTable").datagrid({
            title: "上师列表",
            url: "${pageContext.request.contextPath}/admin/getAllAdminByLimit",

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
                {title: "管理员id", field: "id", width:100},

                {
                    title: "管理员邮箱", field: "username", width:100

                },

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
        $("#updateId").html(rowData.id);
        $("#updateIId").val(rowData.id);
        $("#updateEmail").val(rowData.username);
        $("#updatePwd").val(rowData.password);

    }
    function toOpenAddDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#addDiv").dialog("open");
    }

    //修改⑤：点击提交按钮，执行修改操作
    function doUpdate() {
        var pwd = $("#updatePwd").val();
        var confirmPwd = $("#updateConfirmPwd").val();

        if (pwd == confirmPwd) {
            //通过form控件，以ajax的方式提交表单
            $("#updateForm").form("submit",{
                url:"${pageContext.request.contextPath}/admin/updateAdmin",
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
            });
        } else {
            alert("两次输入的密码不一致，请重新输入");
        }

    }

    function doAdd() {
        var pwd = $("#addPwd").val();
        var confirmPwd = $("#addConfirmPwd").val();

        if (pwd == confirmPwd) {
            //通过form控件，以ajax的方式提交表单
            $("#addForm").form("submit",{
                url:"${pageContext.request.contextPath}/admin/insertAdmin",
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
		} else {
            alert("两次输入的密码不一致，请重新输入");
        }

    }

    function doMultiDelete() {
        var selectedRows = $("#bannerTable").datagrid("getSelections");
        if(selectedRows.length==0){
            //没有选中内容
            $.messager.alert("提示框","请选中要删除的内容","info");
        }else{
            $.messager.confirm("确认框","真的要删除么，删掉数据可就找不回来了",function(result){
                if(result){
                    //alert(0)

                    //把选中的内容的id获取到放在一个数组中
                    var selectedId = new Array(selectedRows.length);
                    for(var i=0;i<selectedRows.length;i++){
                        selectedId[i]=selectedRows[i].id;
                    }

                    //发送ajax请求到后台，执行批量删除操作
                    $.ajax({
                        url:"${pageContext.request.contextPath}/admin/deleteAdminByIds",
                        //data:"userIds="+selectedId,
                        data:{"ids":selectedId},//这样的写法，jQuery会对参数做处理==》jQuery深度序列化
                        traditional:true,
                        //dataType:"json",
                        success:function(data){
                            var isOk = true;
                            if(data){
                                $.messager.alert("提示框","删除成功","info");
                                $("#bannerTable").datagrid("reload");
                            }else{
                                $.messager.alert("提示框","删除失败，请确认","warning");
                            }

                        }
                    })
                }
            });
        }
    }

    function aaa() {
        /*设置搜索*/

            var name = $("#inName").val()
            $("#bannerTable").datagrid("load",{"username":name});

    }

</script>

</body>
</html>
