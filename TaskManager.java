package src;


public class TaskManager {
	private Task[] taskList = new Task[100]; 
	private int size = 0; 

    	public void addTask(Task task) {
    	    if (size < taskList.length) {
    	        taskList[size] = task;
    	        size++;
    	    } else {
    	        System.out.println("Task list is full!");
    	    }
    	}


public void removeTask(Task task) {
    for (int i = 0; i < size; i++) {
        if (taskList[i].equals(task)) {
            for (int j = i; j < size - 1; j++) {
                taskList[j] = taskList[j + 1];
            }
            taskList[size - 1] = null;
            size--;
            break;
        }
    }
}

public void sortByDueDate() {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            String date1 = taskList[j].getDueDate();
            String date2 = taskList[j + 1].getDueDate();

            if (date1.compareTo(date2) > 0) {
                Task temp = taskList[j];
                taskList[j] = taskList[j + 1];
                taskList[j + 1] = temp;
            }
        }
    }
}
public Task[] getTasks() {
    Task[] currentTasks = new Task[size];
    for (int i = 0; i < size; i++) {
        currentTasks[i] = taskList[i];
    }
    return currentTasks;
}


    
}
