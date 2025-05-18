package education;

import java.io.*;
import java.util.*;

public class Database {
    private static final String STUDENTS_FILE = "students.txt";
    private static final String TEACHERS_FILE = "teachers.txt";

    private List<Student> students;
    private List<Teacher> teachers;

    public Database() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        loadStudents();
        loadTeachers();
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    students.add(Student.fromDataString(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping bad student line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }


    public static List<Teacher> loadTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    teachers.add(Teacher.fromDataString(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid teacher entry: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }
    
    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Student student : students) {
                writer.write(student.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveTeachers(List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("teachers.txt"))) {
            for (Teacher teacher : teachers) {
                writer.write(teacher.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudents(students); 
    }
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        saveTeachers(teachers); 
    }

    public User authenticate(String username, String password) {
        for (Student s : students) {
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                return s;
            }
        }
        for (Teacher t : teachers) {
            if (t.getUsername().equals(username) && t.getPassword().equals(password)) {
                return t;
            }
        }
        return null;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
