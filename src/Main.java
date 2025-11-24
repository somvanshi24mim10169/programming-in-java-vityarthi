import javax.swing.*;

/**
 * Main class to launch the Student Result Management System
 */
public class Main {
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
        
        // Create and display the dashboard on EDT
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            
            // Show welcome message
            JOptionPane.showMessageDialog(dashboard,
                "Welcome to Smart Student Result Management System!\n\n" +
                "Features:\n" +
                "• Add/Update/Delete Students\n" +
                "• Enter Subject Marks\n" +
                "• Automatic Grade Calculation\n" +
                "• View All Students\n" +
                "• Search Student Records\n" +
                "• File-based Data Storage",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}