package calendar;

import file.FileEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

public class CalendarView {

    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private int currentWeek;

    List<Event> events = new ArrayList<>();



    private LocalDate currentDate = LocalDate.now();
    private LocalDateTime currentDateTime;

    //new param

    CalendarGrid calendarGrid = new CalendarGrid();

    public CalendarView(YearMonth yearMonth, LocalDateTime when) {
        this.currentYearMonth = yearMonth;
        this.currentDateTime = when;

        //somehow set the day of the week to wednessday.
        //even better figure out how
        while (!currentDate.getDayOfWeek().toString().equals("WEDNESDAY") ) {
            currentDate = currentDate.minusDays(1);
        }

        while(!currentDateTime.getDayOfWeek().toString().equals("WEDNESDAY")) {
            currentDateTime.minusDays(1);
        }



        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();

        Button previousWeek = new Button("Tidligere");
        previousWeek.setOnAction(e -> previousWeek());

        Button nextWeek = new Button("Næste");
        nextWeek.setOnAction(e -> nextWeek());


        HBox titleBar = new HBox(previousWeek, calendarTitle, nextWeek);
        titleBar.setAlignment(Pos.BASELINE_CENTER);

        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, currentDateTime);


        view = new VBox(calendarGrid.finalCalendar(titleBar));
    }


    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */

    public void populateCalendar(YearMonth yearMonth, final LocalDateTime workdate) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }

        while (!currentDateTime.getDayOfWeek().toString().equals("MONDAY") ) {
            currentDateTime = currentDateTime.minusDays(1);
        }

        //Java hack to get week of year.
        //Source: https://stackoverflow.com/questions/26012434/get-week-number-of-localdate-java-8
        TemporalField tf = WeekFields.ISO.weekOfWeekBasedYear();
        this.currentWeek = currentDateTime.get(WeekFields.ISO.weekOfWeekBasedYear());

        getEvents();

        int i = 0,
            j = 0;

        //first day of week
        int firstDay = currentDateTime.getDayOfMonth();

        int[] hours = new int[]{8, 10, 12, 14, 16, 18, 20, 22, 24};

        // Populate the calendar with day numbers
        for (CalendarPane ap : calendarGrid.getAllCalendarHours()) {
            //Remove existing content
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }

            Text txt;
            int thisday = firstDay + i;

            i++;
            if (i%7 == 0) {
                i=0;
                j++;
            }
        }
        // Change the title of the calendar
        calendarTitle.setText("   Uge: " + currentWeek + "," + currentDate.getYear() + "   ");
    }


    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousWeek() {
        this.currentDate = this.currentDate.minusDays(7);
        populateCalendar(currentYearMonth, currentDateTime);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextWeek() {
        this.currentDate = this.currentDate.plusDays(7);
        populateCalendar(currentYearMonth, currentDateTime);
    }

    /**
     * The FX element that the calendar is build inside.
     */
    public VBox getView() {
        return view;
    }

    /**
     * Load up saved events and filter for current week.
     */
    public void getEvents() {
        FileEvent fileEvent = new FileEvent();

        fileEvent.getEventList().forEach((v)-> {
            int week = v.getReservation().get(Calendar.WEEK_OF_YEAR);
            if (week == currentWeek) {
                events.add(v);
            }
        });
    }
}
