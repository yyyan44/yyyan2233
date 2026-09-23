import java.util.Scanner;

/**
 * 学生类：一个学生 = 学号 + 姓名 + 一张"课程成绩顺序表"。
 * 因为每个学生的课程数可能不同，所以课程成绩用 SeqList<CourseScore> 动态存放。
 */
public class Student {
    private String id;                          // 学号
    private String name;                        // 姓名
    private SeqList<CourseScore> scores;        // 该生的课程成绩表（顺序存储）

    public Student(String id, String name, int courseCount) {
        this.id = id;
        this.name = name;
        this.scores = new SeqList<>(courseCount > 0 ? courseCount : 4);
    }

    /** 添加一门课的成绩（尾插进成绩子表） */
    public void addScore(String courseName, double score) {
        scores.add(new CourseScore(courseName, score));
    }

    /** 计算平均分：遍历成绩子表累加后除以课程数 */
    public double getAverage() {
        if (scores.isEmpty()) return 0;
        double sum = 0;
        for (int i = 1; i <= scores.size(); i++)
            sum += scores.get(i).getScore();
        return sum / scores.size();
    }

    /** 查询某门课的成绩，没选这门课返回 -1 */
    public double getScoreOf(String courseName) {
        for (int i = 1; i <= scores.size(); i++) {
            CourseScore cs = scores.get(i);
            if (cs.getCourseName().equals(courseName))
                return cs.getScore();
        }
        return -1;
    }

    public String getId()   { return id; }
    public String getName() { return name; }
    public SeqList<CourseScore> getScores() { return scores; }

    /** 从控制台读入一个学生的全部信息 */
    public static Student input(Scanner sc) {
        System.out.print("  学号 姓名 课程数：");
        String id = sc.next();
        String name = sc.next();
        int n = sc.nextInt();
        Student s = new Student(id, name, n);
        for (int i = 1; i <= n; i++) {
            System.out.print("  第" + i + "门课 课程名 成绩：");
            String course = sc.next();
            double score = sc.nextDouble();
            s.addScore(course, score);
        }
        return s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("  ").append(name).append("  ");
        for (int i = 1; i <= scores.size(); i++)
            sb.append(scores.get(i)).append("  ");
        sb.append("平均分：").append(String.format("%.2f", getAverage()));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) return false;
        return id.equals(((Student) o).id);
    }
}
