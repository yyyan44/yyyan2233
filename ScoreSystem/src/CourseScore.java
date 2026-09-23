/**
 * 课程成绩记录：一门课的名字和分数。
 * 作为顺序表的数据元素，存放在每个学生的"成绩子表"里。
 */
public class CourseScore {
    private String courseName; // 课程名
    private double score;      // 成绩

    public CourseScore(String courseName, double score) {
        this.courseName = courseName;
        this.score = score;
    }

    public String getCourseName() { return courseName; }
    public double getScore() { return score; }

    @Override
    public String toString() {
        return courseName + "=" + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseScore)) return false;
        CourseScore c = (CourseScore) o;
        return courseName.equals(c.courseName);
    }
}
