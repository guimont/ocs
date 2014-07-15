/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 13/05/14
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
var list = [10,8,6,7,12,4,10,8,6,7,12,4,10,8,6,7,12,4,10,8,6,7,12,4];

function load () {
    $.ajax({
        url: 'http://localhost:9000/customer/sg',
        type: 'GET',
        success: function() { alert('GET completed'); }
    });
};

function chart(pos,layer) {

    var rectList = [];
    var group = new Kinetic.Group();
    group.add(new Kinetic.Rect({
        x: pos.x-20,
        y: pos.y-160,
        width: 180,
        height: 180,
        stroke: 'grey',
        strokeWidth: 1,
        cornerRadius: 5
    }));



    group.add(new Kinetic.Rect({
        x: pos.x-20,
        y: pos.y-160,
        width: 180,
        height: 20,
        stroke: 'grey',
        fill: 'grey',
        strokeWidth: 1,
        cornerRadius: 4
    }));

    group.add(new Kinetic.Text({
        x: pos.x-20,
        y: pos.y-180,
        text: 'ENGINE',
        fontSize: 18,
        fontFamily: 'Calibri',
        fill: 'white',
        width: 150,
        padding: 20,
        align: 'center'
    }));


    for (var i=0; i<list.length;i++) {
        rectList[i] = new Kinetic.Rect({
            x: pos.x+i*6,
            y: pos.y,
            width: 5,
            height: -list[i]*10,
            fill: 'grey'
        });

        rectList[i].on('mouseover', function() {
            this.fill('black');
            layer.draw();
        });

        rectList[i].on('mouseout', function() {
            this.fill('grey');
            layer.draw();
        });



        group.add(rectList[i]);
    }

    group.on('mouseup', function() {
        group.scale({x:0.8,y:0.8});
        layer.draw();
    });
    group.scale(0.8,0.8);
    layer.add(group);

    load();
}