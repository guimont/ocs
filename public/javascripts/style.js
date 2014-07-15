function elemDimensions() {
    $('#filters, #spacer250, #mainMenu').height(($( window ).height())-$('header').height());
    $('#jobHolder').height(($( window ).height())-$('header').height()-$('#mainViewNav').height()-$('#sortByBand').height()-22);
}; elemDimensions();

$(window).resize(function() {
    elemDimensions();
});

function UIInteraction() {

    // --- ITEMS --------------------------------------------------------------------------- //

    $('.jobBigPicto, .jobsSmallPictos, .jobDown').mousedown(function () {
        if($('#jobHolder').hasClass('thumbJobs')){ $(this).parent().find('.jobHoverInfo').css('top','0'); }
        if($('#jobHolder').hasClass('listJobs')){ $(this).parent().find('.jobHoverInfo').css('left','0'); }
        $(this).parent().find('.jobBigPicto, .jobsSmallPictos, .jobDown, .viewerEyeSelected').css('opacity','0.2');
        $(this).parent().addClass('jobDetailDisplay');
    });

    $('.jobInfoNameBlock, .jobInfoContextBlock').mousedown(function () {
        if($('#jobHolder').hasClass('thumbJobs')){ $(this).parent().css('top','272px'); }
        if($('#jobHolder').hasClass('listJobs')){ $(this).parent().css('left','-100%'); }
        $(this).parent().parent().find('.jobBigPicto, .jobsSmallPictos, .jobDown, .viewerEyeSelected').css('opacity','1');
        $(this).parent().parent().removeClass('jobDetailDisplay');
    });

    $('.viewerEye').mousedown(function(){
        if($(this).hasClass('viewerEyeSelected')){ 
            $(this).removeClass('viewerEyeSelected'); 
            $(this).parent().css('border-color','#dbdbdb');
            $(this).parent('.listJobs .job').css('background-color',"#fff");
        }
        else { $(this).addClass('viewerEyeSelected'); 
            $(this).parent().css('border-color','#ce502f');
            $(this).parent('.listJobs .job').css('background-color',"#fcf5f2");
        }
    });

    $('.jobDetailsBtn').mousedown(function(){ window.location = 'swf.html'; });

    // --- HEADER NAV ---------------------------------------------------------------------- //

    $('#navButton').mousedown(function(){ $('#mainMenu').css('left','0'); });
    $('#navButton, #mainMenu').mouseleave(function(){ $('#mainMenu').css('left','-250px'); });

    $('#repoSelect').mousedown(function(){ $('#repoSelectBox').slideDown('fast'); $(this).css('background-image','url("images/whiteArrowUp.png")'); });
    $('#repoSelect, #repoSelectBox').mouseleave(function(){ $('#repoSelectBox').slideUp('fast'); $('#repoSelect').css('background-image','url("images/whiteArrow.png")'); });

    $('#searchIn').mousedown(function(){ $('#searchInBox').slideDown('fast'); $(this).css('background-image','url("images/blackArrowUp.png")'); });
    $('#searchIn, #searchInBox').mouseleave(function(){ $('#searchInBox').slideUp('fast'); $('#searchIn').css('background-image','url("images/blackArrow.png")'); });

    $('#langSelect').mousedown(function(){ $('#langSelectBox').slideDown('fast'); $(this).css('background-image','url("images/grayArrowUp.png")'); });
    $('#langSelect, #langSelectBox').mouseleave(function(){ $('#langSelectBox').slideUp('fast'); $('#langSelect').css('background-image','url("images/grayArrow.png")'); });

    $('#account').mousedown(function(){ $('#accountBox').slideDown('fast'); });
    $('#account, #accountBox').mouseleave(function(){ $('#accountBox').slideUp('fast'); });

    // --- MAINVIEW NAV -------------------------------------------------------------------- //

    $('#viewerNumber').mousedown(function(){
        $('#viewerBox').slideDown('fast');
        $(this).find('.arrow-up').css('display','block');
        $(this).find('.arrow-down').css('display','none');
    });
    $('#viewerNumber, #viewerBox').mouseleave(function(){
        $('#viewerBox').slideUp('fast');
        $(this).find('.arrow-up').css('display','none');
        $(this).find('.arrow-down').css('display','block');
    });

    $('#numDisp').mousedown(function(){
        $('#numDispBox').slideDown('fast');
        $(this).css('background-image','url("images/blackArrowUp.png")');
    });
    $('#numDisp, #numDispBox').mouseleave(function(){
        $('#numDispBox').slideUp('fast');
        $('#numDisp').css('background-image','url("images/blackArrow.png")');
    });
    $('.numDispOption').mousedown(function(){ $('#currentNumDisp').html($(this).html()); });

    $('#dispAsThumb').mousedown(
        function () {
            $('#jobHolder').removeClass('listJobs');
            $('#jobHolder').addClass('thumbJobs');
            $(this).addClass('dispAsSelected');
            $('#dispAsList').removeClass('dispAsSelected');
            $('.job').each(function(){
                $(this).find('.jobHoverInfo').css({'top':'272px','left':'0'});
            });
            $('.jobDetailDisplay').each(function(){
                $(this).find('.jobHoverInfo').css({'top':'0','left':'0'});
            }); 
            $('.viewerEyeSelected').parent().css('background-color','#fff');
        });
    $('#dispAsList').mousedown(
        function () {
            $('#jobHolder').removeClass('thumbJobs');
            $('#jobHolder').addClass('listJobs');
            $(this).addClass('dispAsSelected');
            $('#dispAsThumb').removeClass('dispAsSelected');
            $('.job').each(function(){
                $(this).find('.jobHoverInfo').css({'top':'0','left':'-100%'});
            });
            $('.jobDetailDisplay').each(function(){
                $(this).find('.jobHoverInfo').css({'top':'0','left':'0'});
            });
            $('.viewerEyeSelected').parent().css('background-color','#fcf5f2');
        });

    $('.sortBy').mousedown( function () { $('.sortBy').removeClass('sortBySelected'); $(this).addClass('sortBySelected'); });

    // --- LEFT MENUS ---------------------------------------------------------------------- //

    $('#searchBarBtn').mousedown(function(){
        if($("#searchBar").val().length > 1){
            $('#choiceKeyWords').append('<div class="choice"><p class="choiceText">- '+$("#searchBar").val()+'</div>');
            $("#searchBar").val('');
            $('.choice').mousedown(
            function () { $(this).fadeOut('1000'); });
        }
    });

    $('#appFilter').mousedown(function(){ $('#appFilterMenu').css('left','0'); });
    $('#appFilterMenu .closeHiddenMenu').mousedown(function(){ $('#appFilterMenu').css('left','-250px'); });
    
    $('#ecFilter').mousedown(function(){ $('#ecFilterMenu').css('left','0'); });
    $('#ecFilterMenu .closeHiddenMenu').mousedown(function(){ $('#ecFilterMenu').css('left','-250px'); });
    
    $('#hnFilter').mousedown(function(){ $('#hnFilterMenu').css('left','0'); });
    $('#hnFilterMenu .closeHiddenMenu').mousedown(function(){ $('#hnFilterMenu').css('left','-250px'); });

    $('.choice').mousedown( function () { $(this).fadeOut('1000'); });

    $('.jobType').mousedown(function(){
        $('#choiceJobTypes').append('<div class="choice"><p class="choiceText">- '+$(this).find($('p')).html()+'</div>');
        $('.choice').mousedown(
        function () { $(this).fadeOut('1000'); });
    });

    $('#appItems .listItem').mousedown(function(){
        $('#choiceApps').append('<div class="choice"><p class="choiceText">- '+$(this).html()+'</p></div>');
        $('#appFilterMenu').css('left','-250px');
        $('#filters').scrollTop(0);
        $('.choice').mousedown(
        function () { $(this).fadeOut('1000'); });
    });

    $('#ecItems .listItem').mousedown(function(){
        $('#choiceEC').append('<div class="choice"><p class="choiceText">- '+$(this).html()+'</p></div>');
        $('#ecFilterMenu').css('left','-250px');
        $('#filters').scrollTop(0);
        $('.choice').mousedown(
        function () { $(this).fadeOut('1000'); });
    });

    $('#hnItems .listItem').mousedown(function(){
        $('#choiceHN').append('<div class="choice"><p class="choiceText">- '+$(this).html()+'</p></div>');
        $('#hnFilterMenu').css('left','-250px');
        $('#filters').scrollTop(0);
        $('.choice').mousedown(
        function () { $(this).fadeOut('1000'); });
    });

    $('.method').mousedown( function () { $('.method').removeClass('methodSelected'); $(this).addClass('methodSelected'); });
    $('.scheduling').mousedown( function () { $('.scheduling').removeClass('schedulingSelected'); $(this).addClass('schedulingSelected'); });

    // --- SWF VIEW ------------------------------------------------------------------------ //

    $('#send').mousedown(function(){ $('#sendBox').slideDown('fast'); });
    $('#send, #sendBox').mouseleave(function(){ $('#sendBox').slideUp('fast'); $('#sendBoxMailBox').slideUp('fast'); });

    $('#sendBoxMailBtn').mousedown(function(){ $('#sendBoxMailBox').slideToggle('fast'); });
    $('#mailBoxCancel').mousedown(function(){ $('#sendBoxMailBox').slideUp('fast'); });

    $('#swfMenuBtn').mousedown(function(){ $('#swfSwfMenu').css('left','0'); });
    $('#swfSwfMenu .closeHiddenMenu').mousedown(function(){ $('#swfSwfMenu').css('left','-250px'); });

    $('#relSwfMenuBtn').mousedown(function(){ $('#relatedSwfSwfMenu').css('left','0'); });
    $('#relatedSwfSwfMenu .closeHiddenMenu').mousedown(function(){ $('#relatedSwfSwfMenu').css('left','-250px'); });

    $('#relResMenuBtn').mousedown(function(){ $('#relatedResSwfMenu').css('left','0'); });
    $('#relatedResSwfMenu .closeHiddenMenu').mousedown(function(){ $('#relatedResSwfMenu').css('left','-250px'); });

    $('#techDetailsMenuBtn').mousedown(function(){ $('#techDetailsSwfMenu').css('left','0'); });
    $('#techDetailsSwfMenu .closeHiddenMenu').mousedown(function(){ $('#techDetailsSwfMenu').css('left','-250px'); });

    $('.relatedItem').mousedown(function(){ 
        $('.relatedItem').removeClass('relatedItemSelected'); 
        $(this).addClass('relatedItemSelected');
        $('.relatedItem').find('.arrow-left').css('display','none');
        $('.relatedItem').find('.arrow-right').css('display','block'); 
        $(this).find('.arrow-left').css('display','block');
        $(this).find('.arrow-right').css('display','none');
    });

}; UIInteraction();


function unactiveUI() {
    $('.methodUnactive').each(function(){
        $(this).unbind('mousedown');
        $(this).find('.hint--bottom').remove();
    });
    $('.schedulingUnactive').each(function(){
        $(this).unbind('mousedown');
        $(this).find('span').removeClass('hint--bottom');
        $(this).find('span').removeAttr('data-hint');
    });
    $('.paginationUnactive').each(function(){
        $(this).unbind('mousedown');
    });
}; unactiveUI();
