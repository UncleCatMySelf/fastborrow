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
                    <form role="form" class="form-horizontal" action="/fastborrow/admin/inventory/index">
                        <div class="form-group">
                            <label for="isbn" class="col-sm-1 control-label">ISBN</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="isbn" name="isbn" value="${sIsbn!''}" />
                            </div>
                            <button type="submit" class="col-sm-2 btn btn-default">搜索</button>
                        </div>
                    </form>
                </div>
                <div class="col-md-12 column">
                    <form role="form"  class="form-horizontal" method="post" action="/fastborrow/admin/inventory/add">
                        <div class="form-group">
                            <label class="col-sm-1 control-label">存放位置</label>
                            <div class="col-sm-4">
                                <select name="whereTag" id="whereTag" class="form-control">
                                    <#list bookWhereList as bookWhere>
                                        <option value="${bookWhere.whereTag}">
                                            ${bookWhere.whereName}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                            <input hidden type="text" name="isbn" class="col-sm-2" value="${(bookMessage.isbn)!''}">
                            <button type="submit" class="col-sm-2  btn btn-default">新增</button>
                        </div>
                    </form>
                </div>
                <div class="col-md-12 column">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>书名</th>
                            <th>作者</th>
                            <th>ISBN</th>
                            <th>价格</th>
                            <th>分类</th>
                            <th>特色简介</th>
                            <th>出版社</th>
                            <th>出版日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${bookMessage.bookName!''}</td>
                            <td>${bookMessage.author!''}</td>
                            <td>${bookMessage.isbn!''}</td>
                            <td>${bookMessage.price!''}</td>
                            <#list tagList as tag>
                                <#if (bookMessage.tagNum)?? && bookMessage.tagNum == tag.tagNum>
                                    <td>${tag.tagName}</td>
                                </#if>
                            </#list>
                            <td>${bookMessage.info!''}</td>
                            <td>${bookMessage.press!''}</td>
                            <td>${bookMessage.pressTime!''}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <table class="table table-hover table-striped">
                        <thead>
                        <tr>
                            <th>新增成功——墨书Id</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${newMobookId!''}</td>
                        </tr>
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
