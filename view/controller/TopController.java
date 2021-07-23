package view.controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import view.MainController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TopController implements Initializable {
    @FXML
    private HBox topMenuPane;

    @FXML
    private MenuItem administerUsers;
    @FXML
    private MenuItem administerWares;
    @FXML
    private MenuItem printTotalReportMenu;
    @FXML
    private MenuItem setClock21;
    @FXML
    private MenuItem setClock8;

    private MainController mainController;

    public TopController(MainController mainController) {
        this.mainController = mainController;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(MainController.class.getResource("panes/topMenuPane.fxml"));
            loader.load();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        administerUsers.setOnAction(event -> {
            System.out.println("pay");

            //Set center to payment
            System.out.println("FIX: RightController: paymentButton");
            mainController.adminsterUserStart();
        });

        administerUsers.setOnAction(event -> {
            //Set center to user pane
            mainController.adminsterUserStart();
        });

        administerWares.setOnAction(event -> {
            //Set center to ware pane
            mainController.adminsterWareStart();
        });



        printTotalReportMenu.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Print Totalrapport");
            alert.setHeaderText("Print Totalrapport");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                mainController.getTotalReport().printTotalRapport();
            }

        });


        setClock8.setOnAction(event -> {
            mainController.setSystemTime(mainController.getSystemTime().withHour(8));
        });

        setClock21.setOnAction(event -> {
            mainController.setSystemTime(mainController.getSystemTime().withHour(21));
        });
    }


    public HBox getDefault() {
        return topMenuPane;
    }
}
