package education;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<Course> courses = new ArrayList<>();
    private Map<Course, Double> grades = new HashMap<>();

    public Student(String name, int age, String username, String password) {
        super(name, age, username, password);
    }

    public void enroll(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void addGrade(Course course, double grade) {
        // Ensure the student is enrolled in the course before adding a grade
        if (!courses.contains(course)) {
            enroll(course);
        }
        grades.put(course, grade);
    }

    public Map<Course, Double> getGrades() {
        return grades;
    }
  
    @Override
    public String getInfo() {
        return "Student - Name: " + name + ", Age: " + age + ", Courses: " + courses;
    }
    public String toDataString() {
        return name + "," + age + "," + username + "," + password;
    }
    public static Student fromDataString(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int age = Integer.parseInt(parts[1]);
        String username = parts[2];
        String password = parts[3];

        Student s = new Student(username, age, username, password);
        s.name = name;
        return s;
    }

    public List<Course> getCourses() {
        return courses;
    }
    
}

