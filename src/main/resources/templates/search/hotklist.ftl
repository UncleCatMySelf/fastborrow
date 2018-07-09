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
                                热门关键字列表
                            </h3>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>关键字名称</th>
                                    <th>TYPE</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list hotKeyList as hotKey>
                                <tr>
                                    <td>${hotKey.id}</td>
                                    <td>${hotKey.hotkeyName}</td>
                                    <td>${hotKey.hotkeyType}</td>
                                    <td>${hotKey.createTime}</td>
                                    <td>${hotKey.updateTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/hotkey/index?id=${hotKey.id}">修改</a>
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