app.controller('cartController', function ($scope, $controller, $location, cartService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.entity = {};

    $scope.username = Cookies.get('username');

    $scope.addCart = function (bookId) {
        cartService.findBookById(bookId).success(
            function (response) {
                if (response.bookType == 1) {
                    cartService.findAll().success(
                        function (response) {
                            $scope.bookList = response;
                            $scope.bookIdList = [];
                            angular.forEach($scope.bookList, function (value) {
                                $scope.bookIdList.push(value.bookId);
                            });
                            if (!$scope.bookIdList.in_array(bookId)) {
                                cartService.addCart(bookId).success(
                                    function (response) {
                                        if (response.success) {
                                            layer.msg('加入成功');
                                        }
                                    }
                                )
                            } else {
                                layer.msg("已在购物车");
                            }
                        }
                    )
                } else {
                    layer.msg("商品已下架");
                }
            }
        )

    }

    Array.prototype.in_array = function (e) {
        for (i = 0; i < this.length; i++) {
            if (this[i] == e)
                return true;
        }
        return false;
    }

    $scope.findUserAllBook = function (userId) {
        window.location.href = '../userAllBookList.html#?userId=' + userId;
    }

    $scope.totalLength = 0;
    $scope.allSelect = false;
    $scope.findBookInCart = function () {
        cartService.findBookInCart().success(
            function (response) {
                $scope.entityList = response;
                angular.forEach($scope.entityList, function (value) {
                    $scope.totalLength += value.cartVoList.length;
                })
                $scope.allSelect = false;
            }
        )
    }

    $scope.totalPrice = 0.00;
    $scope.total = 0;
    //全选
    $scope.selectAll = function ($event) {
        if ($event.target.checked) {
            $scope.selectIds = [];  //先清空，防止在操作了一个轮回之后，重复添加了...
            $scope.totalPrice = 0.00;
            $scope.allSelect = true;
            angular.forEach($scope.entityList, function (value) {  //list.devices这是循环从后台获取的数组，并添加到刚刚定义的数组里
                value.status = true;
                angular.forEach(value.cartVoList, function (cartVo) {
                    cartVo.status = true;
                    $scope.selectIds.push(cartVo.cart.id)
                    $scope.totalPrice = $scope.totalPrice + cartVo.bookVo.price;
                })
                $scope.totalLength += value.cartVoList.length;
                $scope.total += value.cartVoList.length;
            })
        } else {
            $scope.allSelect = false;
            angular.forEach($scope.entityList, function (value) {
                value.status = false;
                angular.forEach(value.cartVoList, function (cartVo) {  //list.devices这是循环从后台获取的数组，并添加到刚刚定义的数组里
                    cartVo.status = false;
                })
                $scope.selectIds = [];
                $scope.totalPrice = 0.0;
            })
            $scope.total = 0;
        }
    }

    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id);
            angular.forEach($scope.entityList, function (value) {
                $scope.cartIds = [];
                angular.forEach(value.cartVoList, function (cartVo) {
                    $scope.cartIds.push(cartVo.cart.id);
                    if (cartVo.cart.id == id) {
                        cartVo.status = true;
                        $scope.totalPrice = $scope.totalPrice + cartVo.bookVo.price;
                    }
                })
                var flag = 0;
                angular.forEach($scope.cartIds, function (i) {
                    if ($scope.selectIds.in_array(i)) {
                        flag += 1;
                    }
                })
                if (flag == $scope.cartIds.length) {
                    value.status = true;
                }
            })
            $scope.total += 1;
        } else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index, 1);
            angular.forEach($scope.entityList, function (value) {
                angular.forEach(value.cartVoList, function (cartVo) {
                    if (cartVo.cart.id == id) {
                        cartVo.status = false;
                        $scope.totalPrice = $scope.totalPrice - cartVo.bookVo.price;
                        value.status = false;
                    }
                })
            })
            $scope.total -= 1;
        }
        $scope.isAllSelect();
    }

    $scope.updateSelect = function ($event, userId) {
        if ($event.target.checked) {
            angular.forEach($scope.entityList, function (value) {
                if (value.user.id == userId) {
                    value.status = true;
                    angular.forEach(value.cartVoList, function (cartVo) {
                        cartVo.status = true;
                        $scope.selectIds.push(cartVo.cart.id)
                        $scope.totalPrice = $scope.totalPrice + cartVo.bookVo.price;
                    })
                    $scope.total += value.cartVoList.length;
                }
            })
        } else {
            angular.forEach($scope.entityList, function (value) {
                if (value.user.id == userId) {
                    value.status = false;
                    angular.forEach(value.cartVoList, function (cartVo) {
                        cartVo.status = false;
                        var index = $scope.selectIds.indexOf(cartVo.cart.id);
                        $scope.selectIds.splice(index, 1);
                        $scope.totalPrice = $scope.totalPrice - cartVo.bookVo.price;
                    })
                    $scope.total -= value.cartVoList.length;
                }
            })
        }
        $scope.isAllSelect();
    }

    $scope.delete = function (cartId) {
        cartService.delete(cartId).success(
            function (response) {
                if (response.success) {
                    $scope.totalPrice = 0.00;
                    $scope.total = 0;
                    $scope.selectIds = [];
                    $scope.allSelect = false;
                    $scope.findBookInCart();
                }
            }
        )
    }

    $scope.isAllSelect = function () {
        if ($scope.selectIds.length == $scope.totalLength) {
            $scope.allSelect = true;
        } else {
            $scope.allSelect = false;
        }
    }

    $scope.toPay = function () {
        if ($scope.total > 0) {
            cartService.checkBook($scope.selectIds).success(
                function (response) {
                    if (response.length == 0) {
                        cartService.addToCartList($scope.selectIds).success(
                            function (response) {
                                if (response.success) {
                                    window.location.href = 'orderInfo.html';
                                }
                            }
                        )
                    } else {
                        var msg = '';
                        var i = 1;
                        angular.forEach(response, function (value) {
                            if (i == 1) {
                                msg += value;
                            } else {
                                msg += "," + value;
                            }
                            i += 1;
                        })
                        msg += '已下架，请删除';
                        layer.alert(msg, {
                            skin: 'layui-layer-molv'
                            , closeBtn: 0
                        });
                    }
                })
        } else {
            layer.msg('请选择商品');
        }
    }

    $scope.doSearch = function () {
        if (typeof $scope.keyword == "undefined" || $scope.keyword == "") {
            layer.tips('不能为空', '#tip', {
                tips: [1, 'black'],
                time: 4000
            });
        } else {
            window.location.href = "/bookshop/view/searchBookList.html#?keyword=" + $scope.keyword;
        }
    }
});




