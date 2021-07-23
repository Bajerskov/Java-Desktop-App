package totalRaport;

import file.FileTotalreport;
import user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class TotalReport {

    FileTotalreport transactionFile = new FileTotalreport();
    Calendar c = Calendar.getInstance();
    int counter = 0;


    public TotalReport() {
        System.out.println("Test");
        counter = transactionFile.getTransactions().size();
    }

    public void checktime() {
        if (LocalTime.now().getHour() == 5) {
            printTotalRapport();
        }

    }


    public void printTotalRapport() {
        System.out.println("print totalreport");
        transactionFile.refresh();
        transactionFile.getTransactions().forEach((v) -> {
            System.out.println(v.number + " " + v.card + " " + v.amountPayed + " " + v.date + " " + v.time);
        });
    }

    public void addTransaction(User user, double payment, int paymentform) {
        counter++;
        transactionFile.appendLine(output);

        checktime();
    }
}
