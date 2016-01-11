/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
minCounter = 2;
counter = 2;
function addTask(divName) {
    var newp = document.createElement('p');
    newp.id = "task"+counter;
    newp.innerHTML = "<span> " + (counter) + ".</span> \
                <select name=\"select1[]\">\
                    <option>a</option>\
                    <option>b</option>\
                    <option>c</option>\
                </select>\
                <select name=\"select2[]\">\
                    <option>a</option>\
                    <option>b</option>\
                    <option>c</option>\
                </select>";
    document.getElementById(divName).appendChild(newp);
    counter++;
}

function removeTask(divName) {
    if (counter > minCounter) {
        var parent = document.getElementById(divName);
        var child = document.getElementById("task" + (counter - 1));
        parent.removeChild(child);
        counter--;
    }
}
