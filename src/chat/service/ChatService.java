package chat.service;

import java.util.List;
import chat.domain.AdminUser;
import chat.domain.Message;
import chat.domain.RegularUser;
import chat.domain.User;
import chat.domain.UserRole;
import chat.repository.interfaces.MessageRepository;
import chat.repository.interfaces.UserRepository;

public class ChatService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private int nextUserId;

    public ChatService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.nextUserId = 1;
    }

    public User createUser(String name, UserRole role) {
        User user;
        if (role == UserRole.ADMIN) {
            user = new AdminUser(nextUserId, name);
        } else {
            user = new RegularUser(nextUserId, name);
        }
        nextUserId++;
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public boolean sendMessage(User sender, User receiver, String content) {
        if (sender == null || receiver == null || sender.getId() == receiver.getId()) {
            return false;
        }

        messageRepository.save(new Message(sender, receiver, content));
        return true;
    }

    public int broadcast(User sender, String content) {
        if (sender == null || !sender.canBroadcast()) {
            return -1;
        }

        int count = 0;
        for (User user : userRepository.findAll()) {
            if (user.getId() != sender.getId()) {
                messageRepository.save(new Message(sender, user, content));
                count++;
            }
        }
        return count;
    }

    public List<Message> getReceivedMessages(User receiver) {
        return messageRepository.findReceivedBy(receiver);
    }
}
