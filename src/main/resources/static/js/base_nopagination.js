var app = angular.module('bookshop', []);

app.filter('trustHtml',['$sce',function ($sce) {
    return function (data) {
        return $sce.trustAsHtml(data);
    }
}])