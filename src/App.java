import chat.repository.interfaces.MessageRepository;
import chat.repository.interfaces.UserRepository;
import chat.repository.inmemory.InMemoryMessageRepository;
import chat.repository.inmemory.InMemoryUserRepository;
import chat.service.ChatService;
import chat.ui.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        MessageRepository messageRepository = new InMemoryMessageRepository();
        ChatService chatService = new ChatService(userRepository, messageRepository);

        ConsoleMenu menu = new ConsoleMenu(chatService);
        menu.run();
    }
}
