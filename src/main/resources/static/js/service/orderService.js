app.service('orderService', function ($http) {

    this.getSelectCartList = function () {
        return $http.get('../cart/getSelectCartList');
    }

    this.submitOrder = function () {
        return $http.get('../orders/submitOrder');
    }

    this.getPayUrl = function () {
        return $http.get('../orders/getPayUrl');
    }

    this.queryPayStatus = function (orderNo) {
        return $http.get('../orders/queryPayStatus/' + orderNo);
    }

    this.findOrderSend = function (pageNum) {
        return $http.get('../orders/findOrderSend/' + pageNum);
    }

    this.confirm = function (orderId) {
        return $http.get('../orders/confirm/' + orderId);
    }

    this.findOrderPay = function (pageNum) {
        return $http.get('../orders/findOrderPay/' + pageNum);
    }

    this.findOrderSell = function (pageNum) {
        return $http.get('../orders/findOrderSell/' + pageNum);
    }

    this.findOrderSuccess = function (pageNum) {
        return $http.get('../orders/findOrderSuccess/' + pageNum);
    }

    this.cancelOrder = function (orderId) {
        return $http.get('../orders/cancelOrder/' + orderId);
    }

    this.payAgain = function (orderId) {
        return $http.get('../orders/payAgain/' + orderId);
    }
})