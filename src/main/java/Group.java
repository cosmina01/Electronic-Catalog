import com.sun.source.tree.Tree;

import java.util.*;

public class Group extends TreeSet<Student> implements Comparable<Group> {
    Assistant assistant;
    String ID;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public boolean hasStudent(Student student) {
        for (Student st : this) {
            if (st.compareTo(student) == 0) {
                return true;
            }
        }
        return false;
    }

    public Student getStudent(String firstName, String lastName) {
        for (Student student : this) {
            if (student.getFirstName().equalsIgnoreCase(firstName)
                && student.getLastName().equalsIgnoreCase(lastName))
            {
                return student;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Group o) {
        return this.ID.compareTo(o.ID);
    }
}
