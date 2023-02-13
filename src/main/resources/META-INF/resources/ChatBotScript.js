function sendRequest(problem) {
    req = new XMLHttpRequest()

    req.onload = function () {
        clear();
        const responseArray = JSON.parse(this.responseText);

        let element = document.getElementById("responseArea");
        element.style.display="inline";

        let clearButton = document.createElement("button")
        clearButton.innerHTML="Close"
        clearButton.addEventListener("click", function () {
            clear()
        })
        element.append(clearButton)

        for (let i in responseArray) {
            let lineBreak = document.createElement("br");
            let num =  Number(i) + 1

            let node = document.createTextNode(num + ". " + responseArray[i] )

            element.append(node)
            element.append(lineBreak)
        }
    }

    req.open("POST", "/ChatBot/getJsonSolution", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("problem="+problem);
}

function clear() {
    let element = document.getElementById("responseArea");
    element.innerHTML = "";
    element.style.display="none";
}