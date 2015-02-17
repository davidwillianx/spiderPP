/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    accordionSetUp(accordion);
    
    gameSocket.connect();
    gameSocket.dataConnection.connection.onmessage = function(evnt){
        gameSocket.message.set(JSON.parse(evnt.data));
        gameSocket.message.launch();
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
           });
        }
    });
});

var gameSocket = {
      dataConnection: {
          perfil: rmPath,
          room: prPath,
          host: "ws://localhost:8080/spiderPP/spiderSocketGame",
          connection: ""
      },
      
      message: {
          data:"",
          set: function(messageComming){this.data = messageComming;},
          launch: function(){
              try{
                  window[this.data.type](this.data.message,this.data.idUsuario);
              }catch(error){console.log('Error at reflection clause');}
              
          }
      },

     connect: function(){
          try{
              this.dataConnection.connection = new WebSocket(this.dataConnection.host+
                                                "/"+this.dataConnection.room+
                                                "/"+this.dataConnection.perfil);
          }catch(error){
              console.log("something wrong:: "+error.message);
          }
      },
      send: function(data){
          var messageToSend = data;
          sendMessage(this.dataConnection.connection, messageToSend);
      }
};


function chatMessage(message,id) {
    if (id === idUsuario)
        appendMessageSent(message);
    else
        appendMessageReceived(message);
    scrollToFinish();
}


