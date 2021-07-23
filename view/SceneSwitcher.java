package view;

import com.sun.tools.javac.Main;
import inventory.Food;
import inventory.Item;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import totalRaport.TotalReport;
import user.User;
import user.Users;
import view.controller.PaymentStuff;
import view.controller.RightController;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SceneSwitcher implements Initializable {

    @FXML
    private HBox topMenuPane;
    @FXML
    private AnchorPane kontantbetaling;
    @FXML
    private AnchorPane kreditkortbetaling;
    @FXML
    private BorderPane standardPane;
    @FXML
    private AnchorPane betalingstart;
    @FXML
    private AnchorPane addDeleteUser;
    @FXML
    private AnchorPane addDeleteWare;
    @FXML
    private Button okKontantButton;
    @FXML
    private Button okKreditKortButton;
    @FXML
    private Button okAddUserButton;
    @FXML
    private TextField addUserTextField;
    @FXML
    private Button kreditkortBtn;
    @FXML
    private Button okDeleteUserButton;

    @FXML
    private Text kreditkortSum;

    @FXML
    private Button kreditbetalsum;

    @FXML
    private Button kontantbetalingBtn;

    @FXML
    private Text kontantSum;
    @FXML
    private TextField kreditTextField;

    @FXML
    private Button kontantbetalsum;

    @FXML
    private Button splitBetaling;
    @FXML
    private TextField kontantTextField;
    @FXML
    private ChoiceBox<User> sletBrugerChoiceBox;



    private double totalAmount = 0;
    private int paymentCard = 0000;
    private MainController mainController;
    private Users users;
    private BorderPane root;
    Random random = new Random();
    private ObservableList<Item> itemsToPayFor = FXCollections.observableArrayList();


    public SceneSwitcher(BorderPane root, MainController mainController) {
        this.root = root;
        this.mainController = mainController;

        tryLoad("panes/anchorBetalingsmuligheder.fxml");
        tryLoad("panes/topMenuPane.fxml");
        tryLoad("panes/anchorKontantBetaling.fxml");
        tryLoad("panes/anchorKortbetaling.fxml");
        tryLoad("panes/anchorAddDeleteUser.fxml");
        tryLoad("panes/anchorAddDeleteWare.fxml");
    }

    private void tryLoad(String path) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(SceneSwitcher.class.getResource(path));
            loader.load();
        } catch (IOException e) {
            System.err.println(e);
        }

    }


    public BorderPane getStandardPane() {
        return standardPane;
    }

    public HBox getTopmenu() {
        return topMenuPane;
    }

    public AnchorPane getBetalingStart() {
        return betalingstart;
    }

    public AnchorPane getKontant() {
        System.out.println("GETME: ");
        return kontantbetaling;
    }

    public AnchorPane getAddDeleteUser(){
        updateUserList();
        return addDeleteUser;
    }

    public AnchorPane getAddDeleteWare() {
        return addDeleteWare;
    }

    public AnchorPane getAnchorPane2() {
        return kreditkortbetaling;
    }

    public void updateUserList() {
        sletBrugerChoiceBox.getItems().clear();
        sletBrugerChoiceBox.getItems().add(new User("Vælg Bruger", 0));
        List<User> userlist = mainController.getRightController().getUserControl().getUsers();
        System.out.println(userlist);
        sletBrugerChoiceBox.getItems().addAll(userlist);

        sletBrugerChoiceBox.setOnAction(event -> okDeleteUserButton.setDisable(false));

        sletBrugerChoiceBox.getSelectionModel().selectFirst();

    }


    //////
    //  NEW UPDATED PAYMENT SYSTEM
    /////

    //the selected items to pay for
    private List<Item> currentItems = new ArrayList<>();
    //the remaining sum to be payed
    private double currentRemainder;
    private double currentTotal;

    //when payment button is pressed, set selected items and price
    public void paymentSetup(ObservableList<Item> items) {
        System.out.println("Payment setup \n items: " + items);
        currentRemainder = 0;
        itemsToPayFor = items;
        items.forEach(item -> currentRemainder += item.getPrice());
        currentTotal = currentRemainder;

        //Set owed amount on interface.
        updateGraphics(currentRemainder);

        System.out.println("Items: \n"+itemsToPayFor);

    }

    //when payment has already begun but selected items change.
    public void paymentChange(ObservableList<Item> newitems) {
        System.out.println("change in order");

        System.out.println(currentRemainder);
        itemsToPayFor = newitems;
        currentRemainder = 0;
        System.out.println("new Current remainder: " + currentRemainder);
        itemsToPayFor.forEach(item -> {
            currentRemainder += item.getPrice();
            System.out.println("\t new price:" +currentRemainder);
        });

        updateGraphics(currentRemainder);

    }

    private void updateGraphics(double amount) {
        kreditkortSum.setText("Beløb: " + amount +" DKK");
        kontantSum.setText("Beløb: " + amount +" DKK");
    }


    public void tryPayment(double payedAmount) {

        //add transaction to totalreport.

        mainController.getTotalReport().addTransaction(mainController.getActiveuser(), payedAmount, paymentCard);


        //pay for current transaction
        if (payedAmount==currentRemainder) {
            //if the payed amount is exactly the owed amount
            System.out.println("exact payment");
            //the current transaction is over, test if there are more items to pay for.
            findRemainingTransaction();
        } else if(payedAmount>currentRemainder) {
            //if the payed amount is more than the owed amount

            //Alert clerc about the amount to pay back.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tilbagebetaling");
            alert.setHeaderText("Kunde skal have :" + (payedAmount-currentRemainder) + " DKK tilbage");
            alert.showAndWait();

            //the current transaction is over, test if there are more items to pay for.
            findRemainingTransaction();
        } else if(payedAmount<currentRemainder) {
            //if the payed amount is less than the owed amount.

            //the transaction is now yet over, start over with adjusted remainder.
            currentRemainder = currentRemainder-payedAmount;
            updateGraphics(currentRemainder);
            root.setCenter(betalingstart);
        }

    }

    public void endTransaction() {
        System.out.println("End transaction");
        currentRemainder = 0;
        currentItems.clear();

        System.out.println("Deleting order button");
        //removes the current orderbutton.
        mainController.getLeftController().removeOrderBtn();

        mainController.setCenter();
    }



    public void findRemainingTransaction() {

        System.out.println("Find remaining transactions:");

        //remove the selected items from the current order.
        System.out.println("items to pay for: " +itemsToPayFor);
        mainController.getRightController().removeItems(itemsToPayFor);

        //check if there are any items left in transactionslist.
        if (mainController.getRightController().remainingItems()) {
            //if true-  get the rest of the items from transaction list.
            System.out.println("Found more transactions");
            System.out.println("rest of items: " + mainController.getRightController().getSelectedItems());

            paymentSetup(mainController.getRightController().getSelectedItems());

            root.setCenter(betalingstart);
        } else {
            System.out.println("no more transactions");
            endTransaction();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        okAddUserButton.setOnAction(event ->{
            if(addUserTextField.getText() != null) {
                System.out.println("Adding User: " + addUserTextField.getText());
                mainController.getRightController().getUserControl().insertUser(addUserTextField.getText());
                mainController.getRightController().updateUserList();
                sletBrugerChoiceBox.getItems().clear();
                sletBrugerChoiceBox.getItems().add(new User("Vælg Bruger", 0));
                sletBrugerChoiceBox.getItems().addAll(mainController.getRightController().getUserControl().getUsers());
                sletBrugerChoiceBox.getSelectionModel().selectFirst();
                addUserTextField.clear();
            }
        });

        okDeleteUserButton.setOnAction(event -> {
            User usrdelete = sletBrugerChoiceBox.getSelectionModel().getSelectedItem();
            System.out.println("Delete user " + usrdelete);
            mainController.getRightController().getUserControl().deleteUser(usrdelete);
            okDeleteUserButton.setDisable(true);
            updateUserList();
            mainController.getRightController().updateUserList();
            sletBrugerChoiceBox.getSelectionModel().selectFirst();
        });

        //in betalingstart
        kreditkortBtn.setOnAction(event -> root.setCenter(kreditkortbetaling));

        kontantbetalingBtn.setOnAction(event -> root.setCenter(kontantbetaling));


        //pay complete sum with credit card
        kreditbetalsum.setOnAction(event -> {
            System.out.println("Paying complete sum with credit card");
            if (paymentCard == 0)
                paymentCard = random.nextInt(8998)+1001;

            //Paying the rest of the sum with credit = the remaining sum
            tryPayment(currentRemainder);

        });


        //pay compelte sum with cash
        kontantbetalsum.setOnAction(event -> {
            System.out.println("Paying complete sum with cash");

            //paying the rest of the sum with cash = the remaining sum.
            tryPayment(currentRemainder);
        });


        //Pay part of sum with credit card
        okKreditKortButton.setOnAction(event -> {
            //check if textfield contains anything and then if it is a real number
            try {
                double amount = Double.parseDouble(kreditTextField.getText());
                tryPayment(amount);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        //Pay part of sum with cash
        okKontantButton.setOnAction(event -> {
            //check if textfield contains anything and then if it is a real number
            try {
                double amount = Double.parseDouble(kontantTextField.getText());
                tryPayment(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });




    }


}
