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
                                图书管理列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <form role="form" class="form-horizontal" action="/fastborrow/admin/bookmessage/search">
                                <div class="form-group">
                                    <label for="isbn" class="col-sm-1 control-label">ISBN</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="isbn" name="isbn" value="${sIsbn!''}" />
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
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list bookMessagePage.content as bookMessage>
                                <tr>
                                    <td>${bookMessage.isbn}</td>
                                    <td>${bookMessage.bookName}</td>
                                    <td>${bookMessage.price}</td>
                                    <#list tagList as tag>
                                        <#if (bookMessage.tagNum)?? && bookMessage.tagNum == tag.tagNum>
                                            <td>${tag.tagName}</td>
                                        </#if>
                                    </#list>
                                    <td>${bookMessage.pressTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/bookmessage/index?isbn=${bookMessage.isbn}">修改</a>
                                    </td>
                                    <td>
                                        <#if bookMessage.getBookStatusEnum().getMessage() == "上线">
                                            <a href="/fastborrow/admin/bookmessage/off_sale?isbn=${bookMessage.isbn}">下线</a>
                                        <#else>
                                            <a href="/fastborrow/admin/bookmessage/on_sale?isbn=${bookMessage.isbn}">上线</a>
                                        </#if>
                                    </td>
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
                            <li><a href="/fastborrow/admin/bookmessage/search?page=${currentPage - 1}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">上一页</a></li>
                        </#if>

                        <#list 1..bookMessagePage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/bookmessage/search?page=${index}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte bookMessagePage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/bookmessage/search?page=${currentPage + 1}&size=${size}&isbn=${sIsbn}&bookName=${sBookName}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>