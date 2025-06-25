package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskReminderApp extends JFrame {
    private final TaskManager taskManager = new TaskManager();
    private final DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private final JList<Task> taskJList = new JList<>(taskListModel);

    public TaskReminderApp() {
        setTitle("Task Reminder App");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField titleField = new JTextField();
        JTextField dueDateField = new JTextField();
        JButton addButton = new JButton("Add Task");

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        inputPanel.add(dueDateField);
        inputPanel.add(addButton);

       
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton markDoneButton = new JButton("Mark Completed");
        JButton deleteButton = new JButton("Delete Task");
        JButton sortButton = new JButton("Sort by Date");
        buttonPanel.add(markDoneButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);

       
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskJList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

       
        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String dueDate = dueDateField.getText().trim();
            if (title.isEmpty() || dueDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both title and due date.");
                return;
            }
            Task task = new Task(title, dueDate);
            taskManager.addTask(task);
            updateTaskList();
            titleField.setText("");
            dueDateField.setText("");
        });
