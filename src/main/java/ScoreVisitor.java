import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreVisitor implements Visitor {

    // student, curs, nota
    private class Tuple<S, C, G> {
        S student;
        C courseName;
        G gradeValue;

        public Tuple(S student, C courseName, G scoreValue) {
            this.student = student;
            this.courseName = courseName;
            this.gradeValue = scoreValue;
        }
    }

    Map<Teacher, List<Tuple<Student, String, Double>>> examScores = new HashMap<>();
    Map<Assistant, List<Tuple<Student, String, Double>>> partialScores = new HashMap<>();

    public void addExamScore(Teacher teacher, Student student, String courseName, Double scoreValue) {
        if (examScores.containsKey(teacher)) {
            examScores.get(teacher).add(new Tuple<>(student, courseName, scoreValue));
        } else {
            List<Tuple<Student, String, Double>> teacherList = new ArrayList<>();
            teacherList.add(new Tuple<>(student, courseName, scoreValue));
            examScores.put(teacher, teacherList);
        }
    }

    public void addPartialScore(Assistant assistant, Student student, String courseName, Double scoreValue) {
        if (partialScores.containsKey(assistant)) {
            partialScores.get(assistant).add(new Tuple<>(student, courseName, scoreValue));
        } else {
            List<Tuple<Student, String, Double>> assistantList = new ArrayList<>();
            assistantList.add(new Tuple<>(student, courseName, scoreValue));
            partialScores.put(assistant, assistantList);
        }
    }

    @Override
    public void visit(Assistant assistant) {
        Catalog catalog = Catalog.getInstance();
        List<Tuple<Student, String, Double>> assistantScores = this.partialScores.get(assistant);
        for (Tuple<Student, String, Double> score : assistantScores) {
            Course course = catalog.getCourse(score.courseName);
            Grade grade = course.getGrade(score.student);
            if (grade != null) {
                grade.setPartialScore(score.gradeValue);
            }
            else {
                grade = new Grade(score.gradeValue, 0.0, score.student, score.courseName);
                course.addGrade(grade);
            }
            catalog.notifyObservers(grade);
        }
    }

    @Override
    public void visit(Teacher teacher) {
        Catalog catalog = Catalog.getInstance();
        List<Tuple<Student, String, Double>> teacherScores = this.examScores.get(teacher);
        if (teacherScores == null) {
            System.err.println("Could not find Teacher: " + teacher + " in examScores map");
        }
        for (Tuple<Student, String, Double> score : teacherScores) {
            Course course = catalog.getCourse(score.courseName);
            Grade grade = course.getGrade(score.student);
            if (grade != null) {
                grade.setExamScore(score.gradeValue);
            }
            else {
                grade = new Grade(0.0, score.gradeValue, score.student, score.courseName);
                course.addGrade(grade);
            }
            catalog.notifyObservers(grade);
        }
    }
}
