package order;

import inventory.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {

    Set<File> activeOders = new HashSet<>();
    File dir = new File("src/order");

    public Order() {

    }

    public void newOrder(List<Item> items, String name) {
        this.saveOrder(items, name);
    }

    private String itemToFile(Item item) {
        return item.getProductID() + " " + item.getName().replaceAll(" ","_")  + " " + item.getPrice()  + " " + item.getSuperCategory()  + " " + item.getSubCategory();
    }

    //save new order - check if other order exists.
    private void saveOrder(List<Item> items, String ordername) {

        System.out.println("save");
        File[] listOfFiles = dir.listFiles();

        try( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("src/order/"+ordername+".txt")))) {
            for (Item item : items) {
                pw.println(itemToFile(item));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public ObservableList<Item> loadOrder(String order) {
        System.out.println("load");
        for (File file : dir.listFiles()) {
            if (file.getName().equals(order+".txt")) {
                return readFile(file);
            }
        }
        return FXCollections.observableArrayList ();
    }

    public List<Button> loadSavedOrders() {
        List<Button> list = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if(file.getName().contains(".txt")) {
                String name = file.getName().replaceAll(".txt", "");
                Button button = new Button(name);
                button.setId(name);
                list.add(button);
            }

        }
        return list;
    }

    private ObservableList<Item> readFile(File file) {
        ObservableList<Item> items = FXCollections.observableArrayList ();
        try(BufferedReader br = new BufferedReader(new FileReader("src/order/"+file.getName()))) {
            String currentline;
            while ((currentline = br.readLine()) != null) {
                String[] splitLine = currentline.split(" ");
                Item item = lineToItem(splitLine);
                if (item != null)
                items.add(lineToItem(splitLine));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return items;
    }


    private Item lineToItem(String[] lines) {
        int productId = Integer.parseInt(lines[0]);
        String name = lines[1].replaceAll("_"," ");

        int price = Integer.parseInt(lines[2]);
        String category = lines[3];
        String subCategory = lines[4];

        if (category.toLowerCase().equals("food"))
            return (new Food(name, price, productId, subCategory));

        else if(category.toLowerCase().equals("snack"))
            return (new Snacks(name, price, productId, subCategory));

        else if(category.toLowerCase().equals("drink"))
            return (new Drinks(name, price, productId, subCategory));

        else if(category.toLowerCase().equals("misc"))
            return (new Miscellaneous(name, price, productId, subCategory));
        else
            return null;


    }

    public boolean deleteOrder(String order) {
        try {
            File file = new File("src/order/" + order + ".txt");
            if (file.exists()) {
                file.delete();
                return true;
            } else {

                System.out.println("error deleting file.");
                return true;
            }
        } catch ( Exception e) {

            System.out.println(e.getMessage());
            return false;
        }
    }


}
