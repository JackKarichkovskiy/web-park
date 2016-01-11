/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
minCounter = 2;
counter = 2;
function addTask(divName) {
    var task1 = document.getElementById('task1');
    var newp = task1.cloneNode(true);
    newp.id = "task" + (counter);
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
