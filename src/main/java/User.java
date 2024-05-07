import java.util.Objects;

public abstract class User implements Comparable<User> {
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

//    public User(){
//        super();
//    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    @Override
    public int compareTo(User o) {
        int result = this.getLastName().compareTo(o.getLastName());
        if (result == 0) {
            result = this.getFirstName().compareTo(o.getFirstName());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
//        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
        return compareTo(user) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
