/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 13/05/14
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */

var marginInverseFrameY = 160;
var fontSize = 18;

/**
 *
 * @param group
 * @param pos
 * @param elt
 * @param heightM
 * @param eltSize
 * @param i
 * @param text
 * @param layer
 * @param color
 */
function drawChart(group, pos, elt ,heightM, eltSize, i, text , layer, color) {
    var tooltip;

    var rectBack = new Kinetic.Rect({
        x: pos.x+i*(eltSize+2),
        y: pos.y,
        width: eltSize,
        height: -marginInverseFrameY+20,
        fill: '#F5F5F5'
    });

    group.add(rectBack);
    if  (i%4==0) {

        group.add(new Kinetic.Rect({
            x: pos.x+i*(eltSize+2)+4,
            y: pos.y,
            width: 1,
            height:-marginInverseFrameY+20,
            fill: '#AC7969'
        }));

        group.add(new Kinetic.Text({
            x: pos.x+i*(eltSize+2),
            y: pos.y,
            text: text,
            fontSize: 8,
            fontFamily: 'Calibri',
            fill: '#AC7969',
            width: 150,
            align: 'left'
        }));
    }


    var rect = new Kinetic.Rect({
        x: pos.x+i*(eltSize+2),
        y: pos.y,
        width: eltSize,
        height: -elt.global*heightM,
        fill: color
    });


    var rectLayer = new Kinetic.Rect({
        x: pos.x+i*(eltSize+2)-1,
        y: pos.y,
        width: eltSize+2,
        height: -marginInverseFrameY+20
    });

    group.add(rect);
    group.add(rectLayer);

    var chartGroup = new Kinetic.Group();


    rectLayer.on('mouseover', function() {
        dist = 0;
        anim.start();
        group.add(chartGroup);
        layer.draw();
    });

    rectLayer.on('mouseout', function() {
        anim.stop();
        chartGroup.remove();
        layer.draw();
    });





    var dist =  0;
    var anim = new Kinetic.Animation(function() {
        chartGroup.destroyChildren();

        var spos = {x:pos.x-18,y:pos.y};
        if (i>14) spos.x-=100;



        dist += 35;
        if (dist >= 360) {
            anim.stop();
            dist = 370;
        }


        tooltip = new Kinetic.Label({
            x: spos.x+i*(eltSize+2)+4,
            y: 50,
            height: 85
        });


        var rect = new Kinetic.Rect({
            x:spos.x,
            y: 80,
            width: 170,
            height: -110,
            fill: 'white',
            shadowOffset: {x:1,y:1}
        });

        tooltip.add(rect);

        drawRunPie(tooltip, spos, elt,dist);

        chartGroup.add(tooltip);

        layer.draw();
    });

}


function drawChartMax(group, pos, max ,mean, heightM, width) {
    group.add(new Kinetic.Text({
        x:pos.x-40,
        y: 146 - heightM*max,
        text: max,
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: '#AC7969'
    }));

    group.add(new Kinetic.Rect({
        x: pos.x-12,
        y: 150 - heightM*max,
        width: width,
        height:1,
        fill: '#AC7969',
        opacity: 0.2
    }));


    group.add(new Kinetic.Text({
        x:pos.x-40,
        y: 146 - heightM*mean,
        text: mean.toFixed(),
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: '#AC7969'
    }));

    group.add(new Kinetic.Rect({
        x: pos.x-10,
        y: 150 - heightM*mean,
        width: width,
        height:1,
        fill: '#AC7969',
        opacity: 0.2
    }));

}


/**
 *
 * @param tooltip
 * @param pos
 * @param elt
 * @param max_ang
 */
function drawRunPie(tooltip, pos, elt, max_ang) {
    var angT = elt.terminate*360 / elt.global;
    var angA = elt.aborted*360 / elt.global;
    var angO = elt.timeover*360 / elt.global;
    var angE = elt.waitevent*360 / elt.global;


    /**
     * completed status
     */
    tooltip.add(new Kinetic.Rect({
        x:pos.x+82,
        y: -20,
        width: 8,
        height: 8,
        fill: '#71CE3B',
        shadowOffset: {x:1,y:1}
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: -23,
        text: 'completed',
        fontFamily: 'Calibri',
        fontSize: 14,
        fill: 'black'
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: -9,
        text: '('+elt.terminate+')',
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: 'black'
    }));

    tooltip.add(new Kinetic.Arc({
        x: pos.x+40,
        y: pos.y-120,
        innerRadius: 30,
        outerRadius: 35,
        fill: '#71CE3B',
        strokeWidth: 0,
        lineJoin: "round",
        angle: Math.min(angT, max_ang),
        rotationDeg: -90
    }));

    if (max_ang < angT) return;


    /**
     * aborted status
     * @type {number}
     */

    tooltip.add(new Kinetic.Rect({
        x:pos.x+82,
        y: 5,
        width: 8,
        height: 8,
        fill: 'red',
        shadowOffset: {x:1,y:1}
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 2,
        text: 'aborted',
        fontFamily: 'Calibri',
        fontSize: 14,
        fill: 'black'
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 16,
        text: '('+elt.aborted+')',
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: 'black'
    }));


    var angMax = angT + angA <= max_ang ? angA : angT+angA-max_ang;
    tooltip.add(new Kinetic.Arc({
        x: pos.x+40,
        y: pos.y-120,
        innerRadius: 30,
        outerRadius: 35,
        fill: 'red',
        strokeWidth: 0,
        lineJoin: "round",
        angle: angMax,
        rotationDeg: -90+angT
    }));

    if (max_ang < angT+angA) return;


    tooltip.add(new Kinetic.Rect({
        x:pos.x+82,
        y: 30,
        width: 8,
        height: 8,
        fill: 'orange',
        shadowOffset: {x:1,y:1}
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 27,
        text: 'time overrun',
        fontFamily: 'Calibri',
        fontSize: 14,
        fill: 'black'
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 39,
        text: '('+elt.timeover+')',
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: 'black'
    }));

    angMax = angT + angA + angO <= max_ang ? angO : angT+angA+angO-max_ang;
    tooltip.add(new Kinetic.Arc({
        x: pos.x+40,
        y: pos.y-120,
        innerRadius: 30,
        outerRadius: 35,
        fill: 'orange',
        strokeWidth: 0,
        lineJoin: "round",
        angle: angMax,
        rotationDeg: -90+angT+angA
    }));


    if (max_ang < angT+angA+angO) return;

    angMax = angT + angA + angO + angE <= max_ang ? angE : angT + angA + angO + angE - max_ang;
    tooltip.add(new Kinetic.Arc({
        x: pos.x+40,
        y: pos.y-120,
        innerRadius: 30,
        outerRadius: 35,
        fill: 'yellow',
        strokeWidth: 0,
        lineJoin: "round",
        angle: angMax,
        rotationDeg: -90+angT+angA+angO

    }));


    tooltip.add(new Kinetic.Rect({
        x:pos.x+82,
        y: 55,
        width: 8,
        height: 8,
        fill: 'yellow',
        shadowOffset: {x:1,y:1}
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 52,
        text: 'wait event',
        fontFamily: 'Calibri',
        fontSize: 14,
        fill: 'black'
    }));

    tooltip.add(new Kinetic.Text({
        x:pos.x+95,
        y: 66,
        text: '('+elt.waitevent+')',
        fontFamily: 'Calibri',
        fontSize: 10,
        fill: 'black'
    }));



}


/**
*
 * Second chart
 *
 *
 *
 */

/**
 * draw a pie with for a value
 * @param group to attach pie and child
 * @param pos reference pos
 * @param value to darw
 * @param layer to force refresh
 * @param list to draw list on a mouseover
 * @param heightM maximum size of list
 * @param eltSize
 */
function drawPie(group, pos, value, layer, list, heightM,  eltSize , unit, max) {
    var cursor = unit==='Mo' ?  -235 : 160;
    var fontSize = unit==='Mo' ?  24 : 34;
    var endAngle = (360 * value /100);
    group.add(new Kinetic.Arc({
        x: pos.x+50,
        y: pos.y-70,
        innerRadius: 50,
        outerRadius: 55,
        fill: '#428bca',
        strokeWidth: 0,
        lineJoin: "round",
        angle: endAngle,
        rotationDeg: -90
     }));

    group.add(new Kinetic.Arc({
        x: pos.x+50,
        y: pos.y-70,
        innerRadius: 50,
        outerRadius: 55,
        fill: '#ddd',
        strokeWidth: 0,
        lineJoin: "round",
        angle: 360 - endAngle,
        rotationDeg: -90 + endAngle
    }));

    if (unit==='Mo') {
        group.add(new Kinetic.Text({
            x:  pos.x + 8,
            y: pos.y - 88,
            text:  value.toFixed(1)+unit,
            fontSize: fontSize,
            fontFamily: 'Calibri',
            fill: '#428bca',
            width: 150,
            align: 'left'
        }));
    } else {
        group.add(new Kinetic.Text({
            x:  pos.x + 18,
            y: pos.y - 95,
            text:  value.toFixed(1)+unit,
            fontSize: fontSize,
            fontFamily: 'Calibri',
            fill: '#428bca',
            width: 150,
            align: 'left'
        }));
    }

    var cicrle = new Kinetic.Circle({
        x: pos.x+50,
        y: pos.y-70,
        radius: 55
    });

    group.add(cicrle);


    var chartGroup = new Kinetic.Group();
    cicrle.on('mouseover', function() {

            anim.start();
            group.add(chartGroup);
            layer.draw();

    });

    cicrle.on('mouseout', function(e) {
        chartGroup.remove();
        layer.draw();
        anim.stop();
        dist = 0;
    });


    var dist =  0;
    var anim = new Kinetic.Animation(function() {
        chartGroup.destroyChildren();
        dist += 0.1;
        if (dist >= 1) {
            anim.stop();
        }
        chartGroup.add(new Kinetic.Rect({
            x: pos.x+cursor,
            y: pos.y,
            width: 200,
            height:-200,
            fill: 'white'
        }));
        for (var i=0; i<run.list.length;i++) {
            drawCpuChart(chartGroup, pos, list[i].dataDay, unit==='Mo' ?'memory':'cpu' , heightM, eltSize, i, list[i].day, dist, cursor);
        }

        drawChartMax(chartGroup, {x:pos.x+cursor+10,y:pos.y}, max, value, heightM , 205)

        layer.draw();
    });



}

/**
 * draw chart associated to pie stat
 * @param group
 * @param pos
 * @param elt
 * @param f
 * @param heightM
 * @param eltSize
 * @param i
 * @param text
 * @param dist
 * @param cursor
 */
function drawCpuChart(group, pos, elt,f, heightM,  eltSize, i, text, dist, cursor) {

    if  (i%4 == 0) {

        group.add(new Kinetic.Rect({
            x: pos.x+cursor+i*(eltSize+2)+1,
            y: pos.y,
            width: 1,
            height:-marginInverseFrameY+20,
            fill: '#AC7969'
        }));

        group.add(new Kinetic.Text({
            x: pos.x+cursor+i*(eltSize+2),
            y: pos.y,
            text: text,
            fontSize: 8,
            fontFamily: 'Calibri',
            fill: '#AC7969',
            width: 150,
            align: 'left'
        }));


    }

    var rect = new Kinetic.Rect({
        x: pos.x+cursor+i*(eltSize+2),
        y: pos.y,
        width: eltSize-1,
        height: -elt[f]*heightM*dist,
        fill: '#428bca'
    });

    group.add(rect);

}
