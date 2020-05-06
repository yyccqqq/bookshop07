app.controller('userController', function ($scope, $interval, $controller, userService) {

        $controller('baseController', {$scope: $scope});//继承

        $scope.entity = {};

        $scope.username = Cookies.get("username");

        $scope.login = function () {
            var studentId = $scope.entity.studentId;
            var password = $scope.entity.password;
            if ((typeof studentId == "undefined" || studentId == null || studentId == "") || (typeof password == "undefined" || password == null || password == "")) {
                layer.msg('账号和密码不能为空');
            } else {
                userService.login($scope.entity).success(
                    function (response) {
                        if (response.success) {
                            userService.findUser().success(
                                function (response) {
                                    Cookies.set('id', response.id);
                                    Cookies.set('username', response.username);
                                    window.location.href = "view/index.html";
                                }
                            )
                        } else {
                            layer.msg('账号或密码错误');
                        }
                    })
            }
        }

        $scope.register = function () {
            var emailreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if (typeof $scope.entity.studentId == "undefined" || $scope.entity.studentId == "") {
                $("#studentIdmsg").html("请输入学号");
            } else {
                if ($scope.checkPasswordForm()) {
                    if ($scope.checkEmailReg()) {
                        if ($scope.checkUserForm()) {
                            userService.register($scope.entity).success(
                                function (response) {
                                    if (response.success) {
                                        $scope.entity = {};
                                        $scope.repassword = '';
                                        window.location.href = "login.html";
                                    } else {
                                        layer.msg('注册失败，请稍后再试');
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        $scope.checkStudentId = function () {
            userService.checkStudentId($scope.entity.studentId).success(
                function (response) {
                    if (!response.success) {
                        $("#studentIdmsg").html("学号已被注册");
                        $scope.entity.studentId = '';
                    }
                }
            )
        }

        $scope.checkEmail = function () {
            userService.checkEmail($scope.entity.email).success(
                function (response) {
                    if (!response.success) {
                        $("#emailmsg").html("邮箱已被绑定");
                        $scope.entity.email = '';
                    }
                }
            )
        }

        $scope.countdown = "获取验证码";
        $scope.isDisable = false;
        $scope.sendCode = function (val) {
            var email = $scope.entity.email;
            var emailreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if (!(typeof email == "undefined" || email == null || email == "")) {
                if (!emailreg.test(email)) {
                    $("#emailmsg").html("邮箱格式不正确");
                } else {
                    userService.checkEmail($scope.entity.email).success(
                        function (response) {
                            if (!response.success) {
                                $scope.isDisable = true;
                                var second = 60;
                                var time = function () {
                                    second--;
                                    if (second <= 0) {
                                        second = "";
                                        $scope.isDisable = false;
                                    }
                                    $scope.countdown = "重新获取 " + second;
                                };
                                $interval(time, 1000, 60);
                                userService.sendCode($scope.entity.email).success(
                                    function (response) {
                                        if (response.success) {
                                            layer.msg('发送成功');
                                        }
                                    }
                                )
                            } else {
                                $("#emailmsg").html("邮箱未被注册");
                            }
                        }
                    )
                }
            } else {
                $("#emailmsg").html("请输入邮箱");
            }
        }

        $scope.updatePassword = function () {
            var email = $scope.entity.email;
            var code = $scope.code;
            var password = $scope.entity.password;
            $scope.updateEntity = {};
            if ($scope.checkEmailReg()) {
                if ((typeof $scope.code == "undefined" || $scope.code == "")) {
                    $("#codemsg").html("请输入验证码");
                } else {
                    if ($scope.checkPasswordForm()) {
                        $scope.updateEntity.email = email;
                        $scope.updateEntity.code = code;
                        $scope.updateEntity.password = password;
                        userService.updatePassword($scope.updateEntity).success(
                            function (response) {
                                if (response.success) {
                                    $scope.entity = {};
                                    $scope.repassword = "";
                                    window.location.href = "login.html";
                                } else {
                                    layer.msg("验证码错误");
                                }
                            }
                        )
                    }
                }
            }
        }

        $scope.findMy = function () {
            userService.findMy().success(
                function (response) {
                    $scope.entity = response;
                }
            )
        }

        $scope.updateUser = function () {
            if ($scope.checkUserForm()) {
                userService.updateUser($scope.entity).success(
                    function (response) {
                        if (response.success) {
                            window.location.href = "myBookshelf.html";
                        }
                    }
                )
            }
        }

        $scope.checkOldPassword = function () {
            userService.findOldPassword($scope.oldPassword).success(
                function (response) {
                    if (!response.success) {
                        $("#oldpassmsg").html("旧密码错误");
                        $scope.oldPassword = '';
                    }
                }
            )
        }

        $scope.editPassword = function () {
            if (typeof $scope.oldPassword == "undefined" || $scope.oldPassword == "") {
                $("#oldpassmsg").html("请输入旧密码");
            } else {
                if ($scope.checkPasswordForm()) {
                    userService.editPassword($scope.entity).success(
                        function (response) {
                            if (response.success) {
                                window.location.href = '../login.html';
                            }
                        }
                    )
                }
            }
        }

        $scope.checkUserForm = function () {
            if (typeof $scope.entity.name == "undefined" || $scope.entity.name == "") {
                $("#namemsg").html("请输入姓名");
                return false;
            } else if (typeof $scope.entity.sex == "undefined" || $scope.entity.sex == "") {
                $("#sexmsg").html("请选择性别");
                return false;
            } else if (typeof $scope.entity.tel == "undefined" || $scope.entity.tel == "") {
                $("#telmsg").html("请输入联系电话");
                return false;
            } else if (typeof $scope.entity.address == "undefined" || $scope.entity.address == "") {
                $("#addressmsg").html("请输入住址");
                return false;
            } else if (typeof $scope.entity.major == "undefined" || $scope.entity.major == "") {
                $("#majormsg").html("请输入专业");
                return false;
            } else {
                return true;
            }
        }

        $scope.checkPasswordForm = function () {
            if ((typeof $scope.entity.password == "undefined" || $scope.entity.password == "")) {
                $("#passmsg").html("请输入密码");
                return false;
            } else if ((typeof $scope.repassword == "undefined" || $scope.repassword == "")) {
                $("#repassmsg").html("请再次输入密码");
                return false;
            } else if ($scope.entity.password != $scope.repassword) {
                $("#repassmsg").html("两次密码不一致");
                return false;
            } else {
                return true;
            }
        }

        $scope.checkEmailReg = function () {
            var emailreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if ((typeof $scope.entity.email == "undefined" || $scope.entity.email == "")) {
                $("#emailmsg").html("请输入邮箱");
                return false;
            } else if (!emailreg.test($scope.entity.email)) {
                $("#emailmsg").html("邮箱格式不正确");
                return false;
            } else {
                return true;
            }
        }
    }
);

function clearstudentidmsg() {
    $("#studentIdmsg").html("");
}

function clearpassmsg() {
    $("#passmsg").html("");
}

function clearrepassmsg() {
    $("#repassmsg").html("");
}

function clearemailmsg() {
    $("#emailmsg").html("");
}

function clearnamemsg() {
    $("#namemsg").html("");
}

function clearsexmsg() {
    $("#sexmsg").html("");
}

function cleartelmsg() {
    $("#telmsg").html("");
}

function clearaddressmsg() {
    $("#addressmsg").html("");
}

function clearmajormsg() {
    $("#majormsg").html("");
}

function clearcodemsg() {
    $("#codemsg").html("");
}

function clearoldpassmsg() {
    $("#oldpassmsg").html("");
}
