package chat.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import chat.domain.Message;
import chat.domain.User;

public class InMemoryMessageRepository implements chat.repository.interfaces.MessageRepository {
    private final List<Message> messages;

    public InMemoryMessageRepository() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void save(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> findReceivedBy(User user) {
        List<Message> received = new ArrayList<>();
        for (Message message : messages) {
            if (message.getReceiver().getId() == user.getId()) {
                received.add(message);
            }
        }
        return received;
    }
}
