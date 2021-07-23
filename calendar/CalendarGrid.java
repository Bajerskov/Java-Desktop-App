package calendar;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CalendarGrid {

    private ArrayList<CalendarPane> allCalendarHours = new ArrayList<>(65); //7 dage * 9 klokkeslet

    public ArrayList<CalendarPane> getAllCalendarHours() {
        return allCalendarHours;
    }

    public CalendarGrid() {
    }

    public GridPane mainGrid() {
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        calendar.setStyle("-fx-background-color: #16c012;");
        calendar.setStyle("-webkit-fx-float: left;-moz-fx-float: left;-ms-fx-float: left;-o-fx-float: left;-khtml-fx-float: left;fx-float: left;");


        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                CalendarPane cp = new CalendarPane();
                cp.setPrefSize(200,200);
                calendar.add(cp,j,i);
                allCalendarHours.add(cp);
            }
        }

        return calendar;
    }

    public GridPane dayLabels() {
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Mandag"), new Text("Tirsdag"),
                new Text("Onsdag"), new Text("Torsdag"), new Text("Fredag"),
                new Text("Lørdag"),  new Text("Søndag")};

        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        dayLabels.setStyle("-fx-background-color: #40c0bb; -fx-content-display: inline");
        dayLabels.setGridLinesVisible(true);

        int col = 1;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        return dayLabels;
    }

    public GridPane hourLabels() {

        //Hours of day labels
        Text[] hournames = new Text[]{ new Text("08:00"),new Text("10:00"),new Text("12:00"),new Text("14:00"),
                new Text("16:00"),new Text("18:00"),new Text("20:00"), new Text("22:00"), new Text("24:00")};
        GridPane hourLabels = new GridPane();
        hourLabels.setStyle("-fx-background-color: #c02d1a; -webkit-fx-float: left;-moz-fx-float: left;-ms-fx-float: left;-o-fx-float: left;-khtml-fx-float: left;fx-float: left;");
        hourLabels.setPrefWidth(50);
        hourLabels.setPrefHeight(400);
        int row = 1;
        for (Text txt : hournames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(50, 50);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            hourLabels.add(ap,0, row++);
        }

        return hourLabels;
    }

    public GridPane finalCalendar(HBox titleBar) {

        // Create the calendar view
        GridPane gp = new GridPane();
        gp.setPrefWidth(600);
        gp.gridLinesVisibleProperty();
        gp.setPrefHeight(400);
        gp.add(titleBar,1 , 0);
        gp.add(this.dayLabels(), 1,1);
        gp.add(this.hourLabels(), 0, 2);
        gp.add(this.mainGrid(), 1, 2);

        return gp;
    }
}
