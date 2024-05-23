import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class User {
    String username;
    String password;
    String fullName;

    User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    abstract void displayMenu();
    abstract void processMenuChoice(String choice, List<Event> events, List<User> users);
}

class Admin extends User {
    Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    void displayMenu() {
        System.out.println("1. Register Organizer");
        System.out.println("2. Register Speaker");
        System.out.println("3. Logout");
    }

    @Override
    void processMenuChoice(String choice, List<Event> events, List<User> users) {
        Scanner scanner = new Scanner(System.in);
        if ("1".equals(choice)) {
            System.out.print("Enter Organizer Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Organizer Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Organizer Full Name: ");
            String fullName = scanner.nextLine();
            users.add(new Organizer(username, password, fullName));
            System.out.println("Organizer registered successfully!");
        } else if ("2".equals(choice)) {
            System.out.print("Enter Speaker Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Speaker Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Speaker Full Name: ");
            String fullName = scanner.nextLine();
            users.add(new Speaker(username, password, fullName));
            System.out.println("Speaker registered successfully!");
        }
    }
}

class Organizer extends User {
    List<Event> events = new ArrayList<>();

    Organizer(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    void createEvent(String title, String date, String location, String description) {
        events.add(new Event(title, date, location, description));
    }

    @Override
    void displayMenu() {
        System.out.println("1. Create Event");
        System.out.println("2. Edit Event");
        System.out.println("3. Manage Tickets");
        System.out.println("4. Register Participant");
        System.out.println("5. Logout");
    }

    @Override
    void processMenuChoice(String choice, List<Event> events, List<User> users) {
        Scanner scanner = new Scanner(System.in);
        if ("1".equals(choice)) {
            System.out.print("Enter Event Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Event Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            System.out.print("Enter Event Location: ");
            String location = scanner.nextLine();
            System.out.print("Enter Event Description: ");
            String description = scanner.nextLine();
            createEvent(title, date, location, description);
            events.add(this.events.get(this.events.size() - 1));
            System.out.println("Event created successfully!");
        } else if ("2".equals(choice)) {
            System.out.println("Select Event to Edit: ");
            for (int i = 0; i < this.events.size(); i++) {
                System.out.println((i + 1) + ". " + this.events.get(i).title);
            }
            int eventChoice = Integer.parseInt(scanner.nextLine()) - 1;
            Event event = this.events.get(eventChoice);
            System.out.print("Edit Event Title (Current: " + event.title + "): ");
            event.title = scanner.nextLine();
            System.out.print("Edit Event Date (Current: " + event.date + "): ");
            event.date = scanner.nextLine();
            System.out.print("Edit Event Location (Current: " + event.location + "): ");
            event.location = scanner.nextLine();
            System.out.print("Edit Event Description (Current: " + event.description + "): ");
            event.description = scanner.nextLine();
            System.out.println("Event updated successfully!");
        } else if ("3".equals(choice)) {
            System.out.println("Managing tickets is not implemented yet.");
        } else if ("4".equals(choice)) {
            System.out.print("Enter Participant Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Participant Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Participant Full Name: ");
            String fullName = scanner.nextLine();
            users.add(new Participant(username, password, fullName));
            System.out.println("Participant registered successfully!");
        }
    }
}

class Participant extends User {
    Participant(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    void displayMenu() {
        System.out.println("1. View Events");
        System.out.println("2. Register for Event");
        System.out.println("3. Logout");
    }

    @Override
    void processMenuChoice(String choice, List<Event> events, List<User> users) {
        Scanner scanner = new Scanner(System.in);
        if ("1".equals(choice)) {
            System.out.println("Available Events:");
            for (Event event : events) {
                System.out.println(event);
            }
        } else if ("2".equals(choice)) {
            System.out.println("Select Event to Register: ");
            for (int i = 0; i < events.size(); i++) {
                System.out.println((i + 1) + ". " + events.get(i).title);
            }
            int eventChoice = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println("Registered for event: " + events.get(eventChoice).title);
        }
    }
}

class Speaker extends User {
    Speaker(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    void displayMenu() {
        System.out.println("1. View Events");
        System.out.println("2. Confirm Participation");
        System.out.println("3. Logout");
    }

    @Override
    void processMenuChoice(String choice, List<Event> events, List<User> users) {
        if ("1".equals(choice)) {
            System.out.println("Available Events:");
            for (Event event : events) {
                System.out.println(event);
            }
        } else if ("2".equals(choice)) {
            System.out.println("Confirming participation is not implemented yet.");
        }
    }
}

class Event {
    String title;
    String date;
    String location;
    String description;

    Event(String title, String date, String location, String description) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin("admin", "password", "Admin User");
        Organizer organizer = new Organizer("organizer", "password", "Event Organizer");
        Participant participant = new Participant("participant", "password", "Event Participant");
        Speaker speaker = new Speaker("speaker", "password", "Event Speaker");

        organizer.createEvent("Tech Conference", "2024-06-01", "Convention Center", "Technology Talks");
        List<Event> events = new ArrayList<>();
        events.add(organizer.events.get(0));

        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(organizer);
        users.add(participant);
        users.add(speaker);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            User currentUser = login(users, scanner);
            if (currentUser == null) continue;

            while (true) {
                currentUser.displayMenu();
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                if ("3".equals(choice) || ("5".equals(choice) && currentUser instanceof Organizer)) {
                    System.out.println("Logging out...");
                    break;
                }

                currentUser.processMenuChoice(choice, events, users);
            }
        }
    }

    static User login(List<User> users, Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Welcome, " + user.fullName + "!");
                return user;
            }
        }

        System.out.println("Invalid login. Try again.");
        return null;
    }
}