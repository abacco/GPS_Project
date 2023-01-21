var xAxis = [0];
var xMax = 0;

var config = {
    type: 'line',
    data: {
        labels: xAxis,
        datasets: [{
            label: 'Temperatura Corporea',
            backgroundColor: '#00a51b',
            borderColor: '#00a51b',
            data: [
            ],
            fill: false,
        }, {
            label: 'Frequenza Cardiaca',
            fill: false,
            backgroundColor: '#ff2424',
            borderColor: '#ff2424',
            data: [
            ],
        }, {
            label: 'Ossigenazione',
            fill: false,
            backgroundColor: '#2832f8',
            borderColor: '#2832f8',
            data: [
            ],
        }, {
            label: 'Colesterolo',
            fill: false,
            backgroundColor: '#ffbd3e',
            borderColor: '#ffbd3e',
            data: [
            ],
        }, {
            label: 'Pressione massima',
            fill: false,
            backgroundColor: '#fd50e7',
            borderColor: '#fd50e7',
            data: [
            ],
        }, {
            label: 'Pressione minima',
            fill: false,
            backgroundColor: '#fd50e7',
            borderColor: '#fd50e7',
            data: [
            ],
        }]
    },
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: 'Chart.js Line Chart'
            },
            tooltip: {
                mode: 'index',
                intersect: false,
            }
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            x: {
                display: true,
                title: {
                    display: true,
                    text: 'Day'
                }
            },
            y: {
                display: true,
                title: {
                    display: true,
                    text: 'Value'
                }
            }
        }
    }
};

window.onload = function() {
    var ctx = document.getElementById('canvas').getContext('2d');
    window.myLine = new Chart(ctx, config);
};

var source = new EventSource("/devices/stream");
source.onmessage = function (event) {
    var incoming = JSON.parse(event.data);
    console.log(incoming)

    if (config.data.datasets.length > 0) {
        xMax = xMax +5;
        config.data.labels.push(xMax);

        config.data.datasets[0].data.push(incoming.temp);
        config.data.datasets[1].data.push(incoming.heartFrequency);
        config.data.datasets[2].data.push(incoming.ossigenazione);
        config.data.datasets[3].data.push(incoming.colesterolo);
        config.data.datasets[4].data.push(incoming.pressione);
        config.data.datasets[5].data.push(incoming.pressione_due);

        window.myLine.update();
    }

    tr_temp = "<tr role=\"row\">\n" +
        "            <td role=\"cell\" data-label=\"Device Name\">A</td>\n" +
        "            <td role=\"cell\" data-label=\"Heart Frequency\">B</td>\n" +
        "            <td role=\"cell\" data-label=\"Temperature\">C</td>\n" +
        "            <td role=\"cell\" data-label=\"Ossigenazione\">D</td>\n" +
        "            <td role=\"cell\" data-label=\"Colesterolo\">E</td>\n" +
        "            <td role=\"cell\" data-label=\"Pressione Massima\">F</td>\n" +
        "            <td role=\"cell\" data-label=\"Pressione Minima\">F</td>\n" +
        "            <td role=\"cell\" data-label=\"Reading\">G</td>\n" +
        "        </tr>";


    var tableBody = document.getElementById("tempBody");
    var tableBody2 = document.getElementById("tempBody2");

    var row = document.createElement("tr");
    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.deviceName);
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.temp + "°");
    /*if(incoming.temp > 36){
        //alert("You have contracted FEVER")
    }*/
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.heartFrequency + " bpm");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.ossigenazione + " %");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.colesterolo + " mg/dl");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.pressione + " mmHg");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(incoming.pressione_due + " mmHg");
    cell.appendChild(cellText);
    row.appendChild(cell);

    var cell = document.createElement("td")
    var cellText = document.createTextNode(new Date());
    cell.appendChild(cellText);
    row.appendChild(cell);

    tableBody.appendChild(row);
};