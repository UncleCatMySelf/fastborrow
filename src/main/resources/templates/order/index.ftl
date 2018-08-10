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
                        订单详情页
                    </h3>
                </div>
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/fastborrow/admin/order/send">
                        <div class="form-group">
                            <label>快递单号</label>
                                <#if orderMaster.payStatus == 0>
                                <label style="color: red">
                                    当前订单支付状态：等待支付！请勿派单
                                </label>
                                <input disabled name="expressNum" type="text" class="form-control"/>
                                <#elseif orderMaster.payStatus == 1>
                                <label style="color: green">
                                    当前订单支付状态：支付成功！可以派单
                                </label>
                                <input name="expressNum" type="text" class="form-control"/>
                                </#if>
                            <input hidden name="orderId" value="${orderMaster.orderId!''}">
                        </div>
                        <#if orderMaster.payStatus == 0>
                            <button disabled type="submit" class="btn btn-default">提交</button>
                        <#elseif orderMaster.payStatus == 1>
                            <button type="submit" class="btn btn-default">提交</button>
                        </#if>
                    </form>
                </div>
                <div class="col-md-12 column">
                        <div class="form-group">
                            <label class="col-md-3">订单ID</label>
                            <label class="col-md-9">${orderMaster.orderId}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">买家名称</label>
                            <label class="col-md-9">${orderMaster.buyerName}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">买家电话</label>
                            <label class="col-md-9">${orderMaster.buyerPhone}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">买家地址</label>
                            <label class="col-md-9">${orderMaster.buyerAddress}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">买家Openid</label>
                            <label class="col-md-9">${orderMaster.buyerOpenid}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">订单押金</label>
                            <label class="col-md-9">${orderMaster.orderDeposit}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">实际付款</label>
                            <label class="col-md-9">${orderMaster.orderPayment}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">快递费用</label>
                            <label class="col-md-9">${orderMaster.orderExpress}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">订单金额</label>
                            <label class="col-md-9">${orderMaster.orderAmount}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">子订单数量</label>
                            <label class="col-md-9">${orderMaster.orderNum}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">续借期限</label>
                            <label class="col-md-9">
                                <#if orderMaster.orderTime == 1>
                                    三个月
                                <#elseif orderMaster.orderTime == 2>
                                    半年
                                <#elseif orderMaster.orderTime == 3>
                                    一年
                                </#if>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">备注</label>
                            <label class="col-md-9">${orderMaster.note}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">支付状态</label>
                            <label class="col-md-9" style="color: red">
                            <#if orderMaster.payStatus == 0>
                                等待支付
                            <#elseif orderMaster.payStatus == 1>
                                支付成功
                            </#if>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3">下单时间</label>
                            <label class="col-md-9">${orderMaster.createTime}</label>
                        </div>
                </div>
                <!--子订单详情-->
                <div class="col-md-12 column">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>墨书ID</th>
                            <th>图书名称</th>
                            <th>ISBN</th>
                            <th>位置</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list detailVOList as detailVO>
                        <tr>
                            <td>${detailVO.mobookId}</td>
                            <td>${detailVO.mobookName}</td>
                            <td>${detailVO.isbn}</td>
                            <#list bookWhereList as bookWhere>
                                <#if (detailVO.whereTag)?? && detailVO.whereTag == bookWhere.whereTag>
                                    <td>${bookWhere.whereName}</td>
                                </#if>
                            </#list>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
