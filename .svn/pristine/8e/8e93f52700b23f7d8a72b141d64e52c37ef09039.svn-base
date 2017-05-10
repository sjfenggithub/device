function setServer(){
    $(function () {
        function node(hexin,node,distance){
            var x= 0,y= 0,xArr=[],yArr=[];
            var result=[];
            var portNumber =0;
            if(node.length<=13){
                portNumber =node.length;
            }else {
               portNumber = Math.ceil((node.length - 1) / 3);
            }
            console.log(portNumber);
            var interval=(2*distance)/portNumber;
            for (var i = 0; i < node.length; i++) {
                if(node.length<=13){
                    x=hexin[0]-distance+i*interval;
                    y=hexin[1]+distance+interval;
                }else {
                    if(node[i].name<=portNumber){
                        x=hexin[0]-distance-interval;
                        y=hexin[1]-distance+(i-portNumber)*interval;
                    }else if(node[i].name<=portNumber*2){
                        x=hexin[0]-distance+(i-2*portNumber)*interval;
                        y=hexin[1]+distance+interval;
                    }else if(node[i].name<=portNumber*3) {
                        x=hexin[0]+distance+interval;
                        y=hexin[1]-distance+(i-3*portNumber)*interval;
                    }
                }
                xArr.push(x);
                yArr.push(y);
            }
            result=[xArr,yArr];
            return result;
        }
        function nfvNode(hexin,node,distance) {
            var x= 0,y= 0,xArr=[],yArr=[];
            var nfvResult=[];
            var portNumber=node.length;
            var interval=(2*distance)/portNumber;
            for(var i=0;i<node.length;i++){
                x=hexin[0]-distance+interval*i;
                y=hexin[1]-distance;
                xArr.push(x);
                yArr.push(y);
            }
            nfvResult=[xArr,yArr];
            return nfvResult;
        }
        $.ajax({
            url:'topoController/selectToptList.do',
            type:'get',
            dataType:'json',
            success: function (json) {
                console.log(json);
                var nodes=[];
                var links=[];
                function creatNode(name,x,y,category,value){
                    var node={
                        name:name,
                        symbolSize:category==0?60:(category==2?40:40),
                        symbol:category==0?'image://images/controller.png':category==2?name=='more'?'image://images/dot.png':'image://images/pyhSwitch.png':category==3?'image://images/visSwitch.png':'image://images/nfv.png',
                        label:{normal:{position:category==2||3?x==40?'left':x==460?'right':'top':'top',show:true}},
                        category:category,
                        value:value,
                        x:x,
                        y:y
                    }
                    nodes.push(node);
                }
                myChart.showLoading();
                var nodeList=json.Switch;
                var x,y;
                for (var i = 0; i < nodeList.length; i++) {
                    var s=node([550,240],nodeList,160);
                    if(nodeList.length!=1){
                        x=s[0][i];
                        y=s[1][i];
                        creatNode(nodeList[i].name,x,y,nodeList[i].category,nodeList[i].value);
                    }else{
                        creatNode(nodeList[i].name,160,160,nodeList[i].category,nodeList[i].value);
                    }
                }
                console.log('switch');
                console.log(nodes);
                var nodeList2=json.Controller;
                creatNode(nodeList2[0].name,550,240,nodeList2[0].category,nodeList2[0].value);
                console.log('cotrl');
                console.log(nodes);
                var nodeList1=json.NFV;
                var nfvs=nfvNode([550,240],nodeList1,160);
                for (var i = 0; i < nodeList1.length; i++) {
                    x=nfvs[0][i];
                    y=nfvs[1][i];
                    creatNode(nodeList1[i].name,x,y,nodeList1[i].category,nodeList1[i].value);
                }
                console.log('nfv');
                console.log(nodes);
                var nodeList3=json.dot[0];
                    var s=node([550,240],nodeList,60);
                    x=s[0][i];
                    y=s[1][i];
                    creatNode(nodeList.name,x,y,nodeList.category,nodeList3.value);
                console.log('dot');
                console.log(nodes);
                var linklist=json.edge;
                for (var i = 0; i <linklist .length; i++) {
                    links.push({
                        source:linklist[i].source,
                        target:linklist[i].target,
                        lineStyle:{normal:{color:linklist[i].value>=50?"#f00":"#0f0"}},
                        weight:1
                    });
                }
                console.log('edge');
                console.log(nodes);
                myChart.hideLoading();
                option = {
                    backgroundColor:'rgb(255, 255, 255)',
                    title: {
                        text: '服务器拓扑图'
                    },
                    tooltip: {
                        formatter: function (params) {
                            return params.value;
                        }
                    },
                    animationDurationUpdate: 1500,
                    animationEasingUpdate: 'quinticInOut',
                    legend:{
                        x:'right',
                        data:[{name:'Controller',icon: 'circle'},{name:'NFV',icon: 'circle'},{name:'vSwitch',icon: 'circle'},{name:'phySwitch',icon: 'circle'}],
                        selectedMode:true
                    },
                    series:[
                        {
                            type: 'graph',
                            name:'servers',
                            layout:'none',
                            //symbolSize: 50,
                            roam: true,
                            categories:[
                                {
                                    name:'Controller',
                                    itemStyle:{
                                        normal:{
                                            color:'#869bff'
                                        }
                                    }
                                },
                                {
                                    name:'NFV',
                                    itemStyle:{
                                        normal:{
                                            color:'#0fbee7'
                                        }
                                    }
                                },
                                {
                                    name:'vSwitch',
                                    itemStyle:{
                                        normal:{
                                            color:'#706f72'
                                        }
                                    }
                                },
                                {
                                    name:'phySwitch',
                                    itemStyle:{
                                        normal:{
                                            color:'#4f63a2'
                                        }
                                    }
                                }
                            ],
                            edgeSymbolSize: [4, 10],
                            edgeLabel: {
                                normal: {
                                    textStyle: {
                                        fontSize: 20
                                    },
                                    position:"middle"
                                }
                            },
                            data:nodes,
                            links: links,
                            lineStyle: {
                                normal: {
                                    color: 'source',
                                    curveness: 0.1
                                }
                            }
                        }
                    ]
                };
                myChart.setOption(option);
                myChart.on('click', function (params) {
                    if(params.componentType==='series'){
                        if(params.seriesType=='graph'){
                            if(params.dataType=='node'){
                                if(params.data.category==1){
                                    var nfv=json.NFV;
                                    var nfvUrl=[];
                                    var nfvIp=[];
                                    for (var i = 0; i < json.NFV.length; i++) {
                                        nfvUrl.push(json.NFV[i].url);
                                        nfvIp.push(json.NFV[i].ip);
                                    }
                                    var nfvNode=params.data;
                                    for (var i = 0; i < json.NFV.length; i++) {
                                        if(params.data.name==json.NFV[i].name) {
                                            console.log(nfvUrl[i]);
                                            window.document.location.href=nfvUrl[i];
                                            $.ajax({
                                                url:'',
                                                type:'post',
                                                data:{'ip':json.NFV[i].ip},
                                                success: function () {

                                                }

                                            })
                                        }
                                    }
                                }else if(params.data.category==0){
                                    location.href='aero_index.html#/aContrl';
                                }
                                else{
                                    location.href='aero_index.html#/switchCtrl';
                                }
                            }
                        }
                    }
                });
            }
        });
        return myChart;
    });
}




