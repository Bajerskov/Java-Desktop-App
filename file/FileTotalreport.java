package file;

import file.Files;
import totalRaport.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileTotalreport extends Files {

    public FileTotalreport() {
        super("totalRaport/totalRapport.txt");
    }

    List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public Object splitLine(String currentline) {
        if (transactions == null)
            transactions = new ArrayList<>();

        //Split input string by space delimiter.
        String[] splitString = currentline.split(" ");

        //assign String format "Number Card AmountPayed Date(dd/mm/yyyy) Time(HH:MM:SS)" to variables and cast correct types.
        int number = Integer.parseInt(splitString[0]);
        int card = Integer.parseInt(splitString[1]);
        double amountPayed = Double.parseDouble(splitString[2]);
        String time = splitString[3];
        String date = splitString[4];
        int userid = Integer.parseInt(splitString[5]);

        //Return Transaction object
        transactions.add(new Transaction(number, card, amountPayed, time, date, userid));
        return null;
    }


    public void refresh() {
        transactions.clear();
        read();
    }

    public boolean archive() {
        LocalDate currentDate = LocalDate.now().minusDays(1);
        String date = currentDate.getDayOfMonth()+""+currentDate.getMonthValue()+""+currentDate.getYear();

        File newFile = new File(System.getProperty("user.dir") + "/src/totalRaport/archive/"+date+".txt");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(super.getFile()));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(currentLine);
                bufferedWriter.newLine();
            }

            //Empty the file of all text by making a new printwriter and closing it on the file.
            new PrintWriter(super.getFile()).close();

        } catch(IOException e) {
            System.out.println("Error while handling file: " + e.getMessage());
            return false;
        }
        return true;
    }


    public boolean appendLine(String input) {
        try (FileWriter fw = new FileWriter(super.getFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println(input);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
     }
}
