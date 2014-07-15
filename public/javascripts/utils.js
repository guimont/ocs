/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 13/05/14
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */


var marginFrameX = 20;
var marginInverseFrameY = 160;
var fontSize = 18;
var titleHeight = 20;
var titlePadding = 20;
var radiusFrame = 5;
var radiusTitle = 4;

function drawFrame(size, pos, group, color, title) {

    //add global frame
    group.add(new Kinetic.Rect({
        x: pos.x-marginFrameX,
        y: pos.y-marginInverseFrameY,
        width: size.x,
        height: size.y,
        stroke: color,
        strokeWidth: 1,
        cornerRadius: radiusFrame
    }));


    //add title frame
    group.add(new Kinetic.Rect({
        x: pos.x-marginFrameX,
        y: pos.y-marginInverseFrameY,
        width: size.x,
        height: titleHeight,
        stroke: color,
        fill: color,
        strokeWidth: 1,
        cornerRadius: radiusTitle
    }));

    //add text frame
    group.add(new Kinetic.Text({
        x: pos.x-marginFrameX,
        y: pos.y-size.y,
        text: title,
        fontSize: fontSize,
        fontFamily: 'Calibri',
        fill: 'white',
        width: 150,
        padding: titlePadding,
        align: 'left'
    }));
}

/*
    draw a chart bar
 */
function drawChart(group, pos, elt,heightM, eltSize, i, text , layer, color) {
    var tooltip;
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
        height: -elt*heightM,
        fill: color
    });

    rect.on('mouseover', function() {
       tooltip = new Kinetic.Label({
            x: pos.x+i*(eltSize+2)+4,
            y: 50,

           height: 80,
            opacity: 0.75
        });
        tooltip.add(new Kinetic.Tag({
            fill: 'green',
            pointerDirection: 'down',
            pointerWidth: 5,
            pointerHeight: 5,
            lineJoin: 'round',
            shadowColor: '#2C3730',
            shadowBlur: 10,
            shadowOffset: {x:5,y:5},
            height: 80

        }));

        tooltip.add(new Kinetic.Text({
            text: '  '+elt+' jobs     \n' +'  '+ text,
            fontFamily: 'Calibri',
            fontSize: 14,
            height: 40,
            offsetY: -7,
            fill: 'white'
        }));
        group.add(tooltip);

        this.fill('#2C3730');
        layer.draw();
    });

    rect.on('mouseout', function() {
        this.fill(color);
        tooltip.remove();
        layer.draw();
    });

    group.add(rect);
}


/*
draw a chart bar
*/
function draw2Chart(group, pos, elt, elt2, heightM, heightM2,  eltSize, i, text , layer, color) {
    if  (i%4==0) {

        group.add(new Kinetic.Rect({
            x: pos.x+i*(eltSize*2+2)+4,
            y: pos.y,
            width: 1,
            height:-marginInverseFrameY+20,
            fill: '#AC7969'
        }));

        group.add(new Kinetic.Text({
            x: pos.x+i*(eltSize*2+2),
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
        x: pos.x+i*(eltSize*2+2),
        y: pos.y,
        width: eltSize,
        height: -elt*heightM,
        fill: color
    });

    var rect2 = new Kinetic.Rect({
        x: pos.x+i*(eltSize*2+2)+eltSize,
        y: pos.y,
        width: eltSize,
        height: -elt2*heightM2,
        fill: 'blue'
    });

    rect.on('mouseover', function() {
        this.fill('black');
        layer.draw();
    });

    rect.on('mouseout', function() {
        this.fill(color);
        layer.draw();
    });

    group.add(rect);
    group.add(rect2);
}

