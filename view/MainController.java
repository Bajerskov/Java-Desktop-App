package view;

import inventory.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import totalRaport.TotalReport;
import user.User;
import view.controller.CenterController;
import view.controller.LeftController;
import view.controller.RightController;
import view.controller.TopController;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //MAIN STRUCTURE
  private BorderPane root;
  private TopController topController;
  private LeftController leftController;
  private CenterController centerController;
  private RightController rightController;
  private SceneSwitcher sceneSwitcher;


  //set globally used variables
    private ObservableList<Item> orderItems = FXCollections.observableArrayList ();
    private ObservableList transaction;
    private User activeuser;
    private TotalReport totalReport = new TotalReport();
    private LocalTime systemTime = LocalTime.now();

    public MainController(BorderPane root) {
        this.root = root;
        sceneSwitcher = new SceneSwitcher(root, this);
        //load
        load();
    }

    public TopController getTopController() {
        return topController;
    }

    public LeftController getLeftController() {
        return leftController;
    }

    public CenterController getCenterController() {
        return centerController;
    }

    public RightController getRightController() {
        return rightController;
    }

    public SceneSwitcher getSceneSwitcher() {
        return sceneSwitcher;
    }

    public User getActiveuser() {
        return activeuser;
    }

    public TotalReport getTotalReport() {
        return totalReport;
    }

    public ObservableList<Item> getOrderItems() {
        return orderItems;
    }

    public void setActiveuser(User activeuser) {
        this.activeuser = activeuser;
    }

    public LocalTime getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(LocalTime systemTime) {
        this.systemTime = systemTime;
    }

    private void load() {
        //initialize parts
        topController = new TopController(this);
        rightController = new RightController(orderItems, this);
        centerController = new CenterController(orderItems,this);
        leftController = new LeftController(orderItems, this);

        //Set parts
        setTop();
        setLeft();
        setCenter();
        setRight();
    }

    //Method to add an item to list of items in transaction
    public static void addToTransaction(Item item, ObservableList<Item> items ) {
        items.add(item);
    }


    public void setTop() {
        root.setTop(topController.getDefault());
    }

    public void setLeft() {
        root.setLeft(leftController.getDefault());
    }

    public void setRight() {
        root.setRight(rightController.getDefault());
    }

    public void setCenter() {
        root.setCenter(centerController.getDefault());
    }

    public void paymentStart() {
        root.setCenter(sceneSwitcher.getBetalingStart());
    }

    public void adminsterUserStart(){
        root.setCenter(sceneSwitcher.getAddDeleteUser());
    }

    public void adminsterWareStart(){
        root.setCenter(sceneSwitcher.getAddDeleteWare());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
