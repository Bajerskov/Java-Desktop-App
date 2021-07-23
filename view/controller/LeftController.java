package view.controller;

import inventory.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import order.Order;
import view.MainController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeftController implements Initializable{

    @FXML
    private VBox leftSidePane;
    @FXML
    private Button addOrderButton;
    @FXML
    private VBox activeOrders;
    @FXML
    private ImageView newOrderView;

    private String activeOrder;
    private Order orderController = new Order();
    private ObservableList<Item> items;
    private MainController mainController;

    public LeftController(ObservableList<Item> items, MainController mainController) {
        this.mainController = mainController;
        this.items = items;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(MainController.class.getResource("panes/leftSidePane.fxml"));
            loader.load();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        loadSavedOrders();
    }

    public String getActiveOrder() {
        return activeOrder;
    }

    public VBox getActiveOrders() {
        return activeOrders;
    }


    private void loadSavedOrders() {
        List<Button> buttons = orderController.loadSavedOrders();
        buttons.forEach(button -> newBtnStyle(button));
        activeOrders.getChildren().addAll(buttons);
    }

    //Method to set the active order.
    //Changes appearance of clicked button and gets saved order from txt file and applies it to listview1
    public void changeActive(Button btn) {
        mainController.setCenter();

        String text = btn.getText();
        System.out.println(text);

        //save old order
        if (activeOrder != null)
            orderController.newOrder(mainController.getRightController().getTransactionList().getItems(), activeOrder);




        mainController.getCenterController().clearUndermenu();

        activeOrders.getChildren().forEach((v)-> setBtnStyle((Button)v));

        setActiveBtnStyle(btn);

        activeOrder = text;
        items.clear();
        mainController.getRightController().clearSelected();
        items.setAll(orderController.loadOrder(text));
    }


    //Method to create a popup dialog to prompt for table number/name
    protected void dialog() {
        TextInputDialog textInput = new TextInputDialog("");
        textInput.setTitle("Text Input Dialog");
        textInput.getDialogPane().setContentText("Nyt bord nr: ");
        textInput.showAndWait()
                .ifPresent(response -> {
                    if (!response.isEmpty()) {
                        newOrderbutton(response.toUpperCase());
                    }
                });
    }

    //Method to apply a certain styling to a button
    public void newBtnStyle(Button btn) {
        btn.setStyle("-fx-background-color: #3c7fb1, linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%)");
        btn.setPrefHeight(90);
        btn.setPrefWidth(260);
        btn.setFont(new Font(39));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeActive(btn);
            }
        });
    }

    //Method to apply a certain styling to a button
    public static void setBtnStyle(Button btn) {
        btn.setStyle("-fx-background-color: #3c7fb1, linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%)");

    }

    //Method to apply an active button styling
    public static void setActiveBtnStyle(Button btn) {
        btn.setStyle("-fx-background-color: #42b13c,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d2fcd4 49%, #bdfdc9 50%, #70f575 100%)");
    }

    //Method to create a new order.
    private void newOrderbutton(String txt) {
        Button btn = new Button(txt);
        newBtnStyle(btn);

        boolean containsbtn = false;
        btn.setId(txt);
        for (Node node : activeOrders.getChildren()) {
            String button = ((Button) node).getText();
            if (txt.equals(button))
                containsbtn = true;

        }
        if (!containsbtn) {
            activeOrders.getChildren().add(btn);

            changeActive(btn);
        } else {
            //Alert clerc about the amount to pay back.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nyt bord");
            alert.setHeaderText("Bord: " + txt + " eksistere allerede");
            alert.showAndWait();
        }
    }

    public void removeOrderBtn() {
        List<Node> nodes = activeOrders.getChildren();
        System.out.println("list of nodes");


        for (Node node : activeOrders.getChildren()) {

            String button = ((Button) node).getText();
            if (button.equals(activeOrder)) {
                activeOrders.getChildren().remove(node);
                break;
            }
        }


        orderController.deleteOrder(activeOrder);

    }


    public VBox getDefault() {
        return leftSidePane;
    }


    private void loadImages() {
        File plusIcon = new File("src/Images/plusIcon.png");

        Image plusImage = new Image(plusIcon.toURI().toString());
        newOrderView.setImage(plusImage);
        newOrderView.setFitWidth(64);
        newOrderView.setFitHeight(64);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadImages();

        addOrderButton.setOnAction(event -> {
            dialog();
        });

    }
}
