app.service('bookService', function ($http) {

    this.findBook = function () {
        return $http.get('../book/findBook');
    }

    this.findBookByCategoryId = function (categoryId, pageNum) {
        return $http.get('../book/findBookByCategoryId/' + categoryId + '/' + pageNum);
    }

    this.findGoodBook = function (pageNum) {
        return $http.get('../book/findGoodBook/' + pageNum);
    }

    this.search = function (keyword, pageNum) {
        return $http.get('../book/search/' + keyword + '/' + pageNum);
    }

    this.findUserAllBook = function (userId, pageNum) {
        return $http.get('../book/findUserAllBook/' + userId + '/' + pageNum);
    }

    this.uploadSellBook = function (entity) {
        return $http.post('../book/uploadSellBook', entity);
    }

    this.findMySellBook = function (pageNum) {
        return $http.get('../book/findMySellBook/' + pageNum);
    }

    this.findBookById = function (id) {
        return $http.get('../book/findBookById/' + id);
    }

    this.updateSellBook = function (entity) {
        return $http.put('../book/updateSellBook', entity);
    }

    this.deleteSellBook = function (ids) {
        return $http.post('../book/deleteSellBook', ids);
    }

})