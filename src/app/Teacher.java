package education;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private String subject;
    private List<Course> courses = new ArrayList<>();

    public Teacher(String name, int age, String subject, String username, String password) {
        super(name, age, username, password);
        this.subject = subject;
    }
    public Teacher(String name, int age, String username, String password) {
        super(name, age, username, password);
        this.subject = "";
    }


    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String getInfo() {
        return "Teacher - Name: " + name + ", Age: " + age + ", Subject: " + subject;
    }
    public String toDataString() {
        return name + "," + age + "," + username + "," + password + "," + subject;
    }

    public static Teacher fromDataString(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int age = Integer.parseInt(parts[1]);
        String username = parts[2];
        String password = parts[3];
        String subject = parts.length > 4 ? parts[4] : ""; 
        return new Teacher(name, age, subject, username, password);
    }

}