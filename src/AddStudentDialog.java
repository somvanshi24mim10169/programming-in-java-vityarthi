import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentDialog extends JDialog {
    private ResultManager resultManager;
    private Student existingStudent;
    
    private JTextField rollNoField;
    private JTextField nameField;
    private JTextField classField;
    private JButton saveButton;
    private JButton cancelButton;
    
    public AddStudentDialog(Dashboard parent, ResultManager resultManager) {
        this(parent, resultManager, null);
    }
    
    public AddStudentDialog(Dashboard parent, ResultManager resultManager, Student existingStudent) {
        super(parent, true);
        this.resultManager = resultManager;
        this.existingStudent = existingStudent;
        
        setTitle(existingStudent != null ? "Update Student" : "Add New Student");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initializeUI();
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Roll Number
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Roll Number:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        rollNoField = new JTextField(20);
        if (existingStudent != null) {
            rollNoField.setText(existingStudent.getRollNo());
            rollNoField.setEditable(false); // Cannot change roll number for updates
        }
        formPanel.add(rollNoField, gbc);
        
        // Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Full Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        if (existingStudent != null) {
            nameField.setText(existingStudent.getName());
        }
        formPanel.add(nameField, gbc);
        
        // Class
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        formPanel.add(new JLabel("Class:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        classField = new JTextField(20);
        if (existingStudent != null) {
            classField.setText(existingStudent.getClassName());
        }
        formPanel.add(classField, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton(existingStudent != null ? "Update" : "Save");
        cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(new SaveButtonListener());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String rollNo = rollNoField.getText().trim();
            String name = nameField.getText().trim();
            String className = classField.getText().trim();
            
            if (rollNo.isEmpty() || name.isEmpty() || className.isEmpty()) {
                JOptionPane.showMessageDialog(AddStudentDialog.this, 
                    "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                if (existingStudent != null) {
                    // Update existing student
                    if (resultManager.updateStudent(rollNo, name, className)) {
                        JOptionPane.showMessageDialog(AddStudentDialog.this, 
                            "Student updated successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddStudentDialog.this, 
                            "Error updating student!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Add new student
                    Student student = new Student(rollNo, name, className);
                    if (resultManager.addStudent(student)) {
                        JOptionPane.showMessageDialog(AddStudentDialog.this, 
                            "Student added successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddStudentDialog.this, 
                            "Roll number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddStudentDialog.this, 
                    "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}