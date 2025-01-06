import java.util.*;

class Item {
    String name;
    double price;
    int stock;

    public Item(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

class OrderItem {
    String itemName;
    int quantity;

    public OrderItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}

class Order {
    List<OrderItem> items;

    public Order(List<OrderItem> items) {
        this.items = items;
    }
}

class OrderService {
    private Map<String, Item> inventory;

    public OrderService(Map<String, Item> inventory) {
        this.inventory = inventory;
    }

    public boolean validateOrder(Order order) {
        for (OrderItem orderItem : order.items) {
            Item item = inventory.get(orderItem.itemName);
            if (item == null || item.stock < orderItem.quantity) {
                System.out.println("Error: " + orderItem.itemName + " is out of stock or insufficient quantity.");
                return false;
            }
        }
        return true;
    }

    public double calculateTotal(Order order) {
        double subtotal = 0;
        for (OrderItem orderItem : order.items) {
            Item item = inventory.get(orderItem.itemName);
            subtotal += item.price * orderItem.quantity;
        }

        
        double discount = subtotal > 100 ? subtotal * 0.05 : 0;

        /double tax = (subtotal - discount) * 0.10;

        return subtotal - discount + tax;
    }

    public void processOrder(Order order) {
        if (!validateOrder(order)) {
            System.out.println("Order validation failed. Please check the items and quantities.");
            return;
        }

        double total = calculateTotal(order);

       
        for (OrderItem orderItem : order.items) {
            Item item = inventory.get(orderItem.itemName);
            item.stock -= orderItem.quantity;
        }

       
        generateReceipt(order, total);

        displayInventory();
    }

    private void generateReceipt(Order order, double total) {
        System.out.println("\n--- Order Receipt ---");
        for (OrderItem orderItem : order.items) {
            Item item = inventory.get(orderItem.itemName);
            System.out.println(item.name + " x " + orderItem.quantity + " @ $" + item.price + " = $" + (item.price * orderItem.quantity));
        }
        System.out.println("Subtotal: $" + (total / 1.10)); // Subtotal before tax
        System.out.println("Discount: $" + (total > 100 ? (total / 1.10) * 0.05 : 0));
        System.out.println("Tax (10%): $" + (total - (total / 1.10)));
        System.out.println("Total: $" + total);
        System.out.println("----------------------\n");
    }

    private void displayInventory() {
        System.out.println("--- Updated Inventory ---");
        for (Item item : inventory.values()) {
            System.out.println(item.name + " - Stock: " + item.stock);
        }
        System.out.println("-------------------------\n");
    }
}

public class OrderProcessingSystem {
    public static void main(String[] args) {
        
        Map<String, Item> inventory = new HashMap<>();
        inventory.put("Apple", new Item("Apple", 150, 10));
        inventory.put("Banana", new Item("Banana", 50, 20));
        inventory.put("Orange", new Item("Orange", 10, 15));
        inventory.put("Mango", new Item("Mango", 20, 5));

        
        OrderService orderService = new OrderService(inventory);

        
        Scanner scanner = new Scanner(System.in);
        List<OrderItem> orderItems = new ArrayList<>();

        while (true) {
            System.out.println("Available Products:");
            for (Item item : inventory.values()) {
                System.out.println(item.name + " - Price: $" + item.price + " - Stock: " + item.stock);
            }

            System.out.print("Enter item name (or 'done' to finish): ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            if (!inventory.containsKey(itemName)) {
                System.out.println("Error: Item not found in inventory. Please try again.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            orderItems.add(new OrderItem(itemName, quantity));
        }

        Order order = new Order(orderItems);

        orderService.processOrder(order);

        scanner.close();
    }
}
