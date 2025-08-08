package view;

import java.util.Scanner;

public class AddressBookView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("\n--- Address Book Menu ---");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Update Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Search Contact");
        System.out.println("6. View Contacts by Tag");
        System.out.println("7. Show Upcoming Birthdays");
        System.out.println("8. Export Contacts to File");
        System.out.println("9. Exit");
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
