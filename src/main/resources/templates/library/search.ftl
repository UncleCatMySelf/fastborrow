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
                                借书库用户信息
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <form role="form" class="form-horizontal" action="/fastborrow/admin/library/search">
                                <div class="form-group">
                                    <label for="userId" class="col-sm-1 control-label">用户id</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="userId" name="userId" value="${userId!''}" />
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
                                    <th>用户名称</th>
                                    <th>图书名</th>
                                    <th>isbn</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list collectionVOList as collectionvo>
                                <tr>
                                    <td>${collectionvo.userId!''}</td>
                                    <td>${collectionvo.userName!''}</td>
                                    <td>${collectionvo.bookName!''}</td>
                                    <td>${collectionvo.isbn!''}</td>
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
                            <li><a href="/fastborrow/admin/library/search?page=${currentPage - 1}&size=${size}&userId=${userId}">上一页</a></li>
                        </#if>

                        <#list 1..collectionPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/library/search?page=${index}&size=${size}&userId=${userId}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte collectionPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/library/search?page=${currentPage + 1}&size=${size}&userId=${userId}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>