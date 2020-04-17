app.controller('myBookshelfController', function ($scope, $interval, $controller, $location, bookService, userService, uploadService, askBookService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.entity = {};

    $scope.pageNum = 1;

    $scope.username = Cookies.get('username');

    $scope.uploadSellBook = function () {
        var file = document.getElementById("file").value;
        var suffixIndex = file.lastIndexOf(".");
        var suffix = file.substring(suffixIndex + 1).toUpperCase();
        if (file == '') {
            $("#picmsg").html("请选择图片");
        } else if (suffix != "JPG" && suffix != "JPEG" && suffix != "PNG") {
            $("#picmsg").html("请上传图片文件");
        } else {
            if ($scope.checkSellBookForm()) {
                uploadService.uploadImage().success(
                    function (response) {
                        if (response.success) {
                            $scope.entity.image = response.message;
                            bookService.uploadSellBook($scope.entity).success(
                                function (response) {
                                    if (response.success) {
                                        layer.alert('上传成功', {
                                            skin: 'layui-layer-molv' //样式类名
                                            , closeBtn: 0
                                        }, function () {
                                            window.location.href = 'myBookshelf.html';
                                        });
                                    }
                                }
                            )
                        } else {
                            layer.alert('上传失败，请稍后再试', {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            }, function () {
                                window.location.href = 'myBookshelf.html';
                            });
                        }
                    }
                )
            }
        }

    }

    $scope.findMySellBook = function (pageNum) {
        $scope.sellBookIds = [];
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        bookService.findMySellBook($scope.pageNum).success(
            function (response) {
                $scope.entityList = response.pageInfo.list;
                $scope.user = response.user;
                $scope.total = response.pageInfo.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findMySellBook(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.uploadAskBook = function () {
        var file = document.getElementById("file").value;
        var suffixIndex = file.lastIndexOf(".");
        var suffix = file.substring(suffixIndex + 1).toUpperCase();
        if (file == '') {
            $("#picmsg").html("请选择图片");
        } else if (suffix != "JPG" && suffix != "JPEG" && suffix != "PNG") {
            $("#picmsg").html("请上传图片文件");
        } else {
            if ($scope.checkAskBookForm()) {
                uploadService.uploadImage().success(
                    function (response) {
                        if (response.success) {
                            $scope.entity.image = response.message;
                            askBookService.uploadAskBook($scope.entity).success(
                                function (response) {
                                    if (response.success) {
                                        layer.alert('上传成功', {
                                            skin: 'layui-layer-molv' //样式类名
                                            , closeBtn: 0
                                        }, function () {
                                            window.location.href = 'myBookshelf.html';
                                        });
                                    }
                                }
                            )
                        } else {
                            layer.alert('上传失败，请稍后再试', {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            }, function () {
                                window.location.href = 'myBookshelf.html';
                            });
                        }
                    }
                )
            }
        }
    }

    $scope.findMyAskBook = function (pageNum) {
        $scope.askBookIds = [];
        if (typeof pageNum != "undefined") {
            $scope.pageNum = pageNum;
        }
        askBookService.findMyAskBook($scope.pageNum).success(
            function (response) {
                $scope.askBookList = response.list;
                $scope.total = response.total;
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'page2'
                        , count: $scope.total
                        , limit: 4
                        , curr: $scope.pageNum
                        , jump: function (obj, first) {
                            if (!first) {
                                $scope.findMyAskBook(obj.curr);
                            }
                        }
                    });
                });
            })
    }

    $scope.findBookById = function () {
        var bookId = $location.search()['bookId'];
        bookService.findBookById(bookId).success(
            function (response) {
                $scope.entity = response;
            }
        )
    }

    $scope.updateSellBook = function () {
        var file = document.getElementById("file").value;
        if (file != '') {
            var suffixIndex = file.lastIndexOf(".");
            var suffix = file.substring(suffixIndex + 1).toUpperCase();
            if (suffix != "JPG" && suffix != "JPEG" && suffix != "PNG") {
                $("#picmsg").html("请上传图片文件");
            } else {
                if ($scope.checkSellBookForm()) {
                    uploadService.deleteImage($scope.entity.id).success(
                        function (response) {
                            if (response.success) {
                                uploadService.uploadImage().success(
                                    function (response) {
                                        if (response.success) {
                                            $scope.entity.image = response.message;
                                            bookService.updateSellBook($scope.entity).success(
                                                function (response) {
                                                    if (response.success) {
                                                        layer.alert('修改成功', {
                                                            skin: 'layui-layer-molv' //样式类名
                                                            , closeBtn: 0
                                                        }, function () {
                                                            window.location.href = 'myBookshelf.html';
                                                        });
                                                    }
                                                }
                                            )
                                        } else {
                                            layer.alert('上传失败，请稍后再试', {
                                                skin: 'layui-layer-molv' //样式类名
                                                , closeBtn: 0
                                            }, function () {
                                                window.location.href = 'myBookshelf.html';
                                            });
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            }
        } else {
            if ($scope.checkSellBookForm()) {
                bookService.updateSellBook($scope.entity).success(
                    function (response) {
                        if (response.success) {
                            layer.alert('修改成功', {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            }, function () {
                                window.location.href = 'myBookshelf.html';
                            });
                        }
                    }
                )
            }
        }
    }

    $scope.findAskBookById = function () {
        var bookId = $location.search()['bookId'];
        askBookService.findAskBookById(bookId).success(
            function (response) {
                $scope.entity = response;
            }
        )
    }

    $scope.updateAskBook = function () {
        var file = document.getElementById("file").value;
        if (file != '') {
            var suffixIndex = file.lastIndexOf(".");
            var suffix = file.substring(suffixIndex + 1).toUpperCase();
            if (suffix != "JPG" && suffix != "JPEG" && suffix != "PNG") {
                $("#picmsg").html("请上传图片文件");
            } else {
                if ($scope.checkAskBookForm()) {
                    uploadService.uploadImage().success(
                        function (response) {
                            if (response.success) {
                                $scope.entity.image = response.message;
                                askBookService.updateAskBook($scope.entity).success(
                                    function (response) {
                                        if (response.success) {
                                            layer.alert('修改成功', {
                                                skin: 'layui-layer-molv' //样式类名
                                                , closeBtn: 0
                                            }, function () {
                                                window.location.href = 'myBookshelf.html';
                                            });
                                        }
                                    }
                                )
                            } else {
                                layer.alert('上传失败，请稍后再试', {
                                    skin: 'layui-layer-molv' //样式类名
                                    , closeBtn: 0
                                }, function () {
                                    window.location.href = 'myBookshelf.html';
                                });
                            }
                        }
                    )
                }
            }
        } else {
            if ($scope.checkAskBookForm()) {
                askBookService.updateAskBook($scope.entity).success(
                    function (response) {
                        if (response.success) {
                            layer.alert('修改成功', {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            }, function () {
                                window.location.href = 'myBookshelf.html';
                            });
                        }
                    }
                )
            }
        }
    }

    $scope.sellBookIds = [];
    $scope.updateSellBookSelect = function ($event, id) {
        if (!$scope.sellBookIds.in_array(id)) {
            $($event.target).css("background-image", "url(../img/delete1.png)");
            $scope.sellBookIds.push(id);
        } else {
            $($event.target).css("background-image", "url(../img/delete2.png)");
            var index = $scope.sellBookIds.indexOf(id);
            $scope.sellBookIds.splice(index, 1);
        }
    }

    $scope.askBookIds = [];
    $scope.updateAskBookSelect = function ($event, id) {
        if (!$scope.askBookIds.in_array(id)) {
            $($event.target).css("background-image", "url(../img/delete1.png)");
            $scope.askBookIds.push(id);
        } else {
            $($event.target).css("background-image", "url(../img/delete2.png)");
            var index = $scope.askBookIds.indexOf(id);
            $scope.askBookIds.splice(index, 1);
        }
    }

    $scope.deleteSellBook = function () {
        if ($scope.sellBookIds.length == 0) {
            layer.msg('未选中图书');
        } else {
            layer.confirm('确定删除？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                bookService.deleteSellBook($scope.sellBookIds).success(
                    function (response) {
                        if (response.success) {
                            $scope.findMySellBook(1);
                        }
                    })
                layer.msg('删除成功');
            });
        }
    }

    $scope.deleteAskBook = function () {
        if ($scope.askBookIds.length == 0) {
            layer.msg('未选中图书');
        } else {
            layer.confirm('确定删除？', {
                btn: ['确定', '删除'] //按钮
            }, function () {
                askBookService.deleteAskBook($scope.askBookIds).success(
                    function (response) {
                        if (response.success) {
                            $scope.findMyAskBook(1);
                        }
                    })
                layer.msg('删除成功');
            });
        }
    }


    Array.prototype.in_array = function (e) {
        for (i = 0; i < this.length; i++) {
            if (this[i] == e)
                return true;
        }
        return false;
    }

    $scope.checkSellBookForm = function () {
        var re = /^[0-9]+.?[0-9]*$/;
        if (typeof $scope.entity.name == "undefined" || $scope.entity.name == "") {
            $("#namemsg").html("请输入书名");
            return false;
        } else if (typeof $scope.entity.cid == "undefined" || $scope.entity.cid == "") {
            $("#categorymsg").html("请选择分类");
            return false;
        } else if (typeof $scope.entity.author == "undefined" || $scope.entity.author == "") {
            $("#authormsg").html("请输入作者");
            return false;
        } else if (typeof $scope.entity.originalPrice == "undefined" || $scope.entity.originalPrice == "") {
            $("#originalpricemsg").html("请输入书本原价");
            return false;
        } else if (!re.test($scope.entity.originalPrice)) {
            $("#originalpricemsg").html("请输入正数");
            return false;
        } else if (typeof $scope.entity.price == "undefined" || $scope.entity.price == "") {
            $("#pricemsg").html("请输入书本售价");
            return false;
        } else if (!re.test($scope.entity.price)) {
            $("#pricemsg").html("请输入正数");
            return false;
        } else if (typeof $scope.entity.press == "undefined" || $scope.entity.press == "") {
            $("#pressmsg").html("请输入出版社");
            return false;
        } else if (typeof $scope.entity.publishDate == "undefined" || $scope.entity.publishDate == "") {
            $("#publishdatemsg").html("请输入出版时间");
            return false;
        } else if (typeof $scope.entity.version == "undefined" || $scope.entity.version == "") {
            $("#versionmsg").html("请输入印刷版本");
            return false;
        } else if (typeof $scope.entity.degree == "undefined" || $scope.entity.degree == "") {
            $("#degreemsg").html("请输入书本品相");
            return false;
        } else if (!re.test($scope.entity.degree)) {
            $("#degreemsg").html("请输入正数");
            return false;
        } else if ($scope.entity.degree < 1 || $scope.entity.degree > 10) {
            $("#degreemsg").html("请输入小于10的数");
            return false;
        } else if (typeof $scope.entity.description == "undefined" || $scope.entity.description == "") {
            $("#bookdescriptionmsg").html("请输入书本描述");
            return false;
        } else {
            return true;
        }
    }

    $scope.checkAskBookForm = function () {
        if (typeof $scope.entity.name == "undefined" || $scope.entity.name == "") {
            $("#namemsg").html("请输入书名");
            return false;
        } else if (typeof $scope.entity.author == "undefined" || $scope.entity.author == "") {
            $("#authormsg").html("请输入作者");
            return false;
        } else if (typeof $scope.entity.press == "undefined" || $scope.entity.press == "") {
            $("#pressmsg").html("请输入出版社");
            return false;
        } else if (typeof $scope.entity.description == "undefined" || $scope.entity.description == "") {
            $("#bookdescriptionmsg").html("请输入书本描述");
            return false;
        } else {
            return true;
        }
    }
});

function clearpicmsg() {
    $("#picmsg").html("");
}

function clearnamemsg() {
    $("#namemsg").html("")
}

function clearcategorymsg() {
    $("#categorymsg").html("");
}

function clearauthormsg() {
    $("#authormsg").html("");
}

function clearoriginalpricemsg() {
    $("#originalpricemsg").html("");
}

function clearpricemsg() {
    $("#pricemsg").html("");
}

function clearpressmsg() {
    $("#pressmsg").html("");
}

function clearpublishdatemsg() {
    $("#publishdatemsg").html("");
}

function clearversionmsg() {
    $("#versionmsg").html("");
}

function cleardegreemsg() {
    $("#degreemsg").html("");
}

function clearbookdescriptionmsg() {
    $("#bookdescriptionmsg").html("");
}