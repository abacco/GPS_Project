$(document).ready(function(){


    //Inizializzazione Malattie
    let malattiaA = $(".aterosclerosi");
    let malattiaI = $(".infarto");

    malattiaA.css("background-color","#cdebe6");
    malattiaI.css("background-color","white");

    var tableBody = document.getElementById("ateroPredict");

    var tableBody2 = document.getElementById("infartoPredict");

    var graficoAtero = document.getElementById("graficoAtero");
    var graficoInfarto = document.getElementById("graficoInfarto");

    //Inizializzazione array per la stampa delle predizioni
    var source = new EventSource("/AreaPredizioni/stream");
    source.onmessage = function (event) {

        var incoming = JSON.parse(event.data)
        var array1 = incoming[0].split(",")
        var array2 = incoming[1].split(",")

        console.log(array1,array2);



        //Stampa della percentuale relativo all'Aterosclerosi
        tableBody.innerText=array1[0] + "%";

        //Stampa della percentuale relativo all'Infarto
        tableBody2.innerText=array2[0] + "%";




        //Stampa della tabella relativo all'Aterosclerosi
        graficoAtero.innerText=array1[2];

        //Stampa della tabella relativo all'Infarto
        graficoInfarto.innerText=array2[2];

    }


    //Selezione della malattia Aterosclerosi e visualizzazione del grafico relativo
    malattiaA.click(function(){

        if(malattiaA.css("background-color") != "#cdebe6"){
            malattiaA.css("background-color","#cdebe6");
            malattiaI.css("background-color","white");

            graficoAtero.style.display="inline";
            graficoInfarto.style.display="none";

        }


    });

    //Selezione della malattia Infarto e visualizzazione del grafico relativo
    malattiaI.click(function(){

        if(malattiaI.css("background-color") != "#cdebe6"){
            malattiaI.css("background-color","#cdebe6");
            malattiaA.css("background-color","white");

            graficoInfarto.style.display="inline";
            graficoAtero.style.display="none";

        }
    });




});