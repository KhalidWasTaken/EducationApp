package app;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class User {
    protected String name;
    protected int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract String getInfo();
}

class Student extends User {
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

class Teacher extends User {
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    @Override
    public String getInfo() {
        return "Teacher - Name: " + name + ", Age: " + age + ", Subject: " + subject;
    }
}

public class EducationAppGUI {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getUserChoice();
            System.out.println("[DEBUG] You chose: " + choice);
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addTeacher();
                case 3 -> listUsers();
                case 4 -> saveToFile();
                case 5 -> {
                    System.out.println("Exiting program.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n===== Quality Education Console App =====");
        System.out.println("1. Add Student");
        System.out.println("2. Add Teacher");
        System.out.println("3. List All Users");
        System.out.println("4. Save Data to File");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter student age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            Student student = new Student(name, age);

            System.out.print("Enter course to enroll: ");
            student.enroll(scanner.nextLine().trim());

            students.add(student);
            System.out.println("Student added successfully.");
            System.out.println("[DEBUG] students size = " + students.size());
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void addTeacher() {
        try {
            System.out.print("Enter teacher name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter teacher age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter subject: ");
            String subject = scanner.nextLine().trim();
            teachers.add(new Teacher(name, age, subject));
            System.out.println("Teacher added successfully.");
            System.out.println("[DEBUG] teachers size = " + teachers.size());
        } catch (Exception e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }

    private static void listUsers() {
        System.out.println("[DEBUG] Listing users: " +
            students.size() + " students, " + teachers.size() + " teachers.");

        System.out.println("\n--- Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s.getInfo());
            }
        }

        System.out.println("\n--- Teachers ---");
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
        } else {
            for (Teacher t : teachers) {
                System.out.println(t.getInfo());
            }
        }
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter("education_data.txt")) {
            writer.println("Students:");
            for (Student s : students) writer.println(s.getInfo());
            writer.println("Teachers:");
            for (Teacher t : teachers) writer.println(t.getInfo());
            System.out.println("Data saved to education_data.txt.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}
