package inventory;

public class Food extends Item {

    public Food(String name, int price, int productID, String subCategory) {
        super(name, price, productID, "food", subCategory);
    }
}