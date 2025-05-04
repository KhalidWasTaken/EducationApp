// Student.java
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<String> courses = new ArrayList<>();

    public Student(String name, int age) {
        super(name, age);
    }

    public void enroll(String course) {
        courses.add(course);
    }

    @Override
    public String getInfo() {
        return "Student - Name: " + name + ", Age: " + age + ", Courses: " + courses;
    }
}
    