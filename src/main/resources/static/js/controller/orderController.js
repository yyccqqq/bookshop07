app.controller('orderController', function ($scope, $controller, $location, $interval, orderService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.entity = {};

    $scope.pageNum = 1;

    $scope.username = Cookies.get('username');

    $scope.total = 0;

    $scope.getSelectCartList = function () {
        orderService.getSelectCartList().success(
            function (response) {
                if (!(response == null || response == '')) {
                    $scope.entityList = response.bookVoList;
                    $scope.total = response.bookVoList.length;
                    $scope.user = response.user;
                    $scope.totalPrice = 10.00;
                    angular.forEach($scope.entityList, function (value) {
                        $scope.totalPrice += value.price;
                    })
                } else {
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.msg("你的操作过于频繁，即将跳回首页", {
                            icon: 2,
                            timer: 3000
                        });
                    });
                    $scope.timing();
                }
            }
        )
    }

    $scope.submitOrder = function () {
        orderService.submitOrder().success(
            function (response) {
                window.location.href = "pay.html";
            })
    }

    $scope.getPayUrl = function () {
        orderService.getPayUrl().success(
            function (response) {
                if (!(response == null || response == '')) {
                    $scope.money = response.money;
                    $scope.orderNo = response.orderNo;
                    var qrCode = response.qrCode;
                    var qr = new QRious({
                        level: 'H',
                        element: document.getElementById('qrCode'),
                        value: qrCode,
                        size: 250
                    });
                    $scope.queryPayStatus($scope.orderNo);
                } else {
                    layer.msg("你的操作过于频繁，即将跳回首页", {
                        icon: 2,
                        timer: 3000
                    });
                    $scope.timing();
                }
            }
        )
    }

    $scope.queryPayStatus = function (orderNo) {
        orderService.queryPayStatus(orderNo).success(
            function (response) {
                if (response.success) {
                    layer.alert('支付成功', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    }, function () {
                        layer.msg('即将跳回首页', {
                            icon: 1,
                            timer: 3000
                        });
                        $scope.timing();
                    });
                } else {
                    layer.alert('支付失败', {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    }, function () {
                        layer.msg('即将跳回首页', {
                            icon: 2,
                            timer: 3000
                        });
                        $scope.timing();
                    });
                }
            })
    }

    $scope.timing = function () {
        var timeout = $interval($scope.toIndex, 3000);
    }

    $scope.toIndex = function () {
        window.location.href = 'index.html';
    }

    $scope.findOrderSend = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        orderService.findOrderSend($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findOrderSend(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.returnGoods = function (orderId) {
        orderService.cancelOrder(orderId).success(
            function (response) {
                if (response.success) {
                    $scope.findOrderSend(1);
                }
            }
        )
    }

    $scope.confirm = function (orderId) {
        orderService.confirm(orderId).success(
            function (response) {
                if (response.success) {
                    $scope.findOrderSend(1);
                }
            }
        )
    }

    $scope.findOrderPay = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        orderService.findOrderPay($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findOrderPay(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.findOrderSell = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        orderService.findOrderSell($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findOrderSell(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.findOrderSuccess = function (pageNum) {
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        orderService.findOrderSuccess($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findOrderSuccess(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.payAgain = function (orderId) {
        orderService.payAgain(orderId).success(
            function (response) {
                if (response.success) {
                    window.location.href = 'pay.html';
                }
            }
        )

    }

    $scope.cancelOrder = function (orderId) {
        orderService.cancelOrder(orderId).success(
            function (response) {
                if (response.success) {
                    $scope.findOrderPay(1);
                }
            }
        )
    }

    $scope.cancelSellerOrder = function (orderId) {
        orderService.cancelOrder(orderId).success(
            function (response) {
                if (response.success) {
                    $scope.findOrderSell(1);
                }
            }
        )
    }
});
