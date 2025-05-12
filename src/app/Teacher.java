package app;

public class Teacher extends User {
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
