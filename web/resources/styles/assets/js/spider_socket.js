/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    
    disableCardArea();
    accordionSetUp(accordion);
    
    gameSocket.connect();
    
    gameSocket.connection.onopen = function(){
        console.log('connection status >> '+gameSocket.connection.readyState);
    };
    
    gameSocket.connection.onmessage = function(event){
        gameSocket.message.set(JSON.parse(event.data));
        gameSocket.message.launch();
    };
    
    gameSocket.connection.onerror = function(event){
        console.log('something wrong? ');
    };
    
    
    
    $('body').on('keypress','#chat-message-input',function(txtElement){
       if(txtElement.which === 13){
            txtElement.preventDefault();

            gameSocket.send({
                "idUsuario": idUsuario,
                "idProjeto": idProjeto,
                "author": author,
                "message": $(this).val(), 
                "type": "chatMessage"
           },bindMessage($(this).val()));
        }
    });
    
    $('body').on('click','.activity',function(event){
        
        story.set($(this).parents().eq(1));
        
            if(story.isRoot())
            {
                story.setGreenStatus();
                
                accordionElement(story.htmlElement.row,
                story.htmlElement.circle.popover({
                    content:'Esta estoria ja foi divida, escolha uma de suas subtarefas para estimar',
                    title:'Só pra lembrar... ;)'
                }));

                story.setAliceBlueStatus();
                return;
            }
            story.setGreenStatus();
            story.setAliceBlueStatus();

            console.log('Preparando para enviar');
            gameSocket.send({"id":story.getId(),"type":"taskSelected"});
    });
    
    $('#game-start').click(function(e){
         e.preventDefault();
         gameSocket.cards.anotherUsers.splice(0,gameSocket.cards.anotherUsers.length);
         
        if(!story.isSetted()){
            console.log('story wasnt setted');
            return;
        }
        gameSocket.send({'type':'round','value':'startTime'});
        console.log('Data was sent');

    });
    
    $('.opt-card').click(function(e){
        var card = {"type":"card","value":$(this).attr('rate'),"userName":author,"userId":idUsuario};
        gameSocket.cards.my = card;
        gameSocket.send(card);
        myCardSelection(card);
        console.log('card option was sent');
    });
    
    
    
    $('body').on('click','#div-est',function(){
        
    });
    
    $('body').on('click','#set-rate',function(){
        var rateValues = [0,1,2,5,8,13,20];
        var htmlOptionRate = '<select id="rate-option">';
        
        $(rateValues).each(function(index,rateValue){
            htmlOptionRate += '<option value="'+rateValue+'">'+rateValue+'</option>';
        });
        htmlOptionRate += '</select>';
        showModalDialog(htmlOptionRate,'Defina Estimativa');
    });
    
    $('body').on('change','#rate-option',function(){
        console.log(' < '+$(this).val());
        var option  = confirm('deseja confirmar estimativa ? ');
        
        if(!option){return;}
        
        gameSocket.send({
                "type":"rate"
                ,"score":$(this).val()
                ,"storyId": story.getId()
            });
    });
    
    
    
});


var gameSocket = {
      
    connection: "",
      
      cards: {
          my: "",
          anotherUsers: []
      },

    dataConnection: {
          perfil: prPath,
          room: rmPath,
          host: "ws://localhost:8080/spiderPP/spiderSocketGame"
      },
      
      message: {
          data:"",
          set: function(messageComming){this.data = messageComming;},
          launch: function(){
              try{
                  console.log('>>'+this.data.type);
                  window[this.data.type](this.data);
              }catch(error){console.log('Error at reflection clause ' + error.message);}
          }
      },

     connect: function(){
          try{
                this.connection =  new WebSocket(this.dataConnection.host+
                                                "/"+this.dataConnection.room+
                                                "/"+this.dataConnection.perfil);
          }catch(error){
              console.log("something wrong :: connection error:: "+error.message);
          }
      },
      
      send: function(data,fn){
          var messageToSend = data;
          this.connection.send(JSON.stringify(messageToSend));
      }
};

var story = {
    htmlElement:{
      circle:"",
      row:""
    },
    getId:function(){
        return this.htmlElement.row.attr('id');
    },
    isRoot: function(){
        if(this.htmlElement.row.attr('parent') === 'root'){
            return true;
        }
        return false;
    },
    isSetted: function(){
        if(this.htmlElement.row.length === 0){
            return false;
        }
        
        return true;
    },
    set: function(jqStoryHtml){
        if(this.isSetted()){
            this.setBlueStatus();
            this.setWhiteStatus();
        }
        this.htmlElement.circle = jqStoryHtml.find('.activity');
        this.htmlElement.row = jqStoryHtml;
    },
    setGreenStatus: function(){
        this.htmlElement.circle.children('.tiles').removeClass('blue').addClass('green');
    },
    setBlueStatus:function(){
        this.htmlElement.circle.children('.tiles').removeClass('green').addClass('blue');
    },
     setAliceBlueStatus: function(){
        this.htmlElement.row.attr('style','background-color:aliceblue');
    },
    setWhiteStatus: function(){
        this.htmlElement.row.attr('style','background-color:white');
    }
};



function showCardsResult(){
    var otherChoice = "";
    
    for (var index = 0; index < gameSocket.cards.anotherUsers.length; index++) {
        otherChoice += buildCardSelected(gameSocket.cards.anotherUsers[index]);
    }

    $('#other-choice').html('');
    $(otherChoice).hide().appendTo('#other-choice').fadeIn(999);
    
}

function showStoryActions(){
    $('<div id="rate-option"><button \n\
        id="set-rate" class="btn btn-success \n\
        btn-small">est</button>\n\
        <button id="div-est" class="btn \n\
        btn-warning btn-small">div</button><div>').hide()
        .appendTo($(story.htmlElement.row.find('.form-rate')))
        .fadeIn(999);
}

function showModalDialog(message,head){
        if($('#modal-dialog').length !== 0){
            $('#modal-dialog').modal('hide');
        }
        var modalHtml =  '<div class="modal fade" id="modal-dialog">'
                         +'<div class="modal-dialog">'
                         +'<div class="modal-content">'
                         +'<div class="modal-header">'
                         +'<button type="button" class="close"\n\
                        data-dismiss="modal" aria-hidden="true">×</button>'
                        +head+'<div class="modal-body">'
                        +message+'</div><div class="modal-footer">'
                        +'<button class="btn btn-primary" data-dismiss="modal">fechar</button>'
                        +'</div>' +'</div>' +'</div></div>';
                $('body').prepend(modalHtml);
                $('#modal-dialog').modal();
                
}      

//SOCKET EVENTS

function chatMessage(data) {
    
    if (data.idUsuario === idUsuario)
        appendMessageSent(data.message);
    else
        appendMessageReceived(data.message);
    scrollToFinish();
}

function round(data){
    
    if(!story.isSetted()){
        console.log('there are no story setted');
        return;
    }
    enableCardArea();
    $('#other-choice, #my-choice').html('');
    $('.countdown').html('<div id="countdown"></div>');
    $("#countdown").countdown360({ 
        radius:24.5,
        label: false,
        seconds: 20,
        fontColor: '#8ac575',
        onComplete: function(){
            disableCardArea();
            showCardsResult();
            $('#countdown').fadeOut().remove();
            showStoryActions();
       }
    });
}



function card(otherUserCard){

    console.log('length'+gameSocket.cards.anotherUsers.length);
    
    for(var index = 0; index < gameSocket.cards.anotherUsers.length; index++){
        if(otherUserCard.userId === gameSocket.cards.anotherUsers[index].userId){
            gameSocket.cards.anotherUsers[index].value = otherUserCard.value;
            return;
        }
    }
    gameSocket.cards.anotherUsers.push(otherUserCard);
    var cardBacking = buildCardHidden(otherUserCard.userName);
    $(cardBacking).hide().appendTo('#other-choice').fadeIn(999);
}


function rate(data){
    showModalDialog('Registro da estimativa realizado  <span class=" badge badge-important">'+ data.score+'</span>', 'Sucesso');
    var newRateValue =  $('<span class=" badge ">'+data.score+'</span>');
    newRateValue.appendTo(story.htmlElement.row.find('.rate-box')).fadeIn(999,function(){
                   story.htmlElement.row.find('.form-rate').html('');
    });
}

//Waiting for subtaks case ;D
function taskSelected(data){
    story.set($('#'+data.id));
    story.setGreenStatus();
    story.setAliceBlueStatus();
}

