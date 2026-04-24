package chat.repository.interfaces;

import java.util.List;
import chat.domain.Message;
import chat.domain.User;

public interface MessageRepository {
    void save(Message message);

    List<Message> findReceivedBy(User user);
}