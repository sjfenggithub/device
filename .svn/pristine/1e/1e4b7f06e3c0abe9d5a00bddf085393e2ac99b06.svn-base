(function (angular) {
    var app=angular.module('openflowApp',[]);
    app.controller('openflowCtrl', function ($scope,$http) {
        $scope.openData=[
            {"id":"table=30","priority":"priority=1024","match":"udp,reg0=0x1,tp_src=68,tp_dst=67","instruction":"actions=goto_table:110","info":"cookie=0x0, duration=64449.139s,n_packets=152, n_bytes=12748"}
        ]
        $scope.firstS='dpid';
        $http({
            method:'post',
            url:'http://172.16.0.127:8080/ssm-3/fordetController/insertPortdet.do'}).success(function (data) {
            $scope.data=data;
        })
    })
})(angular);