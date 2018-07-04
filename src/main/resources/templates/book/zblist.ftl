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
                                主编推荐列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>墨书Id</th>
                                    <th>书名</th>
                                    <th>ISBN</th>
                                    <th>价格</th>
                                    <th>分类</th>
                                    <th>出版日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list bookMessageList as bookMessage>
                                <tr>
                                    <td>${bookMessage.mobookId}</td>
                                    <td>${bookMessage.bookName}</td>
                                    <td>${bookMessage.isbn}</td>
                                    <td>${bookMessage.price}</td>
                                    <#list tagList as tag>
                                        <#if (bookMessage.tagNum)?? && bookMessage.tagNum == tag.tagNum>
                                            <td>${tag.tagName}</td>
                                        </#if>
                                    </#list>
                                    <td>${bookMessage.pressTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/bookmessage/no_zbbook?mobookId=${bookMessage.mobookId}">撤销推荐</a>
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
    </body>
</html>