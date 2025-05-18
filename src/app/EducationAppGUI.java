package education;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class EducationAppGUI extends JFrame {
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private User currentUser;

    // Custom colors
    private final Color PRIMARY_COLOR = new Color(0, 102, 204);
    private final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private final Color ACCENT_COLOR = new Color(255, 153, 0);

    public EducationAppGUI() {

        students = Database.loadStudents();      
        teachers = Database.loadTeachers(); 
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Database.saveStudents(students);
            Database.saveTeachers(teachers);
        }));

        initLoginScreen();
               
        try {
            students = Database.loadStudents();
            teachers = Database.loadTeachers();
        } catch (Exception ex) {
            // Handle error, e.g., show a dialog or log
            // For example, you could show an error message:
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Loading Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Log the exception for debugging
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Database.saveStudents(students);
            Database.saveTeachers(teachers);
        }));

        initLoginScreen();

    }


    private void initLoginScreen() {
        setTitle("Education App Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SECONDARY_COLOR);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(SECONDARY_COLOR);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel titleLabel = new JLabel("Education App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JComboBox<String> userTypeCombo = new JComboBox<>(new String[]{"Student", "Teacher"});
        
      
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color fieldBg = Color.WHITE;
        
        usernameField.setBackground(fieldBg);
        passwordField.setBackground(fieldBg);
        userTypeCombo.setBackground(fieldBg);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel typeLabel = new JLabel("User Type:");
        typeLabel.setFont(labelFont);
        loginPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(userTypeCombo, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(120, 35));
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String userType = (String) userTypeCombo.getSelectedItem();

            if (authenticateUser(username, password, userType)) {
                openUserSpecificGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private boolean authenticateUser(String username, String password, String userType) {
        if (userType.equals("Student")) {
            for (Student student : students) {
                if (student.getUsername().equals(username) && student.checkPassword(password)) {
                    currentUser = student;
                    return true;
                }
            }
        } else {
            for (Teacher teacher : teachers) {
            	if (teacher.getUsername().equals(username) && teacher.getPassword().equals(password)) {
                    currentUser = teacher;
                    return true;
                }
            }
        }
        return false;
    }

    private void openUserSpecificGUI() {
        dispose();
        if (currentUser instanceof Student) {
            openStudentGUI((Student) currentUser);
        } else if (currentUser instanceof Teacher) {
            openTeacherGUI((Teacher) currentUser);
        }
    }

    private void openStudentGUI(Student student) {
        JFrame studentFrame = new JFrame("Student Dashboard - " + student.name);
        studentFrame.setSize(800, 600);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.getContentPane().setBackground(SECONDARY_COLOR);
    
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(SECONDARY_COLOR);
    
 
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Student Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
    
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("Name: " + student.name);
        JLabel ageLabel = new JLabel("Age: " + student.age);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        infoPanel.add(nameLabel);
        infoPanel.add(ageLabel);
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(SECONDARY_COLOR);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel gradesPanel = new JPanel(new BorderLayout());
        gradesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea performanceArea = new JTextArea();
        performanceArea.setEditable(false);
        performanceArea.setFont(new Font("Arial", Font.PLAIN, 14));
        performanceArea.setBackground(Color.WHITE);
        
        
        Map<Course, Double> grades = student.getGrades();
        if (!grades.isEmpty()) {
            performanceArea.append("Your Grades:\n\n");
            double totalQualityPoints = 0;
            int totalCredits = 0;
            
            for (Map.Entry<Course, Double> entry : grades.entrySet()) {
                double grade = entry.getValue();
                double gradePoints = convertToGradePoints(grade);
                totalQualityPoints += gradePoints;
                totalCredits++;
                
                performanceArea.append(String.format(" • %s: %.1f%% (Grade Points: %.1f)\n", 
                    entry.getKey(), grade, gradePoints));
            }
            
            double gpa = totalQualityPoints / totalCredits;
            performanceArea.append(String.format("\nGPA: %.2f / 4.0\n", gpa));
            performanceArea.append("GPA Scale: A (4.0), B (3.0), C (2.0), D (1.0), F (0.0)");
        } else {
            performanceArea.setText("No grades recorded yet.");
        }
        
        JScrollPane gradesScrollPane = new JScrollPane(performanceArea);
        gradesPanel.add(gradesScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Grades", gradesPanel);
    
        JPanel coursesPanel = new JPanel(new BorderLayout());
        DefaultTableModel coursesModel = new DefaultTableModel(new String[]{"Course Name", "Status"}, 0);
        JTable coursesTable = new JTable(coursesModel);
        
        for (Course course : student.getCourses()) {
            String status = grades.containsKey(course) ? "Completed" : "In Progress";
            coursesModel.addRow(new Object[]{course.toString(), status});
        }
        
        JScrollPane coursesScrollPane = new JScrollPane(coursesTable);
        coursesPanel.add(coursesScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Courses", coursesPanel);
    

        JPanel chartPanel = new JPanel(new BorderLayout());
        if (!grades.isEmpty()) {
            JLabel chartLabel = new JLabel("Performance Overview", SwingConstants.CENTER);
            chartLabel.setFont(new Font("Arial", Font.BOLD, 16));
    
            JPanel barPanel = new JPanel(new GridLayout(grades.size(), 1, 5, 5));
            for (Map.Entry<Course, Double> entry : grades.entrySet()) {
                JProgressBar bar = new JProgressBar(0, 100);
                bar.setValue(entry.getValue().intValue());
                bar.setString(entry.getKey() + ": " + entry.getValue() + "%");
                bar.setStringPainted(true);
                bar.setFont(new Font("Arial", Font.PLAIN, 12));
                barPanel.add(bar);
            }
            chartPanel.add(chartLabel, BorderLayout.NORTH);
            chartPanel.add(barPanel, BorderLayout.CENTER);
        } else {
            chartPanel.add(new JLabel("No performance data available", SwingConstants.CENTER));
        }
        tabbedPane.addTab("Performance Chart", chartPanel);
    
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(ACCENT_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            studentFrame.dispose();
            new EducationAppGUI();
        });
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.add(logoutButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    
        studentFrame.add(mainPanel);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setVisible(true);
    }

    private double convertToGradePoints(double percentageGrade) {
        if (percentageGrade >= 90) return 4.0;
        if (percentageGrade >= 80) return 3.0;
        if (percentageGrade >= 70) return 2.0;
        if (percentageGrade >= 60) return 1.0;
        return 0.0;
    }

    private void openTeacherGUI(Teacher teacher) {
        JFrame teacherFrame = new JFrame("Teacher Dashboard - " + teacher.name);
        teacherFrame.setSize(1000, 700);
        teacherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teacherFrame.getContentPane().setBackground(SECONDARY_COLOR);
    
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(SECONDARY_COLOR);
    
       
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(teacher.name + " - Teacher Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(new JLabel("Subject: " + teacher.getSubject()));
        infoPanel.add(new JLabel("Courses: " + teacher.getCourses().size()));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
    
        
        JPanel gradePanel = createGradeManagementPanel(teacher);
        tabbedPane.addTab("Grade Management", gradePanel);
    
        
        JPanel statsPanel = createCourseStatsPanel(teacher);
        tabbedPane.addTab("Course Statistics", statsPanel);
    
       
        JPanel rosterPanel = createStudentRosterPanel(teacher);
        tabbedPane.addTab("Student Roster", rosterPanel);
    
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(ACCENT_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            teacherFrame.dispose();
            new EducationAppGUI();
        });
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.add(logoutButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    
        teacherFrame.add(mainPanel);
        teacherFrame.setLocationRelativeTo(null);
        teacherFrame.setVisible(true);
    }
    
    private JPanel createGradeManagementPanel(Teacher teacher) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JComboBox<Course> courseCombo = new JComboBox<>();
        teacher.getCourses().forEach(courseCombo::addItem);
        
        selectionPanel.add(new JLabel("Select Course:"));
        selectionPanel.add(courseCombo);
    
        String[] columnNames = {"Student", "Current Grade", "Letter Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable gradesTable = new JTable(tableModel);
        gradesTable.setRowHeight(25);
        
        courseCombo.addActionListener(e -> updateGradeTable(tableModel, (Course)courseCombo.getSelectedItem()));
    
        JPanel gradeEntryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField gradeField = new JTextField(5);
        JButton updateButton = new JButton("Update Grade");
        updateButton.setBackground(PRIMARY_COLOR);
        updateButton.setForeground(Color.WHITE);
        
        updateButton.addActionListener(e -> {
            int row = gradesTable.getSelectedRow();
            if (row >= 0) {
                String studentName = (String)tableModel.getValueAt(row, 0);
                try {
                    double newGrade = Double.parseDouble(gradeField.getText());
                    Student student = findStudentByName(studentName);
                    Course course = (Course)courseCombo.getSelectedItem();
                    
                    if (student != null) {
                        student.addGrade(course, newGrade);
                        updateGradeTable(tableModel, course);
                        gradeField.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        gradeEntryPanel.add(new JLabel("New Grade:"));
        gradeEntryPanel.add(gradeField);
        gradeEntryPanel.add(updateButton);
    
        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);
        panel.add(gradeEntryPanel, BorderLayout.SOUTH);
    
        if (courseCombo.getItemCount() > 0) {
            updateGradeTable(tableModel, (Course)courseCombo.getSelectedItem());
        }
    
        return panel;
    }
    
    private void updateGradeTable(DefaultTableModel model, Course course) {
        model.setRowCount(0);
        students.stream()
            .filter(s -> s.getCourses().contains(course))
            .forEach(s -> {
                Double grade = s.getGrades().get(course);
                String letterGrade = grade != null ? convertToLetterGrade(grade) : "-";
                model.addRow(new Object[]{
                    s.name,
                    grade != null ? String.format("%.1f", grade) : "-",
                    letterGrade
                });
            });
    }
    
    private String convertToLetterGrade(double grade) {
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }
    
    private JPanel createCourseStatsPanel(Teacher teacher) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        DefaultTableModel statsModel = new DefaultTableModel(new String[]{"Course", "Students", "Avg Grade"}, 0);
        JTable statsTable = new JTable(statsModel);
        
        teacher.getCourses().forEach(course -> {
            long studentCount = students.stream()
                .filter(s -> s.getCourses().contains(course))
                .count();
                
            double avgGrade = students.stream()
                .filter(s -> s.getCourses().contains(course))
                .mapToDouble(s -> s.getGrades().getOrDefault(course, 0.0))
                .average()
                .orElse(0.0);
                
            statsModel.addRow(new Object[]{
                course,
                studentCount,
                avgGrade > 0 ? String.format("%.1f", avgGrade) : "N/A"
            });
        });
    
        panel.add(new JScrollPane(statsTable), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStudentRosterPanel(Teacher teacher) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        DefaultTableModel rosterModel = new DefaultTableModel(new String[]{"Student", "Courses", "Avg Grade"}, 0);
        JTable rosterTable = new JTable(rosterModel);
        
        students.stream()
            .filter(s -> s.getCourses().stream().anyMatch(c -> teacher.getCourses().contains(c)))
            .forEach(s -> {
                long commonCourses = s.getCourses().stream()
                    .filter(c -> teacher.getCourses().contains(c))
                    .count();
                    
                double avgGrade = s.getGrades().entrySet().stream()
                    .filter(e -> teacher.getCourses().contains(e.getKey()))
                    .mapToDouble(Map.Entry::getValue)
                    .average()
                    .orElse(0.0);
                    
                rosterModel.addRow(new Object[]{
                    s.name,
                    commonCourses,
                    avgGrade > 0 ? String.format("%.1f", avgGrade) : "N/A"
                });
            });
    
        panel.add(new JScrollPane(rosterTable), BorderLayout.CENTER);
        return panel;
    }
    
    private Student findStudentByName(String name) {
        return students.stream()
            .filter(s -> s.name.equals(name))
            .findFirst()
            .orElse(null);
    }
    public void addStudent(String name, int age, String username, String password) {
        Student student = new Student(name, age, username, password);
        students.add(student);
    }

    public void addTeacher(String name, int age, String subject, String username, String password) {
        Teacher teacher = new Teacher(name, age, subject, username, password);
        teachers.add(teacher);
    }
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            EducationAppGUI app = new EducationAppGUI();

            app.addStudent("John Doe", 20, "student1", "password");
            app.addStudent("Jane Smith", 22, "student2", "password");
            app.addStudent("Mike Johnson", 21, "student3", "password");

            Course mathCourse = new Course("Math");
            Course scienceCourse = new Course("Science");
            Course physicsCourse = new Course("Physics");

            Student student1 = app.students.get(0);
            student1.enroll(mathCourse);
            student1.enroll(scienceCourse);
            student1.addGrade(mathCourse, 85.5);
            student1.addGrade(scienceCourse, 78.0);

            Student student2 = app.students.get(1);
            student2.enroll(scienceCourse);
            student2.enroll(physicsCourse);
            student2.addGrade(scienceCourse, 92.0);
            student2.addGrade(physicsCourse, 88.5);

            Student student3 = app.students.get(2);
            student3.enroll(mathCourse);
            student3.enroll(physicsCourse);
            student3.addGrade(mathCourse, 90.0);
            student3.addGrade(physicsCourse, 85.0);

            app.addTeacher("Mr. Brown", 35, "Mathematics", "teacher1", "password");
            app.addTeacher("Ms. Garcia", 40, "Science", "teacher2", "password");

            Teacher teacher1 = app.teachers.get(0);
            teacher1.addCourse(mathCourse);
            teacher1.addCourse(physicsCourse);

            Teacher teacher2 = app.teachers.get(1);
            teacher2.addCourse(scienceCourse);
            teacher2.addCourse(physicsCourse);
        });
    }

}

