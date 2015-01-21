
$(document).ready(socketStart());

function socketStart(){

            disableCardArea();
               
               var perfil = prPath; 
               var room  = rmPath;
               
               var  host = "ws://localhost:8080/spiderPP/spiderSocketGame";
               
               var spiderSocket = new WebSocket(host+"/"+room+"/"+perfil);
               var storySelected = "";
               var smStorySelected = "";
               var formDivStory  = '<div class="grid" ><div class="row form-row"><div class="col-md-8"><input type="text" class="form-control" placeholder="Nome para estoria"></div></div><div class="row form-row"><div class="col-md-9"><textarea  name="descricao" class="form-control" style="width:34em;height:10em;" placeholder="descreva a atividade"></textarea></div></div></div>';
               
               var storyHtmlElementSelected = "";
               var rateValues = [0,2,5,8,13,20];
               
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
                       if(message.kind === "rateSuccess"){
                           $('#modal-dialog').modal('hide');
                            showModalDialog(message.message,'Informacao');                           
                       }

                       //NOTICE GERENCIA AS MENSAGENS DE INFORMACAO :: CRITICO / ERROR / INFO
                       //console.log("estamos errados"+message.message);
                       //window.location = "../projeto"; 
//                        showModalDialog(message.message,'Informacao');
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
                       showModalDialog('Registro da estimativa realizado  <span class=" badge badge-important">'+ message.score+'</span>', 'Sucesso');
                       $('<span class=" badge ">'+message.score+'</span>').hide().appendTo(storyHtmlElementSelected.siblings('.score').children('.rate-box')).fadeIn(999,function(){
                            storyHtmlElementSelected.siblings('.score').children('.form-rate').html('');
                       });
                   }
                   
                   if(message.type === "stories")
                   {
                       
                       alert("AHHHHHHHHHHHHHHHH It can be reality its working ");
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
               
               $('body').on("change","#rate-value",function(e){
                   
                    option = confirm('deseja confirmar estimativa ? ' );
                    
                    if(!option){return;}
                    var estRateValue = $('#rate-value').val();
                    var estim = {
                        "type": "rate", 
                        "score": estRateValue,
                        "storyId": storyHtmlElementSelected.attr('id')
                    };
                    spiderSocket.send(JSON.stringify(estim));
                    $('#modal-dialog').modal('hide');
                    
               });
               
               $('.score .form-rate').on('click','#set-rate',function(){
                   var optionRateValue = '<select id="rate-value">';
                    $.each(rateValues,function(index,rateValue){
                            optionRateValue += '<option value="'+rateValue+'">'+rateValue+'</option>';
                     });
                     optionRateValue +='</select>';
                     
                   showModalDialog(optionRateValue,'Escolha o valor da estimativa');
               });
               
               $('.score .form-rate').on('click','#div-est',function(){
                    
                    showModalDialog(formDivStory,'<h4>Inclua estorias</h4><button class="btn btn btn-primary" id="more-subtasks">+add</button> <button class="btn btn btn-primary" id="save-subtasks">salvar</button>');
               });
               
               $('body').on('click','#more-subtasks',function(){
                    $(formDivStory).hide().appendTo($('#modal-dialog .modal-dialog .modal-content .modal-header .modal-body')).fadeIn(999);
               });
               
               $('body').on('click','#save-subtasks',function(){
                  
                    var modalElementFormCollection  = $('#modal-dialog .modal-dialog .modal-content .modal-header .modal-body').children();
                    var formStoryAttibutes = [];
                       var subtasks = [];
                  
                    $.each(modalElementFormCollection,function(index,elementForm){
                        formStoryAttibutes.push($(elementForm).children().find('.form-control'));
                    });
                    
                    $.each(formStoryAttibutes,function(index,formStoryAttribute){
                            if(formStoryAttribute[0].value !== 0){
                                if(formStoryAttribute[1].value !== 0){
                                    var subtask = {
                                        "name": formStoryAttribute[0].value,
                                        "description": formStoryAttribute[1].value,
                                        "storyId": storyHtmlElementSelected.attr('id'),
                                        "projectId": idProjeto,
                                        "type": "subtask"
                                    };
                                    spiderSocket.send(JSON.stringify(subtask));
                                }
                            }
                    });
                    
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
                   $('<button id="set-rate" class="btn btn-success btn-small">est</button>\n\
                    <button id="div-est" class="btn btn-warning btn-small">div</button>')
                       .hide().appendTo(
                                        storyHtmlElementSelected
                                            .siblings('.score')
                                            .children('.form-rate'))
                                .fadeIn(999);

               }
           }
           
           function showModalDialog(message,head){
               var modalHtml =  '<div class="modal fade" id="modal-dialog">'
                                            +'<div class="modal-dialog">'
                                            +'<div class="modal-content">'
                                                +'<div class="modal-header">'
                                                +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'
                                                +head
                                                +'<div class="modal-body">'
                                                    +message
                                                    +'</div><div class="modal-footer">'
                                                        +'<button class="btn btn-primary" data-dismiss="modal">fechar</button>'
                                                    +'</div>'
                                                +'</div>'
                                            +'</div></div>';
                $('body').prepend(modalHtml);
                $('#modal-dialog').modal();
                
           }
           
           
           
           $('body').on('hidden.bs.modal','#modal-dialog',function (e) {
                $(this).remove();
           });
           
           
    

         


