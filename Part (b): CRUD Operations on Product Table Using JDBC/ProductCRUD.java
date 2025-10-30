import java.sql.*;
import java.util.*;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/shopdb";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false); // Enable transaction control

            int choice;
            do {
                System.out.println("\n=== Product Management ===");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addProduct(con, sc);
                    case 2 -> viewProducts(con);
                    case 3 -> updateProduct(con, sc);
                    case 4 -> deleteProduct(con, sc);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }

    private static void addProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement("INSERT INTO product VALUES (?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);

            ps.executeUpdate();
            con.commit();
            System.out.println("✅ Product added successfully!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void viewProducts(Connection con) {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM product")) {

            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getDouble("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new price: ");
            double price = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement("UPDATE product SET price=? WHERE id=?");
            ps.setDouble(1, price);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            con.commit();
            if (rows > 0)
                System.out.println("✅ Product updated successfully!");
            else
                System.out.println("❌ Product not found!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement("DELETE FROM product WHERE id=?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            con.commit();
            if (rows > 0)
                System.out.println("✅ Product deleted successfully!");
            else
                System.out.println("❌ Product not found!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }
}
