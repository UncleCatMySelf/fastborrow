$(document).ready(function () {
    // 获取左侧导航栏ul的所有子li
    var navLi = $("#navUl").children();

    for (var i = 0; i < navLi.length; i++) {
        var Lis = new Array();
        Lis[i] = $("#navUl").children()[i+1];
        // 给所有的li添加点击事件
        $(Lis[i]).click(function () {
            for (var j = 0; j < navLi.length; j++) {
                $(navLi[j]).css("background","");
            }
            $(this).css("background","#337ab7");
        });        
    }

    // 页面加载时获取之前点击的索引值
    var index = window.sessionStorage.getItem("index");
    var titLis = new Array();
    // 获取标题下的导航栏
    var titUl = $(navLi).children("ul");
    // 循环所有子ul的长度
    for (var i = 0; i < $($(navLi).children("ul")).length; i++) {

        // 循环每个子ul的长度，去掉第一个li标题，循环从1开始
        for (var j = 1; j < $($(titUl[i]).children()).length; j++) {
            var suoYin = new Array();
            suoYin[j] = $($(titUl[i]).children())[j];
            // 将拿到的所有li都添加进数组中
            titLis.push($($(titUl[i]).children())[j]);
        }
    }
    
    // console.log($(titLis)[index]);
    $(titLis[index]).css("background","#337ab7");
    $(titLis).each(function (i, element) {
        // 给每一个子li都添加点击事件
        $(titLis[i]).click(function () {
            // 存储点击时的索引值
            window.sessionStorage.setItem('index', i);
            // console.log(i)  
            // $(titLis).each (function(j, element) {
            //     $(titLis[j]).css("background", "");
            // });
            // $(this).css("background","#337ab7");
        });
    }); 
});


// 需求，记录子菜单的点击位置，点击跳转链接后依然位于当前位置
    // 需要一个参数接收点击时的索引值
    // 将参数保存到本地存储中
    // 页面加载时检查索引值，根据索引值显示当前的li