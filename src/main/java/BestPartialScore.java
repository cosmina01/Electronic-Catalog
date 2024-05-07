import java.util.List;

public class BestPartialScore implements IScoreStrategy {

    @Override
    public Grade getBestGrade(List<Grade> grades) {
        if (grades == null && grades.isEmpty()) {
            System.out.println("Empty or null Grade List");
            return null;
        }
        Grade bestGrade = grades.get(0);
        for (Grade grade : grades) {
            if (grade.getPartialScore() > bestGrade.getPartialScore()) {
                bestGrade = grade;
            }
        }
        return bestGrade;
    }

    @Override
    public String toString() {
        return "BestPartialScore";
    }
}
