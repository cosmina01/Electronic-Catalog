import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherPage implements ActionListener {
    Teacher selectedTeacher;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    DefaultListModel<Course> model = new DefaultListModel<>();
    JList<Course> courseJList = new JList<>(model);
    JTextField teacherFirstNameField;
    JTextField teacherLastNameField;
    public TeacherPage() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 200));
        frame.getContentPane().setBackground(Color.pink);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel teacherNameLabel = new JLabel("Enter teacher name: ");
        teacherFirstNameField = new JTextField(20);
        teacherLastNameField = new JTextField(20);

        JButton displayCoursesButton = new JButton("Display Courses");
        displayCoursesButton.addActionListener(this);


        //model.addAll(Catalog.getInstance().getAllCourses());
        courseJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    JList<Course> sourceList = (JList<Course>) e.getSource();
                    Course selectedCourse = sourceList.getSelectedValue();
                    if (selectedCourse == null) { return; }
                    Set<Teacher> teacherSet = (Set<Teacher>) selectedCourse.getTeacher();
                    for (Teacher teacher : teacherSet) {
                        if (teacher.compareTo(selectedTeacher) == 0) {
                            break;
                        }
                    }


                }
            }
        });
        courseJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        courseJList.setVisibleRowCount(-1);

        panel.add(teacherNameLabel);
        panel.add(teacherFirstNameField);
        panel.add(teacherLastNameField);
        panel.add(displayCoursesButton);
        panel.add(courseJList);
        courseJList.setVisible(false);

        frame.add(panel);
        frame.setLocation(200, 200);
    }

    private void onDisplayCourses() {
        String firstName = teacherFirstNameField.getText();
        String lastName = teacherLastNameField.getText();
        selectedTeacher = new Teacher(firstName, lastName);

        model.removeAllElements();
        model.addAll(Catalog.getInstance().getCoursesForTeacher(selectedTeacher));
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
