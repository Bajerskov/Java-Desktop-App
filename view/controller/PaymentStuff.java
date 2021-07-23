package view.controller;

import inventory.Item;

import java.util.List;

public class PaymentStuff {

    List<Item> fullTransaction;

    double remainder;

    public PaymentStuff(List<Item> fullTransaction) {
        this.fullTransaction = fullTransaction;
        fullTransaction.forEach(item -> this.remainder += item.getPrice());
    }



    public void pay(double amount) {

        if (remainder == amount) {
          //return to center

            System.out.println("Pay all: remainder: " + remainder + " amount to pay: " + amount);
        }  else if(remainder < amount) {
            //payed more than  owed. Show dialogue with amount to pay back
            System.out.println("payed more: " + remainder + " amount to pay: " + amount + " pay back: " + (amount - remainder));
        } else if(remainder > amount) {
            //didn't pay enough.
            System.out.println("didnt pay all: remainder" + remainder + " amount to pay: " + amount + " new remainder: " + (remainder-amount));

        }
    }


}
