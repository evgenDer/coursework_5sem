package controllers.user;

import client.Client;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.*;
import com.calendarfx.view.page.DayPage;
import controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import model.Group;
import model.Teacher;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class ControllerSchedule extends Controller {

    @FXML
    private AnchorPane paneSchedule;

    @FXML
    void initialize() {
        super.initUser();
        CalendarView schedule = new CalendarView();
        Client.getInstance().sendMessage("findTeacherSchedule");
        Client.getInstance().sendMessage(Client.getInstance().getLogin());
        Teacher teacher = (Teacher) Client.getInstance().readObject();
        CalendarSource calendarSource = new CalendarSource();
        System.out.println(Client.getInstance().getLogin());

        for (int i = 0; i < teacher.getListGroups().size(); i++) {
            Group group = teacher.getListGroups().get(i);
            Calendar lessonCalendar = new Calendar(group.getLevel());
            lessonCalendar.setReadOnly(true);
            StringBuffer lessonName = new StringBuffer(group.getCourse().getName() + "\n");
            lessonName.append("Уровень " + group.getLevel() + "\n");
            lessonName.append("Занятие в группе №" + group.getIdGroup() + "\n");
            Entry<String> lessonEntry = new Entry<>(lessonName.toString());
            lessonEntry.setInterval(LocalDate.now());
            lessonEntry.changeStartDate(LocalDate.now());
            lessonEntry.changeEndDate(LocalDate.now());
            lessonEntry.changeStartTime(group.getStartLesson().toLocalTime());
            lessonEntry.changeEndTime(group.getStartLesson().toLocalTime().plus(1, ChronoUnit.HOURS));
            lessonCalendar.addEntry(lessonEntry);
            calendarSource.getCalendars().add(lessonCalendar);
        }
        schedule.setShowToolBar(false);

        schedule.getDayPage().setPrefSize(650, 585);
        schedule.getCalendarSources().setAll(calendarSource);
        schedule.getDayPage().setShowNavigation(false);

        paneSchedule.getChildren().add(schedule);
    }

}
