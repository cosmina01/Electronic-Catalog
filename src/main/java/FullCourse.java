import java.util.ArrayList;

public class FullCourse extends Course {
    public FullCourse(FullCourseBuilder builder) {
        super(builder);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduated = new ArrayList<>();
        for (Grade grade : this.grades) {
            Double partial = grade.getPartialScore();
            Double exam = grade.getExamScore();
            if (partial >= 3 && exam >= 2) {
                graduated.add(grade.getStudent());
            }
        }
        return graduated;
    }

    @Override
    public String getTypeString() {
        return "FullCourse";
    }

    static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder() {}

        @Override
        public FullCourse build() {
            FullCourse curs = new FullCourse(this);
            return curs;
        }
    }
}
