public class Parent extends User implements Observer {
    public Parent(String firstName, String lastName){
        super(firstName, lastName);
    }

    @Override
    public void update(Notification notification) {
        if (notification.grade.getStudent().getLastName().equalsIgnoreCase(this.getLastName())) {
            System.out.println(this + " received notification: " + notification);
        }
    }

    @Override
    public String toString() {
        return "Parent: " + super.toString();
    }
}
