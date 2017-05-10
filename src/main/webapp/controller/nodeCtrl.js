(function (angular) {
    var app=angular.module('nodeApp',[]);
    app.controller('nodeCtrl', function ($scope,$http) {
        $scope.nodeData=[
            {"ip":"192.168.224.124","bridge":"br-int","mac":"5a:a0:f2:01:20:4d","dpid":"00005aa0f201204d","vm":"1","tunnel":"2"}
        ]
        $scope.firstS='switch ip';
        $http({
            method:'post',
            url:'http://172.16.0.127:8080/ssm-3/fordetController/insertPortdet.do'}).success(function (data) {
            $scope.data=data;
        })
    });
})(angular);