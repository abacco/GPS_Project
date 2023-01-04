function sendRequest(problem) {
    req = new XMLHttpRequest()

    req.onload = function () {
        clear();
        const responseArray = JSON.parse(this.responseText);

        let element = document.getElementById("responseArea");

        for (let i in responseArray) {
            let node = document.createTextNode(responseArray[i])

            element.append(node)
        }
    }

    req.open("POST", "/ChatBot/getJsonSolution", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("problem="+problem);
}

function clear() {
    let element = document.getElementById("responseArea");
    element.innerHTML = "";
}