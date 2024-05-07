import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicInterface implements ActionListener {
    private JButton studentButton;
    private JButton parentButton;
    private JButton teacherButton;
    private JButton assistantButton;
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Main Page");

    StudentPage studentPage;
    TeacherPage teacherPage;
    AssistantPage assistantPage;

    public GraphicInterface() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(380, 200));
        frame.getContentPane().setBackground (Color.pink);
        frame.setLayout(new SpringLayout());

        studentButton = new JButton("Student");
        parentButton = new JButton("Parent");
        teacherButton = new JButton("Teacher");
        assistantButton = new JButton("Assistant");

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentPage = new StudentPage();
                studentPage.setVisible(true);
            }
        });

        parentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        teacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherPage = new TeacherPage();
                teacherPage.setVisible(true);
            }
        });

        assistantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assistantPage = new AssistantPage();
                assistantPage.setVisible(true);
            }
        });

        panel.add(studentButton);
        panel.add(parentButton);
        panel.add(teacherButton);
        panel.add(assistantButton);

        frame.add(panel);
        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {}
}
