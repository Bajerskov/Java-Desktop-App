package calendar;

import java.util.Calendar;

public class Event implements Comparable<Event>{
    private String tableNumber;
    private String costumerName;
    private int guestAmount;
    private String chosenMenu;
 //   private String reservationTime;
 //   private String reservationDate;
    private String specialNote;

    private Calendar reservation = Calendar.getInstance();


    public Event() {
        //Event("c1", "None", "0", "None", "00:00", "now", "none");
    }

    public Event(String tableNumber, String costumerName, int guestAmount, String chosenMenu, String reservationTime, String reservationDate, String specialNote) {
        this.tableNumber = tableNumber;
        this.costumerName = costumerName;
        this.guestAmount = guestAmount;
        this.chosenMenu = chosenMenu;
        this.specialNote = specialNote;

        this.setReservationDate(reservationDate);
        this.setReservationTime(reservationTime);
        System.out.println(this.reservation.getTime());

    }

    @Override
    public int compareTo(Event o) {
        return reservation.compareTo(o.reservation);
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public int getGuestAmount() {
        return guestAmount;
    }

    public String getChosenMenu() {
        return chosenMenu;
    }

    public Calendar getReservation() {
        return reservation;
    }

    public String getSpecialNote() {
        return specialNote;
    }


    /*
     * Set variables
     */

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setCostumerName(String costumerName) {
        String[] s = costumerName.split("_");
        String name = "";
        for (int i = 0; i < s.length; i++) {
            name = name + " " + s[i];
        }
        this.costumerName = name;
    }

    public void setGuestAmount(int guestAmount) {
        this.guestAmount = guestAmount;
    }

    public void setChosenMenu(String chosenMenu) {
        this.chosenMenu = chosenMenu;
    }

    public void setReservationTime(String reservationTime) {
        String[] time = reservationTime.split(":");
        int hour = Integer.parseInt(time[0]),
            minute = Integer.parseInt(time[1]);

        this.reservation.set(this.reservation.HOUR_OF_DAY, hour);
        this.reservation.set(this.reservation.MINUTE, minute);
        this.reservation.set(this.reservation.SECOND, 00);
    }

    public void setReservationDate(String reservationDate) {
        String[] date = reservationDate.split("/");

        //Assuming Day/month/year format (dd:mm:yyyy)
        int day = Integer.parseInt(date[0]),
            month = Integer.parseInt(date[1]),
            year = Integer.parseInt(date[2]);

        this.reservation.set(year, month, day);
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }
}
