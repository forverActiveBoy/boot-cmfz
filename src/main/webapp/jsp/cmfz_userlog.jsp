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
	<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="doMultiDelete()">删除</a>
</div>


<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#bannerTable").datagrid({
            title: "日志列表",
            url: "${pageContext.request.contextPath}/adminLog/show",

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
                {title: "日志id", field: "logId", width:100},

                {
                    title: "进行操作", field: "logAction", width:100

                },

                {
                    title: "操作者", field: "adminUsername", width:100,
				},

                {
                    title: "操作时间", field: "logDate",
                },

                {
                    title: "ip", field: "logIp",
                },

                {
                    title: "操作结果", field: "logResult",
                },

            ]]

        });



    });

    function doMultiDelete() {
        var selectedRows = $("#bannerTable").datagrid("getSelections");
        if(selectedRows.length==0){
            //没有选中内容
            $.messager.alert("提示框","请选中要删除的内容","info");
        }else{
            $.messager.confirm("确认框","请确认是否要删除选中的内容",function(result){
                if(result){
                    //alert(0)

                    //把选中的内容的id获取到放在一个数组中
                    var selectedId = new Array(selectedRows.length);
                    for(var i=0;i<selectedRows.length;i++){
                        selectedId[i]=selectedRows[i].logId;
                    }

                    //发送ajax请求到后台，执行批量删除操作
                    $.ajax({
                        url:"${pageContext.request.contextPath}/adminLog/multiDel",
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
                                $.messager.alert("提示框","删除失败，请找人","warning");
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
