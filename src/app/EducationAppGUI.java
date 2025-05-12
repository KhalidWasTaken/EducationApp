package app;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class EducationAppGUI extends JFrame {
    private java.util.List<Student> students = new ArrayList<>();
    private java.util.List<Teacher> teachers = new ArrayList<>();
    private JTextArea outputArea;

    public EducationAppGUI() {
        setTitle("Quality Education App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JButton addStudentBtn = new JButton("Add Student");
        JButton addTeacherBtn = new JButton("Add Teacher");
        JButton listUsersBtn = new JButton("List Users");
        JButton saveDataBtn = new JButton("Save Data");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(addStudentBtn);
        buttonPanel.add(addTeacherBtn);
        buttonPanel.add(listUsersBtn);
        buttonPanel.add(saveDataBtn);
        buttonPanel.add(exitBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addStudentBtn.addActionListener(e -> addStudent());
        addTeacherBtn.addActionListener(e -> addTeacher());
        listUsersBtn.addActionListener(e -> listUsers());
        saveDataBtn.addActionListener(e -> saveData());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void addStudent() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter Student Name:");
            if (name == null || name.trim().isEmpty()) return;

            int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Student Age:"));
            Student s = new Student(name, age);

            while (true) {
                String course = JOptionPane.showInputDialog(this, "Enter Course (or leave blank to finish):");
                if (course == null || course.trim().isEmpty()) break;
                s.enroll(new Course(course));
            }

            students.add(s);
            outputArea.append("Student added.
");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void addTeacher() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter Teacher Name:");
            if (name == null || name.trim().isEmpty()) return;

            int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Teacher Age:"));
            String subject = JOptionPane.showInputDialog(this, "Enter Subject:");
            if (subject == null || subject.trim().isEmpty()) return;

            teachers.add(new Teacher(name, age, subject));
            outputArea.append("Teacher added.
");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void listUsers() {
        outputArea.setText("");
        outputArea.append("--- Students ---\n");
        for (Student s : students) outputArea.append(s.getInfo() + "\n");
        outputArea.append("--- Teachers ---\n");
        for (Teacher t : teachers) outputArea.append(t.getInfo() + "\n");
    }

    private void saveData() {
        try (PrintWriter writer = new PrintWriter("education_data.txt")) {
            writer.println("Students:");
            for (Student s : students) writer.println(s.getInfo());
            writer.println("Teachers:");
            for (Teacher t : teachers) writer.println(t.getInfo());
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EducationAppGUI::new);
    }
}
