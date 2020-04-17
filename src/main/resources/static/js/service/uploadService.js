app.service('uploadService', function ($http) {

    this.uploadImage = function () {
        var formData = new FormData();
        formData.append('file', file.files[0]);
        return $http({
            method: 'post',
            url: '../book/upload',
            data: formData,
            headers: {
                //设置请求头 angular自动转换传输文件的形式
                'Content-Type': undefined
            },
            //表单数据序列化传输
            transformRequest: angular.identity
        })
    }

    this.deleteImage=function (id) {
        return $http.get('../book/delete/'+id);
    }
})