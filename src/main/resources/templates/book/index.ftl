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
                    <form role="form" method="post" action="/fastborrow/admin/bookmessage/save">
                        <div class="form-group">
                            <label>图书名字</label>
                            <input name="bookName" type="text" class="form-control" value="${(bookMessage.bookName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>ISBN</label>
                            <input name="isbn" type="text" class="form-control" value="${(bookMessage.isbn)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="price" type="text" class="form-control" value="${(bookMessage.price)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>阅读人数</label>
                            <input name="num" type="number" class="form-control" value="${(bookMessage.num)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>评分</label>
                            <input name="score" type="number" class="form-control" value="${(bookMessage.score)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>作者</label>
                            <input name="author" type="text" class="form-control" value="${(bookMessage.author)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>作者简介</label>
                            <input name="authorInfo" type="text" class="form-control" value="${(bookMessage.authorInfo)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>出版社</label>
                            <input name="press" type="text" class="form-control" value="${(bookMessage.press)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>出版时间</label>
                            <input name="pressTime" type="date" class="form-control" value="${(bookMessage.pressTime)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>特色简介</label>
                            <textarea name="info" class="form-control">
                                ${(bookMessage.info)!''}
                            </textarea>
                        </div>
                        <div class="form-group">
                            <label>主要内容</label>
                            <textarea name="summary" class="form-control">
                                ${(bookMessage.summary)!''}
                            </textarea>
                        </div>
                        <div class="form-group">
                            <label>目录</label>
                            <textarea name="catalog" class="form-control">
                                ${(bookMessage.catalog)!''}
                            </textarea>
                        </div>
                        <div class="form-group">
                            <label>封面1</label>
                            <img width="100" height="100" src="${(bookMessage.images)!''}">
                            <input name="images" type="text" class="form-control" value="${(bookMessage.images)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>封面2</label>
                            <img width="100" height="100" src="${(bookMessage.images2)!''}">
                            <input name="images2" type="text" class="form-control" value="${(bookMessage.images2)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>介绍长图</label>
                            <img width="100" height="100" src="${(bookMessage.imageContext)!''}">
                            <input name="imageContext" type="text" class="form-control" value="${(bookMessage.imageContext)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>标签</label>
                            <input name="lable" type="text" class="form-control" value="${(bookMessage.lable)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>分类</label>
                            <select name="tagNum" id="" class="form-control">
                            <#list tagList as tag>
                                <option value="${tag.tagNum}"
                                    <#if (bookMessage.tagNum)?? &&  bookMessage.tagNum ==   tag.tagNum>
                                        selected
                                    </#if>
                                >
                                ${tag.tagName}
                                </option>
                            </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
