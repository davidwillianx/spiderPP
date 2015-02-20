/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    
    
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
        story.htmlElement.circle = $(this);
        story.htmlElement.line = $(this).parents().eq(1);
        
        
            if(story.isRoot())
            {
                story.htmlElement.circle.children('.tiles').addClass('green');
                
                
                accordionElement(story.htmlElement.line,
                story.htmlElement.circle.popover({
                    content:'Esta estoria ja foi divida, escolha uma de suas subtarefas para estimar',
                    title:'Só pra lembrar... ;)'
                }));
                story.htmlElement.circle.parents().eq(1).attr('style','background-color:aliceblue');
                return;
            }
            story.htmlElement.circle.children('.tiles').addClass('green');
            story.htmlElement.line.attr('style','background-color:aliceblue');
            console.log('Preparando para enviar');
            gameSocket.send({"id":story.htmlElement.line.attr('id'),"type":"story"});
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
                  window[this.data.type](this.data);
              }catch(error){console.log('Error at reflection clause');}
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
      line:""
    },
    isRoot: function(){
        if(this.htmlElement.line.attr('parent') === 'root'){
            return true;
        }
        return false;
    }
};

function chatMessage(data) {
    
    if (data.idUsuario === idUsuario)
        appendMessageSent(data.message);
    else
        appendMessageReceived(data.message);
    scrollToFinish();
}


