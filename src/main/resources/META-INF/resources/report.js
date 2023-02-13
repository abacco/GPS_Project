function generate() {
    repClear();
    //prende la data e la mette nell'uri
    var date1 = new Date(document.getElementById("startDate").value);
    formattedStartDate = date1.getFullYear() + "-" + (date1.getMonth()+ 1) + "-" + date1.getDate();
    if(formattedStartDate == "NaN-NaN-NaN"){
        alert("Inserire una data di inizio valida!");
        repClear();
        return;
    }

    var date2 = new Date(document.getElementById("endDate").value);
    formattedEndDate = date2.getFullYear() + "-" + (date2.getMonth()+ 1) + "-" + date2.getDate();
    if(formattedEndDate == "NaN-NaN-NaN"){
        alert("Inserire una data di fine valida!");
        repClear();
        return;
    }

    if(date1 > date2){
        alert("Inserire un intervallo di tempo valido!");
        repClear();
        return;
    }

    range = formattedStartDate + "," + formattedEndDate;
    console.log(range)
    //let element = document.getElementById("daterange");
    let string = "http://localhost:8080/Report?daterange="+ encodeURIComponent(range);

    let report = document.createElement("a");
    report.href = string;
    let img = document.createElement("img");
    img.src ="https://i.imgur.com/iapMPv5.png";
    img.classList.add("icona");
    report.href = string;
    report.img = "https://i.imgur.com/iapMPv5.png";
    report.innerHTML = "Download Report";
    report.classList.add("scarica");
    let br = document.createElement("br");
    report.download = "Report.pdf";
    report.append(br);
    report.append(img);
    document.getElementById("date").append(report);
    InserisciReport(range);
}

function repClear() {
    document.getElementById("date").innerHTML = "";
}

//fa una get di un arraylist di device entro un range e li inserisce nell'hmtl
function InserisciReport(range) {

    req = new XMLHttpRequest()
    req.onload = function () {
        clear();
        const responseArray = JSON.parse(this.responseText);
        let element = document.getElementById("stampa");
        element.innerHTML = "Medie giornaliere dei parametri rilevate dal " + new Date(document.getElementById("startDate").value).toLocaleDateString('it-IT', { day: 'numeric', month: 'long', year: 'numeric' }) + " al " + new Date(document.getElementById("endDate").value).toLocaleDateString('it-IT', { day: 'numeric', month: 'long', year: 'numeric' }) + ":";
        for (let i in responseArray) {
            let table = document.createElement("table");
            table.classList.add("tabella");

            let data = document.createElement("tr");
            let mis = document.createElement("tr");

            if(responseArray[i].startsWith("Medie") || responseArray[i] === " ")
                continue;
            var misurazione = responseArray[i].split("⠀");

            data.append(misurazione[0]);
            mis.append(misurazione[1]);
            table.append(data)
            table.append(mis)
            element.append(table)

        }
    }

    req.open("POST", "/Report/getReports", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("daterange="+range);
}