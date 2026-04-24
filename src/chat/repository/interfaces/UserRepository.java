package chat.repository.interfaces;

import java.util.List;
import chat.domain.User;

public interface UserRepository {
    User save(User user);

    User findById(int id);

    List<User> findAll();
}