app.controller('bookController', function ($scope, $controller, $location, bookService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.entity = {};

    $scope.username = Cookies.get('username');

    $scope.pageNum = 1;


    $scope.findBook = function () {
        bookService.findBook().success(
            function (response) {
                $scope.entityMap = response;
            }
        )
    }

    $scope.findBookByCategoryId = function (pageNum) {
        var categoryId = $location.search()['categoryId'];
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        bookService.findBookByCategoryId(categoryId, $scope.pageNum).success(
            function (response) {
                $scope.entityList = response.pageInfo.list;
                $scope.category = response.category.name;
                $scope.total = response.pageInfo.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , curr: $scope.pageNum
                        , limit: 8
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findBookByCategoryId(obj.curr);
                            }
                        }
                    });
                });
            })
    }


    $scope.findGoodBook = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        bookService.findGoodBook($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.pageInfo.list;
                $scope.total = response.pageInfo.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , curr: $scope.pageNum
                        , limit: 8
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findGoodBook(obj.curr);
                            }
                        }
                    });
                });
            }
        )
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

    $scope.search = function (pageNum) {
        $scope.keyword = $location.search()['keyword'];
        bookService.search($scope.keyword, $scope.pageNum).success(
            function (response) {
                $scope.entityList = response.content;
                $scope.total = response.size;
                $scope.msg = $scope.keyword;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    //执行一个laypage实例
                    laypage.render({
                        elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                        , count: $scope.total //数据总数，从服务端得到
                        , limit: 8
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            //首次不执行
                            if (!first) {
                                $scope.search(obj.curr);
                            }
                        }
                    });
                });
            }
        )
    }

    $scope.searchBook = function (pageNum) {
        if (typeof $scope.keyword == "undefined" || $scope.keyword == "") {
            layer.tips('不能为空', '#tip', {
                tips: [1, 'black'],
                time: 4000
            });
        } else {
            bookService.search($scope.keyword, $scope.pageNum).success(
                function (response) {
                    $scope.entityList = response.content;
                    $scope.total = response.size;
                    $scope.msg = $scope.keyword;
                    layui.use('laypage', function () {
                        var laypage = layui.laypage;
                        //执行一个laypage实例
                        laypage.render({
                            elem: 'page' //注意，这里的 test1 是 ID，不用加 # 号
                            , count: $scope.total //数据总数，从服务端得到
                            , limit: 8
                            , curr: $scope.pageNum
                            , jump: function (obj, first) {
                                //首次不执行
                                if (!first) {
                                    $scope.search(obj.curr);
                                }
                            }
                        });
                    });
                }
            )
        }
    }

    $scope.findUserAllBook = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        $scope.userId = $location.search()['userId'];
        bookService.findUserAllBook($scope.userId, $scope.pageNum).success(
            function (response) {
                $scope.entityList = response.pageInfo.list;
                $scope.total = response.pageInfo.total;
                $scope.seller = response.seller;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 8
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findUserAllBook(obj.curr);
                            }
                        }
                    });
                });
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










