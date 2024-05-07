import java.util.*;

public abstract class Course {
    String name;
    Teacher teacher;
    Set<Assistant> assistants = new HashSet<>();
    Set<Grade> grades = new TreeSet<>();
    Map<String, Group> idToGroup = new HashMap<>();
    Integer creditPoints;
    IScoreStrategy scoreStrategy;
    Snapshot snapshot;

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Assistant> getAssistants() {
        return assistants;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public Map<String, Group> getIdToGroup() {
        return idToGroup;
    }

    public Group getGroup(String ID) {
        return idToGroup.get(ID);
    }

    public Group findGroupForStudent(Student student) {
        for (Group group : idToGroup.values()) {
            if (group.hasStudent(student)) {
                return group;
            }
        }
        return null;
    }

    public boolean hasStudent(Student student) {
        for (Group group : idToGroup.values()) {
            if (group.hasStudent(student)) {
                return true;
            }
        }
        return false;
    }

    public Integer getCreditPoints() {
        return this.creditPoints;
    }

    public IScoreStrategy getScoreStrategy() { return this.scoreStrategy; }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setAssistants(Set<Assistant> assistants) {
        this.assistants = assistants;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public void setIdToGroup(Map<String, Group> idToGroup) {
        this.idToGroup = idToGroup;
    }

    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints = creditPoints;
    }

    protected Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.idToGroup = builder.idToGroup;
        this.creditPoints = builder.creditPoints;
        this.scoreStrategy = builder.scoreStrategy;
    }

    // Seteaza asistentul în grupa cu ID-ul indicat ˘
    // Daca nu exist ˘ a deja, ad ˘ auga asistentul s ˘ , i în mult, imea asistent, ilor
    public void addAssistant(String ID, Assistant assistant) throws Exception {
        if (idToGroup.containsKey(ID)) {
            idToGroup.get(ID).setAssistant(assistant);
        }
        else {
            throw new Exception("Group not found");
        }

        assistants.add(assistant);
    }

    // Adauga studentul în grupa cu ID-ul indicat ˘
    public void addStudent(String ID, Student student)  {
        if (idToGroup.containsKey(ID)) {
            idToGroup.get(ID).add(student);
        }
        else {
            System.err.println("Group not found");
        }
    }

    // Adauga grupa ˘
    public void addGroup(Group group){
        idToGroup.put(group.ID, group);
    }

    // Instant, iaza o grup ˘ a s ˘ , i o adauga˘
    public void addGroup(String ID, Assistant assistant){
        Group newGroup = new Group(ID, assistant);
        idToGroup.put(ID, newGroup);
        assistants.add(assistant);
    }

    // Instant, iaza o grup ˘ a s ˘ , i o adauga˘
    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp){
        Group newGroup = new Group(ID, assistant, comp);
        idToGroup.put(ID, newGroup);
        assistants.add(assistant);
    }

    // Returneaza nota unui student sau null ˘
    public Grade getGrade(Student student) {
        for (Grade current : grades) {
            if (current.getStudent().compareTo(student) == 0) {
                return current;
            }
        }
        return null;
    }

    public void removeAllGrades() {
        grades.clear();
    }

    // Adauga o not ˘ a˘
    public void addGrade(Grade grade){
        grades.add(grade);
    }

    // Returneaza o list ˘ a cu tot ˘ , i student, ii
    public Set<Student> getAllStudents(){
        Set<Student> allStudents = new HashSet<>();
        for (Group current : idToGroup.values()) {
            allStudents.addAll(current);
        }
        return allStudents;
    }

    // Returneaza un dict ˘ , ionar cu situat, ia student, ilor
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studentToGrade = new HashMap<>();
        for (Group current : idToGroup.values()) {
            for (Student student : current) {
                studentToGrade.put(student, null);
            }
        }
        for (Grade current : grades) {
            studentToGrade.put(current.getStudent(), current);
        }

        return studentToGrade;
    }

    // Metoda ce o s ˘ a fie implementat ˘ a pentru a determina student ˘ , ii promovat, i
    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {
        List<Grade> gradesList = grades.stream().toList();
        Grade bestGrade = scoreStrategy.getBestGrade(gradesList);
        Student bestStudent = bestGrade.getStudent();
        return bestStudent;
    }

    public abstract String getTypeString();

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", type=" + getTypeString() +
                ", teacher=" + teacher +
                ", assistants=" + assistants +
                ", grades=" + grades +
                ", idToGroup=" + idToGroup +
                ", creditPoints=" + creditPoints +
                ", scoreStrategy=" + scoreStrategy +
                '}';
    }

    static abstract class CourseBuilder {
        private String name;
        private Teacher teacher;
        private Set<Assistant> assistants = new HashSet<>();
        private Set<Grade> grades = new TreeSet<>();
        private Map<String, Group> idToGroup = new HashMap<>();
        private Integer creditPoints;
        private IScoreStrategy scoreStrategy;

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder teacher(String firstName, String lastName) {
            this.teacher = new Teacher(firstName, lastName);
            return this;
        }

        public CourseBuilder assistants(Set<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(Set<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder idToGroup(Map<String, Group> idToGroup) {
            this.idToGroup = idToGroup;
            return this;
        }

        public CourseBuilder creditPoints(Integer creditPoints) {
            this.creditPoints = creditPoints;
            return this;
        }

        public CourseBuilder scoreStrategy(IScoreStrategy scoreStrategy) {
            this.scoreStrategy = scoreStrategy;
            return this;
        }

        public abstract Course build();
    }

    public void makeBackup() {
        this.snapshot = new Snapshot();
        Set<Grade> gradesCopy = new TreeSet<>();
        for (Grade grade : this.grades) {
            gradesCopy.add(grade.clone());
        }
        this.snapshot.grades = gradesCopy;
    }

    public void undo() {
        this.grades = this.snapshot.grades;
    }

    private class Snapshot {
        Set<Grade> grades;
    }

    public static IScoreStrategy createStrategy(String strategyStr) {
        return switch (strategyStr) {
            case "BestExamScore" -> new BestExamScore();
            case "BestPartialScore" -> new BestPartialScore();
            case "BestTotalScore" -> new BestTotalScore();
            default -> null;
        };
    }
    public static CourseBuilder createBuilder(String builderTypeStr) {
        return switch (builderTypeStr) {
            case "FullCourse" -> new FullCourse.FullCourseBuilder();
            case "PartialCourse" -> new PartialCourse.PartialCourseBuilder();
            default -> null;
        };
    }
}
