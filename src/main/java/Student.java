import java.util.Comparator;

public class Student extends User {
    private Parent mother;
    private Parent father;

    public Student(String firstName, String lastName){
        super(firstName, lastName);
    }

    public Student(Student other) {
        super(other.getFirstName(), other.getLastName());
    }
    
    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return this.mother;
    }

    public Parent getFather() {
        return this.father;
    }
}