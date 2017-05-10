(function (angular) {
    var app=angular.module('interfaceApp',[]);
    app.controller('interfaceCtrl', function ($scope,$http) {
        $scope.interData=[
            {"ip":"192.168.224.124","intface":"tapd5d77b36-e2","interface":"fe:16:3e:71:8c:5e","ofport":"8","rx":"91","tx":"198244","speed":"1000000"}
        ]
        $scope.firstS='switch ip';
        $http({
            method:'post',
            url:'http://172.16.0.127:8080/ssm-3/fordetController/insertPortdet.do'}).success(function (data) {
            $scope.data=data;
        })
    })
})(angular);