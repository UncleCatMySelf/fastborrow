<html>
<#include "common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
    <#--边栏-->
    <#include "common/nav.ftl">

    <#--主要内容-->
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="row clearfix">
                        <div class="col-md-8 column" style="height: 100%; margin: 0">
                            <h3>
                                数据分析模块
                            </h3>
                            <div id="container" style="height: 100%"></div>
                            <input hidden id="list" value="${listStr}">
                        </div>
                        <div class="col-md-4 column">
                            <h3>
                                墨书快借搜索Top10
                            </h3>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>
                                        关键字
                                    </th>
                                    <th>
                                        搜索次数
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list redisSearchVOList as redisSearchVO>
                                <tr class="success" id="${redisSearchVO}">
                                    <td>
                                    ${redisSearchVO.key}
                                    </td>
                                    <td>
                                    ${redisSearchVO.num}
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
    <script type="text/javascript">
        var dom = document.getElementById("container");
        var list = document.getElementById("list");
        //console.log(list.value);
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        var option = {
            title: {
                text: '历史前10热门搜索关键字',
                subtext: '墨书科技',
                left: 'center'
            },
            dataset: {source: [[ 'product','amount'],${listStr}]},
            grid: {containLabel: true},
            xAxis: {name: 'amount'},
            yAxis: {type: 'category'},
            series: [
                {
                    type: 'bar',
                    encode: {
                        // Map the "amount" column to X axis.
                        x: 'amount',
                        // Map the "product" column to Y axis
                        y: 'product'
                    }
                }
            ]
        };
        ;
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    </script>
</body>
</html>