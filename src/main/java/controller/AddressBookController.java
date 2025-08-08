package controller;

import model.Contact;
import view.AddressBookView;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AddressBookController {
    private final List<Contact> contacts = new ArrayList<>();
    private final AddressBookView view = new AddressBookView();

    public void startApp() {
        boolean running = true;

        while (running) {
            view.displayMenu();
            String choice = view.getInput("Enter your choice: ");

            switch (choice) {
            case "1":
                addContact();
                break;
            case "2":
                viewContacts();
                break;
            case "3":
                updateContact();
                break;
            case "4":
                deleteContact();
                break;
            case "5":
                searchContact();
                break;
            case "6":
                viewContactsByTag();
                break;
            case "7":
                showUpcomingBirthdays();
                break;
            case "8":
                exportContactsToFile();
                break;
            case "9":
                running = false;
                view.showMessage("Exiting Address Book. Thank you!");
                break;
            default:
                view.showMessage("Invalid choice. Try again.");
                break;
        }

        }

        view.close();
    }

    private void addContact() {
        String name = view.getInput("Enter name: ");
        String phone = view.getInput("Enter phone: ");
        String email = view.getInput("Enter email: ");
        String tag = view.getInput("Enter tag (e.g., friend, work): ");
        String birthdayStr = view.getInput("Enter birthday (yyyy-mm-dd) or leave blank: ");

        LocalDate birthday = null;
        if (!birthdayStr.isEmpty()) {
            try {
                birthday = LocalDate.parse(birthdayStr);
            } catch (DateTimeParseException e) {
                view.showMessage("Invalid date format. Birthday set as N/A.");
            }
        }

        contacts.add(new Contact(name, phone, email, tag, birthday));
        view.showMessage("Contact added successfully!");
    }

    private void viewContacts() {
        if (contacts.isEmpty()) {
            view.showMessage("No contacts found.");
        } else {
            contacts.forEach(c -> view.showMessage(c.toString()));
        }
    }

    private void updateContact() {
        String name = view.getInput("Enter contact name to update: ");
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                String newPhone = view.getInput("Enter new phone: ");
                String newEmail = view.getInput("Enter new email: ");
                String newTag = view.getInput("Enter new tag: ");
                String newBirthdayStr = view.getInput("Enter new birthday (yyyy-mm-dd): ");
                LocalDate birthday = null;
                try {
                    birthday = LocalDate.parse(newBirthdayStr);
                } catch (Exception e) {
                    view.showMessage("Invalid date, keeping old birthday.");
                }
                c.setPhone(newPhone);
                c.setEmail(newEmail);
                c.setTag(newTag);
                if (birthday != null) c.setBirthday(birthday);
                view.showMessage("Contact updated.");
                return;
            }
        }
        view.showMessage("Contact not found.");
    }

    private void deleteContact() {
        String name = view.getInput("Enter contact name to delete: ");
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equalsIgnoreCase(name)) {
                iterator.remove();
                view.showMessage("Contact deleted.");
                return;
            }
        }
        view.showMessage("Contact not found.");
    }

    private void searchContact() {
        String keyword = view.getInput("Enter keyword (name/email/phone): ");
        boolean found = false;
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(keyword) ||
                c.getPhone().equalsIgnoreCase(keyword) ||
                c.getEmail().equalsIgnoreCase(keyword)) {
                view.showMessage(c.toString());
                found = true;
            }
        }
        if (!found) {
            view.showMessage("No contact found.");
        }
    }

    private void viewContactsByTag() {
        String tag = view.getInput("Enter tag to filter by: ");
        boolean found = false;
        for (Contact c : contacts) {
            if (c.getTag().equalsIgnoreCase(tag)) {
                view.showMessage(c.toString());
                found = true;
            }
        }
        if (!found) {
            view.showMessage("No contacts found with that tag.");
        }
    }

    private void showUpcomingBirthdays() {
        LocalDate now = LocalDate.now();
        LocalDate threshold = now.plusDays(30);
        boolean found = false;

        for (Contact c : contacts) {
            LocalDate bd = c.getBirthday();
            if (bd != null && bd.getMonth() == threshold.getMonth()
                && bd.getDayOfMonth() >= now.getDayOfMonth()
                && bd.getDayOfMonth() <= threshold.getDayOfMonth()) {
                view.showMessage("Upcoming birthday: " + c.getName() + " on " + bd);
                found = true;
            }
        }

        if (!found) {
            view.showMessage("No upcoming birthdays in next 30 days.");
        }
    }

    private void exportContactsToFile() {
        try (FileWriter writer = new FileWriter("contacts_export.txt")) {
            for (Contact c : contacts) {
                writer.write(c.toCSV() + "\n");
            }
            view.showMessage("Contacts exported to contacts_export.txt");
        } catch (IOException e) {
            view.showMessage("Error exporting contacts.");
        }
    }
}
