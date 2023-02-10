function generate() {
    repClear();
    //prende la data e la mette nell'uri
    var date = new Date(document.getElementById("startDate").value);
    formattedStartDate = date.getFullYear() + "-" + (date.getMonth()+ 1) + "-" + date.getDate();
    if(formattedStartDate == "NaN-NaN-NaN"){
        alert("Inserire una data di inizio valida!");
        repClear();
        return;
    }

    var date = new Date(document.getElementById("endDate").value);
    formattedEndDate = date.getFullYear() + "-" + (date.getMonth()+ 1) + "-" + date.getDate();
    if(formattedEndDate == "NaN-NaN-NaN"){
        alert("Inserire una data di fine valida!");
        repClear();
        return;
    }

    if(formattedStartDate.valueOf() > formattedEndDate.valueOf()){
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
        element.innerHTML = "Media dei valori dal " + new Date(document.getElementById("startDate").value).toLocaleDateString('it-IT', { day: 'numeric', month: 'long', year: 'numeric' }) + " al " + new Date(document.getElementById("endDate").value).toLocaleDateString('it-IT', { day: 'numeric', month: 'long', year: 'numeric' }) + ":";
        for (let i in responseArray) {
            let node = document.createElement("tr");
            node.append(responseArray[i]);
            element.append(node)

        }
    }

    req.open("POST", "/Report/getReports", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("daterange="+range);
}