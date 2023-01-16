$(document).ready(function(){


    //Inizializzazione Malattie
    let malattiaA = $(".aterosclerosi");
    let malattiaI = $(".infarto");

    malattiaA.css("background-color","#cdebe6");
    malattiaI.css("background-color","white");




    var source = new EventSource("/AreaPredizioni");
    source.onmessage = function (event) {
        var incoming = JSON.parse(event.data);


        if (config.data.datasets.length > 0) {
            xMax = xMax +6;
            config.data.labels.push(xMax);

            config.data.datasets[0].data.push(incoming[0].rischio);
            config.data.datasets[1].data.push(incoming[1].rischio);
            config.data.datasets[2].data.push(incoming[0].percentualeRischio);
            config.data.datasets[3].data.push(incoming[1].percentualeRischio);
            config.data.datasets[4].data.push(incoming[0].modello);
            config.data.datasets[5].data.push(incoming[1].modello);

            window.myLine.update();
        }

        //Stampa della percentuale relativo all'Aterosclerosi
        var tableBody = document.getElementById("ateroBody");

        var row = document.createElement("tr");
        var cell = document.createElement("td")
        var cellText = document.createTextNode("incoming.percentualeA");
        cell.appendChild(cellText);
        row.appendChild(cell);
        tableBody.appendChild(row);

        //Stampa della percentuale relativo all'Infarto
        var tableBody2 = document.getElementById("infartoBody");

        var row = document.createElement("tr");
        var cell = document.createElement("td")
        var cellText = document.createTextNode("incoming.percentualeI");
        cell.appendChild(cellText);
        row.appendChild(cell);
        tableBody2.appendChild(row);

        //Stampa del grafico relativo all'Aterosclerosi
        var graficoBody = document.getElementById("graficoBody");

        var div = document.createElement("div");
        var spanA = document.createElement("span");
        spanA.style.display ="inline";
        var grafico = document.createTextNode("grafico1");
        spanA.appendChild(grafico);
        div.appendChild(spanA);
        graficoBody.appendChild(div);


        //Stampa del grafico relativo all'Infarto
        var graficoBody = document.getElementById("graficoBody");

        var div = document.createElement("div");
        var spanI = document.createElement("span");
        spanI.style.display ="none";
        var grafico = document.createTextNode("grafico2");
        spanI.appendChild(grafico);
        div.appendChild(spanI);
        graficoBody.appendChild(div);

    }


    //Selezione della malattia Aterosclerosi e visualizzazione del grafico relativo
    malattiaA.click(function(){

        if(malattiaA.css("background-color") != "#cdebe6"){
            malattiaA.css("background-color","#cdebe6");
            malattiaI.css("background-color","white");

            spanA.style.display ="inline";
            spanI.style.display ="none";
        }


    });

    //Selezione della malattia Infarto e visualizzazione del grafico relativo
    malattiaI.click(function(){

        if(malattiaI.css("background-color") != "#cdebe6"){
            malattiaI.css("background-color","#cdebe6");
            malattiaA.css("background-color","white");

            spanI.style.display ="inline";
            spanA.style.display ="none";

        }
    });



});