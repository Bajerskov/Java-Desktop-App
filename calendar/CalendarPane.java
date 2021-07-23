package calendar;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
/**
 * Create an anchor pane that can store additional data.
 */
public class CalendarPane extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private Event[] events;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * children of the anchor pane
     */
    public CalendarPane( Node... children) {
        super(children);

        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> onClick(children.id));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    private void onClick(int id){
        System.out.println("This pane's id is: " + id);

    }
}
