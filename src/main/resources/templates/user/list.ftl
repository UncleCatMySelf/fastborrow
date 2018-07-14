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
                            <form role="form" class="form-horizontal" action="/fastborrow/admin/user/list">
                                <div class="form-group">
                                    <label for="userId" class="col-sm-1 control-label">用户ID</label>
                                    <div class="col-sm-4">
                                        <input type="number" class="form-control" id="userId" name="userId" value="${userId!''}" />
                                    </div>
                                    <button type="submit" class="col-sm-2 btn btn-default">搜索</button>
                                </div>
                            </form>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>用户Id</th>
                                    <th>用户名</th>
                                    <th>用户头像</th>
                                    <th>城市</th>
                                    <th>国家</th>
                                    <th>性别</th>
                                    <th>省份</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list userPage.content as user>
                                <tr>
                                    <td>${user.userId!''}</td>
                                    <td>${user.userName!''}</td>
                                    <td><img height="100" width="100" src="${user.avatarUrl!''}"></td>
                                    <td>${user.city!''}</td>
                                    <td>${user.country!''}</td>
                                    <#if (user.gender)?? && user.gender == 1>
                                        <td>男</td>
                                    <#else>
                                        <td>女</td>
                                    </#if>
                                    <td>${user.province!''}</td>
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
                            <li><a href="/fastborrow/admin/user/list?page=${currentPage - 1}&size=${size}&userId=${userId}">上一页</a></li>
                        </#if>

                        <#list 1..userPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/user/list?page=${index}&size=${size}&userId=${userId}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte userPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/user/list?page=${currentPage + 1}&size=${size}&userId=${userId}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>