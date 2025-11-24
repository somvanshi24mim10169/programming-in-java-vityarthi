# Smart Student Result Management System

A comprehensive Java-based desktop application for managing student academic records with automated grade calculation and result generation.

## Features

- **Student Management**: Add, update, delete, and search students
- **Academic Operations**: Enter subject marks, auto-calculate grades
- **Data Management**: File-based storage using Java Serialization
- **User-Friendly GUI**: Built with Java Swing
- **Automated Calculations**: Total marks, percentage, and grade calculation

## Installation

### Prerequisites
- Java JDK 8 or higher
- VS Code (recommended) or any Java IDE

### Steps
1. Clone the repository:
```bash
git clone https://github.com/your-username/StudentResultSystem.git
cd StudentResultSystem
```

### Running the Application
VS Code
Open src/Main.java

Click "Run" button or press Ctrl+F5

Terminal
```
# Compile
javac -d bin src/*.java

# Run
java -cp bin Main
```

Project Structure
```
StudentResultSystem/
├── src/
│   ├── Main.java
│   ├── Dashboard.java
│   ├── Student.java
│   ├── ResultManager.java
│   ├── FileHandler.java
│   ├── AddStudentDialog.java
│   ├── ViewStudentsDialog.java
│   ├── EnterMarksDialog.java
│   └── SearchStudentDialog.java
├── data/
│   └── students.dat
├── screenshots/
└── README.md
```

### Usage
Add Students: Use "Add New Student" to register students

Enter Marks: Input subject-wise marks (0-100)

View Results: Automatic calculation of total, percentage, and grade

Search: Find students by roll number

Manage: Update or delete student records

### Sample Test Data
Students:

Roll No: 101, Name: John Smith, Class: 10A

Roll No: 102, Name: Emma Wilson, Class: 10B

Marks:

Mathematics: 85, Science: 92, English: 78

Mathematics: 95, Science: 88, English: 91

### Grading System
A+: 90-100%

A: 80-89%

B+: 70-79%

B: 60-69%

C: 50-59%

D: 40-49%

F: Below 40%

### Technologies Used
Java SE

Java Swing

File I/O & Serialization

Object-Oriented Programming

### Development
Clean, commented code

Modular design

Input validation

Error handling
