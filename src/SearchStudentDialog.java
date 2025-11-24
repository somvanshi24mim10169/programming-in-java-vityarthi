import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchStudentDialog extends JDialog {
    private ResultManager resultManager;
    
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton;
    
    public SearchStudentDialog(Dashboard parent, ResultManager resultManager) {
        super(parent, true);
        this.resultManager = resultManager;
        
        setTitle("Search Student");
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initializeUI();
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Student by Roll Number"));
        
        searchPanel.add(new JLabel("Roll Number:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        searchPanel.add(searchButton);
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Result area
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String rollNo = searchField.getText().trim();
            if (rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(SearchStudentDialog.this, 
                    "Please enter roll number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Student student = resultManager.getStudentByRollNo(rollNo);
            if (student != null) {
                displayStudentDetails(student);
            } else {
                resultArea.setText("No student found with Roll No: " + rollNo);
            }
        }
    }
    
    private void displayStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("STUDENT DETAILS\n");
        sb.append("===============\n");
        sb.append("Roll Number : ").append(student.getRollNo()).append("\n");
        sb.append("Name        : ").append(student.getName()).append("\n");
        sb.append("Class       : ").append(student.getClassName()).append("\n\n");
        
        sb.append("ACADEMIC PERFORMANCE\n");
        sb.append("====================\n");
        
        if (!student.getSubjects().isEmpty()) {
            sb.append("Subject-wise Marks:\n");
            sb.append("-------------------\n");
            for (var entry : student.getSubjects().entrySet()) {
                sb.append(String.format("  %-15s: %3d/100\n", entry.getKey(), entry.getValue()));
            }
            sb.append("\n");
        }
        
        sb.append("FINAL RESULT\n");
        sb.append("============\n");
        sb.append(String.format("Total Marks    : %.2f/%d\n", 
            student.getTotalMarks(), student.getSubjects().size() * 100));
        sb.append(String.format("Percentage     : %.2f%%\n", student.getPercentage()));
        sb.append(String.format("Grade          : %s\n", student.getGrade()));
        
        // Add grade interpretation
        sb.append("\nGRADE INTERPRETATION\n");
        sb.append("====================\n");
        sb.append("A+ : Outstanding (90% and above)\n");
        sb.append("A  : Excellent (80% - 89%)\n");
        sb.append("B+ : Very Good (70% - 79%)\n");
        sb.append("B  : Good (60% - 69%)\n");
        sb.append("C  : Average (50% - 59%)\n");
        sb.append("D  : Below Average (40% - 49%)\n");
        sb.append("F  : Fail (Below 40%)\n");
        
        resultArea.setText(sb.toString());
    }
}