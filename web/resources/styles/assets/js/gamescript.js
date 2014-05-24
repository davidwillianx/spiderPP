/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var boxMessage;
var inputMessage;
var cardsTurn = [];
        
$(document).ready(function() {
    inputMessage = $('#chat-message-input');
    boxMessage = $('.chat-messages');
    chatScrollStart();
    inputMessage.focus();
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


function buildCardSelected(card)
{
   return  showCard = '<div class="col-md-2 col-xs-5 no-padding m-r-5">'

            + '<div class="tiles green text-center " id="myOption">'
            + '<h2 class="semi-bold text-white  weather-widget-big-text no-margin p-t-20 p-b-10" id="myOptionValue">' + card.value + '</h2>'
            + '<div class="tiles-title blend p-b-25 text-white">'+card.userNameOption+'</div>'
            + '<div class="clearfix"></div>'
            + '</div>'
            + '</div>';
    
}
function myCardSelection(card)
{
    showCard = '<div class="col-md-2 col-xs-5 no-padding m-r-5">'

            + '<div class="tiles green text-center " id="myOption">'
            + '<h2 class="semi-bold text-white  weather-widget-big-text no-margin p-t-20 p-b-10" id="myOptionValue">' + card.value + '</h2>'
            + '<div class="tiles-title blend p-b-25 text-white">Me</div>'
            + '<div class="clearfix"></div>'
            + '</div>'
            + '</div>';

    if ($('#myOption').length === 0)
        $('#rowSeleted').append(showCard);
    else
        $('#myOption #myOptionValue').html(card.value);
}

function userCardSelection(card)
{
    if (cardsTurn.length !== 0)
    {
        for (var indexList = 0; indexList < cardsTurn.length; indexList++)
            if (cardsTurn[indexList].idUsuario === card.idUsuario)
                    cardsTurn[indexList].value = card.value;
    }
    cardsTurn.push(card);
}

function openGameCardSelection()
{
    cardsTurn.length = 0;
}

function showCardsSelected()
{   listOfCardsSelected = '';
    for (var indexList = 0 ; indexList < cardsTurn.length ; indexList++)
        listOfCardsSelected  += buildCardSelected(cardsTurn[indexList]);

    $('#rowSeleted').append(listOfCardsSelected);
}

function disableCardArea()
{
    $("#cardSection").block({message:"aguarde o inicio da rodada"});
    
}

function enableCardArea()
{
    
     $("#cardSection").unblock();
}