import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class AssistantPage implements ActionListener {
    Assistant selectedAssistant;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    DefaultListModel<Course> model = new DefaultListModel<>();
    JList<Course> courseJList = new JList<>(model);
    JTextField assistantFirstNameField;
    JTextField assiatantLastNameField;
    public AssistantPage() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 200));
        frame.getContentPane().setBackground(Color.pink);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel teacherNameLabel = new JLabel("Enter teacher name: ");
        assistantFirstNameField = new JTextField(20);
        assiatantLastNameField = new JTextField(20);

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
                    Set<Assistant> assistantSet = (Set<Assistant>) selectedCourse.getAssistants();
                    for (Assistant assistant : assistantSet) {
                        if (assistant.compareTo(selectedAssistant) == 0) {
                            break;
                        }
                    }


                }
            }
        });
        courseJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        courseJList.setVisibleRowCount(-1);

        panel.add(teacherNameLabel);
        panel.add(assistantFirstNameField);
        panel.add(assiatantLastNameField);
        panel.add(displayCoursesButton);
        panel.add(courseJList);
        courseJList.setVisible(false);

        frame.add(panel);
        frame.setLocation(200, 200);
    }
    private void onDisplayCourses() {
        String firstName = assistantFirstNameField.getText();
        String lastName = assiatantLastNameField.getText();
        selectedAssistant = new Assistant(firstName, lastName);

        model.removeAllElements();
        model.addAll(Catalog.getInstance().getCoursesForAssistant(selectedAssistant));
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
