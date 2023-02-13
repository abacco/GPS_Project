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
        var ateroPrediction = incoming[0].split(",")
        var infartoPrediction = incoming[1].split(",")

        console.log(ateroPrediction,infartoPrediction);

        //il campo uno corrisponde al grado di rischio
        checkCorrettezza(ateroPrediction[1]);
        checkCorrettezza(infartoPrediction[1]);

        function checkCorrettezza(rischio){
            if(rischio  == "sotto_controllo"){
                checkbox.css("background-color","forestgreen");
                checkbox.val("green");
            }
            if(rischio == "rischio"){
                alert("Attenzione! Riscio di Aterosclerosi elevato!");
                checkbox.css("background-color","red");
                checkbox.val("red");
            } else if (rischio == "attenzione"){
                //alert("Attenzione! Riscio di Aterosclerosi moderato!");
                checkbox.css("background-color","yellow");
                checkbox.val("yellow");
            }
        }

    };



});