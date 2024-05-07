import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    private static User createUser(JSONObject userJson, UserFactory.UserType userType) {
        String firstName = (String) userJson.get("firstName");
        String lastName = (String) userJson.get("lastName");
        return UserFactory.getUser(userType, firstName, lastName);
    }

    private static Assistant createAssistant(JSONObject assistantJson) {
        Assistant assistant = (Assistant) createUser(assistantJson, UserFactory.UserType.ASSISTANT);
        return assistant;
    }

    private static Teacher createTeacher(JSONObject teacherJson) {
        Teacher teacher = (Teacher) createUser(teacherJson, UserFactory.UserType.TEACHER);
        return teacher;
    }

    private static Parent createParent(JSONObject parentJson) {
        Parent parent = (Parent) createUser(parentJson, UserFactory.UserType.PARENT);
        return parent;
    }

    private static Student createStudent(JSONObject studentJson) {
        Student student = (Student) createUser(studentJson, UserFactory.UserType.STUDENT);
        JSONObject motherJson = (JSONObject) studentJson.get("mother");
        if (motherJson != null) {
            Parent studentMother = createParent(motherJson);
            Catalog.getInstance().addObserver(studentMother);
            student.setMother(studentMother);
        }
        JSONObject fatherJson = (JSONObject) studentJson.get("father");
        if (fatherJson != null) {
            Parent studentFather = createParent(fatherJson);
            Catalog.getInstance().addObserver(studentFather);
            student.setFather(studentFather);
        }
        return student;
    }

    private static Group createGroup(JSONObject groupJson) {
        String groupId = (String) groupJson.get("ID");

        JSONObject assistantJson = (JSONObject) groupJson.get("assistant");
        Assistant groupAssistant = (Assistant) createUser(assistantJson, UserFactory.UserType.ASSISTANT);

        JSONArray studentsJson = (JSONArray) groupJson.get("students");
        Group group = new Group(groupId, groupAssistant);
        for (var studentJson : studentsJson) {
            Student groupStudent = createStudent((JSONObject) studentJson);
            group.add(groupStudent);
        }

        return group;
    }

    private static Course createCourse(JSONObject courseJson) {
        // simple
        String typeStr = (String) courseJson.get("type");
        String strategyStr = (String) courseJson.get("strategy");
        String nameStr = (String) courseJson.get("name");

        Course.CourseBuilder builder = Course.createBuilder(typeStr);
        builder.scoreStrategy(Course.createStrategy(strategyStr));
        builder.name(nameStr);

        // compus
        JSONObject teacherJson = (JSONObject) courseJson.get("teacher");
        Teacher courseTeacher = (Teacher) createUser(teacherJson, UserFactory.UserType.TEACHER);
        builder.teacher(courseTeacher);

        // array + compus
        JSONArray assistantsJson = (JSONArray) courseJson.get("assistants");
        Set<Assistant> courseAssistants = new HashSet<>();
        for (var assistantJson : assistantsJson) {
            Assistant courseAssistant = (Assistant) createUser((JSONObject) assistantJson, UserFactory.UserType.ASSISTANT);
            courseAssistants.add(courseAssistant);
        }
        builder.assistants(courseAssistants);

        // array + compus
        JSONArray groupsJson = (JSONArray) courseJson.get("groups");
        Map<String, Group> courseGroups = new HashMap<>();
        for (var groupJson : groupsJson) {
            Group courseGroup = createGroup((JSONObject) groupJson);
            courseGroups.put(courseGroup.ID, courseGroup);
        }
        builder.idToGroup(courseGroups);

        Course course = builder.build();
        System.out.println("Created course: " + course);
        return course;
    }

    private static void createExamScore(JSONObject scoreJson, ScoreVisitor scoreVisitor) {
        JSONObject teacherJson = (JSONObject) scoreJson.get("teacher");
        Teacher teacher = createTeacher(teacherJson);

        JSONObject studentJson = (JSONObject) scoreJson.get("student");
        Student student = createStudent(studentJson);

        String courseName = (String) scoreJson.get("course");
        Double scoreValue = ((Number) scoreJson.get("grade")).doubleValue();

        scoreVisitor.addExamScore(teacher, student, courseName, scoreValue);

        System.out.println("examScore: teacher=" + teacher
                                + ", student=" + student
                                + ", courseName=" + courseName
                                + ", grade=" + scoreValue);
    }

    private static void createPartialScore(JSONObject scoreJson, ScoreVisitor scoreVisitor) {
        JSONObject assistantJson = (JSONObject) scoreJson.get("assistant");
        Assistant assistant = createAssistant(assistantJson);

        JSONObject studentJson = (JSONObject) scoreJson.get("student");
        Student student = createStudent(studentJson);

        String courseName = (String) scoreJson.get("course");
        Double scoreValue = ((Number) scoreJson.get("grade")).doubleValue();

        scoreVisitor.addPartialScore(assistant, student, courseName, scoreValue);

        System.out.println("partialScore: assistant=" + assistant
                + ", student=" + student
                + ", courseName=" + courseName
                + ", grade=" + scoreValue);
    }

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("catalog.json");
//            FileReader fileReader = new FileReader("C:\\Users\\cosmi\\OneDrive\\Desktop\\Tema POO 2022-2023\\Tema_POO\\catalog.json");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            JSONArray coursesJson = (JSONArray) jsonObject.get("courses");
            for (var courseJson : coursesJson) {
                Course course = createCourse((JSONObject) courseJson);
                Catalog.getInstance().addCourse(course);
            }

            // Grades
            ScoreVisitor scoreVisitor = new ScoreVisitor();
            JSONArray examScoresJson = (JSONArray) jsonObject.get("examScores");
            for (var examScoreJson : examScoresJson) {
                createExamScore((JSONObject) examScoreJson, scoreVisitor);
            }
            JSONArray partialScoresJson = (JSONArray) jsonObject.get("partialScores");
            for (var partialScoreJson : partialScoresJson) {
                createPartialScore((JSONObject) partialScoreJson, scoreVisitor);
            }

            {
                System.out.println("");
                System.out.println("======== Testing Program Functionality ========");

                System.out.println("");
                System.out.println("--- Applying Teacher scores using ScoreVisitor ---");
                for (Teacher teacher : Catalog.getInstance().getAllTeachers()) {
                    teacher.accept(scoreVisitor);
                }

                System.out.println("");
                System.out.println("--- Removing all Fathers from Catalog Observers list ---");
                for (Student student : Catalog.getInstance().getAllStudentsForAllCourses()) {
                    if (student.getFather() != null) {
                        Catalog.getInstance().removeObserver(student.getFather());
                    }
                }

                System.out.println("");
                System.out.println("--- Applying Assistant scores using ScoreVisitor ---");
                for (Assistant assistant : Catalog.getInstance().getAllAssistants()) {
                    assistant.accept(scoreVisitor);
                }
                System.out.println("");

                System.out.println("--- Courses ---");
                for (Course course : Catalog.getInstance().getAllCourses()) {
//                    System.out.println("");
                    System.out.println("For course: " + course.getName() + " (" + course.getTypeString() + ", " + course.getScoreStrategy() + ")");
                    System.out.println("getAllStudents: " + course.getAllStudents());
                    System.out.println("getAllStudentGrades: " + course.getAllStudentGrades());
                    System.out.println("getGraduatedStudents: " + course.getGraduatedStudents());
                    System.out.println("getBestStudent: " + course.getBestStudent());

                    System.out.println("# Testing Snapshot");
                    System.out.println("makeBackup");
                    course.makeBackup();
                    System.out.println("removeAllGrades");
                    course.removeAllGrades();
                    System.out.println("getAllStudentGrades: " + course.getAllStudentGrades());
                    System.out.println("undo");
                    course.undo();
                    System.out.println("getAllStudentGrades: " + course.getAllStudentGrades());
                    System.out.println("-----------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        GraphicInterface ui = new GraphicInterface();
    }

}
