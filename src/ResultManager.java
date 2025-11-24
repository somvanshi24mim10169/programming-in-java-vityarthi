import java.util.ArrayList;
import java.util.List;

public class ResultManager {
    private List<Student> students;
    
    public ResultManager() {
        this.students = FileHandler.loadStudents();
    }
    
    
    public boolean addStudent(Student student) {
        // Check if roll number already exists
        if (getStudentByRollNo(student.getRollNo()) != null) {
            return false; // Roll number already exists
        }
        students.add(student);
        saveData();
        return true;
    }
    
    
    public boolean updateStudent(String rollNo, String name, String className) {
        Student student = getStudentByRollNo(rollNo);
        if (student != null) {
            student.setName(name);
            student.setClassName(className);
            saveData();
            return true;
        }
        return false;
    }
    
    
    public boolean deleteStudent(String rollNo) {
        Student student = getStudentByRollNo(rollNo);
        if (student != null) {
            students.remove(student);
            saveData();
            return true;
        }
        return false;
    }
    
    
    public Student getStudentByRollNo(String rollNo) {
        return students.stream()
                .filter(s -> s.getRollNo().equals(rollNo))
                .findFirst()
                .orElse(null);
    }
    
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    
    public boolean addMarks(String rollNo, String subject, int marks) {
        Student student = getStudentByRollNo(rollNo);
        if (student != null) {
            student.addSubject(subject, marks);
            saveData();
            return true;
        }
        return false;
    }
    
    
    private void saveData() {
        FileHandler.saveStudents(students);
    }
}