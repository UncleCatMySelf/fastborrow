$(document).ready(function () {
    // 获取左侧导航栏ul的所有子li
    var navLi = $("#navUl").children();

    for (var i = 0; i < navLi.length; i++) {
        var Lis = new Array();
        Lis[i] = $("#navUl").children()[i+1];
        // 给所有的li添加点击事件
        $(Lis[i]).click(function () {
            // 重置所有样式
            for (var j = 0; j < navLi.length; j++) {
                $(navLi[j]).css("background","");
                $($(navLi[j+2]).children("a")).css("color", "#333");
            }
            // 添加样式
            $(this).css("background","#337ab7");
            $($(this).children("a")).css("color", "#FFF");
        });
        $(Lis[i]).removeClass("open");
    }

    var titLis = new Array();
    // 获取标题下的导航栏
    var titUl = $(navLi).children("ul");
    // 循环所有子ul的长度
    for (var i = 0; i < $($(navLi).children("ul")).length; i++) {
        // 循环每个子ul的长度，去掉第一个li标题，循环从1开始
        for (var j = 1; j < $($(titUl[i]).children()).length; j++) {
            // 将拿到的所有li都添加进数组中
            titLis.push($($(titUl[i]).children())[j]);
        }
    }
    // 页面加载时获取之前点击的索引值
    var index = window.sessionStorage.getItem("index");
    // 页面载入时从本地存储中拿到index值，添加样式
    $(titLis[index]).css("background","#337ab7");
    $($(titLis[index]).children("a")).css("color","#FFF");
    $($(titLis[index]).parent().parent()).addClass("open");


    $(titLis).each(function (i, element) {
        // 给每一个子li都添加点击事件
        $(titLis[i]).click(function () {
            // 存储点击时的索引值
            window.sessionStorage.setItem('index', i);
            // 鼠标覆盖样式  
            // $(titLis).each (function(j, element) {
            //     $(titLis[j]).css("background", "");
            // });
            // $(this).css("background","#337ab7");
        });
    }); 
});