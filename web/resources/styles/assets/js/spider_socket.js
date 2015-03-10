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

        showModalDialog(story.formToDivide+story.formToDivide,'<h4>Inclua estorias</h4>\n\
                                    <button class="btn btn btn-primary" \n\
                                    id="more-subtasks">+add</button> \n\
                                    <button class="btn btn btn-primary" \n\
                                    id="save-subtasks">salvar</button>');
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
    
    $('body').on('click','#more-subtasks',function(){
         $(story.formToDivide).hide()
                .appendTo($('#modal-dialog .modal-dialog \n\
                          .modal-content .modal-header .modal-body'))
                .fadeIn(999);
    });
    
     
    
    $('body').on('click', '#save-subtasks', function () {

        var modalElementFormCollection = $('#modal-dialog .modal-dialog .modal-content .modal-header .modal-body').children();
        var formStoryAttibutes = [];
        var task = {
            "type": "subtasks",
            "storyId": story.getId(),
            "subtasks": []
        };

        $.each(modalElementFormCollection, function (index, elementForm) {
            formStoryAttibutes.push($(elementForm).children().find('.form-control'));
        });

        $.each(formStoryAttibutes, function (index, formStoryAttribute) {
            if (formStoryAttribute[0].value !== 0) {
                if (formStoryAttribute[1].value !== 0) {
                    var subtask = {
                        "name": formStoryAttribute[0].value,
                        "description": formStoryAttribute[1].value,
                        "storyId": story.getId(),
                        "projectId": idProjeto,
                        "type": "subtask",
                        "creationDate": ""
                    };
                    task.subtasks.push(subtask);
                }
            }
        });
        gameSocket.send(task);
        
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
    formToDivide:
           '<div class="grid" ><div class="row form-row">\n\
            <div class="col-md-8">\n\
            <input type="text" class="form-control" placeholder="Nome para estoria">\n\
            </div></div><div class="row form-row">\n\
            <div class="col-md-9"><textarea  name="descricao"\n\
            class="form-control" style="width:34em;height:10em;"\n\
            placeholder="descreva a atividade">\n\
            </textarea></div></div></div>',
    getId:function(){
        return this.htmlElement.row.attr('id');
    },
    getRoot:function(){
      return this.htmlElement.row.parent().prev();
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
    isSubtask: function(){
        if(this.htmlElement.row.parent().is('.stasks'))
            return true;
        return false;
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
    var rateOption  = $('<div id="rate-option"></div>').hide()
                .appendTo(story.htmlElement.row.find('.form-rate'));
    
    var htmlRateButton = '<div id="rate-option"><button \n\
                             id="set-rate" class="btn btn-success\n\
                           btn-small">est</button>';
    var htmlDivisionButton = '<button id="div-est" class="btn\n\
                                 btn-warning btn-small">div</button><div>';
    
    if(story.isSubtask()){
        rateOption.append(htmlRateButton).fadeIn(999);
        return;
    }
    
    rateOption.append(htmlRateButton+htmlDivisionButton).fadeIn(999);

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
    var startButton =  $('#game-start').attr('disabled','disabled');
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
            startButton.attr('disabled',false);
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
    showModalDialog('Registro da estimativa realizado \n\
             <span class=" badge badge-important">'+ data.score+'</span>', 'Sucesso');
    var newRateValue =  $('<span class=" badge ">'+data.score+'</span>');
    newRateValue.appendTo(story.htmlElement.row.find('.rate-box')).fadeIn(999,function(){
                   story.htmlElement.row.find('.form-rate').html('');
    });
}
            
function subtasks(newSubtasks){
    story.htmlElement.row.find('.fa').removeClass('.fa-check-circle').addClass('fa-sitemap');
    story.htmlElement.row.attr('parent','root');
    
    if(story.htmlElement.row.next('.stasks').length === 0 )
         $('<div class="stasks" style="display: block"></div>').insertAfter(story.htmlElement.row);
     
    $(newSubtasks.subtasks).each(function(indexList,subtask){
       appendSubtask(subtask,newSubtasks.reference);
    });
    
    
    showModalDialog('Divisão Realizada','Sucesso !! ');
    $('#rate-option').remove();
    
    var subtaskToggle = story.htmlElement.row.find('.subtask-toggle');
    if(subtaskToggle.length === 0){
      story.htmlElement.row.children('.post')
                .prepend('<div class="p-b-5"><a class="reff \n\
                          href="javascript:;"> <i class="fa fa-2x \n\
                          fa-level-down"></i> <span class="badge \n\
                          animated bounceIn">'
                          +newSubtasks.subtasks.length+'</span> </a> </div>');
                          
        accordionElement(story.htmlElement.row);
    }
}

//I dont need notice yet, it's just to remeber ;)
//function notice(message){
//    showModalDialog(message.message,'Informação');
//}



//Waiting for subtaks case ;D
function taskSelected(data){
    console.log('something wrong');
    story.set($('#'+data.id));
    
    if(story.isSubtask())
        accordioUpElement(story.getRoot());
    
    story.setGreenStatus();
    story.setAliceBlueStatus();
    
}

