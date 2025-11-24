import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String rollNo;
    private String name;
    private String className;
    private Map<String, Integer> subjects; // Subject -> Marks
    private double totalMarks;
    private double percentage;
    private String grade;
    
    public Student(String rollNo, String name, String className) {
        this.rollNo = rollNo;
        this.name = name;
        this.className = className;
        this.subjects = new HashMap<>();
        calculateResults();
    }
    
    // Getters and Setters
    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getClassName() { return className; }
    public Map<String, Integer> getSubjects() { return subjects; }
    public double getTotalMarks() { return totalMarks; }
    public double getPercentage() { return percentage; }
    public String getGrade() { return grade; }
    
    public void setName(String name) { this.name = name; }
    public void setClassName(String className) { this.className = className; }
    
    
    public void addSubject(String subject, int marks) {
        subjects.put(subject, marks);
        calculateResults();
    }
    
    
    public void updateMarks(String subject, int marks) {
        if (subjects.containsKey(subject)) {
            subjects.put(subject, marks);
            calculateResults();
        }
    }
    
    
    private void calculateResults() {
        totalMarks = subjects.values().stream().mapToInt(Integer::intValue).sum();
        int totalPossibleMarks = subjects.size() * 100;
        percentage = totalPossibleMarks > 0 ? (totalMarks / totalPossibleMarks) * 100 : 0;
        grade = calculateGrade(percentage);
    }
    
    
    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B+";
        else if (percentage >= 60) return "B";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D";
        else return "F";
    }
    
    @Override
    public String toString() {
        return String.format("Roll No: %s, Name: %s, Class: %s, Total: %.2f, Percentage: %.2f%%, Grade: %s",
                rollNo, name, className, totalMarks, percentage, grade);
    }
}