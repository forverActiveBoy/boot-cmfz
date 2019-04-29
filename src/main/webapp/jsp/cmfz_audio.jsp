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

<div id="audio_dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
	 data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
	<audio id="audio_id" src="${pageContext.request.contextPath}/audio/Lemon.mp3" autoplay="autoplay" controls="controls"></audio>
</div>

<%--下面是js--%>
<script type="text/javascript">
    $(function(){
        $("#audio_dd").dialog({
            //默认不显示在页面中
            closed:true,
            width:350,
            height:100,
            title:"播放框"
        });

        $("#bannerTable").datagrid({
            title: "音频列表(双击播放)",
            url: "${pageContext.request.contextPath}/audio/getAllAudioByLimit",

            // 让datagrid做分页
            pagination: true,

            // 工具栏
            toolbar:"#myTools",
            onDblClickRow:function(rowIndex, rowData){
                // 1.使用双击方法  能够获取被双击行的信息 例如拿到链接
                $("#audio_dd").dialog("open");
                // 2.打开一个对话框 对话框中只有audio标签  更改标签的src属性为音频url
                // $("#audio_id").prop("${pageContext.request.contextPath}/audio/Lemon.mp3");

            },

            columns: [[
                //一个对象表示的是一列==》一个{}表示一列
                {checkbox: true, field: "userId"},
                {title: "音频id", field: "audioId"},

                {
                    title: "音频名", field: "audioName",

                },

                {
                    title: "音频路径", field: "audioUrl",

				},

                {
                    title: "音频大小", field: "audioSize",
                }


            ]]

        });



    });




</script>

</body>
</html>
