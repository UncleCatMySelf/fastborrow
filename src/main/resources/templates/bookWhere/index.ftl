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
                        图书位置类型详情页
                    </h3>
                </div>

                <div class="col-md-12 column">
                    <form role="form" method="post" action="/fastborrow/admin/bookwhere/save">
                        <div class="form-group">
                            <label>位置名字</label>
                            <input name="whereName" type="text" class="form-control" value="${(bookWhere.whereName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>TYPE</label>
                            <input name="whereTag" type="number" class="form-control" value="${(bookWhere.whereTag)!''}"/>
                        </div>
                        <input hidden type="text" name="id" value="${(bookWhere.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
