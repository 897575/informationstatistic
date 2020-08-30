var allPlatformName;
var allPlatformFlag;
//初始化
$(function () {
    $('#tb_platforms').bootstrapTable("destroy");
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
});
$("#quit").click(function () {
    window.location.href = "/login";
});
$("#init").click(function () {
    window.location.href = "/dataInit"
});
$(".home_page").click(function () {
    window.location.href = "/index";
});
$("#count").click(function () {
    location.href = "/dataCount";
});
$("#system").click(function () {
    location.href = "/systemConfig"
});

$("#btn_add").click(function () {
    $("#modal_cus_name").val("");
    $("#modal_bj_prd").val("");
    $("#modal_bj_no").val("");
    $("#button").children().remove();
    $("#button").append("<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>\n" +
        "<button type='button' class='btn btn-primary' id='sava-btn'>保存</button>");
    $('#myModal').modal();
});

$("#btn_edit").click(function () {
    var arrselections = $("#tb_platforms").bootstrapTable('getSelections');
    if (arrselections.length <= 0) {
        alert('请选择一条需要修改的数据');
        return;
    } else if (arrselections.length > 1) {
        alert("请选择一条数据修改");
        return;
    }
    $("#modal_bj_no").val(arrselections[0].id);
    $("#modal_cus_name").val(arrselections[0].platformName);
    $("#modal_bj_prd").val(arrselections[0].platformFlag);
    allPlatformName = arrselections[0].platformName;
    allPlatformFlag = arrselections[0].platformFlag;
    $("#button").children().remove();
    $("#button").append("<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>\n" +
        "<button type='button' class='btn btn-primary' id='edit-btn'>保存</button>");
    $("#myModal").modal();
});

$(document).on("click", "#sava-btn", function () {
    var platformName = $("#modal_cus_name").val();
    var platformFlag = $("#modal_bj_prd").val();
    if (platformFlag == "") {
        alert("请输入平台标识");
        return;
    }
    if (platformName == "") {
        alert("请输入平台名称");
        return;
    }
    $.ajax({
        url: "/config/add",
        dataType: "json",
        data: {"platformName": platformName, "platformFlag": platformFlag},
        type: "post",
        success: function (result) {
            if (result == 1) {
                alert("新增成功");
                $("#myModal").modal("hide");
                $('#tb_platforms').bootstrapTable("refresh");
                $("#modal_cus_name").val("");
                $("#modal_bj_prd").val("");
                return;
            }
                alert(result.responseText);
        },
        error:function () {
            alert("新增失败");
        }
    });
});

$(document).on("click", "#edit-btn", function () {
    var platformName = $("#modal_cus_name").val();
    var platformFlag = $("#modal_bj_prd").val();
    if (platformName == "") {
        alert("请输入平台名称");

        return;
    }
    if (platformFlag == "") {
        alert("请输入平台代码");
        return;
    }
    if (platformFlag == allPlatformFlag && platformName == allPlatformName) {
        return;
    }
    $.ajax({
        url: "/config/edit",
        type: "post",
        dataType: "json",
        data: {"id": $("#modal_bj_no").val(), "platformName": platformName, "platformFlag": platformFlag},
        success: function (result) {
            if (result == 1) {
                alert("修改成功");
                $("#myModal").modal("hide");
                $("#modal_cus_name").val("");
                $("#modal_bj_prd").val("");
                $('#tb_platforms').bootstrapTable("refresh");
                return ;
            }
                alert(result.responseText);
        },
        error:function () {
            alert("修改失败");
        }
    });
});

$("#btn_delete").click(function () {
    var arrselections = $("#tb_platforms").bootstrapTable('getSelections');
    if (arrselections.length <= 0) {
        alert('请选择有效数据');
        return;
    }
    var flag = window.confirm("确认要删除选择的数据吗？");
    if (!flag) {
        return;
    }
    var ids = new Array();
    for (var i = 0; i < arrselections.length; i++) {
        var str = arrselections[i].id;
        ids[i] = str;
    }
    $.ajax({
        type: "post",
        url: "/config/delete",
        data: {"ids": JSON.stringify(ids)},
        success: function (result) {
            if (result == "1") {
                alert("数据删除成功");
                $("#tb_platforms").bootstrapTable('refresh');
            } else {
                alert("数据删除失败");
            }
        }
    });
});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_platforms').bootstrapTable({
            url: '/config/query',         //请求后台的URL（*）
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
            height: $(window).height() - 360,   //table总高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: '平台序号'
            }, {
                field: 'platformName',
                title: '平台名称'
            }, {
                field: 'platformFlag',
                title: '平台标识'
            }, {
                field: 'createTime',
                title: '创建时间'
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