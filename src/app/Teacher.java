package education;

import java.util.*;

public class Teacher extends User {
    private List<String> assignedCourses;

    public Teacher(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        this.assignedCourses = new ArrayList<>();
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course.getName())) {
            assignedCourses.add(course.getName());
        }
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    @Override
    public String toDataString() {
        StringBuilder data = new StringBuilder();
        data.append(getUsername()).append(",")
            .append(getPassword()).append(",")
            .append(getFirstName()).append(",")
            .append(getLastName());

        // Add assigned courses after a delimiter
        for (String courseName : assignedCourses) {
            data.append(".").append(courseName);
        }

        return data.toString();
    }

    public static Teacher fromDataString(String data) {
        String[] mainParts = data.split(",", 4); // Only split into 4 parts for user fields
        if (mainParts.length < 4) {
            throw new IllegalArgumentException("Invalid teacher data: " + data);
        }

        Teacher teacher = new Teacher(mainParts[0], mainParts[1], mainParts[2], mainParts[3]);

        // If courses exist, they are appended after a dot
        int courseStart = data.indexOf(".");
        if (courseStart != -1) {
            String[] courseParts = data.substring(courseStart + 1).split("\\.");
            for (String courseName : courseParts) {
                teacher.assignedCourses.add(courseName);
            }
        }

        return teacher;
    }

    @Override
    public String getInfo() {
        return "Teacher: " + getFirstName() + " " + getLastName();
    }
}
