/* 
 * This File is responsible for get all tasks and subtasks and put accordion style on it
 */


function accordion(){
    $('body').on('click','.reff',function(){
        var task = $(this).parents().eq(2);
        task.next('.stasks').slideToggle();
    });
    
//    $('#stories').find('.reff').click(function(e){
    
//    });
}

function accordionSetUp(accordionStart){
    $('#stories').find('.reff').each(function(index, rootStory){
        var task = $(rootStory).parents().eq(2);
        task.next('.stasks').slideUp();
    });
    
    accordionStart();
}

function accordionElement(storyHtml){
    storyHtml.next('.stasks').slideToggle();
}

function accordioUpElement(storyHtml){
    
    storyHtml.next('.stasks').slideDown();
}


