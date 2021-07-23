package file;

import calendar.Event;

import java.util.ArrayList;
import java.util.List;

public class FileEvent extends Files {

    List<Event> events;

    public FileEvent() {
        super("calendar/events.txt");
    }

    public List<Event> getEventList() {
        return events;
    }


    private Event splitLines(String line) {
        if (events == null)
            events = new ArrayList<>();

        String[] string = line.split(" ");
        
        String tableNumber = string[0];
        String costumerName = string[1];
        int guestAmount = Integer.parseInt(string[2]);
        String chosenMenu = string[3];
        String time = string[4];
        String date = string[5];
        String specialNote = string[6];

        events.add(new Event(tableNumber, costumerName, guestAmount, chosenMenu, time, date ,specialNote));

        return null;
    }
}
