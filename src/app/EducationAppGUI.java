package education;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EducationAppGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public EducationAppGUI() {
        frame = new JFrame("Education App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        JLabel messageLabel = new JLabel("");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(messageLabel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            List<Student> students = Database.loadStudents();
            List<Teacher> teachers = Database.loadTeachers();

            User foundUser = null;
            for (Student s : students) {
                if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                    foundUser = s;
                    break;
                }
            }
            if (foundUser == null) {
                for (Teacher t : teachers) {
                    if (t.getUsername().equals(username) && t.getPassword().equals(password)) {
                        foundUser = t;
                        break;
                    }
                }
            }

            if (foundUser != null) {
                JOptionPane.showMessageDialog(frame, "Login successful: " + foundUser.getInfo());
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Student", "Teacher"});

        JButton submitButton = new JButton("Register");
        JButton backButton = new JButton("Back");
        JLabel messageLabel = new JLabel("");

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);
        panel.add(submitButton);
        panel.add(backButton);
        panel.add(messageLabel);

        submitButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }

            if (role.equals("Student")) {
                List<Student> students = Database.loadStudents();
                students.add(new Student(username, password, firstName, lastName));
                Database.saveStudents(students);
            } else {
                List<Teacher> teachers = Database.loadTeachers();
                teachers.add(new Teacher(username, password, firstName, lastName));
                Database.saveTeachers(teachers);
            }

            JOptionPane.showMessageDialog(frame, role + " registered successfully.");
            cardLayout.show(mainPanel, "login");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EducationAppGUI::new);
    }
}
