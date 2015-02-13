/* 
 * This File is responsible for get all tasks and subtasks and put accordion style on it
 */


function accordion(){
    $('#description .reff').click(function(e){
      var task =   $(this).parent();
          task.next('.stasks').slideToggle();
    });
}

function accordionSetUp(accordionStart){
    $('#description .reff').each(function(index, rootStory){
        var task = $(rootStory).parent();
        task.next('.stasks').slideUp();
    });
    
    accordionStart();
}



