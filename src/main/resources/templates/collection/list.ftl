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
                                藏书阁用户列表（默认降序）
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>用户id</th>
                                    <th>用户名称</th>
                                    <th>藏书阁数目</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list userPage.content as user>
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.userName}</td>
                                    <td>${user.collectionNum}</td>
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
                            <li><a href="/fastborrow/admin/collection/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#list 1..userPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/collection/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte userPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/collection/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>