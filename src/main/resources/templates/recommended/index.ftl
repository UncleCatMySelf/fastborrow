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
                        推荐方案详情页
                    </h3>
                </div>
                <div class="col-md-12 column">
                    <form role="form" id="rec" >
                        <div class="form-group">
                            <label>推荐标题</label>
                            <input name="title" type="text" class="form-control" value="${title!''}"/>
                        </div>
                        <div class="form-group">
                            <label>推荐文案</label>
                            <input name="info" type="text" class="form-control" value="${info!''}"/>
                        </div>
                        <div class="form-group">
                            <label>作者</label>
                            <input name="author" type="text" class="form-control" value="${author!''}"/>
                        </div>
                        <div class="form-group">
                            <label>发行时间</label>
                            <input name="time" type="date" class="form-control" value="${time!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img width="100" height="100" src="${images!''}">
                            <input name="images" type="text" class="form-control" value="${images!''}"/>
                        </div>
                        <div class="form-group">
                            <label>前端样式</label>
                            <select name="cssstatus" id="" class="form-control">
                                <option value="1"
                                    <#if cssstatus?? && cssstatus ==   1>
                                        selected
                                    </#if>
                                >
                                小样式
                                </option>
                                <option value="2"
                                <#if cssstatus?? && cssstatus ==   2>
                                        selected
                                </#if>
                                >
                                大样式
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="mobookId" class="col-sm-1 control-label">图书编号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="mobookId" name="mobookId" value="${sMobookId!''}" />
                            </div>
                            <label for="bookName" class="col-sm-1 control-label">图书名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="bookName" name="bookName" value="${sBookName!''}" />
                            </div>
                            <button type="button" class="col-sm-2 btn btn-default" onclick="indexBtn()">搜索</button>
                        </div>
                        <div class="col-md-12 column">
                            <table class="table table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>墨书Id</th>
                                    <th>书名</th>
                                    <th>ISBN</th>
                                    <th>价格</th>
                                    <th>分类</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list bookMessagePage.content as bookMessage>
                                <tr>
                                    <td>${bookMessage.mobookId}</td>
                                    <td>${bookMessage.bookName}</td>
                                    <td>${bookMessage.isbn}</td>
                                    <td>${bookMessage.price}</td>
                                    <td>${bookMessage.pressTime}</td>
                                    <td>
                                        <div class="checkbox">
                                            <label><input type="checkbox" value="${bookMessage.mobookId}" name="bookmess" />添加</label>
                                        </div>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/recommended/index?page=${currentPage - 1}&size=${size}&mobookId=${sMobookId}&bookName=${sBookName}">上一页</a></li>
                            </#if>

                            <#list 1..bookMessagePage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/fastborrow/admin/recommended/index?page=${index}&size=${size}&mobookId=${sMobookId}&bookName=${sBookName}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte bookMessagePage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/fastborrow/admin/recommended/index?page=${currentPage + 1}&size=${size}&mobookId=${sMobookId}&bookName=${sBookName}">下一页</a></li>
                            </#if>
                            </ul>
                        </div>
                        <input hidden type="text" name="id" value="${id!''}">
                        <input hidden type="text" name="bookMessIdList" id="bookMessIdList" value="${bookMessIdList!''}" >
                        <button type="button" class="btn btn-default" onclick="submits()">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function indexBtn() {
        document.getElementById('rec').action = "/fastborrow/admin/recommended/index";
        document.getElementById('rec').method = "GET";
        document.getElementById('rec').submit();
    }
    function submits() {
        var value = [];
        $('input:checkbox[name=bookmess]:checked').each (function (k) {
            if (k == 0){
                value[k] = $(this).val();
            }else {
                value[k] = $(this).val();
            }
        });
        //alert(value);
        var bookMessIdList = document.getElementById('bookMessIdList');
        bookMessIdList.value = value;
        document.getElementById('rec').action = "/fastborrow/admin/recommended/save";
        document.getElementById('rec').method = "GET";
        document.getElementById('rec').submit();
    }
</script>
</body>
</html>
