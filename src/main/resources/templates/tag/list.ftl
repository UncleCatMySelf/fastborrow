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
                                图书类型列表
                            </h3>
                        </div>

                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>分类ID</th>
                                    <th>分类名称</th>
                                    <th>TYPE</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list tagList as tag>
                                <tr>
                                    <td>${tag.tagId}</td>
                                    <td>${tag.tagName}</td>
                                    <td>${tag.tagNum}</td>
                                    <td>${tag.createTime}</td>
                                    <td>${tag.updateTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/tag/index?tagId=${tag.tagId}">修改</a>
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