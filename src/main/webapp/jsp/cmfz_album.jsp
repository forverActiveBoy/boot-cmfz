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
            url:'${pageContext.request.contextPath}/album/show',
                idField:'albumId',
                treeField:'albumName',
                method:"get",
                // columns
                columns:[[
                {title:'名称',field:'albumName',width:180},
                {field:'audioUrl',title:'地址',width:350,align:'right'},
                {field:'audioSize',title:'大小',width:80},
            ]],
                toolbar:[{
                text: "专辑详情",
                iconCls: 'icon-edit',
                handler: function () {


                    // 1.通过getSelect方法获取被选中行  可以获取到id
                    // 2.通过id查询到对应的数据
                    // 3.打开一个对话框显示被选中的数据
                    var s = $("#tt").treegrid("getSelected");
                    if (s == undefined) {
                        alert("未选择内容，请先选择内容");

                    }
                    $.ajax({
                        url:"${pageContext.request.contextPath}/album/getOne",
                        data:"id="+s.albumId,
                        type:"post",
                        success:function (data) {

                            $("#albumId").html(data.albumId);
                            $("#albumName").html(data.albumName);
                            $("#albumAuthor").html(data.albumAuthor);
                            $("#updateTime").html(data.albumContent);
                            $("#albumContext").html(data.lessonName);

                            if (data.albumStar == 1) {
                                $("#startNum").html("*");
                            } else if (data.albumStar == 2) {
                                $("#startNum").html("**");
                            } else if (data.albumStar == 3) {
                                $("#startNum").html("***");
                            } else if (data.albumStar == 4) {
                                $("#startNum").html("****");
                            } else if (data.albumStar == 5) {
                                $("#startNum").html("*****");
                            } else {
                                $("#startNum").html("未评价");
                            }

                        }
                    });
                    openShowAlbumDetailsDiv();
                }
            }, '-', {
                text: "添加专辑",
                iconCls: 'icon-help',
                handler: function () {
                    $("#addAlbumDiv").dialog("open");
                }
            }, '-', {
                text: "添加章节",
                iconCls: 'icon-help',
                handler: function () {
                    // 1.获取到行
                    // 2.判断是专辑还是音频
                    $("#addAudioDiv").dialog("open");
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
                title:"添加专辑"
            });

            $("#addAudioDiv").dialog({
                //默认不显示在页面中
                closed:true,
                width:350,
                height:350,
                title:"添加目录"
            });

        })


        function openShowAlbumDetailsDiv() {
            $("#showAlbumDetails").dialog("open");
        }

        function doAddAlbum() {
            var content = $("#addAlbumContext").html();

            $("#addAlbumForm").form("submit",{
                url:"${pageContext.request.contextPath}/album/insertAlbum",
                onSubmit: function(param){
                    param.album = content;
                },
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
                url:"${pageContext.request.contextPath}/audio/insertAudio",
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
	专辑id：<u id="albumId"></u><br/>
	专辑名：<u id="albumName"></u><br/>
	专辑作者：<u id="albumAuthor"></u><br/>
	上传时间：<u id="updateTime"></u><br/>
	专辑描述：<u id="albumContext"></u>
	星级：<u id="startNum"></u><br/>
</div>

<div id="addAlbumDiv">
	<form id="addAlbumForm" method="post" enctype="multipart/form-data">
		专辑名：<input type="text" id="addAlbumName" name="albumName"><br/>
		专辑作者：<input type="text" id="addAlbumAuthor" name="albumAuthor"><br/>
		讲述员：<input type="text" id="addTeller" name="albumTeller"><br/>
		集数：<input type="text" id="addNum" name="albumEpisodes"><br/>
		专辑描述：<textarea type="text" id="addAlbumContext" name="albumContent"></textarea><br/>
		上传封面：<input type="hidden" name="albumImage" value="/audioCollection/A-1.jpg">
		<input type="file" ><br/>
		星级：<select id="addSelect" name="albumStar">
		<option value="-1">-请选择星级-</option>
		<option value="1">-*-</option>
		<option value="2">-**-</option>
		<option value="3">-***-</option>
		<option value="4">-****-</option>
		<option value="5">-*****-</option>
		</select>
		<input type="button" value="添加专辑" onclick="doAddAlbum()"/>
	</form>
</div>

<div id="addAudioDiv">
	<form id="addAudioForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="lessonId" value="0">
		音频名：<input type="text" id="addAudioName" name="audioName"><br/>
		音频大小：<input type="text" id="addAudioSize" name="audioSize"><br/>
		上传音频：<input type="hidden" name="audioUrl" value="123123.mp3">
		<input type="file"><br/>
		选择对应专辑：<select id="albumIid" name="albumId">
		<option value="-1">-请选择对应专辑-</option>
		<option value="1">甘露明珠</option>
		<option value="2">米拉日巴尊者传</option>
		<option value="3">密勒日巴道歌集</option>
		<option value="4">塔洛仁波切念诵</option>
		<option value="5">至尊香根仁波切念诵</option>
		<option value="6">多智钦仁波切念诵</option>
		<option value="7">123</option>
	</select><br>
		<input type="button" value="提交" onclick="doUpdate()">
	</form>
</div>

</body>
</html>
