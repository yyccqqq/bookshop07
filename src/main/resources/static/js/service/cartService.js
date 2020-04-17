app.service('cartService', function ($http) {

    this.findAll = function () {
        return $http.get('../../cart/findAll');
    }

    this.addCart = function (bookId) {
        return $http.get('../../cart/addCart/' + bookId);
    }

    this.findBookInCart = function () {
        return $http.get('/bookshop/cart/findBookInCart');
    }

    this.delete = function (cartId) {
        return $http.delete('../cart/delete/' + cartId);
    }

    this.checkBook = function (ids) {
        return $http.post('../cart/checkBook', ids);
    }

    this.addToCartList = function (ids) {
        return $http.post('../cart/addToCartList', ids);
    }

    this.findBookById = function (id) {
        return $http.get('../../book/findBookById/' + id);
    }

})