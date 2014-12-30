
$(document).ready(socketStart());

function socketStart(){

            disableCardArea();
               
               var perfil = prPath; 
               var room  = rmPath;
               
               var  host = "ws://localhost:8080/spiderPP/spiderSocketGame";
               
               var spiderSocket = new WebSocket(host+"/"+room+"/"+perfil);
               var storySelected = "";
               var smStorySelected = "";
               
               var storyHtmlElementSelected = "";
               
               
               spiderSocket.onopen = function(){
                   //IF estimativa recebe tempo e as estimativas
               };
               
               
               
                   
                   
               spiderSocket.onmessage = function(event)
               {
                   message = JSON.parse(event.data);
                   
                   
                   if(message.type === "chatMessage"){
                     
                     if(message.idUsuario === idUsuario)
                         appendMessageSent(message.message);
                     else  appendMessageReceived(message.message);
                   
                     scrollToFinish();
                   }
                   
                   if(message.type === "gameStart")
                   {
                       openGameCardSelection();
                       $("#timerGamer").html('<div id="countdown360"></div>');
                       $("#gameStart").attr("disabled","disabled");
                       enableCardArea();
                       $("#countdown360").countdown360({
//                           seconds : 300,
                           seconds : 20,
                           onComplete: function(){
                                showCardsSelected();  
                                disableCardArea();
                                showFormRate(storyHtmlElementSelected);
                                gameUnlock = {"type": "gameUnlock"};
                                spiderSocket.send(JSON.stringify(gameUnlock));
                                
                                $("#gameStart").attr("disabled",false);
                                $("#countdown360").fadeOut().remove();
                                
                           }
                       });
                   }
                   
                   if(message.type === "cardSelected")
                   {
                      var card = JSON.parse(event.data); 
                       userCardSelection(card);
                   }
                   
                   if(message.type === "notice")
                   {
                       createModalInfo('modal-info',message.message);
                       $('#modal-info').modal();
                       
                       //NOTICE GERENCIA AS MENSAGENS DE INFORMACAO :: CRITICO / ERROR / INFO
                       //console.log("estamos errados"+message.message);
                       //window.location = "../projeto"; 
                   }
                   
                   
                   if(message.type === "gameUnlock")
                   {
                       $("#main").unblock();
                   }
                   if(message.type === "gameLocked")
                   {
                       $("#main").block({message:"aguarde o inicio da rodada"});
                   }
                   
                   if(message.type === "story")
                   {
                       id = '#'+message.id;
                       removeTargetStory(storyHtmlElementSelected);
                            storyHtmlElementSelected = $(id);
                       addTargetStory(storyHtmlElementSelected);
                   }
                   
                   
                   if(message.type === "rate"){
                       createModalInfo('new-rate-setted','Registro da estimativa realizado  <span class=" badge badge-important">'+ message.score+'</span>');
                       $('#new-rate-setted').modal();
                       storyHtmlElementSelected.siblings('.score').children('.rate-box').append('<span class=" badge ">'+message.score+'</span>');
                       
                   }
               };
               
               $("#gameStart").click(function(e){
                   e.preventDefault();
                      gameStart = {
                       "type": "gameStart",
                       "start": "startTime"
                   };
                   
                   spiderSocket.send(JSON.stringify(gameStart));
               });
               
               $('.opt-card ').click(function(e){
                  var card = {
                     "type": "cardSelected",
                     "value" : $(this).html(),
                     "userName": author,
                     "idUsuario":  idUsuario
                   };
                   
                   myCardSelection(card);
                   spiderSocket.send(JSON.stringify(card));
               });
               
               
               $('.activity').click(function(){
                   
                   $("#rate-value").fadeOut(function(){$(this).remove();});
                  removeTargetStory(storyHtmlElementSelected);
                  storyHtmlElementSelected = $(this);
                  
                  storyDataSelected = {
                       "id":storyHtmlElementSelected.attr("id")
                        ,"type":"story"
                   };
                   
                   addTargetStory(storyHtmlElementSelected);
                   spiderSocket.send(JSON.stringify(storyDataSelected));
               });
               
               $('.score .form-rate').on("change","#rate-value",function(e){
                   
                    option = confirm('deseja confirmar estimativa ? ' );
                    
                    if(!option){return;}
                    var estRateValue = $('#rate-value').val();
                    var estim = {
                        "type": "rate", 
                        "score": estRateValue,
                        "storyId": storyHtmlElementSelected.attr('id')
                    };
                    spiderSocket.send(JSON.stringify(estim));
                    $('#rate-value').remove();
                    
               });
               
               $('body').on('keypress','#chat-message-input',function(txtElement){
                    if(txtElement.which === 13){
                         txtElement.preventDefault();
                          
                        var chatMessage = {
                            "idUsuario":  idUsuario,
                            "idProjeto": idProjeto,
                            "author" : author,
                            "message":  $(this).val(),
                            "type":  "chatMessage"
                            };
                         
                        sendMessage(spiderSocket,chatMessage);
                    }
                });
            }
            
            
            
            
            //UnderVerification
            function registerScore(storyHtmlElementSelected)
            {
                storyHtmlElementSelected.siblings(".score").removeClass('hide');
            }
            
            function hideRegisterScoreComponent(storyHtmlElementSelected)
            {
                storyHtmlElementSelected.siblings(".score").addClass('hide');
            }
            
//         window.onbeforeunload = function()
//         {
//           return  "Voce realmente deseja sair desta pagina?";
//         }
        
           function removeTargetStory(storyHtmlElementSelected)
           {
               if(storyHtmlElementSelected.length !== 0)
                    storyHtmlElementSelected.children(0).removeClass('green').addClass('blue');
           }
           
           function addTargetStory(storyHtmlElementSelected)
           {
               storyHtmlElementSelected.children(0).addClass('green');
           }
           
           function showFormRate(storyHtmlElementSelected)
           {               
               if(storyHtmlElementSelected.length !== 0){
                   storyHtmlElementSelected.siblings('.score').children('.form-rate').html('<select update=":main:stories"  class="form-controll" id="rate-value"><option value="0" label="0"/><option value="1" label="1"/><option value="2" label="2"/><option value="5" label="5"/><option value="8" label="8"/><option value="10" label="10"/><option value="20" label="20"/></select>');
               }
           }
           
           
           function createModalInfo(id,message){
             var modalHtml =  '<div class="modal fade" id="'+id+'">'
                                            +'<div class="modal-dialog">'
                                            +'<div class="modal-content">'
                                                +'<div class="modal-header">'
                                                +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'
                                                +'Infomacao</div>'
                                                +'<div class="modal-body">'
                                                    +message
                                                    +'</div><div class="modal-footer">'
                                                        +'<button class="btn btn-primary" data-dismiss="modal">fechar</button>'
                                                    +'</div>'
                                                +'</div>'
                                            +'</div></div>';
               $('body').prepend(modalHtml);                        
           }

         


