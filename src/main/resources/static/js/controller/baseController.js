app.controller('baseController', function ($scope, $http) {
    $scope.paginationConf = {
        currentPage: 1,   //当前页数据  aglr控制
        totalItems: 10,   //默认总条数 后台返回
        itemsPerPage: 10, //每页支持查询的条数 ag 前端控制
        perPageOptions: [10, 20, 30, 40, 50], //每页条数可选项
        onChange: function () {
            $scope.selectIds = [];
            $scope.allcheckbox = false;
            $scope.checkbox = false;
            $scope.reloadList();
        }
    }

    $scope.searchEntity = {};

    $scope.reloadList = function () {
        var pageNum = $scope.paginationConf.currentPage;
        var pageSize = $scope.paginationConf.itemsPerPage;
        $scope.search(pageNum, pageSize, $scope.searchEntity)
    }

    $scope.selectIds = [];

    $scope.logout = function () {
        $http.get("/bookshop/user/logout").success(
            function () {
                window.location.href = "/bookshop/login.html";
            }
        )
    }
})

layui.use('layer', function () {
    var layer = layui.layer;
});





