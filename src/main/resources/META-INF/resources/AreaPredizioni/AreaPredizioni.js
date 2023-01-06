$(document).ready(function(){

    let malattiaA = $(".aterosclerosi");
    let malattiaI = $(".infarto");

    $(".aterosclerosi").click(function(){

        if(malattiaA.css("background-color") != "#cdebe6"){
            malattiaA.css("background-color","#cdebe6");
            malattiaI.css("background-color","white");
        }

    });

    $(".infarto").click(function(){

        if(malattiaI.css("background-color") != "#cdebe6"){
            malattiaI.css("background-color","#cdebe6");
            malattiaA.css("background-color","white");
        }
    });


});