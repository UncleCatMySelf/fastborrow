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
                                未发货订单
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>订单ID</th>
                                    <th>买家电话</th>
                                    <th>实际付款</th>
                                    <th>子订单数量</th>
                                    <th>续借期限</th>
                                    <th>备注</th>
                                    <th>下单时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list shippedVOList as shipped>
                                <tr>
                                    <td>${shipped.orderId}</td>
                                    <td>${shipped.buyerPhone}</td>
                                    <td>${shipped.orderPayment}</td>
                                    <td>${shipped.orderNum}</td>
                                    <td>
                                        <#if shipped.orderTime == 1>
                                            三个月
                                        <#elseif shipped.orderTime == 2>
                                            半年
                                        <#elseif shipped.orderTime == 3>
                                            一年
                                        </#if>
                                    </td>
                                    <td>${shipped.note}</td>
                                    <td>${shipped.createTime}</td>
                                    <td><a href="/fastborrow/admin/order/delivery?orderId=${shipped.orderId}">发货</a></td>
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
                            <li><a href="/fastborrow/admin/order/Not_shipped?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#list 1..orderMasterPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/order/Not_shipped?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte orderMasterPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/fastborrow/admin/order/Not_shipped?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>