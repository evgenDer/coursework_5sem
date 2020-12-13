

import database.impl.Database;
import database.impl.DatabaseUser;
import model.*;
import util.Calculation;
import util.EmailSender;
import util.Report;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class ThreadClientHandler implements Runnable {

    private static Socket clientSocket;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public ThreadClientHandler(Socket client) {
        ThreadClientHandler.clientSocket = client;
    }

    @Override
    public void run() {

        try {
            sois = new ObjectInputStream(clientSocket.getInputStream());
            soos = new ObjectOutputStream(clientSocket.getOutputStream());
            new Server(clientSocket);

            while (!clientSocket.isClosed()) {
                String choice = sois.readObject().toString();
                Database database = new Database();
                System.out.println(LocalTime.now() + " Запрос: " + choice);
                switch (choice) {
                    case "checkLoginAndPassword": {
                        String login = sois.readObject().toString();
                        String password = sois.readObject().toString();
                        User user = database.getUsers().findByLoginAndPassword(login, password);
                        if (user == null) {
                            soos.writeObject(false);
                        } else {
                            soos.writeObject(true);
                            soos.writeObject(user.getAdmin());
                        }
                        DatabaseUser.getInstance().findByLoginAndPassword(login, password);
                    }
                    break;
                    case "addUser": {
                        User user = (User) sois.readObject();
                        if (database.getUsers().isLoginExist(user.getLogin())) {
                            soos.writeObject(false);
                        } else {
                            soos.writeObject(true);
                            database.getUsers().add(user);
                        }
                    }
                    break;
                    case "removeUser": {
                        String login = sois.readObject().toString();
                        database.getUsers().remove(login);
                    }
                    break;
                    case "findAllUsers": {
                        List<User> users = database.getUsers().findAll();
                        soos.writeObject(users.size());
                        for (User user : users) {
                            soos.writeObject(user);
                        }
                    }
                    break;
                    case "addTeacher": {
                        Teacher teacher = (Teacher) sois.readObject();
                        if (database.getUsers().isLoginExist(teacher.getUser().getLogin())) {
                            soos.writeObject(true);
                            database.getTeachers().add(teacher);
                        } else {
                            soos.writeObject(false);
                        }
                    }
                    break;
                    case "findAllTeachers": {
                        List<Teacher> teachers = database.getTeachers().findAll();
                        soos.writeObject(teachers.size());
                        for (Teacher teacher : teachers) {
                            soos.writeObject(teacher);
                        }
                    }
                    break;
                    case "addCourse": {
                        Course course = (Course) sois.readObject();
                        database.getCourses().add(course);
                    }
                    break;
                    case "hasCorrectId": {
                        int receivedId = (Integer) sois.readObject();
                        if (database.getCourses().hasCorrectId(receivedId)) {
                            soos.writeObject(true);
                        } else {
                            soos.writeObject(false);
                        }
                    }
                    break;
                    case "updateCourseName": {
                        int id = (Integer) sois.readObject();
                        String name = sois.readObject().toString();
                        database.getCourses().updateByName(name, id);
                    }
                    break;
                    case "updateCourseCost": {
                        int id = (Integer) sois.readObject();
                        Double cost = (Double) sois.readObject();
                        database.getCourses().updateByCost(cost, id);
                    }
                    break;
                    case "findAllCourses": {
                        List<Course> courses = database.getCourses().findAll();
                        soos.writeObject(courses.size());
                        for (Course course : courses) {
                            soos.writeObject(course);
                        }
                    }
                    break;
                    case "findCoursesByLevel": {
                        String level = sois.readObject().toString();
                        List<Course> courses = database.getCourses().findByLevel(level);
                        soos.writeObject(courses.size());
                        for (Course course : courses) {
                            soos.writeObject(course);
                        }
                    }
                    break;
                    case "addStudent": {
                        Student student = (Student) sois.readObject();
                        database.getStudents().add(student);
                    }
                    break;
                    case "updateStudentGroup": {
                        int idStudent = (Integer) sois.readObject();
                        int idGroup = (Integer) sois.readObject();
                        database.getStudents().updateByGroupId(idGroup, idStudent);
                    }
                    break;
                    case "updateStudentLevel": {
                        int idStudent = (Integer) sois.readObject();
                        String level = sois.readObject().toString();
                        database.getStudents().updateByLevel(level, idStudent);
                    }
                    break;
                    case "updateStudentAttendance": {
                        Student student = (Student) sois.readObject();
                        database.getStudents().updateAttendant(student.getCountAttendant(), student.getIdStudent());
                    }
                    break;
                    case "removeStudent": {
                        int idStudent = (Integer) sois.readObject();
                        String feedback = sois.readObject().toString();
                        Report reportFeedbackDeleteGroup = new Report(feedback);
                        reportFeedbackDeleteGroup.writeToFile("src/main/resources/reports/feedbackStudents.");
                        database.getStudents().remove(idStudent);
                    }
                    break;
                    case "findProgress": {
                        int idStudent = (Integer) sois.readObject();
                        Student student = database.getTest().findAll(idStudent);
                        student.setAverageScore(database.getTest().findAverageScore(idStudent));
                        soos.writeObject(student);
                    }
                    break;
                    case "findAllStudents": {
                        List<Student> students = database.getStudents().findAll();
                        soos.writeObject(students.size());
                        for (Student student : students) {
                            soos.writeObject(student);
                        }
                    }
                    break;
                    case "findDebtors": {
                        List<Student> debtors = database.getStudents().findDebtors();
                        soos.writeObject(debtors.size());
                        for (Student student : debtors) {
                            soos.writeObject(student);
                        }
                    }
                    break;
                    case "addGroup": {
                        Group group = (Group) sois.readObject();
                        database.getGroups().add(group);
                    }
                    break;
                    case "updateGroupByTeacher": {
                        int idGroup = (Integer) sois.readObject();
                        int idTeacher = (Integer) sois.readObject();
                        database.getGroups().updateByTeacherId(idTeacher, idGroup);
                    }
                    break;
                    case "updateGroupByCourse": {
                        int idGroup = (Integer) sois.readObject();
                        int idCourse = (Integer) sois.readObject();
                        database.getGroups().updateByCourseId(idCourse, idGroup);
                    }
                    case "removeGroup": {
                        int idGroup = (Integer) sois.readObject();
                        String feedback = sois.readObject().toString();
                        Report reportFeedbackDeleteGroup = new Report(feedback);
                        reportFeedbackDeleteGroup.writeToFile("src/main/resources/reports/feedbackDeleteGroups.");
                        database.getGroups().remove(idGroup);
                    }
                    break;
                    case "calculateResultTest": {
                        double correctAnswers = Double.parseDouble(sois.readObject().toString());
                        double errorAnswers = Double.parseDouble(sois.readObject().toString());
                        double resultTest = Math.floor(correctAnswers / (errorAnswers + correctAnswers) * 100);
                        soos.writeObject(resultTest);
                    }
                    break;
                    case "findAllGroups": {
                        List<Group> groups = database.getGroups().findAll();
                        soos.writeObject(groups.size());
                        for (Group group : groups) {
                            soos.writeObject(group);
                        }
                    }
                    break;
                    case "findStudentAttendances": {
                        int idClass = (Integer) sois.readObject();
                        List<Student> students = database.getStudents().findAttendances(idClass);
                        soos.writeObject(students);
                    }
                    break;
                    case "findPayment": {
                        int idStudent = (Integer) sois.readObject();
                        Payment payment = database.getStudents().findPayment(idStudent);
                        soos.writeObject(payment);
                    }
                    break;
                    case "payStudent": {
                        int idStudent = (Integer) sois.readObject();
                        database.getStudents().updatePayment(idStudent);
                    }
                    break;
                    case "calculateDiscount": {
                        int idStudent = (Integer) sois.readObject();
                        Discount discount = (Discount) sois.readObject();
                        int resultDiscount = Calculation.calculateDiscount(discount);
                        soos.writeObject(resultDiscount);
                        database.getStudents().updateDiscount(resultDiscount, idStudent);
                    }
                    break;
                    case "addResultTest": {
                        int idStudent = (Integer) sois.readObject();
                        Test test = (Test) sois.readObject();
                        database.getTest().add(test, idStudent);
                    }
                    break;
                    case "sendPaymentReminder": {
                        String email = sois.readObject().toString();
                        System.out.println(email);
                        EmailSender emailSender = new EmailSender();
                        emailSender.sendPaymentReminder(email);
                    }
                    break;
                    case "findAllGroupTeacher": {
                        String loginTeacher = sois.readObject().toString();
                        List<Group> groups = database.getGroups().findAllGroupTeacher(loginTeacher);
                        soos.writeObject(groups.size());
                        for (Group group : groups) {
                            soos.writeObject(group);
                        }
                    }

                    break;
                    case "findTeacherSchedule": {
                        String loginTeacher = sois.readObject().toString();
                        Calendar calendar = Calendar.getInstance();
                        int dayTodayCode = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                        soos.writeObject(database.getGroups().findGroupByWeekdays(loginTeacher, dayTodayCode));
                    }
                    break;
                    case "exit": {
                        soos.writeObject("OK");
                        soos.close();
                        sois.close();
                        System.out.println("\nClient disconnected");
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("\nClient disconnected.");
            System.out.println("------------------------------");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
