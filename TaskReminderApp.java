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

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(15);

        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dueDateField = new JTextField(15);

        JButton addButton = new JButton("Add Task");
        addButton.setPreferredSize(new Dimension(150, 30)); 

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(titleField,gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(dueDateLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(dueDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Top and bottom margin

        JButton markDoneButton = new JButton("Mark Completed");
        JButton deleteButton = new JButton("Delete Task");
        JButton sortButton = new JButton("Sort by Date");

        Dimension buttonSize = new Dimension(160, 30);
        markDoneButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        sortButton.setPreferredSize(buttonSize);

        buttonPanel.add(markDoneButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);

        JScrollPane scrollPane = new JScrollPane(taskJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Task List"));

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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

    
        markDoneButton.addActionListener(e -> {
            Task selectedTask = taskJList.getSelectedValue();
            if (selectedTask != null) {
                selectedTask.setCompleted(true);
                updateTaskList();
            }
        });

        deleteButton.addActionListener(e -> {
            Task selectedTask = taskJList.getSelectedValue();
            if (selectedTask != null) {
                taskManager.removeTask(selectedTask);
                updateTaskList();
            }
        });

         sortButton.addActionListener(e -> {
            taskManager.sortByDueDate();
            updateTaskList();
        });

        setVisible(true);
    }

    private void updateTaskList() {
        taskListModel.clear();
        ArrayList<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskListModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskReminderApp::new);
    }
}
