(function (angular) {
    var app=angular.module('aeroApp');
    app.config(['$stateProvider','$urlRouterProvider', function ($stateProvider,$urlRouterProvider){
        $urlRouterProvider.otherwise('/aTopology');
        $stateProvider
            .state('aTopology',{
                url:'/aTopology',
                templateUrl:'template/aTopologyTpl.html'
            })
            .state('switchCtrl',{
                url:'/switchCtrl',
                templateUrl:'template/aSwitchTpl.html'
            })
            .state('aContrl',{
                url:'/aContrl',
                templateUrl:'template/aContrlTpl.html'
            })
            .state('falut',{
                url:'/falut',
                templateUrl:'template/openflowTpl.html'
            })
            .state('openflow',{
                url:'/openflow',
                templateUrl:'template/openflowTpl.html'
            })
            .state('node',{
                url:'/node',
                templateUrl:'template/nodeTpl.html'
            })
            .state('interface',{
                url:'/interface',
                templateUrl:'template/interfaceTpl.html'
            })
            .state('vtn',{
                url:'/vtn',
                templateUrl:'template/vtnTpl.html'
            })
    }]);
})(angular);