package ToDo;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.awt.Color;

public class taskReminderGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField titleField;
	private JTextField dateField;
	private DefaultListModel<Task> listModel;
	private JList<Task> taskList;
	private TaskManager taskManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				taskReminderGUI frame = new taskReminderGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public taskReminderGUI() {
		setTitle("Task Reminder App");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int choice = JOptionPane.showConfirmDialog(
		            null,
		            "Do you really want to exit?",
		            "Exit Confirmation",
		            JOptionPane.YES_NO_OPTION
		        );
		        if (choice == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
		});

		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		taskManager = new TaskManager();

		// Colors
		Color red = new Color(211, 47, 47);
		Color white = Color.WHITE;
		Color grayBg = new Color(240, 240, 240);
		Color lightGray = new Color(230, 230, 230);

		contentPane.setBackground(grayBg);

		// Labels and Fields
		JLabel lblTitle = new JLabel("Task Title:");
		lblTitle.setBounds(20, 20, 100, 25);
		contentPane.add(lblTitle);

		titleField = new JTextField();
		titleField.setBounds(100, 20, 150, 25);
		contentPane.add(titleField);

		JLabel lblDate = new JLabel("Due Date (yyyy-mm-dd):");
		lblDate.setBounds(270, 20, 170, 25);
		contentPane.add(lblDate);

		dateField = new JTextField();
		dateField.setBounds(430, 20, 80, 25);
		contentPane.add(dateField);

		// Buttons
		JButton addButton = new JButton("Add Task");
		addButton.setBounds(20, 60, 100, 25);
		contentPane.add(addButton);

		JButton deleteButton = new JButton("Delete Task");
		deleteButton.setBounds(130, 60, 120, 25);
		contentPane.add(deleteButton);

		JButton markDoneButton = new JButton("Mark Done");
		markDoneButton.setBounds(260, 60, 120, 25);
		contentPane.add(markDoneButton);

		JButton sortButton = new JButton("Sort by Date");
		sortButton.setBounds(390, 60, 120, 25);
		contentPane.add(sortButton);

		// Button styling
		JButton[] buttons = { addButton, deleteButton, markDoneButton, sortButton };
		for (JButton btn : buttons) {
			btn.setBackground(red);
			btn.setForeground(white);
			btn.setFocusPainted(false);
		}

		// Task list
		listModel = new DefaultListModel<>();
		taskList = new JList<>(listModel);
		taskList.setBackground(lightGray);
		JScrollPane scrollPane = new JScrollPane(taskList);
		scrollPane.setBounds(13, 100, 490, 240);
		contentPane.add(scrollPane);

		// Actions
		addButton.addActionListener(e -> {
			String title = titleField.getText();
			String dateText = dateField.getText();
			try {
				if (title.isEmpty() || dateText.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fields cannot be empty.");
					return;
				}
				LocalDate dueDate = LocalDate.parse(dateText);
				Task newTask = new Task(title, dueDate);
				taskManager.addTask(newTask);
				refreshTaskList();
				titleField.setText("");
				dateField.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-mm-dd.");
			}
		});

		deleteButton.addActionListener(e -> {
			int selectedIndex = taskList.getSelectedIndex();
			if (selectedIndex != -1) {
				taskManager.deleteTask(selectedIndex);
				refreshTaskList();
			}
		});

		markDoneButton.addActionListener(e -> {
			int selectedIndex = taskList.getSelectedIndex();
			if (selectedIndex != -1) {
				taskManager.markTaskCompleted(selectedIndex);
				refreshTaskList();
			}
		});

		sortButton.addActionListener(e -> {
			taskManager.sortTasksByDueDate();
			refreshTaskList();
		});
	}

	private void refreshTaskList() {
		listModel.clear();
		List<Task> tasks = taskManager.getAllTasks();
		for (Task task : tasks) {
			listModel.addElement(task);
		}
	}
}
