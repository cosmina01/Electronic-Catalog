import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Grade implements Comparable<Grade>, Cloneable {
    private Double partialScore, examScore;
    private Student student;
    private String courseName;

    public Grade(Double partialScore, Double examScore, Student student, String courseName) {
        setGrade(partialScore, examScore, student, courseName);
    }

    public void setGrade(Double partialScore, Double examScore, Student student, String course) {
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.student = student;
        this.courseName = course;
    }

    public String getCourseName(){
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getPartialScore(){
        return partialScore;
    }

    public void setPartialScore(Double partialScore){
        this.partialScore = partialScore;
    }

    public Double getExamScore(){
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent(){
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getTotal() {
        double partialValue = 0.0;
        double examValue = 0.0;
        if (partialScore != null) partialValue = partialScore;
        if (examScore != null) examValue = examScore;

        return partialValue + examValue;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "partial=" + partialScore +
                ", exam=" + examScore +
                ", total=" + new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(getTotal()) +
                ", student=" + student +
                ", course=" + courseName
                + "}";
    }

    @Override
    public int compareTo(Grade o) {
        int result = this.getTotal().compareTo(o.getTotal());
        if (result == 0) {
            result = this.getStudent().compareTo(o.getStudent());
        }
        return result;
    }

    @Override
    public Grade clone() {
        try {
            Grade clone = (Grade) super.clone();
            clone.courseName = this.courseName;
            clone.student = new Student(student);
            clone.examScore = this.examScore;
            clone.partialScore = this.partialScore;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}