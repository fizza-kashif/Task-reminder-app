package src;
import javax.swing.*;
import java.awt.*;

public class TaskReminderApp extends JFrame {
    private final TaskManager taskManager = new TaskManager();
    private final DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private final JList<Task> taskJList = new JList<>(taskListModel);

    public TaskReminderApp() {
        setTitle("Task Reminder App");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(15);
        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dueDateField = new JTextField(15);
        JButton addButton = new JButton("Add Task");
        addButton.setPreferredSize(new Dimension(150, 30));

          GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0; gbc1.gridy = 0;
        gbc1.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(titleLabel, gbc1);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1; gbc2.gridy = 0;
        gbc2.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(titleField, gbc2);

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0; gbc3.gridy = 1;
        gbc3.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(dueDateLabel, gbc3);

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 1; gbc4.gridy = 1;
        gbc4.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(dueDateField, gbc4);

        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 0; gbc5.gridy = 2;
        gbc5.gridwidth = 2;
        gbc5.insets = new Insets(10, 5, 5, 5);
        gbc5.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc5);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton markDoneButton = new JButton("Mark Done");
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

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

       
        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
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
        Task[] tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskListModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskReminderApp::new);
    }
}
