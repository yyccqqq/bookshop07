app.service("askBookService", function ($http) {
  this.uploadAskBook = function (entity) {
    return $http.post("../askbook/uploadAskBook", entity);
  };

  this.findMyAskBook = function (pageNum) {
    return $http.get("../askbook/findMyAskBook/" + pageNum);
  };

  this.findAskBookById = function (id) {
    return $http.get("../askbook/findAskBookById/" + id);
  };

  this.updateAskBook = function (entity) {
    return $http.put("../askbook/updateAskBook", entity);
  };

  this.deleteAskBook = function (ids) {
    return $http.post("../askbook/deleteAskBook", ids);
  };

  this.findAskBook = function (pageNum) {
    return $http.get("../askbook/findAskBook/" + pageNum);
  };
});
