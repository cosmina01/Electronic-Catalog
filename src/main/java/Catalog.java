import javax.print.DocFlavor;
import java.util.*;

public class Catalog implements Subject {
    private final List<Observer> observers;
    private static Catalog instance = null;
    List<Course> coursesList;

    private Catalog() {
        this.observers = new ArrayList<>();
        this.coursesList = new ArrayList<>();
    }

    public static Catalog getInstance(){
        if (instance == null)
            instance = new Catalog();
        return instance;
    }

    // Adauga un curs în catalog
    public void addCourse(Course course) {
        if (!coursesList.contains(course))
            coursesList.add(course);
    }

    public Course getCourse(String name) {
        for (Course course : coursesList) {
            if (course.name.equalsIgnoreCase(name)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getAllCourses() {
        return this.coursesList;
    }

    public List<Course> getCoursesForStudent(Student student) {
        List<Course> coursesForStudent = new ArrayList<>();
        for (Course course : coursesList) {
            if (course.hasStudent(student)) {
                coursesForStudent.add(course);
            }
        }
        return coursesForStudent;
    }

    public List<Course> getCoursesForAssistant(Assistant assistant) {
        List<Course> coursesForAssistant = new ArrayList<>();
        for (Course course : coursesList) {
            if (course.getAssistants().contains(assistant)) {
                coursesForAssistant.add(course);
            }
        }
        return coursesForAssistant;
    }


    public List<Course> getCoursesForTeacher(Teacher teacher) {
        List<Course> coursesForTeacher = new ArrayList<>();
        for (Course course : coursesList) {
            if (course.teacher.equals(teacher)) {
                coursesForTeacher.add(course);
            }
        }
        return coursesForTeacher;
    }

    public Set<Student> getAllStudentsForAllCourses() {
        Set<Student> allStudents = new HashSet<>();
        for (Course course : coursesList) {
            allStudents.addAll(course.getAllStudents());
        }
        return allStudents;
    }

    public Set<Teacher> getAllTeachers() {
        Set<Teacher> allTeachers = new HashSet<>();
        for (Course course : coursesList) {
            allTeachers.add(course.teacher);
        }
        return allTeachers;
    }

    public Set<Assistant> getAllAssistants() {
        Set<Assistant> allAssistants = new HashSet<>();
        for (Course course : coursesList) {
            allAssistants.addAll(course.assistants);
        }
        return allAssistants;
    }

    // Sterge un curs din catalog
    public void removeCourse(Course course) {
        coursesList.remove(course);
    }

    @Override
    public void addObserver(Observer observer) {
        if(observer == null)
            throw new IllegalArgumentException("Null Observer");
        if(!observers.contains(observer))
            this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        if(observer == null)
            throw new IllegalArgumentException("Null Observer");
        if(observers.contains(observer))
            this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for (Observer observer : observers) {
            observer.update(new Notification(grade));
        }
    }
}


