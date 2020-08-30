<!-- 改成你的路径 -->
lay('#version').html('-v' + laydate.v);

//执行一个laydate实例
laydate.render({
    elem: '#start', //指定元素
    type: 'month',
    range: '/'
});

$("#quit").click(function () {
    window.location.href = "/login";
});

$("#init").click(function () {
    window.location.href = "/dataInit"
});

$("#system").click(function () {
    location.href = "/systemConfig";
});

$(".home_page").click(function () {
    location.href = "/index";
});

$("#search").click(function () {
    alert("搜索");
});

$("#platformManage").click(function () {
    location.href="/platformManage";
});

$("#btn_query").click(function () {
    var time = $("#start").val();
    var dates = time.split("/");
    var end = dates[1].split("-")[0].trim();
    var start = dates[0].split("-")[0].trim();
    var platform = $("#platform").val();
    if (platform == null || "" == platform) {
        alert("请选择平台");
        return;
    }
    if (start != end) {
        alert("请不要跨年选择");
        return;
    }
    var oTable = new TableInit();
    oTable.Init();
    $("#btn_export").show();
});

$("#btn_export").click(function () {
    var platform = $("#platform").val();
    var time = $("#start").val();
    window.location.href = "/excel/download?time="+time+"&platform="+platform;
});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_departments').bootstrapTable("destroy");
        $('#tb_departments').bootstrapTable({
            url: '/data/query',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            theadClasses: '.thead-light',
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            showPaginationSwitch: false,        //是否显示分页数
            sortable: false,                     //是否启用排序
            sortName: "title",                     //是否启用排序
            sortOrder: "desc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: '',                //如果要在oTableInit.queryParams方法获取pageNumber和pageSize的值，需要将此值设置为空字符串（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            singleSelect: false,                 //是否单选模式
            height: $(window).height() - 400,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: '序号'
            }, {
                field: 'brand',
                title: '品牌'
            }, {
                field: 'brandLevel',
                title: '品牌等级'
            }, {
                field: 'need',
                title: '诉求'
            }, {
                field: 'needLevel',
                title: '诉求等级',
            }, {
                field: 'sentiment',
                title: '情感'
            }, {
                field: 'time',
                title: '日期'
            }, {
                field: 'num',
                title: '次数'
            }]
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        // 特别说明：
        //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // 如果queryParamsType=limit,params包含{limit, offset, search, sort, order}
        // 如果queryParamsType!=limit,params包含{pageSize, pageNumber, searchText, sortName, sortOrder}
        var temp = {
            pageSize: params.pageSize,   //页面大小
            pageNumber: params.pageNumber,  //页码
            time: $("#start").val(),
            platform: $("#platform").val()
        };
        return temp;
    };
    return oTableInit;
};