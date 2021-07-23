package file;

import inventory.*;

import java.util.HashSet;
import java.util.Set;

public class FileInventory extends Files {

    private Set<Item> itemsList;

    public FileInventory() {
        super("inventory/inventory.txt");

    }

    public Set<Item> getItems() {
        return itemsList;
    }


    @Override
    public Object splitLine(String currentline) {
        //if the itemlist is not instantiated,do it.
        if (itemsList == null)
            itemsList = new HashSet<Item>();
    
        String[] lines = currentline.split(" ");


        //assign and cast the bits to corresponding variables
        int productId = Integer.parseInt(lines[0]);
        String name = lines[1].replaceAll("_", " ");
        int price = Integer.parseInt(lines[2]);
        String category = lines[3];
        String subCategory = lines[4];

        if (category.equals("food"))
            itemsList.add( new Food(name, price, productId, subCategory));

        else if(category.equals("snacks"))
            this.itemsList.add(new Snacks(name, price, productId, subCategory));

        else if(category.equals("drink"))
            this.itemsList.add(new Drinks(name, price, productId, subCategory));

        else if(category.equals("andet"))
            this.itemsList.add(new Miscellaneous(name, price, productId, subCategory));

        return null;
    }
}
