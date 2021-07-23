package inventory;

import javafx.beans.property.BooleanProperty;

import java.util.Objects;

public abstract class Item {

    private String name;
    private int price;
    private int productID;
    private String superCategory;
    private String subCategory;
    private BooleanProperty checkbox;

    public Item(String name, int price, int productID, String superCategory, String subCategory) {
        this.name = name;
        this.price = price;
        this.productID = productID;
        this.superCategory = superCategory;
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProductID() {
        return productID;
    }

    public String getSuperCategory() {
        return superCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public boolean isCheckbox() {
        return checkbox.get();
    }

    public BooleanProperty checkboxProperty() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox.set(checkbox);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return productID == item.productID;
    }

    @Override
    public int hashCode() {

        return Objects.hash(productID);
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getPrice();
    }
}
