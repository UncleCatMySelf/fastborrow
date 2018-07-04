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
                    <form role="form" method="post" action="/fastborrow/admin/advertising/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="adverName" type="text" class="form-control" value="${(advertising.adverName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>介绍</label>
                            <input name="adverInfo" type="text" class="form-control" value="${(advertising.adverInfo)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img width="100" height="100" src="${(advertising.image)!''}">
                            <input name="image" type="text" class="form-control" value="${(advertising.image)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>位置</label>
                            <select name="adverAddress" id="" class="form-control">
                                <#list adverAddressList as adverAddress>
                                    <option value="${adverAddress.advAdNum}"
                                        <#if (advertising.adverAddress)?? &&  advertising.adverAddress ==   adverAddress.advAdNum>
                                            selected
                                        </#if>
                                        >
                                        ${adverAddress.advAdName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="adverId" value="${(advertising.adverId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
