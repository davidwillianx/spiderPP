/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var boxMessage;
var inputMessage;
var cardsTurn = [];
var rowSelected;
var userCardSelected = {};

        
$(document).ready(function() {
    inputMessage = $('#chat-message-input');
    boxMessage = $('.chat-messages');
    chatScrollStart();
    inputMessage.focus();
    rowSelected = $('#row-selected');
});


function chatScrollStart()
{
    boxMessage.slimScroll({
        height: '550px',
        start: 'bottom'
    });
}

function appendMessageSent(msg) {
    boxMessage.append('<div class="user-details-wrapper pull-right animated fadeIn">' +
            '<div class="user-details">' +
            '<div class="bubble old sender">' +
            msg+
            '</div>' +
            '</div>' +
            '<div class="clearfix"></div>' +
            '</div>');
}

function appendMessageReceived(msg)
{
    boxMessage.append('<div class="user-details-wrapper animated fadeIn">' +
//                                       '<div class="user-profile"> '+
//                                            <!--&lt;img src=&quot;assets/img/profiles/d.jpg&quot; alt=&quot;&quot; data-src=&quot;assets/img/profiles/d.jpg&quot; data-src-retina=&quot;assets/img/profiles/d2x.jpg&quot; width=&quot;35&quot; height=&quot;35&quot;&gt;--> 
//                                        '</div>'+
            '<div class="user-details">' +
            '<div class="bubble">' + msg + '</div>' +
            '</div>' +
            '<div class="clearfix"></div>' +
            '<div class="sent_time off">Yesterday 11:25pm</div>' +
            '</div>');
}

function scrollToFinish()
{
    var scrollFinish = boxMessage.prop('scrollHeight') + 'px';
    boxMessage.slimScroll({scrollTo: scrollFinish});
}

function sendMessage(socket, chatMessage)
{
    socket.send(JSON.stringify(chatMessage));
    appendMessageSent(chatMessage.message);
    scrollToFinish();
    inputMessage.val(null).focus();
}
//@TODO  OS BUILDS DE CARDS TEM MESMO HTML (EXALTAR EM UMA VARIAVEL)

function buildCardHidden(name)
{
    return  showCard = '<div class="col-md-2 col-xs-5 no-padding m-r-5">'

            + '<div class="tiles green text-center ">'
            + '<h2 class="semi-bold text-white  weather-widget-big-text no-margin p-t-20 p-b-10" >?</h2>'
            + '<div class="tiles-title blend p-b-25 text-white">' + name + '</div>'
            + '<div class="clearfix"></div>'
            + '</div>'
            + '</div>';

}

function buildCardSelected(card)
{
   return  showCard = '<div class="col-md-2 col-xs-5 no-padding m-r-5">'

            + '<div class="tiles green text-center " id="myOption">'
            + '<h2 class="semi-bold text-white  weather-widget-big-text no-margin p-t-20 p-b-10" id="myOptionValue">' + card.value + '</h2>'
            + '<div class="tiles-title blend p-b-25 text-white"> '+card.userName+'</div>'
            + '<div class="clearfix"></div>'
            + '</div>'
            + '</div>';
    
}
function myCardSelection(card)
{
    userCardSelected = card;
    
    if ($('#myOption').length === 0)
        rowSelected.prepend(buildCardSelected(card));
    else
        $('#myOption #myOptionValue').html(card.value);
}

function userCardSelection(card)
{
    if (cardsTurn.length !== 0)
    {
        for (var indexList = 0; indexList < cardsTurn.length; indexList++)
            if (cardsTurn[indexList].idUsuario === card.idUsuario){
                    cardsTurn[indexList].value = card.value;
                    return;
            }
                
            else{
                cardsTurn.push(card);
                 rowSelected.append(buildCardHidden(card.userName));
            }
            
    }else{
        cardsTurn.push(card);
        rowSelected.append(buildCardHidden(card.userName));
    }
}

function openGameCardSelection()
{
    cardsTurn.length = 0;
    rowSelected.html("");
}

function showCardsSelected()
{   
    listOfCardsSelected = '';
    for (var indexList = 0 ; indexList < cardsTurn.length ; indexList++)
        listOfCardsSelected  += buildCardSelected(cardsTurn[indexList]);
    
    if(userCardSelected.length !== 0)
        rowSelected.html(userCardSelected).append(listOfCardsSelected).fadeIn();
        
    else
        rowSelected.html(listOfCardsSelected).fadeIn();
}

function disableCardArea()
{ 
    $("#cardSection").block({message:"aguarde o inicio da rodada"});
    
}

function enableCardArea()
{
     $("#cardSection").unblock();
}
function buildCurrentDateFormatDMY()
{
    var today  =  new Date();
    var year =  today.getFullYear();
    var month =  today.getMonth()+1;
    var day =  today.getDay();
     
     if(day < 10)
         day ='0'+day;
     if (month <10)
         month = '0'+month;
     return day+'/'+month+'/'+year;
}

function appendSubtask(rootId,subtask){
    
    var htmlTaskTemplate = '<div class="p-t-20 p-b-15 b-b b-grey rateAvailable">'+
        '<div class="post overlap-left-10">'+
                '<div class="activity user-profile-pic-wrapper" data-toggle="tooltip" data-placement="right" title="" id="'+subtask.storyId+'" data-original-title="Selecione estoria clicando aqui">'+
                    '<div class="user-profile-pic-2x tiles blue white-border">'+
                        '<div class="text-white inherit-size p-t-10 p-l-15"> <i class="fa fa-map-marker fa-lg"></i> </div>'+
                    '</div>'+
                '</div>'+
                
            '<div class="info-wrapper small-width">'+
                '<div class="info text-black ">'+
                    '<h4>'+subtask.name+'</h4> <p>'+subtask.description+'</p>'+
                    '<p class="muted small-text">'+buildCurrentDateFormatDMY()+'</p>'+
                '</div>'+
                '<div class="clearfix"></div>'+
            '</div>'+
            '<div class="pull-right col-md-2 score">'+

                '<div class=" rate-box m-b-5">'+
                        
                '</div>'+
                    
                '<div class="form-rate pull-right" display="none">'+
                        
                       
                '</div>'+
            '</div>'+


            '<div class="clearfix"></div>'+
        '</div></div>';
    $(htmlTaskTemplate).hide().insertAfter(rootId).fadeIn(999);
    
    
}