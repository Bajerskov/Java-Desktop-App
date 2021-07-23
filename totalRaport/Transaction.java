package totalRaport;

import user.User;

public class Transaction {

    int number;
    int card;
    double amountPayed;
    String time;
    String date;
    int userid;

    public Transaction(int number, int card, double amountPayed, String date, String time, int userid) {
        this.number = number;
        this.card = card;
        this.amountPayed = amountPayed;
        this.date = date;
        this.time = time;
        this.userid = userid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Transaction{");
        sb.append("number=").append(number);
        sb.append(", card=").append(card);
        sb.append(", amountPayed=").append(amountPayed);
        sb.append(", time='").append(time).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", userid=").append(userid);
        sb.append('}');
        return sb.toString();
    }
}
