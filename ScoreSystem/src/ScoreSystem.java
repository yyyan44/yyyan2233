import java.util.Scanner;

/**
 * 主程序：班级成绩处理系统。
 * 学生数据整体用一个顺序表 SeqList<Student> 存放（顺序存储结构），
 * 每个学生的课程成绩再用一张 SeqList<CourseScore> 存放（课程数可以不同）。
 *
 * 功能：成绩输入 -> 平均分计算 -> 平均成绩排名 -> 按学号查找 -> 按课程名查最高分。
 */
public class ScoreSystem {

    /** 功能1：输入 n 个学生的成绩，尾插进顺序表 */
    public static void inputStudents(SeqList<Student> list, int n, Scanner sc) {
        for (int i = 1; i <= n; i++) {
            System.out.println("输入第" + i + "个学生：");
            list.add(Student.input(sc));   // 顺序表尾插
        }
    }

    /** 功能2+3：按平均分从高到低排名。为不打乱原表，把学生复制到新顺序表里做冒泡排序 */
    public static void rankByAverage(SeqList<Student> list) {
        SeqList<Student> rank = new SeqList<>(list.size());
        for (int i = 1; i <= list.size(); i++)
            rank.add(list.get(i));

        // 冒泡排序：相邻两个比较，平均分小的往后换（降序）
        for (int i = 1; i < rank.size(); i++) {
            boolean swapped = false;
            for (int j = 1; j <= rank.size() - i; j++) {
                if (rank.get(j).getAverage() < rank.get(j + 1).getAverage()) {
                    // 借助删除+插入完成相邻交换：先取出 j+1，再插到 j
                    Student t = rank.remove(j + 1);
                    rank.insert(j, t);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        System.out.println("----- 平均成绩排名 -----");
        for (int i = 1; i <= rank.size(); i++) {
            Student s = rank.get(i);
            System.out.printf("第%d名  %s  %s  平均分：%.2f%n",
                    i, s.getId(), s.getName(), s.getAverage());
        }
    }

    /** 功能4：按学号查找学生（顺序查找），找到打印其完整成绩，返回位序 */
    public static int searchById(SeqList<Student> list, String id) {
        for (int i = 1; i <= list.size(); i++) {
            Student s = list.get(i);
            if (s.getId().equals(id)) {
                System.out.println("找到（第" + i + "个元素）：");
                System.out.println("  " + s);
                return i;
            }
        }
        System.out.println("未找到学号为 " + id + " 的学生。");
        return -1;
    }

    /** 功能5：按课程名查找该课程最高分的同学（遍历每个学生的成绩子表） */
    public static void maxByCourse(SeqList<Student> list, String courseName) {
        Student best = null;
        double bestScore = -1;
        for (int i = 1; i <= list.size(); i++) {
            Student s = list.get(i);
            double sc = s.getScoreOf(courseName);
            if (sc >= 0 && sc > bestScore) {
                bestScore = sc;
                best = s;
            }
        }
        if (best == null) {
            System.out.println("没有学生选修课程：" + courseName);
        } else {
            System.out.printf("《%s》最高分：%.1f  %s  %s%n",
                    courseName, bestScore, best.getId(), best.getName());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SeqList<Student> list = new SeqList<>(16);   // 存放全班学生的顺序表

        System.out.println("===== 学生成绩录入（6人）=====");
        inputStudents(list, 6, sc);

        System.out.println();
        System.out.println("===== 全班成绩及平均分 =====");
        for (int i = 1; i <= list.size(); i++)
            System.out.println(list.get(i));

        System.out.println();
        rankByAverage(list);

        System.out.println();
        System.out.println("----- 按学号查找 -----");
        searchById(list, "2025116003");
        searchById(list, "2025116099");   // 不存在的学号，测试查不到的情况

        System.out.println();
        System.out.println("----- 按课程查找最高分 -----");
        maxByCourse(list, "数据结构");
        maxByCourse(list, "Java程序设计");
        maxByCourse(list, "大学物理");    // 没人选的课，测试边界
    }
}
