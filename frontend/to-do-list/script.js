const taskInput = document.getElementById("taskInput");
const addButton = document.getElementById("addButton");
const taskList = document.getElementById("taskList");
const taskCount = document.getElementById("taskCount");
const emptyMessage = document.getElementById("emptyMessage");


addButton.addEventListener("click", function () {

    const taskText = taskInput.value.trim();

    if (taskText === "") {
        return;
    }

    const li = document.createElement("li");

    li.classList.add("task");


    const span = document.createElement("span");

    span.textContent = taskText;


    const deleteButton = document.createElement("button");

    deleteButton.textContent = "Delete";

    deleteButton.classList.add("deleteButton");


    deleteButton.addEventListener("click", function () {

        li.remove();

        updateTaskCount();

    });


    li.appendChild(span);
    li.appendChild(deleteButton);



    taskList.appendChild(li);


 
    taskInput.value = "";


    updateTaskCount();

});



function updateTaskCount() {

    const totalTasks = taskList.children.length;

    taskCount.textContent = totalTasks + 
        (totalTasks === 1 ? " task" : " tasks");



    if (totalTasks === 0) {
        emptyMessage.style.display = "block";
    } else {
        emptyMessage.style.display = "none";
    }
}


updateTaskCount();