(function (angular) {
    var switchApp=angular.module('switchApp',[]);
    switchApp.controller('switchCtrl', function ($scope,$http) {
        $http({
            method:'post',
            url:'switchController/selectSwitch.do'}).success(function (data) {
            $scope.switchData=data[0];
            console.log(data);
            if(data.type=='v'){
                angular.element('#main').removeClass('').addClass('');
            }else{
                 angular.element('#main').removeClass('').addClass('');
            }
        })
    })
    switchApp.directive('mouseOverLeave', function(){
        return {
            restrict: 'A',
            scope: {
                hover: "="
            },
            link: function(scope, elem, attr){
                elem.bind('mouseover', function(){
                    elem.css("cursor", "pointer");
                    scope.$apply(function(){
                        scope.hover = true;
                    });
                });
                elem.bind('mouseleave', function(){
                    scope.$apply(function(){
                        scope.hover = false;
                    });
                });
            }
        }
    });
})(angular);