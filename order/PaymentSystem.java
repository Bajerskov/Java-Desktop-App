package order;

public class PaymentSystem {
 
    //simulate payment system
    public void systemAcceptance(double paymentAmount) throws InterruptedException {
        if (paymentAmount > 0) {
            System.out.print("Payment being processed by external system");

            System.out.println("Waiting for response...");
            Thread.sleep(1000);
            System.out.println("Waiting for response...");
            Thread.sleep(1000);
            System.out.println("Waiting for response...");
            Thread.sleep(1000);
            System.out.println("Payment Accepted");
        }
    }
}
