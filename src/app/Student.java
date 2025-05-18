package education;

public class Student extends User {
    
    public Student(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public static Student fromDataString(String data) {
        String[] parts = data.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid student data: " + data);
        }
        return new Student(parts[0], parts[1], parts[2], parts[3]);
    }

    @Override
    public String toDataString() {
        return getUsername() + "," + getPassword() + "," + getFirstName() + "," + getLastName();
    }

    @Override
    public String getInfo() {
        return "Student: " + getFirstName() + " " + getLastName();
    }
}
