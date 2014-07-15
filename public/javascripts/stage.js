/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 13/05/14
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
var run = [];
function load () {
    $.ajax({
        url: 'http://localhost:9000/run',
        type: 'GET',
        success: function(data ) {
            run = eval(data)
            var stageChart = new Kinetic.Stage({
                container: "kinetic",
                width: 380,
                height: 160
            });

            var stageStatus = new Kinetic.Stage({
                container: "status",
                width: 380,
                height: 160
            });

            var layerChart = new Kinetic.Layer();
            chartRun({x:20,y:150},layerChart);
            stageChart.add(layerChart);

            var layerStatus = new Kinetic.Layer();
            chartCPU({x:20,y:150},layerStatus);
            stageStatus.add(layerStatus);
        }
    });
};

window.onload = function() {
    load();


};

var SIZEX=380;
var SIZEY=180;

function getmaxRun( list) {
    var max = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay.run > max) max = list[i].dataDay.run;
    }
    return max;
}



function getmaxCpu( list) {
    var max = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay.cpu > max) max = list[i].dataDay.cpu;
    }
    return max;
}

function getmaxMem( list) {
    var max = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay.memory > max) max = list[i].dataDay.memory;
    }
    return max;
}

function chartRun(pos,layer) {

    var group = new Kinetic.Group();
    var eltLength = run.list.length;
    var eltSize = (SIZEX-40)/eltLength - 2;
    var max = getmaxRun(run.list);
    var heightM = (SIZEY-60)/max;

    //drawFrame({x:SIZEX,y:SIZEY},pos,group,'grey','engine');

    for (var i=0; i<run.list.length;i++) {
        drawChart(group, pos, run.list[i].dataDay.run, heightM, eltSize, i, run.list[i].day, layer, 'grey');
    }

    group.on('mouseup', function() {
        group.scale({x:0.8,y:0.8});
        layer.draw();
    });
    group.scale(0.8,0.8);
    layer.add(group);
}


function chartCPU(pos,layer) {

    var group = new Kinetic.Group();
    var eltLength = run.list.length*2;
    var eltSize = (SIZEX-40)/eltLength - 1;
    var heightMCPU = (SIZEY-60)/Math.max(getmaxCpu(run.list),20);
    var heightMMEM = (SIZEY-60)/Math.max(getmaxMem(run.list),20);

    //drawFrame({x:SIZEX,y:SIZEY},pos,group,'#83C348'/*'#E2E482'*/,'cpu');

    for (var i=0; i<run.list.length;i++) {
        //drawChart(group, pos, run.list[i].dataDay.cpu, heightM, eltSize, i, run.list[i].day, layer, '#83C348');
        draw2Chart(group, pos, run.list[i].dataDay.cpu, run.list[i].dataDay.memory, heightMCPU, heightMMEM,eltSize, i, run.list[i].day, layer, '#83C348');
    }

    group.on('mouseup', function() {
        group.scale({x:0.8,y:0.8});
        layer.draw();
    });
    group.scale(0.8,0.8);
    layer.add(group);
}