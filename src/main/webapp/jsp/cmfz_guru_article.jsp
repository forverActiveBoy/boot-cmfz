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
	输入要查询的关键字：<input id="inName" type="text" name="keyWord">
	<input type="submit" value="搜索" onclick="aaa()">
	<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toOpenAddDialog()">添加</a>
</div>

<div id="updateDiv">
	<form method="post" id="updateForm" enctype="multipart/form-data">
		<input type="hidden" id="updateid" name="articleId">
		文章图片：<input type="file" id="updateBanner_image_url" name="name"/><br>
		文章标题：<input id="updateBanner_old_name" name="articleName"/><br>
		文章内容：<input id="updateBanner_description" name="articleContent"/><br>
		作者：<select id="select" value="-1">
		<option value="-1">-请选择对应上师-</option>
	</select>
		<input type="button" value="修改" onclick="doUpdate()"/>
	</form>
</div>

<div id="addDiv">
	<form method="post" id="addForm" enctype="multipart/form-data">
		文章标题：<input id="addName" name="articleName"/><br>
		文章图片：<input id="addFile" type="file" name="name"/><br/>
		文章内容：<input id="addContent" name="articleContent"/><br/>
		作者：<select id="addSelect" name="guruId">
		<option value="-1">-请选择上师-</option>
	</select>
		<input type="button" value="添加" onclick="doAdd()"/>
	</form>
</div>


<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#bannerTable").datagrid({
            title: "上师列表",
            url: "${pageContext.request.contextPath}/article/getAllArticleByLimit",

            // 让datagrid做分页
            pagination: true,

            // 工具栏
            toolbar:"#myTools",
            onDblClickRow:function(rowIndex, rowData){
                $("#select").html("<option value='-1'>-请选择对应上师-</option>");

                $.ajax({
                    url:"${pageContext.request.contextPath}/guru/all",
                    type:"post",
                    success:function (data) {
                        for (var i = 0; i < data.length; i++) {
                            $("#select").append("<option value='"+data[i].guruId+"'>"+data[i].guruNickname+"</option>");
                        }
                    }
                });


                //打开修改对话
                //把原本的内容写入到修改对话框中对应的输入框中，显示出来
                toOpenUpdateDialog(rowIndex,rowData);

            },

            columns: [[
                //一个对象表示的是一列==》一个{}表示一列
                {checkbox: true, field: "Id", width:100},
                {title: "文章标题", field: "articleName", width:250},

                {
                    title: "文章内容", field: "articleContent", width:520

                },

                {
                    title: "文章图片", field: "articleImage", width:100,
                    formatter: function (value, rowData, rowIndex) {
                        // alert(value);
                        return "<img style='width:50;length:50' src='${pageContext.request.contextPath}/image/" + value + "'/>";
                    }
				},

                {
                    title: "作者", field: "guruId",
                    formatter: function (value, rowData, rowIndex) {
                        //value:值
                        if (value == 1) {
                            return "宗萨仁波切";
                        } else if (value == 2) {
                            return "土登尼玛仁波切";
                        } else if (value == 3) {
                            return "十七世大宝法王";
                        } else if (value == 4) {
                            return "创巴仁波切";
                        } else if (value == 5) {
                            return "上师仁波切";
                        } else if (value == 6) {
                            return "索达吉堪布";
                        } else {
                            return "未知";
						}
                    }
                },

                {
                    title: "文章上传时间", field: "articleDate"
                }


            ]]

        });

        //修改②：把修改div标签转换成dialog控件效果
        $("#updateDiv").dialog({
            closed:true,
            width:320,
            title:"修改对话框"
        })

        $("#addDiv").dialog({
            closed:true,
            width:320,
            title:"增加对话框"
        })


    });

    function toOpenUpdateDialog(rowIndex,rowData) {


        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#updateDiv").dialog("open");

        //显示原始数据
        $("#updateid").val(rowData.articleId);
        $("#updateBanner_old_name").val(rowData.articleName);
        $("#updateBanner_description").val(rowData.articleContent);


    }
    function toOpenAddDialog(rowIndex,rowData) {
        //打开修改对话框；调用dialog的open方法，把对话框打开
        $("#addDiv").dialog("open");

        // 清除原来的数据
        $("#addName").val("");
        $("#addContent").val("");
        $("#addFile").val("");
        $("#addSelect").html("<option value='-1'>-请选择对应上师-</option>");

        $.ajax({
            url:"${pageContext.request.contextPath}/guru/all",
            type:"post",
            success:function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#addSelect").append("<option value='"+data[i].guruId+"'>"+data[i].guruName+"</option>");
                }
            }
        });

    }

    //修改⑤：点击提交按钮，执行修改操作
    function doUpdate() {
        //通过form控件，以ajax的方式提交表单
        $("#updateForm").form("submit",{
            url:"${pageContext.request.contextPath}/article/updateArticle",
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
            url:"${pageContext.request.contextPath}/article/insertArticle",
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

    function aaa() {
        /*设置搜索*/

        var name = $("#inName").val()
        $("#bannerTable").datagrid("load",{"keyWord":name});

    }


</script>

</body>
</html>
