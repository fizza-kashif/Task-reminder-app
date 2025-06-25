package src;

import java.util.ArrayList;
import java.util.Arraylist;
import java.util.Comparator;

public class TaskManager {

    private final ArrayList<Task> taskList= new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

}