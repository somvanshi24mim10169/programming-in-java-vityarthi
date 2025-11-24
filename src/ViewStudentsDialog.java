import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewStudentsDialog extends JDialog {
    private ResultManager resultManager;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    public ViewStudentsDialog(Dashboard parent, ResultManager resultManager) {
        super(parent, true);
        this.resultManager = resultManager;
        
        setTitle("View All Students");
        setSize(800, 500);
        setLocationRelativeTo(parent);
        
        initializeUI();
        loadStudentData();
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create table model
        String[] columns = {"Roll No", "Name", "Class", "Total Marks", "Percentage", "Grade", "Subjects Count"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true);
        studentTable.setAutoCreateRowSorter(true);
        studentTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Roll No
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Class
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Total Marks
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Percentage
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(60);  // Grade
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Subjects Count
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");
        
        refreshButton.addActionListener(e -> loadStudentData());
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        
        mainPanel.add(new JLabel("All Students (" + resultManager.getAllStudents().size() + " students)"), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadStudentData() {
        tableModel.setRowCount(0); // Clear existing data
        
        List<Student> students = resultManager.getAllStudents();
        for (Student student : students) {
            Object[] row = {
                student.getRollNo(),
                student.getName(),
                student.getClassName(),
                String.format("%.2f", student.getTotalMarks()),
                String.format("%.2f%%", student.getPercentage()),
                student.getGrade(),
                student.getSubjects().size()
            };
            tableModel.addRow(row);
        }
        
        // Update title with count
        Container contentPane = getContentPane();
        if (contentPane instanceof JPanel) {
            Component[] components = ((JPanel) contentPane).getComponents();
            if (components.length > 0 && components[0] instanceof JLabel) {
                ((JLabel) components[0]).setText("All Students (" + students.size() + " students)");
            }
        }
    }
}