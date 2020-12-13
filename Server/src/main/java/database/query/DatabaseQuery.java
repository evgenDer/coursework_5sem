package database.query;

public class DatabaseQuery {
    public static final String ADD_USER = "INSERT INTO user(login, password, isAdmin, name) " +
            "VALUES (?, ?, ?, ?)";
    public static final String REMOVE_USER = "DELETE FROM user " + "WHERE login = ?";
    public static final String FIND_ALL_USERS = "SELECT login, isAdmin, name" + " FROM user";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user" + " WHERE login = ? ";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = FIND_ALL_USERS + " WHERE login = ?" +
            " AND password = ?";
    public static final String ADD_COURSE = "INSERT INTO course(name, level, cost)" +
            "VALUES (?, ?, ?)";
    public static final String UPDATE_COURSE_COST = "UPDATE course " +
            "SET cost = ? WHERE id_course = ?";
    public static final String UPDATE_COURSE_NAME = "UPDATE course " +
            "SET name = ? WHERE id_course = ?";
    public static final String FIND_ALL_COURSES = "SELECT * FROM course ";
    public static final String FIND_COURSE_BY_ID = FIND_ALL_COURSES + " WHERE id_course = ? ";
    public static final String FIND_COURSES_BY_LEVEl = FIND_ALL_COURSES + " WHERE level = ? ";

    public static final String ADD_TEACHER = "INSERT INTO teacher(login, university)" +
            "VALUES (?, ?)";
    public static final String FIND_TEACHERS = "SELECT user.login, user.name, teacher.university, teacher.id_teacher " +
            "from teacher JOIN user ON teacher.login = user.login";
    public static final String ADD_GROUP = "INSERT INTO class(id_course, id_teacher, time, days_code) " +
            " VALUES (?, ?, ?, ?)";
    public static final String REMOVE_GROUP = "DELETE FROM class " + "WHERE id_class = ?";
    public static final String UPDATE_GROUP_BY_TEACHER = "UPDATE class " + "SET id_teacher = ? WHERE id_class = ?";
    public static final String UPDATE_GROUP_BY_COURSE = "UPDATE class " + "SET id_course = ? WHERE id_class = ?";
    public static final String FIND_GROUPS = "SELECT class.id_class, class.time, " +
            "class.days_code, course.name, course.level, user.name from class " +
            "JOIN course ON course.id_course = class.id_course " +
            "JOIN teacher ON teacher.id_teacher = class.id_teacher " +
            "JOIN user ON teacher.login = user.login";
    public static final String FIND_GROUPS_BY_WEEKDAYS = FIND_GROUPS + " where user.login = ? " +
            "&& class.days_code like concat('%' , ?, '%')";
    public static final String FIND_GROUPS_BY_TEACHER = FIND_GROUPS + " where teacher.login = ? ";
    public static final String ADD_STUDENT = "INSERT INTO student(id_class, name, surname, email, level, date_start)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_DISCOUNT = "UPDATE student " +
            "SET discount = ? where id_student = ?";
    public static final String UPDATE_PAYMENT = "UPDATE student " +
            "SET isPaid = true where id_student = ?";
    public static final String UPDATE_LEVEL_STUDENT = "UPDATE student " +
            "SET level = ? where id_student = ?";
    public static final String UPDATE_GROUP_STUDENT = "UPDATE student " +
            "SET id_class = ? where id_student = ? ";
    public static final String UPDATE_ATTENDANT = "UPDATE student " +
            "SET count_attendance = ? where id_student = ? ";
    public static final String FIND_STUDENTS_ATTENDANCE = "SELECT id_student, name, surname, count_attendance from student" +
            " where id_class = ?";
    public static final String REMOVE_STUDENT = "DELETE FROM student " + "WHERE id_student = ?";
    public static final String FIND_STUDENTS = "SELECT id_student, id_class, name, surname, email, level, date_start " +
            "from student";
    public static final String FIND_DEBTORS = "SELECT id_student, name, surname, email, id_class " +
            "from student where isPaid = 0 ";
    public static final String FIND_PAYMENT = "SELECT student.discount, course.cost from student " +
            "JOIN class ON student.id_class = class.id_class " +
            "JOIN course ON class.id_course = course.id_course " +
            "WHERE id_student = ? ";

    public static String ADD_RESULT_TEST = "INSERT INTO test(id_student, result_test, date_test)" +
            " VALUES(?, ?, ?)";

    public static String FIND_PROGRESS = "SELECT id_test, date_test, result_test from test where id_student = ?";
    public static String FIND_AVERAGE_SCORE = "SELECT avg(result_test) from test where id_student = ?";


}