import javax.swing.*;

public class CourseDetailsPage {
    Course selectedCourse;
    Student selectedStudent;
    public CourseDetailsPage(Course course, Student student) {
        selectedCourse = course;
        selectedStudent = student;

        JFrame frame = new JFrame("Course details for student: " + student);
        JPanel panel = new JPanel();

        JTextArea courseAssistants = new JTextArea(2, 20);
        JScrollPane scrollPane = new JScrollPane(courseAssistants);
        JLabel courseAssistantsLabel = new JLabel("Course Assistants");
        scrollPane.createVerticalScrollBar();
        courseAssistants.setText(selectedCourse.getAssistants().toString());
        courseAssistants.setEditable(false);

        JTextField courseTeacher = new JTextField(20);
        JLabel courseTeacherLabel = new JLabel("Course Teacher");
        System.out.println(selectedCourse.getTeacher());
        courseTeacher.setText(selectedCourse.getTeacher().toString());
        courseTeacher.setEditable(false);

        JTextField courseGrade = new JTextField(20);
        JLabel courseGradeLabel = new JLabel("Student Grade");

        Grade actualGrade = selectedCourse.getGrade(selectedStudent);
        courseGrade.setText("exam: " + actualGrade.getExamScore() + ", partial: " + actualGrade.getPartialScore());
        courseTeacher.setEditable(false);

        Group selectedGroup = selectedCourse.findGroupForStudent(selectedStudent);

        JTextField groupAssistantText = new JTextField(20);
        JLabel groupAssistantLabel = new JLabel("Student's Assistant");
        groupAssistantText.setText(selectedCourse.getGroup(selectedGroup.ID).getAssistant().toString());
        groupAssistantText.setEditable(false);

        panel.add(groupAssistantLabel);
        panel.add(groupAssistantText);
        panel.add(courseGradeLabel);
        panel.add(courseGrade);
        panel.add(courseTeacherLabel);
        panel.add(courseTeacher);
        panel.add(courseAssistantsLabel);
        panel.add(scrollPane);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocation(400, 400);
        frame.setVisible(true);
    }
}
