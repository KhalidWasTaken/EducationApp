package education;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private static final String STUDENT_FILE = "students.txt";

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromDataString(line));
            }
        } catch (IOException e) {
            System.err.println("Could not load students: " + e.getMessage());
        }

        return students;
    }

    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students) {
                writer.write(s.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save students: " + e.getMessage());
        }
    }
    private static final String TEACHER_FILE = "teachers.txt";

    public static List<Teacher> loadTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TEACHER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                teachers.add(Teacher.fromDataString(line));
            }
        } catch (IOException e) {
            System.err.println("Could not load teachers: " + e.getMessage());
        }

        return teachers;
    }

    public static void saveTeachers(List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEACHER_FILE))) {
            for (Teacher t : teachers) {
                writer.write(t.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save teachers: " + e.getMessage());
        }
    }

}