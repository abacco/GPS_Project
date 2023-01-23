$(document).ready(function(){

    //Inizializzazione checkbox
    let checkbox = $(".check");


    //Stile checkbox
    checkbox.css("background-color","forestgreen");
    checkbox.css("border-radius","0");
    checkbox.css("width","20px");
    checkbox.css("height","20px");
    checkbox.css("border-radius","50%");
    checkbox.css("vertical-align","middle");
    checkbox.css("border","1px solid #ddd");
    checkbox.css("-webkit-appearance","none");
    checkbox.css("cursor","pointer");


    //Link checkbox
    checkbox.click(function(){
        window.open("/AreaPredizioni.html","_self");

    });

    //Notifiche per rischio Malattie e aggiornamento pallino rischio
    var source = new EventSource("/AreaPredizioni/stream");

    source.onmessage = function (event) {
        var incoming = JSON.parse(event.data)
        var array1 = incoming[0].split(",")
        var array2 = incoming[1].split(",")

        console.log(array1,array2);

        if(array1[1] == "rischio"){
            alert("Attenzione! Riscio di Aterosclerosi elevato!");
            checkbox.css("background-color","red");

        } else if (array1[1] == "attenzione"){
            alert("Attenzione! Riscio di Aterosclerosi moderato!");
            checkbox.css("background-color","yellow");
        }

        if(array2[1] == "rischio"){
            alert("Attenzione! Riscio di Infarto elevato!");
            checkbox.css("background-color","red");

        } else if (array2[1] == "attenzione"){
            alert("Attenzione! Riscio di Infarto moderato!");
            checkbox.css("background-color","yellow");
        }


    };



});