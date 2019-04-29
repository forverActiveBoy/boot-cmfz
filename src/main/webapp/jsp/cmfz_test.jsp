<%@page contentType="text/html;UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="../js/echarts.js"></script>
<script type="text/javascript" src="../js/echarts.min.js"></script>
<script type="text/javascript" src="../js/china.js"></script>
<script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

<%--引入两个css文件--%>
<link href="${pageContext.request.contextPath}/themes/icon.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/themes/default/easyui.css" rel="stylesheet" type="text/css">

<body>
<!-- 用户男女比例对比统计 -->
<div id="test" style="width: 600px;height:400px;"></div>

<%--统计过去三周的用户注册量变化--%>
<div id="test1" style="width: 600px;height:400px;"></div>

<div id="china" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('test'));

    // 指定图表的配置项和数据
    var option = {
        // 图形的标题
        title: {
            text: '持明法洲用户性别信息统计'
        },
        // 工具栏
        tooltip: {},
        // 定义图例的相关信息  对象
        legend: {
            data:['用户']
        },
        // x轴显示的坐标值  属性名
        xAxis: {
            data: ["男","女"]
        },
        yAxis: {}

    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);





    // 通过ajax请求后台数据 修改series中data数据  什么样的json
   /* $.ajax({
        url:"${pageContext.request.contextPath}/user/getSex",
        type:"get",
        dataType:"json",
        success:function (data) {
            option = {
                series: [{
                    // 用户图例的数据
                    name: '用户',
                    type: 'bar',
                    data: [data.boy, data.girl]
                }]
            }
            myChart.setOption(option);
        }
    });*/

    // 用户男女统计结束

    // 1.基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('test1'));

    // 2.指定图表的配置项和数据
    var option1 = {
        title: {
            text: '过去三周用户注册量变化'
        },
        tooltip: {},
        legend: {
            data:['注册量']
        },
        xAxis: {
            data: ["过去三周","过去两周","过去一周"]
        },
        yAxis: {},
        series: [{
            name: '注册量',
            type: 'line',
            data: [15, 10, 36]
        }]
    };

    // 3.使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
    // 此处写ajax请求


    // 用户注册量变化结束

    // 地图展示开始
    // 基于准备好的dom，初始化echarts实例
    var myChina = echarts.init(document.getElementById('china'));

    var option3 = {
        title : {
            text: '用户地区分布',
            left: 'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:['用户人数']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            left: 'right',
            top: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name: '用户人数',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                }
            }
        ]
    };
    myChina.setOption(option3)

    // 使用ajax请求数据
    /*$.ajax({
        url:"${pageContext.request.contextPath}/user/getProvince",
        type:"get",
        dataType:"json",
        success:function (data) {
            option3 = {
                series : [
                    {
                        name: '用户人数',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data
                    }
                ]
            };
            myChina.setOption(option3);
        }
    })*/

	// 整合ajax
	$.ajax({
        url:"${pageContext.request.contextPath}/user/getData",
        type:"get",
        dataType:"json",
        success:function (data) {

			// 设置性别数量
			option = {
                series: [{
                    // 用户图例的数据
                    name: '用户',
                    type: 'bar',
                    data: [data[data.length - 1].boy, data[data.length - 1].girl]
                }]
            }
            myChart.setOption(option);

            // 设置注册人数
            option1 = {
                series: [{
                    name: '注册量',
                    type: 'line',
                    data: [data[data.length - 1].earliest, data[data.length - 1].earlier, data[data.length - 1].last]
                }]
            }
            myChart1.setOption(option1);

            // 设置省份信息
            option3 = {
                series : [
                    {
                        name: '用户人数',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data
                    }
                ]
            };
            myChina.setOption(option3);
		}
	})

	/*消息推送*/
    var goEasy = new GoEasy({
        appkey: "BS-899a37c94dd4414494f17dd79026256a"
    });

    /*goEasy.subscribe({
        channel: "cmfzChannel",
        onMessage: function (message) {
            console.log("123");
            alert("Channel:" + message.channel + " content:" + message.content);
        }
    });*/



    goEasy.subscribe({
        channel: "cmfzChannel1",
        onMessage: function (message) {
            //因为 message 是 json  需要手动处理
            var data = JSON.parse(message.content);


            // 设置性别数量
            option = {
                series: [{
                    // 用户图例的数据
                    name: '用户',
                    type: 'bar',
                    data: [data[data.length - 1].boy, data[data.length - 1].girl],
                    markPoint : {
                        data : [
                            {type : 'max', name: '男'},
                            {type : 'min', name: '女'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '人数平均线'}
                        ]
                    }
                }]
            }
            myChart.setOption(option);

            // 设置注册人数
            myChart1.setOption({
                series : [
                    {
                        series: [{
                            name: '注册量',
                            type: 'line',
                            data: [data[data.length - 1].earliest, data[data.length - 1].earlier, data[data.length - 1].last]
                        }],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            });

            // 设置省份信息
            option3 = {
                series : [
                    {
                        name: '用户人数',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:data,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            myChina.setOption(option3);
        }
    });

</script>

</body>

