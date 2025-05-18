package education;

import java.util.List;

public interface CourseParticipant {
    void enroll(Course course);
    List<Course> getCourses();
    String getDisplayInfo();
    default String getParticipantType() {
        return this.getClass().getSimpleName();
    }
}