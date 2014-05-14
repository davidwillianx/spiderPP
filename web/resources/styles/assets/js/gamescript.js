/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var boxMessage;
var inputMessage;

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