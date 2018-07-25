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
                                库存管理列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <form role="form" class="form-horizontal" action="/fastborrow/admin/inventory/search">
                                <div class="form-group">
                                    <label for="isbn" class="col-sm-1 control-label">ISBN</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control" id="isbn" name="isbn" value="${sIsbn!''}" />
                                    </div>
                                    <button type="submit" class="col-sm-2 btn btn-default">搜索</button>
                                </div>
                            </form>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>书名</th>
                                    <th>ISBN</th>
                                    <th>在库</th>
                                    <th>借出</th>
                                    <th>总量</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list inventorieBackVOList as inventorieBackVO>
                                <tr>
                                    <td>${inventorieBackVO.bookName}</td>
                                    <td>${inventorieBackVO.isbn}</td>
                                    <td>${inventorieBackVO.numIn}</td>
                                    <td>${inventorieBackVO.numOut}</td>
                                    <td>${inventorieBackVO.sum}</td>
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
                            <li><a href="/fastborrow/admin/inventory/search?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#list 1..bookMessageList.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/inventory/search?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte bookMessageList.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/inventory/search?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>