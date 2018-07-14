<html>
    <#include "../common/header.ftl">
    <body>

        <div id="wrapper" class="toggled">

            <#--边栏-->
            <#include "../common/nav.ftl">

            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <h3>
                                微信用户信息列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <form role="form" id="rec" class="form-horizontal">
                                <input hidden type="number" name="statsInt" id="statsInt">
                                <input hidden type="number" name="statsBal" id="statsBal">
                                <input hidden type="number" name="statsDep" id="statsDep">
                                <button type="button" class="col-sm-3 btn btn-default" onclick="byint()">积分降序</button>
                                <button type="button" class="col-sm-3 btn btn-default" onclick="bydep()">押金降序</button>
                                <button type="button" class="col-sm-3 btn btn-default" onclick="bybal()">余额降序</button>
                                <button type="button" class="col-sm-3 btn btn-default" onclick="byreset()">重置</button>
                            </form>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>用户Id</th>
                                    <th>用户名</th>
                                    <th>积分</th>
                                    <th>押金</th>
                                    <th>余额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list userPage.content as user>
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.userName}</td>
                                    <td>${user.integral}</td>
                                    <td>${user.userDeposit}</td>
                                    <td>${user.userBalance}</td>

                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/user/morelist?page=${currentPage - 1}&size=${size}&statsInt=${statsInt}&statsBal=${statsBal}&statsDep=${statsDep}">上一页</a></li>
                        </#if>

                        <#list 1..userPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/user/morelist?page=${index}&size=${size}&statsInt=${statsInt}&statsBal=${statsBal}&statsDep=${statsDep}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte userPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/user/morelist?page=${currentPage + 1}&size=${size}&statsInt=${statsInt}&statsBal=${statsBal}&statsDep=${statsDep}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function byint() {
                var value = 1;
                var bookMessIdList = document.getElementById('statsInt');
                bookMessIdList.value = value;
                document.getElementById('rec').action = "/fastborrow/admin/user/morelist";
                document.getElementById('rec').method = "GET";
                document.getElementById('rec').submit();
            }
            function bybal() {
                var value = 1;
                var bookMessIdList = document.getElementById('statsBal');
                bookMessIdList.value = value;
                document.getElementById('rec').action = "/fastborrow/admin/user/morelist";
                document.getElementById('rec').method = "GET";
                document.getElementById('rec').submit();
            }
            function bydep() {
                var value = 1;
                var bookMessIdList = document.getElementById('statsDep');
                bookMessIdList.value = value;
                document.getElementById('rec').action = "/fastborrow/admin/user/morelist";
                document.getElementById('rec').method = "GET";
                document.getElementById('rec').submit();
            }
            function byreset() {
                document.getElementById('rec').action = "/fastborrow/admin/user/morelist";
                document.getElementById('rec').method = "GET";
                document.getElementById('rec').submit();
            }

        </script>
    </body>
</html>