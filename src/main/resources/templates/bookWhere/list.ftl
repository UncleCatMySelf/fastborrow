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
                                图书摆方位置列表
                            </h3>
                        </div>

                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>位置ID</th>
                                    <th>位置名称</th>
                                    <th>TYPE</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list bookWhereList as bookWhere>
                                <tr>
                                    <td>${bookWhere.id}</td>
                                    <td>${bookWhere.whereName}</td>
                                    <td>${bookWhere.whereTag}</td>
                                    <td>${bookWhere.createTime}</td>
                                    <td>${bookWhere.updateTime}</td>
                                    <td>
                                        <a href="/fastborrow/admin/bookwhere/index?id=${bookWhere.id}">修改</a>
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