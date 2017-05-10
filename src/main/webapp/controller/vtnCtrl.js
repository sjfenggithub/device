(function (angular) {
    var app=angular.module('vtnApp',[]);
    app.controller('vtnCtrl', function ($scope,$http) {
        $http({
            method:'post',
            url:'http://172.16.0.127:8080/ssm-3/fordetController/insertPortdet.do'}).success(function (data) {
            $scope.data=data;
        })
        $scope.firstS='Port';
        $scope.SecondS='--请选择--';
        $scope.port=['port ID','port IP','port MAC','租户ID','network ID'];
        $scope.subnet=['subnet ID','network IP','租户ID'];
        $scope.network=['network ID','租户ID'];
        $scope.router=['routerID','租户ID'];
        $scope.floatingIP=['floatingIP ID','租户ID','networkID'];
        $scope.securityGroup=['securitygroupID','租户ID'];
        $scope.securityGroupRule=['securitygroupruleID','securitygroupID','租户ID'];
        $scope.data=[];
        $scope.selectChange= function () {
            console.log($scope.firstS);
            if( $scope.firstS == 'Port'){
                //console.log(1);
                $scope.data=$scope.port;
                $scope.SecondS='port ID';
                console.log($scope.data);
            }else if($scope.firstS == 'Subnet'){
                $scope.data=$scope.subnet;
                console.log($scope.data);
                $scope.SecondS='subnet ID';
            }else if($scope.firstS == 'Network'){
                $scope.data=$scope.network;
                console.log($scope.data);
                $scope.SecondS='network ID';
            }else if($scope.firstS == 'Router'){
                $scope.data=$scope.router;
                $scope.SecondS='routerID';
            }else if($scope.firstS == 'FloatingIP'){
                $scope.data=$scope.floatingIP;
                $scope.SecondS='floatingIP ID';
            }else if($scope.firstS =='SecurityGroup'){
                $scope.data=$scope.securityGroup;
                $scope.SecondS='securitygroupID';
            }else if($scope.firstS =='SecurityGroupRule'){
                $scope.data=$scope.securityGroupRule;
                $scope.SecondS='securitygroupruleID';
            }
        }
        $scope.portData=[
            {"id":"60aee502-ca7e-4773-8c13-5262936e598e","name":"port1","mac":"fa:16:3e:d3:2d:b3","fixed":"{\"subnet_id\": \"2fdbd97c-8dc2-468e-af78-b92e48677675\", \"ip_address\": \"1.1.1.2\"}"},
            {"id":"60aee502-ca7e-4773-8c13-5262936e598e","name":"port1","mac":"fa:16:3e:d3:2d:b3","fixed":"{\"subnet_id\": \"2fdbd97c-8dc2-468e-af78-b92e48677675\", \"ip_address\": \"1.1.1.3\"}"},
            {"id":"60aee502-ca7e-4773-8c13-5262936e598e","name":"port1","mac":"fa:16:3e:d3:2d:b3","fixed":"{\"subnet_id\": \"2fdbd97c-8dc2-468e-af78-b92e48677675\", \"ip_address\": \"1.1.1.4\"}"},
            {"id":"60aee502-ca7e-4773-8c13-5262936e598e","name":"port1","mac":"fa:16:3e:d3:2d:b3","fixed":"{\"subnet_id\": \"2fdbd97c-8dc2-468e-af78-b92e48677675\", \"ip_address\": \"1.1.1.5\"}"},
            {"id":"60aee502-ca7e-4773-8c13-5262936e598e","name":"port1","mac":"fa:16:3e:d3:2d:b3","fixed":"{\"subnet_id\": \"2fdbd97c-8dc2-468e-af78-b92e48677675\", \"ip_address\": \"1.1.1.6\"}"}
        ];
        $scope.netData=[
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"},
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"},
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"},
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"},
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"},
            {"id":"b786eac8-dae9-454e-ad89-68b07ba3a4c6","name":"net-192.168.50.0","subnets":"6b007771-8900-4e0c-a9e8-bbb25bfbe134 192.168.50.0/24"}
        ]
        $scope.subData=[
            {"id":"6b007771-8900-4e0c-a9e8-bbb25bfbe134","name":"sub-192.168.50.0","cidr":"192.168.50.0/24","pools":"{\"start\": \"192.168.50.2\", \"end\": \"192.168.50.254\"}"},
            {"id":"6b007771-8900-4e0c-a9e8-bbb25bfbe134","name":"sub-192.168.50.0","cidr":"192.168.50.0/24","pools":"{\"start\": \"192.168.50.2\", \"end\": \"192.168.50.254\"}"},
            {"id":"6b007771-8900-4e0c-a9e8-bbb25bfbe134","name":"sub-192.168.50.0","cidr":"192.168.50.0/24","pools":"{\"start\": \"192.168.50.2\", \"end\": \"192.168.50.254\"}"},
            {"id":"6b007771-8900-4e0c-a9e8-bbb25bfbe134","name":"sub-192.168.50.0","cidr":"192.168.50.0/24","pools":"{\"start\": \"192.168.50.2\", \"end\": \"192.168.50.254\"}"},
            {"id":"6b007771-8900-4e0c-a9e8-bbb25bfbe134","name":"sub-192.168.50.0","cidr":"192.168.50.0/24","pools":"{\"start\": \"192.168.50.2\", \"end\": \"192.168.50.254\"}"}
        ]
        $scope.routerData=[
            {"id":"70f1ac4e-90e5-4365-932c-984c1957d734","name":"vrt1480387995401","external":"{\"network_id\": \"bb4b289f-7902-4f88-b63d-0b7372ee54f1\", \"enable_snat\": true}"},
            {"id":"70f1ac4e-90e5-4365-932c-984c1957d734","name":"vrt1480387995401","external":"{\"network_id\": \"bb4b289f-7902-4f88-b63d-0b7372ee54f1\", \"enable_snat\": true}"},
            {"id":"70f1ac4e-90e5-4365-932c-984c1957d734","name":"vrt1480387995401","external":"{\"network_id\": \"bb4b289f-7902-4f88-b63d-0b7372ee54f1\", \"enable_snat\": true}"},
            {"id":"70f1ac4e-90e5-4365-932c-984c1957d734","name":"vrt1480387995401","external":"{\"network_id\": \"bb4b289f-7902-4f88-b63d-0b7372ee54f1\", \"enable_snat\": true}"},
            {"id":"70f1ac4e-90e5-4365-932c-984c1957d734","name":"vrt1480387995401","external":"{\"network_id\": \"bb4b289f-7902-4f88-b63d-0b7372ee54f1\", \"enable_snat\": true}"}
        ]
        $scope.flaotData=[
            {"id":"0f9f5bd1-87a5-4d02-a689-3c9264987394","fixed":"192.168.0.6","flaoting":"10.1.1.31","port":"f57e8bab-5d39-4933-baec-2aed5e06f296"}
        ]
        $scope.secuData=[
            {"id":"1727b1c4-b875-497c-8f48-118c35fc1c16","name":"sg1480389307478","description":"Allow ssh access to VMs created by Rally for benchmarkin"}
        ]
        $scope.secugrData=[
            {"id":"0201b979-9c54-4d77-a25a-0c5ddb0046ba","security":"sg1480476164271","direction":"ingress","protocol":"icmp","prefix":"0.0.0.0/0","group":""},
            {"id":"0201b979-9c54-4d77-a25a-0c5ddb0046ba","security":"sg1480476164271","direction":"ingress","protocol":"icmp","prefix":"","group":"default"}
        ]
        $scope.showList= function (items) {
            items.isShow=true;
        }
        $scope.hideList= function (items) {
            items.isShow=false;
        }
    });

})(angular);