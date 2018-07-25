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
                        新书推荐操作页
                    </h3>
                </div>
                <div class="col-md-12 column">
                    <form role="form" class="form-horizontal" action="/fastborrow/admin/bookmessage/nb_search">
                        <div class="form-group">
                            <label for="mobookId" class="col-sm-1 control-label">图书编号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="isbn" name="mobookId" value="${sIsbn!''}" />
                            </div>
                            <label for="bookName" class="col-sm-1 control-label">图书名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="bookName" name="bookName" value="${sBookName!''}" />
                            </div>
                            <button type="submit" class="col-sm-2 btn btn-default">搜索</button>
                        </div>
                    </form>
                </div>
                <div class="col-md-12 column">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>ISBN</th>
                            <th>书名</th>
                            <th>价格</th>
                            <th>分类</th>
                            <th>出版日期</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list bookMessagePage.content as bookMessage>
                        <tr>
                            <td>${bookMessage.isbn!''}</td>
                            <td>${bookMessage.bookName!''}</td>
                            <td>${bookMessage.price!''}</td>
                            <#list tagList as tag>
                                <#if (bookMessage.tagNum)?? && bookMessage.tagNum == tag.tagNum>
                                    <td>${tag.tagName}</td>
                                </#if>
                            </#list>
                            <td>${bookMessage.pressTime!''}</td>
                            <td>
                                <a href="/fastborrow/admin/bookmessage/nb_add?isbn=${bookMessage.isbn}">设为新书推荐</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                <#else>
                    <li><a href="/fastborrow/admin/bookmessage/nb_search?page=${currentPage - 1}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">上一页</a></li>
                </#if>

                <#list 1..bookMessagePage.getTotalPages() as index>
                    <#if currentPage == index>
                        <li class="disabled"><a href="#">${index}</a></li>
                    <#else>
                        <li><a href="/fastborrow/admin/bookmessage/nb_search?page=${index}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">${index}</a></li>
                    </#if>
                </#list>
                <#if currentPage gte bookMessagePage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                <#else>
                    <li><a href="/fastborrow/admin/bookmessage/nb_search?page=${currentPage + 1}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">下一页</a></li>
                </#if>
                </ul>
            </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
