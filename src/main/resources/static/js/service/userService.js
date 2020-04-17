app.service('userService', function ($http) {

    this.login = function (entity) {
        return $http.post('user/login', entity);
    }

    this.findUser = function () {
        return $http.get('user/findUser');
    }

    this.checkStudentId = function (studentId) {
        return $http.get('user/checkStudentId/' + studentId);
    }

    this.checkEmail = function (email) {
        return $http.get('user/checkEmail/' + email);
    }

    this.register = function (entity) {
        return $http.post('user/register', entity);
    }

    this.sendCode = function (email) {
        return $http.get('user/sendCode/' + email);
    }

    this.updatePassword = function (entity) {
        return $http.put('user/updatePassword', entity);
    }

    this.findMy = function () {
        return $http.get('../user/findMy');
    }

    this.updateUser = function (entity) {
        return $http.put('../user/updateUser', entity);
    }

    this.findOldPassword = function (oldPassword) {
        return $http.get('../user/findOldPassword/' + oldPassword);
    }

    this.editPassword = function (entity) {
        return $http.put('../user/editPassword', entity);
    }
})