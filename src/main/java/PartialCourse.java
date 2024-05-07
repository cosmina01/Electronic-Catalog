import java.util.ArrayList;

public class PartialCourse extends Course {

    public PartialCourse(PartialCourseBuilder builder) {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduated = new ArrayList<>();
        for (Grade grade : this.grades) {
            if (grade.getTotal() >= 5) {
                graduated.add(grade.getStudent());
            }
        }
        return graduated;
    }

    @Override
    public String getTypeString() {
        return "PartialCourse";
    }

    static class PartialCourseBuilder extends CourseBuilder {
        @Override
        public PartialCourse build() {
            PartialCourse curs = new PartialCourse(this);
            return curs;
        }
    }
}
