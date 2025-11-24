import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterMarksDialog extends JDialog {
    private ResultManager resultManager;
    
    private JTextField rollNoField;
    private JTextField subjectField;
    private JTextField marksField;
    private JButton searchButton;
    private JButton addMarksButton;
    private JTextArea resultArea;
    
    public EnterMarksDialog(Dashboard parent, ResultManager resultManager) {
        super(parent, true);
        this.resultManager = resultManager;
        
        setTitle("Enter Student Marks");
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initializeUI();
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Marks"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Roll Number
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Roll Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        rollNoField = new JTextField(15);
        inputPanel.add(rollNoField, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        inputPanel.add(searchButton, gbc);
        
        // Subject
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(new JLabel("Subject:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        subjectField = new JTextField(20);
        inputPanel.add(subjectField, gbc);
        
        // Marks
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(new JLabel("Marks (0-100):"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        marksField = new JTextField(20);
        inputPanel.add(marksField, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        addMarksButton = new JButton("Add Marks");
        addMarksButton.addActionListener(new AddMarksButtonListener());
        inputPanel.add(addMarksButton, gbc);
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Result area
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Student Information & Results"));
        
        resultArea = new JTextArea(10, 40);
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
            String rollNo = rollNoField.getText().trim();
            if (rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                    "Please enter roll number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Student student = resultManager.getStudentByRollNo(rollNo);
            if (student != null) {
                displayStudentInfo(student);
            } else {
                resultArea.setText("Student not found with Roll No: " + rollNo);
            }
        }
    }
    
    private class AddMarksButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String rollNo = rollNoField.getText().trim();
            String subject = subjectField.getText().trim();
            String marksStr = marksField.getText().trim();
            
            if (rollNo.isEmpty() || subject.isEmpty() || marksStr.isEmpty()) {
                JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                    "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int marks = Integer.parseInt(marksStr);
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                        "Marks must be between 0 and 100!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (resultManager.addMarks(rollNo, subject, marks)) {
                    JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                        "Marks added successfully!");
                    
                    // Refresh student info display
                    Student student = resultManager.getStudentByRollNo(rollNo);
                    if (student != null) {
                        displayStudentInfo(student);
                    }
                    
                    // Clear input fields
                    subjectField.setText("");
                    marksField.setText("");
                } else {
                    JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                        "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EnterMarksDialog.this, 
                    "Please enter valid marks!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void displayStudentInfo(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Roll No: ").append(student.getRollNo()).append("\n");
        sb.append("Name: ").append(student.getName()).append("\n");
        sb.append("Class: ").append(student.getClassName()).append("\n\n");
        sb.append("SUBJECTS AND MARKS:\n");
        sb.append("-------------------\n");
        
        for (var entry : student.getSubjects().entrySet()) {
            sb.append(String.format("%-15s: %3d/100\n", entry.getKey(), entry.getValue()));
        }
        
        sb.append("\nRESULTS:\n");
        sb.append("--------\n");
        sb.append(String.format("Total Marks  : %.2f/%d\n", student.getTotalMarks(), student.getSubjects().size() * 100));
        sb.append(String.format("Percentage   : %.2f%%\n", student.getPercentage()));
        sb.append(String.format("Grade        : %s\n", student.getGrade()));
        
        resultArea.setText(sb.toString());
    }
}