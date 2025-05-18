package education;

import java.util.Objects;

public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Make course name printable
    @Override
    public String toString() {
        return name;
    }

    // So courses can be compared (e.g., for removing duplicates)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
