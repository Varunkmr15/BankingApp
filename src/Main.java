import model.User;
import service.AuthService;
import ui.ConsoleUI;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        ConsoleUI ui = new ConsoleUI();

        while (true) {
            System.out.println("\n=== Banking Application ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    char[] password;
                    if (console != null) {
                        password = console.readPassword("Enter password: ");
                    } else {
                        System.out.print("Enter password: ");
                        password = sc.nextLine().toCharArray();
                    }

                    System.out.print("Full name: ");
                    String fullName = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    User u = AuthService.register(username, password, fullName, email, phone, false);
                    System.out.println("User registered successfully. Your ID: " + u.getId());

                    // Clear sensitive data
                    java.util.Arrays.fill(password, '0');

                } else if (choice.equals("2")) {
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    char[] password;
                    if (console != null) {
                        password = console.readPassword("Enter password: ");
                    } else {
                        System.out.print("Enter password: ");
                        password = sc.nextLine().toCharArray();
                    }

                    User u = AuthService.login(username, password);
                    if (u != null) {
                        System.out.println("Login successful! Welcome " + u.getFullName());
                        ui.userMenu(u); // assuming you make userMenu public
                    } else {
                        System.out.println("Invalid credentials.");
                    }

                    // Clear sensitive data
                    java.util.Arrays.fill(password, '0');

                } else if (choice.equals("3")) {
                    System.out.println("Exiting application. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
