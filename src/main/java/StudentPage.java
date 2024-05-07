import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collection;
import java.util.Set;

public class StudentPage implements ActionListener {
    Student selectedStudent;
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Student Page");
    DefaultListModel<Course> model = new DefaultListModel<>();
    JList<Course> courseJList = new JList<>(model);
    JScrollPane listScroller = new JScrollPane(courseJList);

    JTextField studentFirstNameField;
    JTextField studentLastNameField;

    public StudentPage() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 200));
        frame.getContentPane().setBackground (Color.pink);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel studentNameLabel = new JLabel("Enter student name: ");
        studentFirstNameField = new JTextField(20);
        studentLastNameField = new JTextField(20);

        JButton displayCoursesButton = new JButton("Display Courses");
        displayCoursesButton.addActionListener(this);

        courseJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    JList<Course> sourceList = (JList<Course>) e.getSource();
                    Course selectedCourse = sourceList.getSelectedValue();
                    if (selectedCourse == null) { return; }
                    Set<Student> studentsInCourse = selectedCourse.getAllStudents();
                    for (Student student : studentsInCourse) {
                        if (student.compareTo(selectedStudent) == 0) {
                            new CourseDetailsPage(selectedCourse, selectedStudent);
                            //model1.addElement(selectedCourse.name);
                            break;
                        }

                    }
                }
            }
        });

        courseJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        courseJList.setVisibleRowCount(-1);

        panel.add(studentNameLabel);
        panel.add(studentFirstNameField);
        panel.add(studentLastNameField);
        panel.add(displayCoursesButton);
        panel.add(courseJList);
        courseJList.setVisible(false);

        frame.add(panel);
        frame.setLocation(200, 200);
    }

    private void onDisplayCourses() {
        String firstName = studentFirstNameField.getText();
        String lastName = studentLastNameField.getText();
        selectedStudent = new Student(firstName, lastName);

        model.removeAllElements();
        model.addAll(Catalog.getInstance().getCoursesForStudent(selectedStudent));
        courseJList.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onDisplayCourses();
    }
}
