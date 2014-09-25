/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 13/05/14
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */

/**
 * global data array field
 * @type {Array}
 */
var run = [];

/**
 * init stage kinetic canvas
 * init route for get parameter data
 * init websocket for new notification
 */
function load () {
    $.ajax({
        url: 'http://frlpmdev21:9000/runStub',
        type: 'GET',
        success: function(data ) {
            run = eval(data)

            chartRun({x:60,y:150},layerChart);
            stageChart.add(layerChart);

            chartStat({x:40,y:150},layerStatus);
            stageStatus.add(layerStatus);
        }
    });

    var stageChart = new Kinetic.Stage({
        container: "kinetic",
        width: 440,
        height: 160
    });

    var stageStatus = new Kinetic.Stage({
        container: "status",
        width: 440,
        height: 160
    });

    var layerChart = new Kinetic.Layer();


    var layerStatus = new Kinetic.Layer();


/*
    $(function() {
        var ws = new WebSocket("ws://frlpmdev21:9000/stubFeed");
        ws.onmessage = function(msg) {
            var tmp = eval("("+msg.data+")");
            run.list[tmp.day-1].dataDay = tmp.dataDay
            layerChart.destroyChildren()
            chartRun({x:20,y:150},layerChart);
            stageChart.add(layerChart);
        }
    })*/

};

/**
 * onload javascript function
 */
window.onload = function() {
    load();
};

var SIZEX=380;
var SIZEY=180;
var marginX= 340;



/**
 *
 * @param list
 * @param f
 * @returns {number}
 */

function getMean( list, f) {
    var mean = 0; var sizeNotNull = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay[f] != 0) {
            mean += list[i].dataDay[f];
            sizeNotNull ++;
        }
    }
    return mean / sizeNotNull;
}

/**
 *
 * @param list
 * @param f
 * @param h
 * @returns {number}
 */
function getMeanRun( list, f,h) {
    var mean = 0; var sizeNotNull = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay[f][h] != 0) {
            mean += list[i].dataDay[f][h];
            sizeNotNull ++;
        }
    }
    return mean / sizeNotNull;
}

/**
 *
 * @param list
 * @param f
 * @returns {number}
 */
function getmax( list,f) {
    var max = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay[f] > max) max = list[i].dataDay[f];
    }
    return max;
}

/**
 *
 * @param list
 * @param f
 * @param h
 * @returns {number}
 */

function getmaxRun( list,f,h) {
    var max = 0;
    for (var i=0;i<list.length;i++) {
        if (list[i].dataDay[f][h] > max) max = list[i].dataDay[f][h];
    }
    return max;
}

/**
 * draw run graph
 * @param pos position in page
 * @param layer : add group to layer
 */
function chartRun(pos,layer) {

    var group = new Kinetic.Group();
    var eltLength = run.list.length;
    var eltSize = (SIZEX-40)/eltLength - 2;
    var max = getmaxRun(run.list, 'run','global');
    var heightM = (SIZEY-60)/max;

    for (var i=0; i<run.list.length;i++) {
        drawChart(group, pos, run.list[i].dataDay.run, heightM, eltSize, i, run.list[i].day, layer, 'grey');
    }

    drawChartMax(group, pos, max, getMeanRun(run.list, 'run','global'), heightM, marginX);

    layer.add(group);
}

/**
 * draw stat graph with cpu , memory metrics
 * @param pos position in page
 * @param layer : add group to layer
 */
function chartStat(pos, layer) {

    var group = new Kinetic.Group();
    var eltLength = run.list.length*2;
    var eltSize = (SIZEX-40)/eltLength - 1;
    var heightMCPU = (SIZEY-60)/Math.max(getmax(run.list,'cpu'),0);
    var heightMMEM = (SIZEY-60)/Math.max(getmax(run.list, 'memory'),0);
    drawPie(group, {x:pos.x+20,y:pos.y}, getMean(run.list,'cpu'), layer, run.list, heightMCPU, eltSize, '%', getmax(run.list,'cpu'));
    drawPie(group, {x:pos.x+230,y:pos.y}, getMean(run.list, 'memory'), layer, run.list, heightMMEM, eltSize, 'Mo', getmax(run.list, 'memory'));
    layer.add(group);
}

