public class UserFactory {
    enum UserType {
        STUDENT,
        ASSISTANT,
        PARENT,
        TEACHER
    }

    public static User getUser(UserType userType, String firstName, String lastName) {
        if (userType == null) {
            return null;
        }

        return switch (userType) {
            case STUDENT -> new Student(firstName, lastName);
            case PARENT -> new Parent(firstName, lastName);
            case ASSISTANT -> new Assistant(firstName, lastName);
            case TEACHER -> new Teacher(firstName, lastName);
            default -> null;
        };
    }
}
