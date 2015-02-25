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
            gameSocket.send({"id":story.htmlElement.row.attr('id'),"type":"taskSelected"});
    });
});


var gameSocket = {
      connection: "",
      
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
    isRoot: function(){
        if(this.htmlElement.row.attr('parent') === 'root'){
            return true;
        }
        return false;
    },
    set: function(jqStoryHtml){
        if(this.htmlElement.row.length !== 0){
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



function chatMessage(data) {
    
    if (data.idUsuario === idUsuario)
        appendMessageSent(data.message);
    else
        appendMessageReceived(data.message);
    scrollToFinish();
}

//Waiting for subtaks case ;D
function taskSelected(data){
    story.set($('#'+data.id));
    story.setGreenStatus();
    story.setAliceBlueStatus();
    
}



