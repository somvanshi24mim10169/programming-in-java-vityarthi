import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Dashboard extends JFrame {
    private ResultManager resultManager;
    
    public Dashboard() {
        this.resultManager = new ResultManager();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Smart Student Result Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 245, 255));
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel with buttons
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Smart Student Result Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Complete Student Academic Management Solution", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 245, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Create feature buttons
        JButton addStudentBtn = createFeatureButton("Add New Student", "➕", 
            "Register new student with personal details");
        JButton enterMarksBtn = createFeatureButton("Enter Marks", "📝", 
            "Enter marks for student subjects");
        JButton viewStudentsBtn = createFeatureButton("View All Students", "👥", 
            "Display all registered students");
        JButton searchStudentBtn = createFeatureButton("Search Student", "🔍", 
            "Find student by roll number");
        JButton updateStudentBtn = createFeatureButton("Update Student", "✏️", 
            "Modify student information");
        JButton deleteStudentBtn = createFeatureButton("Delete Student", "🗑️", 
            "Remove student record");
        
        // Add action listeners
        addStudentBtn.addActionListener(e -> new AddStudentDialog(this, resultManager).setVisible(true));
        enterMarksBtn.addActionListener(e -> new EnterMarksDialog(this, resultManager).setVisible(true));
        viewStudentsBtn.addActionListener(e -> new ViewStudentsDialog(this, resultManager).setVisible(true));
        searchStudentBtn.addActionListener(e -> new SearchStudentDialog(this, resultManager).setVisible(true));
        updateStudentBtn.addActionListener(e -> showUpdateStudentDialog());
        deleteStudentBtn.addActionListener(e -> showDeleteStudentDialog());
        
        // Add buttons to panel
        contentPanel.add(addStudentBtn, gbc);
        contentPanel.add(enterMarksBtn, gbc);
        contentPanel.add(viewStudentsBtn, gbc);
        contentPanel.add(searchStudentBtn, gbc);
        contentPanel.add(updateStudentBtn, gbc);
        contentPanel.add(deleteStudentBtn, gbc);
        
        return contentPanel;
    }
    
    private JButton createFeatureButton(String text, String icon, String tooltip) {
        JButton button = new JButton(icon + "  " + text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 2),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 100, 150));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
    
    private void showUpdateStudentDialog() {
        String rollNo = JOptionPane.showInputDialog(this, "Enter Roll Number to Update:");
        if (rollNo != null && !rollNo.trim().isEmpty()) {
            Student student = resultManager.getStudentByRollNo(rollNo.trim());
            if (student != null) {
                new AddStudentDialog(this, resultManager, student).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showDeleteStudentDialog() {
        String rollNo = JOptionPane.showInputDialog(this, "Enter Roll Number to Delete:");
        if (rollNo != null && !rollNo.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete student with Roll No: " + rollNo + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (resultManager.deleteStudent(rollNo.trim())) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}