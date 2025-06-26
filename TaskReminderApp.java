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
