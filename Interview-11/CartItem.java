import java.util.*;

public class CartItem {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public CartItem(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " (x" + quantity + ") - $" + getTotalPrice();
    }
}

class ShoppingCart {
    private List<CartItem> cart = new ArrayList<>();

    // Add item
    public void addItem(int id, String name, double price, int quantity) {
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getId() == id)
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.add(new CartItem(id, name, price, quantity));
        }
    }

    // remove
    public void removeItem(int id, int quantity) {
        Optional<CartItem> itemOpt = cart.stream()
                .filter(item -> item.getId() == id)
                .findFirst();

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            int currentQty = item.getQuantity();

            if (quantity > currentQty) {
                System.out.println("Error: Cannot remove " + quantity +
                        " items. Only " + currentQty + " available.");
            } else if (quantity == currentQty) {
                cart.removeIf(p -> p.getId() == id);
            } else {
                item.setQuantity(currentQty - quantity);
            }
        } else {
            System.out.println("Item with ID " + id + " not found.");
        }
    }

    // Update item
    public void updateItem(int id, String newName, double newPrice, int newQuantity) {
        cart.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .ifPresentOrElse(item -> {
                    item.setQuantity(newQuantity);
                    item.setPrice(newPrice);
                    if (newName != null && !newName.trim().isEmpty()) {
                        item.setName(newName);
                    }
                    System.out.println("Item with ID " + id + " updated.");
                }, () -> System.out.println("Item with ID " + id + " not found."));
    }

    public double calculateTotalPrice() {
        return cart.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Items in your cart:");
        cart.forEach(item -> System.out.println("- " + item));
        System.out.println("Total: $" + calculateTotalPrice());
    }
}
