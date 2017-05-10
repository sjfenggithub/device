(function (angular) {
    var app=angular.module('aControlApp',[]);
    app.controller('aContrl', function ($scope,$http) {
        $http({
            method:'get',
            url:'contController/selectContList.do'}).success(function (data) {
            console.log(data);
            $scope.data=data;
            if(data.number==2){
                $scope.two=true;
            }else if(data.number==3){
                $scope.three=true;
            }else if(data.number==5){
                $scope.five=true;
            }else if(data.number==7){
                $scope.seven=true;
            }
        });

        $scope.two=false;
        $scope.three=false;
        $scope.five=false;
        $scope.seven=false;
        $scope.show=false;
        $scope.isShow= function (item) {
                item.isShow=true;
        }
        $scope.isHide= function (item) {
                item.isShow=false;
        }
        $scope.showList= function (item) {
            item.isShow1=true;
        }
        $scope.hideList= function (item) {
            if(!item.isShowWin){
                item.isShow1=false;
            }
        }
        $scope.showWin= function (item) {
            item.isShowWin=true;
        }
        $scope.hideWin= function (item) {
            item.isShowWin = false;
        }
        var window=document.getElementById('window');
        console.log(angular.element(window));
        $scope.newData= function () {
            $http({
                method:'get',
                url:''
            }).success(function (data) {
                $scope.listData=data;
                console.log(data);
            })
        }
        $scope.turn= function (ip1) {
            $http({
                method: 'post',
                url: 'switchController/selectSwitchFromIp.do',
                data: {ip: ip1},
                headers:{ 'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (data){
                    return $.param(data);
                }
            }).success(function (data) {
                console.log(data);
            })
        }
    });
})(angular);