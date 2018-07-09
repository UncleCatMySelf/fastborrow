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
                        热门关键字操作页
                    </h3>
                </div>
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/fastborrow/admin/hotkey/save">
                        <div class="form-group">
                            <label>关键字名称</label>
                            <input name="hotkeyName" type="text" class="form-control" value="${(hotKey.hotkeyName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>TYPE</label>
                            <input name="hotkeyType" type="number" class="form-control" value="${(hotKey.hotkeyType)!''}"/>
                        </div>
                        <input hidden type="text" name="id" value="${(hotKey.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
