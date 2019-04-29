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
	<script type="text/javascript" src="../js/datagrid-detailview.js"></script>

	<script>
        $(function () {
            $('#tt').treegrid({

                url:'${pageContext.request.contextPath}/lesson/show',
                idField:'lessonId',
                treeField:'lessonName',
                method:"get",
                // columns
                columns:[[
                    {title:'名称',field:'lessonName',width:180},
                    {field:'userId',title:'对应用户id',width:80},
                    {field:'counterCount',title:'计数',width:80},
                ]],
                toolbar:[{
                    text: "功课详情",
                    iconCls: 'icon-edit',
                    handler: function () {
                        // 1.通过getSelect方法获取被选中行  可以获取到id
                        // 2.通过id查询到对应的数据
                        // 3.打开一个对话框显示被选中的数据


                        var s = $("#tt").treegrid("getSelected");
                        $.ajax({
                            url:"${pageContext.request.contextPath}/lesson/getOne",
                            data:"lessonId="+s.lessonId,
                            type:"post",
                            success:function (data) {

                                $("#lessonId").html(data.lessonId);
                                $("#lessonName").html(data.lessonName);
                                if (data.userId != null) {
                                    $("#isMust").html("否");
                                    $("#userId").html(data.userId);
                                } else {
                                    $("#isMust").html("是");
                                    $("#userId").html("必修课没有用户id");
                                }
                            }
                        });
                        openShowAlbumDetailsDiv();

                    }
                }, '-', {
                    text: "添加必修课",
                    iconCls: 'icon-help',
                    handler: function () {
                        $("#addAlbumDiv").dialog("open");
                    }
                }, '-', {
                    text: "修改必修课",
                    iconCls: 'icon-help',
                    handler: function () {
						// 1.获取到行
                        // 2.判断是否是必修
                        var s = $("#tt").treegrid("getSelected");
                        $.ajax({
                            url:"${pageContext.request.contextPath}/lesson/getOne",
                            data:"lessonId="+s.lessonId,
                            type:"post",
                            success:function (data) {

                                if (data.userId != null) {
                                    alert("选择的不是必修课，请重新选择");
                                } else {
                                    $("#addAudioDiv").dialog("open");
                                    $("#lessonUpdateId").html(data.lessonId);
                                    $("#lessonUpdateIId").val(data.lessonId);
                                    $("#lessonUpdateName").val(data.lessonName);
                                }
                            }
                        });

                    }
                }, '-', {
                    text:"删除选中必修课",
					iconCls:'icon-edit',
					handler: function () {

                        var s = $("#tt").treegrid("getSelected");

                        if (s == null) {
                            alert("没有选中内容，请先选择内容");
						}

						// 判断是不是必修课
                        $.ajax({
                            url:"${pageContext.request.contextPath}/lesson/getOne",
                            data:"lessonId="+s.lessonId,
                            type:"post",
                            success:function (data) {

                                if (data.userId != null) {
                                    alert("选择的不是必修课，请重新选择")
                                } else {
                                    if (confirm("确定删除？")) {
                                        $.ajax({
                                            url:"${pageContext.request.contextPath}/lesson/delete",
                                            data:"id="+s.lessonId,
                                            type:"post",
                                            success:function (data) {

                                                if (data){
                                                    $.messager.alert("提示框","删除成功","info");
                                                    $("#tt").treegrid("reload");
                                                } else {
                                                    //如果失败、弹框提示
                                                    $.messager.alert("提示框","删除失败，请确认","warning");
                                                }

                                            }
                                        });
                                    }
                                }
                            }
                        });

                    }
				}
                ]
            });

            $("#showAlbumDetails").dialog({
                //默认不显示在页面中
                closed:true,
                width:350,
                height:350,
                title:"专辑信息详情框"
            });

            $("#addAlbumDiv").dialog({
                //默认不显示在页面中
                closed:true,
                width:350,
                height:350,
                title:"添加课程"
            });
            $("#addAudioDiv").dialog({
                //默认不显示在页面中
                closed:true,
                width:350,
                height:350,
                title:"添加计数器"
            });
        })

        function openShowAlbumDetailsDiv() {
            $("#showAlbumDetails").dialog("open");
        }
        function doAddAlbum() {
            $("#addAlbumForm").form("submit",{
                url:"${pageContext.request.contextPath}/lesson/insert",
                success:function (data) {
                    if (data){
                        $("#addAlbumDiv").dialog("close");
                        $("#tt").treegrid("reload");
                        $.messager.alert("提示框","添加成功","info");
                    } else {
                        //如果失败、弹框提示
                        $.messager.alert("提示框","添加失败","warning");
                    }
                }
            });
        }
        function doUpdate() {
            $("#addAudioForm").form("submit",{
                url:"${pageContext.request.contextPath}/lesson/update",
                success:function (data) {
                    if (data){
                        $("#addAudioDiv").dialog("close");
                        $("#tt").treegrid("reload");
                        $.messager.alert("提示框","修改成功","info");
                    } else {
                        //如果失败、弹框提示
                        $.messager.alert("提示框","修改失败","warning");
                    }
                }
            });
        }
	</script>

</head>
<body>

<table id="tt" style="width:600px;height:400px"></table>

<div id="showAlbumDetails">
	课程id：<u id="lessonId"></u><br/>
	课程名：<u id="lessonName"></u><br/>
	是否是必修课：<u id="isMust"></u><br/>
	对应用户id：<u id="userId"></u><br/>
</div>

<div id="audio_dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
	 data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
	<audio id="audio_id" src="" autoplay="autoplay" controls="controls"></audio>
</div>

<div id="addAlbumDiv">
	<form id="addAlbumForm" method="post" enctype="multipart/form-data">
		课程名：<input type="text" id="lessonAddName" name="lessonName"><br/>
		<input type="button" value="添加必修课" onclick="doAddAlbum()"/>
	</form>
</div>

<div id="addAudioDiv">
	<form id="addAudioForm" method="post" enctype="multipart/form-data">
		课程id：<ul id="lessonUpdateId"></ul><br/>
		<input type="hidden" id="lessonUpdateIId" name="lessonId">
		课程名：<input type="text" id="lessonUpdateName" name="lessonName"><br/>
		<input type="button" value="提交" onclick="doUpdate()">
	</form>
</div>

</body>
</html>
