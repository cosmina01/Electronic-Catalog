import java.util.List;

public interface IScoreStrategy {
    Grade getBestGrade(List<Grade> grades);
}
