package chat.ui;

import java.util.List;
import java.util.Scanner;
import chat.domain.Message;
import chat.domain.User;
import chat.domain.UserRole;
import chat.service.ChatService;

public class ConsoleMenu {
    private final ChatService chatService;
    private final Scanner scanner;

    // dependency injection of ChatService and Scanner for better testability
    public ConsoleMenu(ChatService chatService) {
        this.chatService = chatService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createUserFlow();
                    break;
                case 2:
                    sendMessageFlow();
                    break;
                case 3:
                    viewMessagesFlow();
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

            System.out.println();
        }
    }

    private void showMenu() {
        System.out.println("1. Create User");
        System.out.println("2. Send Message");
        System.out.println("3. View Messages");
        System.out.println("4. Exit");
    }

    private void createUserFlow() {
        String name = readNonEmptyLine("Enter user name: ");
        int typeChoice = readInt("Choose type (1 = Regular, 2 = Admin): ");
        UserRole role = typeChoice == 2 ? UserRole.ADMIN : UserRole.REGULAR;

        User user = chatService.createUser(name, role);
        System.out.println("User created: " + user);
    }

    private void sendMessageFlow() {
        if (chatService.getAllUsers().size() < 2) {
            System.out.println("Need at least 2 users to send messages.");
            return;
        }

        User sender = chooseUser("Choose sender by id: ");
        if (sender == null) {
            return;
        }

        if (sender.canBroadcast()) {
            int mode = readInt("1. Normal Message, 2. Broadcast to all users: ");
            if (mode == 2) {
                String content = readNonEmptyLine("Enter message content: ");
                int sentCount = chatService.broadcast(sender, content);

                if (sentCount == 0) {
                    System.out.println("No other users to receive broadcast.");
                } else {
                    System.out.println("Broadcast sent to " + sentCount + " users.");
                }
                return;
            }
        }

        User receiver = chooseUser("Choose receiver by id: ");
        if (receiver == null) {
            return;
        }

        String content = readNonEmptyLine("Enter message content: ");
        boolean sent = chatService.sendMessage(sender, receiver, content);

        if (sent) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Failed to send message.");
        }
    }

    private void viewMessagesFlow() {
        if (chatService.getAllUsers().isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        User user = chooseUser("Choose user by id: ");
        if (user == null) {
            return;
        }

        List<Message> received = chatService.getReceivedMessages(user);
        System.out.println(user.getName() + " received:");

        if (received.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }

        for (Message message : received) {
            System.out.println(message);
        }
    }

    private User chooseUser(String prompt) {
        List<User> users = chatService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return null;
        }

        printUsers(users);
        int id = readInt(prompt);
        User user = chatService.getUserById(id);

        if (user == null) {
            System.out.println("User not found.");
        }

        return user;
    }

    private void printUsers(List<User> users) {
        System.out.println("Available users:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }
}
