package inventory;

import file.FileInventory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import view.MainController;

import java.util.*;

public class Inventory {

    Set<Item> returnItems = new FileInventory().getItems();

    public Inventory() {
    }

    public List<Button> getSubCat(String subCat, ObservableList<Item> items) {
        List<Button> buttons = new ArrayList<>();
        returnItems.forEach((v)-> {
            if(v.getSubCategory().equals(subCat)) {
                Button btn = new Button(v.getName());
                btn.setPrefWidth(300);
                btn.setPrefHeight(50);
                btn.setFont(new Font("Arial", 20));

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        MainController.addToTransaction(v,items);
                    }
                });

                buttons.add(btn);
            }
        });

        return buttons;
    }
}
