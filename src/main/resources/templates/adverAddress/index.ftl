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
                        广告位置类型详情页
                    </h3>
                </div>

                <div class="col-md-12 column">
                    <form role="form" method="post" action="/fastborrow/admin/adveraddress/save">
                        <div class="form-group">
                            <label>位置名字</label>
                            <input name="advAdName" type="text" class="form-control" value="${(adverAddress.advAdName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>TYPE</label>
                            <input name="advAdNum" type="number" class="form-control" value="${(adverAddress.advAdNum)!''}"/>
                        </div>
                        <input hidden type="text" name="id" value="${(adverAddress.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
