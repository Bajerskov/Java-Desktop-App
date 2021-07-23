package view.controller;
import com.sun.tools.javac.Main;
import inventory.Inventory;

import inventory.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.MainController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class CenterController  implements Initializable {
    private Inventory inventory = new Inventory();
    @FXML
    private AnchorPane centerStartPane;

    /**
     * Item menu blocks
     */
    @FXML
    private VBox underMenuBlock1;
    @FXML
    private VBox underMenuBlock2;
    @FXML
    private VBox underMenuBlock3;
    @FXML
    private VBox underMenuBlock4;

    /**
     * Buttons
     */
    @FXML
    private ImageView foodButton;
    @FXML
    private ImageView drinkButton;
    @FXML
    private ImageView snacksButton;
    @FXML
    private ImageView otherButton;

    @FXML
    private Button underMenuButton1;
    @FXML
    private Button underMenuButton2;
    @FXML
    private Button underMenuButton3;
    @FXML
    private Button underMenuButton4;

    private boolean underMenuButtonsVisible;
    private ObservableList<Item> items;
    private MainController mainController;

    String[] foodButtons = new String[]{"Morgenmad", "Burger", "Sandwich", "Salat"};
    String[] snackButtons = new String[]{null, "Snacks før 21", "Snacks efter 21", null};
    String[] drinkButtons = new String[]{"Brus & Juice", "Hot Stuff", "Vin", "Cocktails"};
    String[] otherButtons = new String[]{null, "Gavekort", "Merchandise", null};

    public CenterController(ObservableList<Item> items, MainController mainController) {
        this.mainController = mainController;
        this.items = items;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(MainController.class.getResource("panes/centerStartPane.fxml"));
            loader.load();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public AnchorPane getDefault() {
        return centerStartPane;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadMenuImages();

        foodButton.setOnMouseClicked(event -> {
                wareMenu("foodButton");
        });

        drinkButton.setOnMouseClicked(event -> {
                wareMenu("drinkButton");
        });

        snacksButton.setOnMouseClicked(event -> {
                wareMenu("snacksButton");
        });

        otherButton.setOnMouseClicked(event -> {
                wareMenu("otherButton");

        });

        EventHandler<ActionEvent> undermenuevent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                underMenuPress(event);
            }
        };

        underMenuButton1.setOnAction(undermenuevent);
        underMenuButton2.setOnAction(undermenuevent);
        underMenuButton3.setOnAction(undermenuevent);
        underMenuButton4.setOnAction(undermenuevent);
    }

    private void makeButtons(String[] buttons) {
        System.out.println("buttons:" );
        for (String button : buttons) {
            System.out.print(button+", ");
        }

        if (buttons[0] != null) {
            underMenuButton1.setVisible(true);
            underMenuButton1.setText(buttons[0]);
        } else
            underMenuButton1.setVisible(false);

        if (buttons[1] != null) {
            underMenuButton2.setVisible(true);
            underMenuButton2.setText(buttons[1]);
        } else
            underMenuButton2.setVisible(false);

        if (buttons[2] != null) {
            underMenuButton3.setVisible(true);
            underMenuButton3.setText(buttons[2]);
        } else
            underMenuButton3.setVisible(false);

        if (buttons[3] != null) {
            underMenuButton4.setVisible(true);
            underMenuButton4.setText(buttons[3]);
        } else
            underMenuButton4.setVisible(false);

        showItems();
    }

    private void showItems() {
        System.out.println("show items");
        underMenuBlock1.setVisible(true);
        underMenuBlock2.setVisible(true);
        underMenuBlock3.setVisible(true);
        underMenuBlock4.setVisible(true);
    }

    private void refreshButtons(){
        underMenuButton1.setDisable(false);
        underMenuButton1.setOpacity(1);

        underMenuButton2.setDisable(false);
        underMenuButton2.setOpacity(1);

        underMenuButton3.setDisable(false);
        underMenuButton3.setOpacity(1);

        underMenuButton4.setDisable(false);
        underMenuButton4.setOpacity(1);
    }

        //Method to change submenu items
        public void wareMenu(String buttonID){
            //clear undermenu
            clearUndermenu();
            showItems();
            refreshButtons();

            LocalTime tempTime = mainController.getSystemTime();
            System.out.println(tempTime);

            System.out.println(tempTime.getHour() > 21);

            //if now is after 21 or before 8 o'clock
            if((tempTime.getHour() > 20) || (tempTime.getHour() < 8)){
                System.out.println("its late");
                foodButton.setDisable(true);
                foodButton.setOpacity(0.4);
                System.out.println("but" + buttonID);

                if (buttonID.equals("snacksButton")) {
                    System.out.println("snack button go");
                    underMenuButton2.setOpacity(0.4);
                    underMenuButton2.setDisable(true);
                }

                if (buttonID.equals("foodButton"))
                    return;
            } else{
                foodButton.setDisable(false);
                foodButton.setOpacity(1);

                if (buttonID.equals("snacksButton")) {
                    System.out.println("snack button go");
                    underMenuButton3.setOpacity(0.4);
                    underMenuButton3.setDisable(true);
                }
            }


            if(buttonID.equals("foodButton"))
                makeButtons(foodButtons);

            if(buttonID.equals("snacksButton")) {
                makeButtons(snackButtons);
            }


            if(buttonID.equals("drinkButton"))
                makeButtons(drinkButtons);

            if(buttonID.equals("otherButton"))
                makeButtons(otherButtons);

        }

    //Method to detect which undermenu button was clicked.
    //Gets menu items corresponding to the text inside the button.
    private void underMenuPress(ActionEvent event) {
        String action = ((Button)event.getSource()).getText().toLowerCase().replaceAll(" ", "_");
        //use action to get items
        System.out.println("test: " + action);
        if (mainController.getLeftController().getActiveOrder() != null)
            fillItems(inventory.getSubCat(action, items));


    }

    // fill in items on ware
    public void fillItems(List<Button> items) {
        //clear all blocks
        clearUndermenu();

        int counter = 1;
        for (Button item : items) {
            if (counter == 1) {
                underMenuBlock1.getChildren().add(item);
            } else if (counter == 2) {
                underMenuBlock2.getChildren().add(item);
            } else if (counter == 3) {
                underMenuBlock3.getChildren().add(item);
            } else if (counter == 4) {
                underMenuBlock4.getChildren().add(item);
            } else {
                counter = 0;
            }
            counter++;
        }
    }

    public void clearUndermenu() {
        underMenuBlock1.getChildren().clear();
        underMenuBlock2.getChildren().clear();
        underMenuBlock3.getChildren().clear();
        underMenuBlock4.getChildren().clear();
    }

    public void loadMenuImages(){
        File foodIcon = new File("src/Images/foodIcon.png");
        File drinkIcon = new File("src/Images/drinkIcon.png");
        File snacksIcon = new File("src/Images/snacksIcon.png");
        File otherIcon = new File("src/Images/otherIcon.png");

        Image foodImage = new Image(foodIcon.toURI().toString());
        foodButton.setImage(foodImage);
        foodButton.setFitWidth(300);
        foodButton.setFitHeight(248);

        Image drinkImage = new Image(drinkIcon.toURI().toString());
        drinkButton.setImage(drinkImage);
        drinkButton.setFitWidth(300);
        drinkButton.setFitHeight(248);

        Image snacksImage = new Image(snacksIcon.toURI().toString());
        snacksButton.setImage(snacksImage);
        snacksButton.setFitWidth(300);
        snacksButton.setFitHeight(248);

        Image otherImage = new Image(otherIcon.toURI().toString());
        otherButton.setImage(otherImage);
        otherButton.setFitWidth(300);
        otherButton.setFitHeight(248);
    }

}
