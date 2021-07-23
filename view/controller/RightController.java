package view.controller;

import inventory.Item;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import order.Order;
import user.User;
import user.Users;
import view.MainController;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


public class RightController implements Initializable{

    @FXML
    private VBox rightSidePane;
    @FXML
    private ListView transactionList;
    @FXML
    private Text orderTotalText;
    @FXML
    private Button paymentButton;
    @FXML
    private ChoiceBox<User> choiceBoxUsers;
    @FXML
    private javafx.scene.image.ImageView deleteButton;
    @FXML
    private javafx.scene.image.ImageView userImage;



    private ObservableList<Item> items;
    private Users usercontrol = new Users();
    private MainController mainController;
    private Order orderControl = new Order();
    private List<Item> selectedItems = new ArrayList<>();
    private ObservableList<Item> selectItems = FXCollections.observableArrayList();
    private double amount;
    private List<BooleanProperty> listCheckbox = new ArrayList<>();



    public RightController(ObservableList<Item> items, MainController mainController) {
        this.items = items;
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            //loader.setLocation(MainController.class.getResource("panes/rightSidePane.fxml"));
            loader.setLocation(MainController.class.getResource("panes/rightpane2.fxml"));

            loader.load();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }



    public void clearSelected() {
        listCheckbox.clear();
        selectedItems.clear();
    }

    public ObservableList<Item> getSelectedItems() {
        return selectItems;
    }

    public ListView getTransactionList() {
        return transactionList;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public List<Item> getItemsForPayment() {
        System.out.println("Setting all items to true");
        System.out.println("Checkbox "+listCheckbox.size() + " items: " + items.size());
        listCheckbox.forEach(item -> item.setValue(true));
        selectedItems = items;
        return this.selectedItems;
    }
    public VBox getDefault() {
       return rightSidePane;
    }

    public void updateUserList() {
        choiceBoxUsers.getItems().clear();
        choiceBoxUsers.getItems().add(new User("Vælg Bruger", 0));

        choiceBoxUsers.getItems().addAll(usercontrol.getUsers());
        choiceBoxUsers.setStyle("-fx-font: 24px \"Arial\"");

        choiceBoxUsers.getSelectionModel().selectFirst();
        mainController.setActiveuser(choiceBoxUsers.getSelectionModel().getSelectedItem());

        choiceBoxUsers.setOnAction(event -> mainController.setActiveuser(choiceBoxUsers.getSelectionModel().getSelectedItem()));

    }

    public Users getUserControl(){
        return usercontrol;
    }


    public void removeItems(ObservableList<Item> removeItems) {
        List<Item> testitems = new ArrayList<>();
        testitems.addAll(removeItems);
        testitems.forEach(item -> items.remove(item));
    }

    public boolean remainingItems() {
        return !items.isEmpty();
    }




    private void loadImages() {
        File userIcon = new File("src/Images/userIcon.png");
        File deleteIcon = new File("src/Images/trashIcon.png");

        javafx.scene.image.Image userImg = new javafx.scene.image.Image(userIcon.toURI().toString());
        userImage.setImage(userImg);
        userImage.setFitWidth(260);
        userImage.setFitHeight(185);

        javafx.scene.image.Image trashCan = new Image(deleteIcon.toURI().toString());
        deleteButton.setImage(trashCan);
        deleteButton.setFitWidth(37);
        deleteButton.setFitHeight(35);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        transactionList.setItems(items);

        loadImages();

        transactionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        transactionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println("hello");
                selectItems = transactionList.getSelectionModel().getSelectedItems();
            }
        });

        transactionList.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                System.out.println("Changed");
                selectItems = transactionList.getSelectionModel().getSelectedItems();
                mainController.getSceneSwitcher().paymentChange(selectItems);
            }
        });


        //when items are added to the list change the total. add a listener to listen for changes.
        items.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
                double total = 0;
                for (Object o : transactionList.getItems()) {
                    Item item = (Item)o;
                    total += item.getPrice();
                }
                amount = total;
                orderTotalText.setText("Total: " +total);
            }
        });

        //pressing the payment button will start the payment screen
        paymentButton.setOnAction(event ->  {
            System.out.println("\n STARTING PAYMENT \n");

            if (transactionList.getSelectionModel().getSelectedItems().isEmpty()) {
                System.out.println("Is empty");
                transactionList.getSelectionModel().selectAll();
                selectItems = transactionList.getSelectionModel().getSelectedItems();
            } else {
                System.out.println("is not empty");
                selectItems = transactionList.getSelectionModel().getSelectedItems();
            }

            System.out.println("Selected items "+selectItems);
            mainController.getSceneSwitcher().paymentSetup(selectItems);
            mainController.paymentStart();
        });

        //update list of users.
        updateUserList();

        userImage.setOnMouseClicked(event -> {
            if (!choiceBoxUsers.isShowing()) {
                choiceBoxUsers.show();
            }
        });



        deleteButton.setOnMouseClicked(event -> {
            removeItems(transactionList.getSelectionModel().getSelectedItems());
            transactionList.getSelectionModel().clearSelection();
        });

    }


}
