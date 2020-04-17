app.controller('askBookController', function ($scope, $interval, $controller, $location, askBookService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.entity = {};

    $scope.pageNum = 1;

    $scope.username = Cookies.get('username');

    $scope.finAskBook = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        askBookService.findAskBook($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 8
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findAskBook(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.doSearch = function () {
        if (typeof $scope.keyword == "undefined" || $scope.keyword == "") {
            layer.tips('不能为空', '#tip', {
                tips: [1, 'black'],
                time: 4000
            });
        } else {
            window.location.href = "searchBookList.html#?keyword=" + $scope.keyword;
        }
    }

    $scope.findAskBookById = function () {
        $scope.id = $location.search()['id'];
        askBookService.findAskBookById($scope.id).success(
            function (response) {
                $scope.entity = response;
            }
        )
    }
});

layui.use('util', function () {
    var util = layui.util;
    //执行
    util.fixbar({
        top: true
        , css: {right: 30, bottom: 20}
        , showHeight: 0
        , bgcolor: '#393D49'
        , click: function (type) {
            console.log(type);
            if (type === 'top') {
            }
        }
    });
});

