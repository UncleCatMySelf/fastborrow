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
                                推荐方案列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <form role="form" class="form-horizontal" action="/fastborrow/admin/recommended/list">
                                <div class="form-group">
                                    <label for="mobookId" class="col-sm-1 control-label">推荐标题</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="title" name="title" value="${title!''}" />
                                    </div>
                                    <button type="submit" class="col-sm-2 btn btn-default">搜索</button>
                                </div>
                            </form>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>标题</th>
                                    <th>封面</th>
                                    <th>作者</th>
                                    <th>文案</th>
                                    <th>发行时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list recommendedPage.content as recommended>
                                <tr>
                                    <td>${recommended.title}</td>
                                    <td><img height="100" width="100" src="${recommended.images}"></td>
                                    <td>${recommended.author}</td>
                                    <td>${recommended.info}</td>
                                    <td>${recommended.time}</td>
                                    <td>
                                        <#if recommended.getRecStatusEnum().getMessage() == "上线">
                                            <a href="/fastborrow/admin/recommended/off_sale?id=${recommended.id}">下线</a>
                                        <#else>
                                            已停用
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/recommended/list?page=${currentPage - 1}&size=${size}&title=${title}">上一页</a></li>
                            </#if>

                            <#list 1..recommendedPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/fastborrow/admin/recommended/list?page=${index}&size=${size}&title=${title}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte recommendedPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/recommended/list?page=${currentPage + 1}&size=${size}&title=${title}">下一页</a></li>
                            </#if>
                            </ul>
                        </div>
                    </div>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>