// Main.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Contact Management ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Delete Contact");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    deleteContact();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO contacts (name, phone) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.executeUpdate();
            System.out.println("Contact added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewContacts() {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contacts");
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nContacts:");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Phone: %s%n", rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteContact() {
        System.out.print("Enter ID of contact to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM contacts WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Contact deleted.");
            } else {
                System.out.println("Contact not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}