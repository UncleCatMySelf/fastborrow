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
                        图书类型详情页
                    </h3>
                </div>

                <div class="col-md-12 column">
                    <form role="form" method="post" action="/fastborrow/admin/tag/save">
                        <div class="form-group">
                            <label>分类名字</label>
                            <input name="tagName" type="text" class="form-control" value="${(tag.tagName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>TYPE</label>
                            <input name="tagNum" type="number" class="form-control" value="${(tag.tagNum)!''}"/>
                        </div>
                        <input hidden type="text" name="tagId" value="${(tag.tagId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
