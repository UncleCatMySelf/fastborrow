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
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>广告ID</th>
                                    <th>名称</th>
                                    <th>介绍</th>
                                    <th>图片</th>
                                    <th>位置</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list advertisingPage.content as advertising>
                                <tr>
                                    <td>${advertising.adverId}</td>
                                    <td>${advertising.adverName}</td>
                                    <td>${advertising.adverInfo}</td>
                                    <td><img height="100" width="100" src="${advertising.image}"></td>
                                    <#list adverAddressList as adverAddress>
                                        <#if (advertising.adverAddress)?? &&  advertising.adverAddress ==   adverAddress.advAdNum>
                                            <td>${adverAddress.advAdName}</td>
                                        </#if>
                                    </#list>
                                    <td>${advertising.createTime}</td>
                                    <td>${advertising.updateTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/advertising/index?adverId=${advertising.adverId}">修改</a>
                                    </td>
                                    <td>
                                        <#if advertising.getAdverStatusEnum().getMessage() == "上线">
                                            <a href="/fastborrow/admin/advertising/off_sale?adverId=${advertising.adverId}">下线</a>
                                        <#else>
                                            <a href="/fastborrow/admin/advertising/on_sale?adverId=${advertising.adverId}">上线</a>
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
                            <li><a href="/fastborrow/admin/advertising/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#list 1..advertisingPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/advertising/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte advertisingPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/advertising/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>